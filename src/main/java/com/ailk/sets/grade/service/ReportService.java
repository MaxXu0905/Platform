package com.ailk.sets.grade.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateInfoTestDao;
import com.ailk.sets.grade.dao.intf.ICandidateReportDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.execute.InvalidDataException;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.report.IReportConfig;
import com.ailk.sets.grade.intf.report.Interview;
import com.ailk.sets.grade.intf.report.Interview.InterviewItem;
import com.ailk.sets.grade.intf.report.InterviewInfo;
import com.ailk.sets.grade.intf.report.OverallItem;
import com.ailk.sets.grade.intf.report.Report;
import com.ailk.sets.grade.intf.report.Summary;
import com.ailk.sets.grade.jdbc.CandidateInfoTest;
import com.ailk.sets.grade.service.intf.IInterviewTemplateService;
import com.ailk.sets.grade.service.intf.IReportService;
import com.ailk.sets.grade.utils.MathUtils;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IReportDao;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;

@Transactional(rollbackFor = Exception.class)
@Service
public class ReportService implements IReportService {

	private static final Logger logger = Logger.getLogger(ReportService.class);

	@Autowired
	private ICandidateReportDao candidateReportDao;

	@Autowired
	private ICandidateTestDao candidateTestDao;

	@Autowired
	private ICandidateInfoTestDao candidateInfoTestDao;

	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;

	@Autowired
	private IInterviewTemplateService interviewTemplateService;

	@Autowired
	private IReportDao reportDao;

	public CandidateReport loadCandidateReport(long testId)
			throws InvalidDataException {
		CandidateReport candidateReport = candidateReportDao.get(testId);
		if (candidateReport == null) {
			String errDesc = "找不到候选人报告数据，testId=" + testId;
			logger.error(errDesc);
			throw new InvalidDataException(BaseResponse.ENOENT, errDesc);
		}

		return candidateReport;
	}

	public CandidateTest loadCandidateTest(long testId)
			throws InvalidDataException {
		CandidateTest candidateTest = candidateTestDao.getEntity(testId);
		if (candidateTest == null) {
			String errDesc = "找不到候选人测试数据，testId=" + testId;
			logger.error(errDesc);
			throw new InvalidDataException(BaseResponse.ENOENT, errDesc);
		}

		return candidateTest;
	}

	@Override
	public Interview getInterview(int employerId, int testType, long testId)
			throws Exception {
		Interview interview = new Interview();

		InterviewInfo interviewInfo = getInterviewInfo(employerId, testType);
		interview.setInterviewInfo(interviewInfo);

		List<InterviewItem> items = getInterviewItems(testId);
		interview.setItems(items);

		return interview;
	}

	@Override
	public InterviewInfo getInterviewInfo(int employerId, int testType)
			throws Exception {
		return interviewTemplateService.load(employerId, testType,
				GradeConst.INTERVIEW_ID);
	}

	@Override
	public List<InterviewItem> getInterviewItems(long testId) throws Exception {
		List<InterviewItem> items = new ArrayList<InterviewItem>();
		List<CandidateInfoTest> candidateInfoTests = candidateInfoTestDao
				.getList(testId);
		for (CandidateInfoTest candidateInfoTest : candidateInfoTests) {
			InterviewItem item = new InterviewItem();

			item.setGroupId(candidateInfoTest.getCandidateInfoTestPK()
					.getGroupId());
			item.setInfoId(candidateInfoTest.getCandidateInfoTestPK()
					.getInfoId());
			item.setValue(candidateInfoTest.getValue());
			item.setRealValue(candidateInfoTest.getRealValue());
			items.add(item);
		}

		return items;
	}

	@Override
	public List<OverallItem> getOverallItems(Report report, long testId)
			throws Exception {
		ReportScoreItem[] scoreItems = new ReportScoreItem[IReportConfig.ITEM_NAMES.length];
		for (int i = 0; i < scoreItems.length; i++)
			scoreItems[i] = new ReportScoreItem();

		getScoresFromReport(report, scoreItems);
		getScoresFromQuestion(report, testId, scoreItems);
		getScoresFromInterview(testId, scoreItems);

		List<OverallItem> items = new ArrayList<OverallItem>();
		for (int i = 0; i < IReportConfig.ITEM_NAMES.length; i++) {
			ReportScoreItem scoreItem = scoreItems[i];

			if (!scoreItem.isExist())
				continue;

			OverallItem item = new OverallItem();
			item.setAnchor(i);
			item.setName(IReportConfig.ITEM_NAMES[i]);
			item.setEditable(scoreItem.isEditable());
			if (scoreItem.isEdited())
				item.setScore(MathUtils.round(scoreItem.getScore(), 1));
			items.add(item);
		}

		return items;
	}

	/**
	 * 获取技术基础得分
	 * 
	 * @param candidateReport
	 *            报告对象
	 * @param scoreItems
	 *            结果数组
	 * @throws Exception
	 */
	private void getScoresFromReport(Report report, ReportScoreItem[] scoreItems)
			throws Exception {
		Summary summary = report.getSummary();
		if (summary == null || summary.getBasicScore() == null)
			return;

		ReportScoreItem scoreItem = scoreItems[IReportConfig.REPORT_COLUMN_BASIC];
		scoreItem.setExist(true);
		scoreItem.setEditable(false);
		scoreItem.setEdited(true);
		scoreItem.setScore(summary.getBasicScore());
	}

	/**
	 * 获取可打分项的得分
	 * 
	 * @param report
	 *            报告
	 * @testId 测试ID
	 * @param scoreItems
	 *            结果数组
	 */
	private void getScoresFromQuestion(Report report, long testId,
			ReportScoreItem[] scoreItems) {
		List<CandidateTestQuestion> candidateTestQuestions = candidateTestQuestionDao
				.getList(testId);
		int[] rows = new int[IReportConfig.ITEM_NAMES.length];
		Map<Long, Integer> reportIndexMap = report.getReportIndexMap();

		for (CandidateTestQuestion candidateTestQuestion : candidateTestQuestions) {
			Integer index = reportIndexMap.get(candidateTestQuestion.getId()
					.getQuestionId());
			if (index == null)
				continue;

			switch (index) {
			case IReportConfig.REPORT_COLUMN_BASIC:
			case IReportConfig.REPORT_COLUMN_QUALITY:
			case IReportConfig.REPORT_COLUMN_UNKNOWN:
				continue;
			}

			ReportScoreItem scoreItem = scoreItems[index];
			scoreItem.setExist(true);
			if (candidateTestQuestion.getGetScore() != null) {
				scoreItem.setEdited(true);
				scoreItem.setScore(scoreItem.getScore()
						+ candidateTestQuestion.getGetScore());
				rows[index]++;
			} else {
				scoreItem.setEditable(true);
			}
		}

		for (int i = 0; i < IReportConfig.ITEM_NAMES.length; i++) {
			if (rows[i] <= 1)
				continue;

			ReportScoreItem scoreItem = scoreItems[i];
			scoreItem.setScore(MathUtils.round(scoreItem.getScore() / rows[i],
					1));
		}
	}

	/**
	 * 获取面试得分
	 * 
	 * @param testId
	 *            测试ID
	 * @param scoreItems
	 *            结果数组
	 * @throws Exception
	 */
	private void getScoresFromInterview(long testId,
			ReportScoreItem[] scoreItems) throws Exception {
		int rows = 0;
		double total = 0.0;
		List<CandidateInfoTest> candidateInfoTests = candidateInfoTestDao
				.getList(testId, GradeConst.INTERVIEW_ITEM);
		for (CandidateInfoTest candidateInfoTest : candidateInfoTests) {
			double realValue = Double.parseDouble(candidateInfoTest
					.getRealValue());

			rows++;
			total += realValue;
		}

		ReportScoreItem scoreItem = scoreItems[IReportConfig.REPORT_COLUMN_QUALITY];
		scoreItem.setExist(true);

		if (rows == 0) {
			scoreItem.setEditable(true);
			scoreItem.setEdited(false);
			scoreItem.setScore(0.0);
		} else {
			scoreItem.setEditable(false);
			scoreItem.setEdited(true);
			scoreItem.setScore(total / rows);
		}
	}

	/**
	 * 获得未推送报告
	 * 
	 * @return
	 */
	@Override
	public List<CandidateReport> getUnNotifiedReport() {
		return reportDao.getList(0, "notified");
	}

	/**
	 * 更新报告
	 */
	@Override
	public void updateReport(CandidateReport candidateReport) throws Exception {
		candidateReportDao.saveOrUpdate(candidateReport);
	}
}
