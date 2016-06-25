package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.grade.jdbc.CandidateInfoTest;

public interface ICandidateInfoTestDao {

	public void save(CandidateInfoTest candidateInfoTest);
	
	public void saveOrUpdate(CandidateInfoTest candidateInfoTest);

	public List<CandidateInfoTest> getList(long testId);
	
	public List<CandidateInfoTest> getList(long testId, String groupId);
	
}
