package com.ailk.sets.grade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.intf.report.GetReportSummaryResponse;
import com.ailk.sets.grade.intf.report.OverallItem;
import com.ailk.sets.grade.intf.report.Report;
import com.ailk.sets.grade.service.intf.IReportService;
import com.ailk.sets.grade.service.intf.IReportSummaryRetrieveService;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.google.gson.Gson;

@Transactional(rollbackFor = Exception.class)
@Service
public class ReportSummaryRetrieveServiceImpl implements
		IReportSummaryRetrieveService {

	// 预定义的分值范围
	private static final Gson gson = new Gson();

	@Autowired
	private IReportService reportService;

	@Override
	public GetReportSummaryResponse getReportSummary(long testId)
			throws Exception {
		CandidateReport candidateReport = reportService
				.loadCandidateReport(testId);

		return getReportSummary(candidateReport);
	}

	@Override
	public GetReportSummaryResponse getReportSummary(
			CandidateReport candidateReport) throws Exception {
		GetReportSummaryResponse response = new GetReportSummaryResponse();

		Report report = gson.fromJson(candidateReport.getContent(),
				Report.class);
		response.setScore(report.getSummary().getScore());
		List<OverallItem> items = reportService.getOverallItems(report,
				candidateReport.getTestId());
		if (!items.isEmpty())
			response.setItems(items);
		response.setParts(report.getParts());

		return response;
	}

}
