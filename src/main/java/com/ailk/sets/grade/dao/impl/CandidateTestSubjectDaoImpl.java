package com.ailk.sets.grade.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateTestSubjectDao;
import com.ailk.sets.grade.jdbc.CandidateTestSubject;

@Repository
public class CandidateTestSubjectDaoImpl implements ICandidateTestSubjectDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(CandidateTestSubject candidateTestSubject) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(candidateTestSubject);
	}

}
