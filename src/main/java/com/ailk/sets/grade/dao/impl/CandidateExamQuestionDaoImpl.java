package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateExamQuestionDao;
import com.ailk.sets.grade.jdbc.CandidateExamQuestion;
import com.ailk.sets.grade.jdbc.CandidateExamQuestionPK;

@Repository
public class CandidateExamQuestionDaoImpl implements ICandidateExamQuestionDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public boolean isEmpty(long testId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CandidateExamQuestion WHERE candidateExamQuestionPK.testId = ?1 LIMIT 1")
				.setLong("1", testId).list().isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateExamQuestion> getList(long testId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CandidateExamQuestion WHERE candidateExamQuestionPK.testId = ?1 ORDER BY serialNo")
				.setLong("1", testId).list();
	}

	@Override
	public CandidateExamQuestion get(
			CandidateExamQuestionPK candidateExamQuestionPK) {
		Session session = sessionFactory.getCurrentSession();

		return (CandidateExamQuestion) session.get(CandidateExamQuestion.class,
				candidateExamQuestionPK);
	}

	@Override
	public void save(CandidateExamQuestion candidateExamQuestion) {
		Session session = sessionFactory.getCurrentSession();

		session.save(candidateExamQuestion);
	}

	@Override
	public void saveOrUpdate(CandidateExamQuestion candidateExamQuestion) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(candidateExamQuestion);
	}

	@Override
	public void update(CandidateExamQuestion candidateExamQuestion) {
		Session session = sessionFactory.getCurrentSession();

		session.update(candidateExamQuestion);
	}

	@Override
	public void deleteByTestId(long testId) {
		Session session = sessionFactory.getCurrentSession();

		session.createQuery(
				"DELETE FROM CandidateExamQuestion WHERE candidateExamQuestionPK.testId = ?1")
				.setLong("1", testId).executeUpdate();
	}

}
