package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;

@Repository
public class QbSkillDegreeDaoImpl {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Cacheable(value = "qbSkillDegree")
	public QbSkillDegree getQbSkillDegree(int degreeId) {
		Session session = sessionFactory.getCurrentSession();

		return (QbSkillDegree) session.get(QbSkillDegree.class, degreeId);
	}

	@SuppressWarnings("unchecked")
	public List<QbSkillDegree> getQbSkillDegrees() {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery(
				"FROM QbSkillDegree ORDER BY degreeWeight desc").list();
	}

	@CacheEvict(value = "qbSkillDegree", allEntries = true)
	public void evict() {
	}

}
