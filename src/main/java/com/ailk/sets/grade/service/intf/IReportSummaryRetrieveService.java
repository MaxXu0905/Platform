package com.ailk.sets.grade.service.intf;

import com.ailk.sets.grade.intf.report.GetReportSummaryResponse;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;

public interface IReportSummaryRetrieveService {

	public GetReportSummaryResponse getReportSummary(long testId)
			throws Exception;

	public GetReportSummaryResponse getReportSummary(
			CandidateReport candidateReport) throws Exception;

}
