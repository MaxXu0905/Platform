package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.platform.intf.empl.domain.CandidateReport;

public interface ICandidateReportDao {

	public CandidateReport get(long testId);

	public void save(CandidateReport candidateReport);

	public void update(CandidateReport candidateReport);
	
	public void saveOrUpdate(CandidateReport candidateReport);

	public List<CandidateReport> getList();

}
