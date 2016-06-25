package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.paper.PaperQuestion;

public interface IPaperQuestionDao extends IBaseDao<PaperQuestion> {
	public List<PaperQuestion> getNormalPaperQuestions(int paperId);
    public List<PaperQuestion> getPaperQuestionsByPaperIdAndSeq(int paperId, int partSeq);
}
