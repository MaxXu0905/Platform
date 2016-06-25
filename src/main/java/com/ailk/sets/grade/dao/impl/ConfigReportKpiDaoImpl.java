package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IConfigReportKpiDao;
import com.ailk.sets.grade.jdbc.ConfigReportKpi;

@Repository
public class ConfigReportKpiDaoImpl implements IConfigReportKpiDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigReportKpi> getListByOpenSession() {
		Session session = sessionFactory.openSession();

		try {
			return session
					.createQuery(
							"FROM ConfigReportKpi ORDER BY configReportKpiPK.kpiName, configReportKpiPK.kpiValueLeft")
					.list();
		} finally {
			try {
				session.close();
			} catch (Exception e) {
			}
		}
	}

}
