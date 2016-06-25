package com.ailk.sets.platform.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillSubjectView;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillSubjectViewId;


@Repository
public class QbSkillSubjectViewDaoImpl {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Cacheable(value = "qbSkillSubjectView")
	public QbSkillSubjectView getQbSkillSubjectViews(String program,
			String skillId) {
		Session session = sessionFactory.getCurrentSession();

		return (QbSkillSubjectView) session.get(QbSkillSubjectView.class,
				new QbSkillSubjectViewId(skillId, program));
	}

	@CacheEvict(value = "qbSkillSubjectView", allEntries = true)
	public void evict() {
	}

}
