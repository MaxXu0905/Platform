package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.grade.jdbc.CandidateExamQuestion;
import com.ailk.sets.grade.jdbc.CandidateExamQuestionPK;

public interface ICandidateExamQuestionDao {

	public boolean isEmpty(long testId);

	public List<CandidateExamQuestion> getList(long testId);

	public CandidateExamQuestion get(
			CandidateExamQuestionPK candidateExamQuestionPK);
	
	public void save(CandidateExamQuestion candidateExamQuestion);
	
	public void saveOrUpdate(CandidateExamQuestion candidateExamQuestion);
	
	public void update(CandidateExamQuestion candidateExamQuestion);
	
	public void deleteByTestId(long testId);

}
