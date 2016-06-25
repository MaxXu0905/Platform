package com.ailk.sets.grade.dao.intf;

import java.util.List;
import java.util.Set;

import com.ailk.sets.grade.jdbc.CandidateRatingQuestion;
import com.ailk.sets.grade.jdbc.CandidateRatingQuestionPK;

public interface ICandidateRatingQuestionDao {

	public CandidateRatingQuestion get(
			CandidateRatingQuestionPK candidateRatingQuestionPK);

	public List<CandidateRatingQuestion> getList(int candidateId, int qbId);

	public Set<Long> getSet(int candidateId, int qbId);

	public void save(CandidateRatingQuestion candidateRatingQuestion);

	public void update(CandidateRatingQuestion candidateRatingQuestion);

	public void saveOrUpdate(CandidateRatingQuestion candidateRatingQuestion);

}
