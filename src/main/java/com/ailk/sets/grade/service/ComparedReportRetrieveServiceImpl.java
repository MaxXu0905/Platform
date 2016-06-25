package com.ailk.sets.grade.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import com.ailk.sets.grade.intf.report.GetComparedReportsResponse;
import com.ailk.sets.grade.intf.report.GetComparedReportsResponse.ComparedItem;
import com.ailk.sets.grade.intf.report.GetComparedReportsResponse.RowItem;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.intf.report.IReportConfig;
import com.ailk.sets.grade.intf.report.InterviewInfo;
import com.ailk.sets.grade.intf.report.InterviewInfo.Group;
import com.ailk.sets.grade.intf.report.InterviewInfo.Item;
import com.ailk.sets.grade.intf.report.Summary;
import com.ailk.sets.grade.jdbc.CandidateInfoTest;
import com.ailk.sets.grade.service.intf.IComparedReportRetrieveService;
import com.ailk.sets.grade.service.intf.IInterviewTemplateService;
import com.ailk.sets.grade.utils.MathUtils;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.google.gson.Gson;

@Transactional(rollbackFor = Exception.class)
@Service
public class ComparedReportRetrieveServiceImpl implements
		IComparedReportRetrieveService {

	private static final Logger logger = Logger
			.getLogger(ComparedReportRetrieveServiceImpl.class);

	// 预定义的分值范围
	private static final Gson gson = new Gson();

	@Autowired
	private ICandidateReportDao candidateReportDao;

	@Autowired
	private ICandidateInfoTestDao candidateInfoTestDao;

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;

	@Autowired
	private IInterviewTemplateService interviewTemplateService;

	@Autowired
	private ICandidateTestDao candidateTestDao;

	@Autowired
	private IPositionDao positionDao;

	@Autowired
	private IReportConfig reportConfig;

	private static class Holder {
		public CandidateReport candidateReport; // 报告
		public List<PerTestInfo> perTestInfos;
		public Set<String> skillSet;
		public List<String> interviewNames;
		public Map<String, String> interviewNameMap;
	}

	private static class PerTestInfo {
		private ReportScoreItem[] scoreItems;
		private Map<String, Double> basicMap;
		private Map<String, Double> interviewMap;

		public PerTestInfo() {
			scoreItems = new ReportScoreItem[IReportConfig.ITEM_NAMES.length];
			for (int i = 0; i < scoreItems.length; i++)
				scoreItems[i] = new ReportScoreItem();

			basicMap = new HashMap<String, Double>();
			interviewMap = new HashMap<String, Double>();
		}

		public ReportScoreItem[] getScoreItems() {
			return scoreItems;
		}

		public Map<String, Double> getBasicMap() {
			return basicMap;
		}

		public Map<String, Double> getInterviewMap() {
			return interviewMap;
		}
	}

	@Override
	public GetComparedReportsResponse getComparedReports(List<Long> testIds)
			throws Exception {
		GetComparedReportsResponse response = new GetComparedReportsResponse();
		Holder holder = new Holder();
		holder.perTestInfos = new ArrayList<PerTestInfo>();
		holder.skillSet = new HashSet<String>();

		// 加载报告信息
		for (long testId : testIds) {
			PerTestInfo perTestInfo = new PerTestInfo();

			loadCandidateReport(holder, testId);
			loadInterviewNames(holder);

			getFromReport(holder, perTestInfo);
			getFromQuestion(holder, perTestInfo);
			getFromInterview(holder, perTestInfo);

			holder.perTestInfos.add(perTestInfo);
		}

		List<ComparedItem> comparedItems = new ArrayList<ComparedItem>();
		response.setComparedItems(comparedItems);

		for (int i = 0; i < IReportConfig.ITEM_NAMES.length; i++) {
			boolean match = false;
			List<RowItem> rowItems = new ArrayList<RowItem>();

			// 首先检查是否需要该行记录
			for (PerTestInfo perTestInfo : holder.perTestInfos) {
				ReportScoreItem scoreItem = perTestInfo.getScoreItems()[i];
				if (!scoreItem.isExist())
					continue;

				match = true;
				break;
			}

			if (!match)
				continue;

			for (PerTestInfo perTestInfo : holder.perTestInfos) {
				ReportScoreItem scoreItem = perTestInfo.getScoreItems()[i];

				RowItem rowItem = new RowItem();
				rowItem.setEditable(scoreItem.isEditable());
				if (scoreItem.isEdited())
					rowItem.setScore(MathUtils.round(scoreItem.getScore(), 1));
				rowItems.add(rowItem);
			}

			ComparedItem comparedItem = new ComparedItem();
			comparedItem.setName(IReportConfig.ITEM_NAMES[i]);
			comparedItem.setLevel(0);
			comparedItem.setAnchor(i);
			comparedItem.setRowItems(rowItems);
			comparedItems.add(comparedItem);

			switch (i) {
			case IReportConfig.REPORT_COLUMN_BASIC: {
				// 按第一与第二间的差距降序排序
				TreeMap<Double, List<String>> treeMap = new TreeMap<Double, List<String>>();
				for (String skill : holder.skillSet) {
					double max1 = Double.MIN_VALUE;
					double max2 = Double.MIN_VALUE;

					for (PerTestInfo perTestInfo : holder.perTestInfos) {
						Map<String, Double> basicMap = perTestInfo
								.getBasicMap();
						Double score = basicMap.get(skill);
						if (score == null)
							score = 0.0;

						if (max1 <= score)
							max1 = score;
						else if (max2 < score)
							max2 = score;
					}

					if (max2 == Double.MIN_VALUE)
						max2 = max1;

					Double gap = max1 - max2;
					List<String> skills = treeMap.get(gap);
					if (skills == null) {
						skills = new ArrayList<String>();
						treeMap.put(gap, skills);
					}
					skills.add(skill);
				}

				// 按顺序输出
				for (List<String> skills : treeMap.values()) {
					for (String skill : skills) {
						rowItems = new ArrayList<RowItem>();

						for (PerTestInfo perTestInfo : holder.perTestInfos) {
							Map<String, Double> basicMap = perTestInfo
									.getBasicMap();
							Double score = basicMap.get(skill);

							RowItem rowItem = new RowItem();
							rowItem.setEditable(false);
							if (score != null)
								rowItem.setScore(MathUtils.round(score, 1));
							rowItems.add(rowItem);
						}

						comparedItem = new ComparedItem();
						comparedItem.setName(skill);
						comparedItem.setLevel(1);
						comparedItem.setRowItems(rowItems);
						comparedItems.add(comparedItem);
					}
				}
				break;
			}
			case IReportConfig.REPORT_COLUMN_QUALITY: {
				for (String interviewName : holder.interviewNames) {
					rowItems = new ArrayList<RowItem>();

					for (PerTestInfo perTestInfo : holder.perTestInfos) {
						Map<String, Double> interviewMap = perTestInfo
								.getInterviewMap();
						Double score = interviewMap.get(interviewName);

						RowItem rowItem = new RowItem();
						rowItem.setEditable(false);
						if (score != null)
							rowItem.setScore(MathUtils.round(score, 1));
						rowItems.add(rowItem);
					}

					comparedItem = new ComparedItem();
					comparedItem.setName(interviewName);
					comparedItem.setLevel(1);
					comparedItem.setRowItems(rowItems);
					comparedItems.add(comparedItem);
				}
				break;
			}
			default:
				break;
			}
		}

		return response;
	}

	/**
	 * 获取报告记录
	 * 
	 * @param holder
	 *            临时存储
	 * @param testId
	 *            测试ID
	 * @throws InvalidDataException
	 */
	private void loadCandidateReport(Holder holder, long testId)
			throws InvalidDataException {
		holder.candidateReport = candidateReportDao.get(testId);
		if (holder.candidateReport == null) {
			String errDesc = "找不到候选人报告数据，testId=" + testId;
			logger.error(errDesc);
			throw new InvalidDataException(BaseResponse.ENOENT, errDesc);
		}
	}

	/**
	 * 
	 * @param holder
	 *            临时存储
	 * @throws Exception
	 */
	private void loadInterviewNames(Holder holder) throws Exception {
		holder.interviewNames = new ArrayList<String>();
		holder.interviewNameMap = new HashMap<String, String>();

		CandidateTest candidateTest = candidateTestDao
				.getEntity(holder.candidateReport.getTestId());
		Position position = positionDao
				.getEntity(candidateTest.getPositionId());

		InterviewInfo interviewInfo = interviewTemplateService.load(
				holder.candidateReport.getEmployerId(), position.getTestType(),
				GradeConst.INTERVIEW_ID);
		List<Group> groups = interviewInfo.getGroups();
		if (groups == null)
			return;

		for (Group group : groups) {
			if (!group.getId().equals(GradeConst.INTERVIEW_ITEM))
				continue;

			List<Item> items = group.getItems();
			for (Item item : items) {
				holder.interviewNames.add(item.getName());
				holder.interviewNameMap.put(item.getId(), item.getName());
			}
		}
	}

	/**
	 * 从报告获取信息
	 * 
	 * @param holder
	 *            临时存储
	 * @param perTestInfo
	 *            每个测试相关的信息
	 * @throws Exception
	 */
	public void getFromReport(Holder holder, PerTestInfo perTestInfo)
			throws Exception {
		String content = holder.candidateReport.getContent();
		GetReportResponse getReportResponse = gson.fromJson(content,
				GetReportResponse.class);

		Summary summary = getReportResponse.getSummary();
		List<String> skills = summary.getSkills();
		List<Double> skillScores = summary.getSkillScores();
		Map<String, Double> basicMap = perTestInfo.getBasicMap();

		for (int i = 0; i < skills.size(); i++) {
			String skill = skills.get(i);
			double score = skillScores.get(i);

			basicMap.put(skill, score);
			holder.skillSet.add(skill);
		}

		ReportScoreItem[] scoreItems = perTestInfo.getScoreItems();
		ReportScoreItem scoreItem = scoreItems[IReportConfig.REPORT_COLUMN_BASIC];
		scoreItem.setExist(true);
		scoreItem.setEditable(false);
		scoreItem.setEdited(true);
		scoreItem.setScore(summary.getBasicScore());
	}

	/**
	 * 从测试结果表中获取信息
	 * 
	 * @param holder
	 *            临时存储
	 * @param perTestInfo
	 *            每个测试相关的信息
	 * @param perTestInfo
	 */
	public void getFromQuestion(Holder holder, PerTestInfo perTestInfo) {
		List<CandidateTestQuestion> candidateTestQuestions = candidateTestQuestionDao
				.getList(holder.candidateReport.getTestId());
		ReportScoreItem[] scoreItems = perTestInfo.getScoreItems();
		int[] reportColumnSize = new int[IReportConfig.ITEM_NAMES.length];

		for (CandidateTestQuestion candidateTestQuestion : candidateTestQuestions) {
			long qid = candidateTestQuestion.getId().getQuestionId();
			QbQuestion qbQuestion = qbQuestionDao.getEntity(qid);
			if (qbQuestion == null)
				continue;

			int questionType = GradeConst.toQuestionTypeInt(qbQuestion
					.getQuestionType());
			int index = reportConfig.getReportIndex(questionType,
					qbQuestion.getCategory());
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
			} else {
				scoreItem.setEditable(true);
			}
			reportColumnSize[index]++;
		}

		// 调整分值
		for (int i = 0; i < reportColumnSize.length; i++) {
			if (reportColumnSize[i] == 0)
				continue;

			ReportScoreItem scoreItem = scoreItems[i];
			scoreItem.setScore(scoreItem.getScore() / reportColumnSize[i]);
		}
	}

	/**
	 * 获取面试相关信息
	 * 
	 * @param holder
	 *            临时存储
	 * @param perTestInfo
	 *            每个测试相关的信息
	 * @return
	 * @throws Exception
	 */
	public void getFromInterview(Holder holder, PerTestInfo perTestInfo)
			throws Exception {
		int rows = 0;
		double total = 0.0;
		Map<String, Double> interviewMap = perTestInfo.getInterviewMap();
		List<CandidateInfoTest> candidateInfoTests = candidateInfoTestDao
				.getList(holder.candidateReport.getTestId(),
						GradeConst.INTERVIEW_ITEM);
		for (CandidateInfoTest candidateInfoTest : candidateInfoTests) {
			String name = holder.interviewNameMap.get(candidateInfoTest
					.getCandidateInfoTestPK().getInfoId());
			if (name == null)
				continue;

			double realValue = Double.parseDouble(candidateInfoTest
					.getRealValue());
			interviewMap.put(name, realValue);

			rows++;
			total += realValue;
		}

		ReportScoreItem[] scoreItems = perTestInfo.getScoreItems();
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
}
