package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.platform.intf.school.domain.CandidateTestScore;

public interface ICandidateTestScoreDao {
	
	public void save(CandidateTestScore candidateTestScore);
	
	public void saveOrUpdate(CandidateTestScore candidateTestScore);

	public List<CandidateTestScore> getList(int skillId);

}
