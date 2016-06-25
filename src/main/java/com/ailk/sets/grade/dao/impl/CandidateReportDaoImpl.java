package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateReportDao;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;

@Repository
public class CandidateReportDaoImpl implements ICandidateReportDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public CandidateReport get(long testId) {
		Session session = sessionFactory.getCurrentSession();

		return (CandidateReport) session.get(CandidateReport.class, testId);
	}

	@Override
	public void save(CandidateReport candidateReport) {
		Session session = sessionFactory.getCurrentSession();

		session.save(candidateReport);
	}

	@Override
	public void update(CandidateReport candidateReport) {
		Session session = sessionFactory.getCurrentSession();

		session.update(candidateReport);
	}
	
	@Override
	public void saveOrUpdate(CandidateReport candidateReport) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(candidateReport);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateReport> getList() {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery("FROM CandidateReport").list();
	}

}
