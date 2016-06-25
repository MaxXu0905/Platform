package com.ailk.sets.grade.service.intf;

import java.util.List;

import com.ailk.sets.grade.intf.report.GetComparedReportsResponse;

public interface IComparedReportRetrieveService {

	public GetComparedReportsResponse getComparedReports(List<Long> testIds)
			throws Exception;

}
