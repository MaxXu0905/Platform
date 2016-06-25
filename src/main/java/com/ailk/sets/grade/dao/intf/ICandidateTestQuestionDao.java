package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestionId;

public interface ICandidateTestQuestionDao extends
		IBaseDao<CandidateTestQuestion> {

	public CandidateTestQuestion get(
			CandidateTestQuestionId candidateTestQuestionId);

	public List<CandidateTestQuestion> getList();

	public List<CandidateTestQuestion> getList(long testId);

	public void update(CandidateTestQuestion candidateTestQuestion);

	public List<CandidateTestQuestion> getListByPart(long testId, int partSeq);

	public void updateAnswerFlag(long testId, long qid, int answerFlag);

}
