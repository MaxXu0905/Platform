package com.ailk.sets.platform.dao.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IStatQuestionDao;
import com.ailk.sets.platform.domain.StatQuestion;

@Repository
public class StatQuestionDaoImpl extends BaseDaoImpl<StatQuestion> implements
		IStatQuestionDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	@Cacheable(value = "statQuestion")
	public StatQuestion getEntity(Serializable questionId) {
		Session session = sessionFactory.getCurrentSession();

		return (StatQuestion) session.get(StatQuestion.class, questionId);
	}

	@Override
	@CacheEvict(value = "statQuestion", allEntries = true)
	public void evict() {
	}

}
