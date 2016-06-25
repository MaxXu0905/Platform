package com.ailk.sets.grade.service.intf;

import com.ailk.sets.grade.intf.report.GetReportResponse;

public interface IReportRetrieveService {

	public GetReportResponse getReport(long testId) throws Exception;

	public GetReportResponse getReport(long testId, String reportPassport)
			throws Exception;

}
