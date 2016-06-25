package com.ailk.sets.grade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.intf.report.Interview.InterviewItem;
import com.ailk.sets.grade.intf.report.OverallItem;
import com.ailk.sets.grade.intf.report.Part;
import com.ailk.sets.grade.intf.report.PartItem;
import com.ailk.sets.grade.intf.report.Report;
import com.ailk.sets.grade.service.intf.IReportRetrieveService;
import com.ailk.sets.grade.service.intf.IReportService;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestionId;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.google.gson.Gson;

@Transactional(rollbackFor = Exception.class)
@Service
public class ReportRetrieveServiceImpl implements IReportRetrieveService {

	// 预定义的分值范围
	private static final Gson gson = new Gson();

	@Autowired
	private IReportService reportService;

	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;

	/**
	 * 获取报告
	 * 
	 * @param testId
	 *            报告ID
	 * @return
	 */
	@Override
	public GetReportResponse getReport(long testId) throws Exception {
		CandidateReport candidateReport = reportService
				.loadCandidateReport(testId);

		return getReport(candidateReport);
	}

	@Override
	public GetReportResponse getReport(long testId, String reportPassport)
			throws Exception {
		CandidateReport candidateReport = reportService
				.loadCandidateReport(testId);
		if (!candidateReport.getReportPassport().equals(reportPassport)) {
			GetReportResponse response = new GetReportResponse();
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("报告通信证错误，testId=" + testId
					+ ", reportPassport=" + reportPassport);
			return response;
		}

		return getReport(candidateReport);
	}

	/**
	 * 获取报告详情
	 * 
	 * @param candidateReport
	 *            候选人报告对象
	 * @return
	 * @throws Exception
	 */
	private GetReportResponse getReport(CandidateReport candidateReport)
			throws Exception {
		CandidateTest candidateTest = reportService
				.loadCandidateTest(candidateReport.getTestId());

		String content = candidateReport.getContent();
		Report report = gson.fromJson(content, Report.class);
		GetReportResponse response = new GetReportResponse(report);
		if (response.getErrorCode() != 0)
			return response;

		// 补充测试结果状态信息
		response.setTestResult(candidateTest.getTestResult());

		// 设置是否为样例报告
		if (candidateReport.getSample() == 1)
			response.setSample(true);
		else
			response.setSample(false);

		// 获取报告概述信息
		List<OverallItem> items = reportService.getOverallItems(report,
				candidateReport.getTestId());
		response.setOverallItems(items);

		// 设置面试评分
		List<InterviewItem> interviewItems = reportService
				.getInterviewItems(candidateReport.getTestId());
		response.getInterview().setItems(interviewItems);

		// 设置部分信息
		List<Part> parts = response.getParts();
		if (parts != null) {
			for (Part part : parts) {
				List<PartItem> partItems = part.getPartItems();
				for (PartItem partItem : partItems) {
					CandidateTestQuestionId id = new CandidateTestQuestionId();
					id.setTestId(candidateReport.getTestId());
					id.setQuestionId(partItem.getQuestionId());

					CandidateTestQuestion candidateTestQuestion = candidateTestQuestionDao
							.getEntity(id);
					if (candidateTestQuestion == null)
						continue;

					partItem.setScore(candidateTestQuestion.getGetScore());

					partItem.setTitle(QuestionUtils.escape(partItem.getTitle(),
							partItem.isHtml()));
					partItem.setOptions(QuestionUtils.escape(
							partItem.getOptions(), partItem.isHtml()));
				}
			}
		}

		return response;
	}

}
