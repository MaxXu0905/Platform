package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateInfoTestDao;
import com.ailk.sets.grade.jdbc.CandidateInfoTest;

@Repository
public class CandidateInfoTestDaoImpl implements ICandidateInfoTestDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(CandidateInfoTest candidateInfoTest) {
		Session session = sessionFactory.getCurrentSession();

		session.save(candidateInfoTest);
	}

	@Override
	public void saveOrUpdate(CandidateInfoTest candidateInfoTest) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(candidateInfoTest);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateInfoTest> getList(long testId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CandidateInfoTest WHERE candidateInfoTestPK.testId = ?1")
				.setLong("1", testId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateInfoTest> getList(long testId, String groupId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CandidateInfoTest WHERE candidateInfoTestPK.testId = ?1 AND candidateInfoTestPK.groupId = ?2")
				.setLong("1", testId).setString("2", groupId).list();
	}

}
