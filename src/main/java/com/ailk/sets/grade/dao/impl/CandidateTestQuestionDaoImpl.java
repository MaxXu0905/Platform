package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestionId;

@Repository
public class CandidateTestQuestionDaoImpl extends
		BaseDaoImpl<CandidateTestQuestion> implements ICandidateTestQuestionDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public CandidateTestQuestion get(
			CandidateTestQuestionId candidateTestQuestionId) {
		Session session = sessionFactory.getCurrentSession();

		return (CandidateTestQuestion) session.get(CandidateTestQuestion.class,
				candidateTestQuestionId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateTestQuestion> getList() {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery("FROM CandidateTestQuestion").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateTestQuestion> getList(long testId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CandidateTestQuestion WHERE id.testId = ?1 AND partSeq <= 20 ORDER BY questionSeq")
				.setLong("1", testId).list();
	}

	@Override
	public void update(CandidateTestQuestion candidateTestQuestion) {
		Session session = sessionFactory.getCurrentSession();

		session.update(candidateTestQuestion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateTestQuestion> getListByPart(long testId, int partSeq) {
		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("from CandidateTestQuestion where id.testId = :testId and partSeq = :partSeq  order by questionSeq asc");
		query.setParameter("testId", testId);
		query.setParameter("partSeq", partSeq);
		return query.list();
	}

	@Override
	public void updateAnswerFlag(long testId, long qid, int answerFlag) {
		Session session = sessionFactory.getCurrentSession();

		session.createQuery(
				"UPDATE CandidateTestQuestion SET answerFlag = ?1 WHERE id.testId = ?2 AND id.questionId = ?3")
				.setInteger("1", answerFlag).setLong("2", testId)
				.setLong("3", qid).executeUpdate();
	}

}
