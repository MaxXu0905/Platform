package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IQuestionRatingDao;
import com.ailk.sets.grade.jdbc.QuestionRating;
import com.ailk.sets.grade.jdbc.QuestionRatingPK;

@Repository
public class QuestionRatingDaoImpl implements IQuestionRatingDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public QuestionRating get(QuestionRatingPK questionRatingPK) {
		Session session = sessionFactory.getCurrentSession();

		return (QuestionRating) session.get(QuestionRating.class,
				questionRatingPK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionRating> getList() {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery("FROM QuestionRating").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionRating> getList(int qbId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM QuestionRating WHERE questionRatingPK.qbId = ?1")
				.setInteger("1", qbId).list();
	}

	@Override
	public void save(QuestionRating questionRating) {
		Session session = sessionFactory.getCurrentSession();

		session.save(questionRating);
	}

	@Override
	public void update(QuestionRating questionRating) {
		Session session = sessionFactory.getCurrentSession();

		session.update(questionRating);
	}

	@Override
	public void saveOrUpdate(QuestionRating questionRating) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(questionRating);
	}

}
