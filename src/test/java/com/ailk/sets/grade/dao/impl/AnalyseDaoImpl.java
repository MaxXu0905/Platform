package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IAnalyseDao;
import com.ailk.sets.grade.jdbc.Analyse;

@Repository
public class AnalyseDaoImpl implements IAnalyseDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Analyse> getList() {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery("FROM Analyse").list();
	}

	@Override
	public void saveOrUpdate(Analyse analyse) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(analyse);
	}

}
