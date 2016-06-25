package com.ailk.sets.grade.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateRatingQuestionDao;
import com.ailk.sets.grade.jdbc.CandidateRatingQuestion;
import com.ailk.sets.grade.jdbc.CandidateRatingQuestionPK;

@Repository
public class CandidateRatingQuestionDaoImpl implements
		ICandidateRatingQuestionDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public CandidateRatingQuestion get(
			CandidateRatingQuestionPK candidateRatingQuestionPK) {
		Session session = sessionFactory.getCurrentSession();

		return (CandidateRatingQuestion) session.get(
				CandidateRatingQuestion.class, candidateRatingQuestionPK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateRatingQuestion> getList(int candidateId, int qbId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CandidateRatingQuestion WHERE candidateRatingQuestionPK.candidateId = ?1 AND candidateRatingQuestionPK.qbId = ?2")
				.setInteger("1", candidateId).setInteger("2", qbId).list();
	}

	@Override
	public Set<Long> getSet(int candidateId, int qbId) {
		Set<Long> set = new HashSet<Long>();
		List<CandidateRatingQuestion> candidateRatingQuestions = getList(candidateId, qbId);
		
		for (CandidateRatingQuestion candidateRatingQuestion : candidateRatingQuestions) {
			set.add(candidateRatingQuestion.getCandidateRatingQuestionPK().getQuestionId());
		}
		
		return set;
	}

	@Override
	public void save(CandidateRatingQuestion candidateRatingQuestion) {
		Session session = sessionFactory.getCurrentSession();

		session.save(candidateRatingQuestion);
	}

	@Override
	public void update(CandidateRatingQuestion candidateRatingQuestion) {
		Session session = sessionFactory.getCurrentSession();

		session.update(candidateRatingQuestion);
	}

	@Override
	public void saveOrUpdate(CandidateRatingQuestion candidateRatingQuestion) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(candidateRatingQuestion);
	}

}
