package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IFeedBackDao;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.feedback.CandidateTestFeedback;

@Repository
public class FeedBackDaoImpl extends BaseDaoImpl<CandidateTestFeedback> implements IFeedBackDao {
	public int getTotalCommentNum(long questionId){
		Session session = getSession();
		Query q = session.createQuery("select count(*) from CandidateTestFeedback where fbMore is not null and questionId =" + questionId);
		Long total = (Long)q.uniqueResult();
		return total.intValue();
		
	}
	
	/**
	 * 获取评论信息
	 * @param questionId
	 * @param page
	 * @return
	 */
	public List<CandidateTestFeedback> getCandidateTestFeedbacks(long questionId, Page page){
		Session session = getSession();
		Query query = session.createQuery("from CandidateTestFeedback where fbMore is not null and questionId =" + questionId);
		query.setFirstResult((page.getRequestPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}
}
