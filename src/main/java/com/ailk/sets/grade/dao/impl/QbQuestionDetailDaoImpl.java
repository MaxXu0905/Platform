package com.ailk.sets.grade.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;

@Repository
public class QbQuestionDetailDaoImpl implements IQbQuestionDetailDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	@Cacheable(value = "qbQuestionDetail")
	public QbQuestionDetail get(long questionId) {
		Session session = sessionFactory.getCurrentSession();

		return (QbQuestionDetail) session.get(QbQuestionDetail.class,
				questionId);
	}

	@Override
	public QbQuestionDetail getWithoutCache(long questionId) {
		Session session = sessionFactory.getCurrentSession();

		return (QbQuestionDetail) session.get(QbQuestionDetail.class,
				questionId);
	}

	@Override
	@CacheEvict(value = "qbQuestionDetail", key = "#qbQuestionDetail.questionId")
	public QbQuestionDetail save(QbQuestionDetail qbQuestionDetail) {
		Session session = sessionFactory.getCurrentSession();

		session.save(qbQuestionDetail);
		return qbQuestionDetail;
	}

	@Override
	@CacheEvict(value = "qbQuestionDetail", key = "#qbQuestionDetail.questionId")
	public QbQuestionDetail update(QbQuestionDetail qbQuestionDetail) {
		Session session = sessionFactory.getCurrentSession();

		session.update(qbQuestionDetail);
		return qbQuestionDetail;
	}

	@Override
	@CacheEvict(value = "qbQuestionDetail", key = "#qbQuestionDetail.questionId")
	public QbQuestionDetail saveOrUpdate(QbQuestionDetail qbQuestionDetail) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(qbQuestionDetail);
		return qbQuestionDetail;
	}

	@Override
	@CacheEvict(value = "qbQuestionDetail", allEntries = true)
	public void evict() {
	}

}
