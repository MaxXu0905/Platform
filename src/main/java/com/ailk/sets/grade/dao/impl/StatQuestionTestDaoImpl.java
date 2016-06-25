package com.ailk.sets.grade.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IStatQuestionTestDao;
import com.ailk.sets.grade.jdbc.StatQuestionTest;
import com.ailk.sets.grade.jdbc.StatQuestionTestPK;

@Repository
public class StatQuestionTestDaoImpl implements IStatQuestionTestDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	@Cacheable(value = "statQuestionTest")
	public StatQuestionTest getById(long questionId, int positionLevel) {
		Session session = sessionFactory.getCurrentSession();

		StatQuestionTestPK statQuestionTestPK = new StatQuestionTestPK();
		statQuestionTestPK.setQuestionId(questionId);
		statQuestionTestPK.setPositionLevel(positionLevel);
		return (StatQuestionTest) session.get(StatQuestionTest.class, statQuestionTestPK);
	}

	@Override
	@CacheEvict(value = "statQuestionTest", allEntries = true)
	public void evict() {
	}

}
