package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.feedback.CandidateTestFeedback;

public interface IFeedBackDao extends IBaseDao<CandidateTestFeedback> {
	public int getTotalCommentNum(long questionId);
	
	/**
	 * 获取评论信息
	 * @param questionId
	 * @param page
	 * @return
	 */
	public List<CandidateTestFeedback> getCandidateTestFeedbacks(long questionId, Page page);
}
