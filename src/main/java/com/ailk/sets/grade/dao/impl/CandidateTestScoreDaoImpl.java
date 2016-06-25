package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateTestScoreDao;
import com.ailk.sets.platform.intf.school.domain.CandidateTestScore;

@Repository
public class CandidateTestScoreDaoImpl implements ICandidateTestScoreDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(CandidateTestScore candidateTestScore) {
		Session session = sessionFactory.getCurrentSession();

		session.save(candidateTestScore);
	}

	@Override
	public void saveOrUpdate(CandidateTestScore candidateTestScore) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(candidateTestScore);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateTestScore> getList(int skillId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery("FROM CandidateTestScore Where id.skillId = ?1")
				.setInteger("1", skillId).list();
	}

}
