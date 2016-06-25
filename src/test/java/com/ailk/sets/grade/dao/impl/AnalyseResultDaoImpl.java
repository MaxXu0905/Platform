package com.ailk.sets.grade.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IAnalyseResultDao;
import com.ailk.sets.grade.jdbc.AnalyseResult;

@Repository
public class AnalyseResultDaoImpl implements IAnalyseResultDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(AnalyseResult analyseResult) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(analyseResult);
	}

}
