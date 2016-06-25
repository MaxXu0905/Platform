package com.ailk.sets.grade.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateRatingDao;
import com.ailk.sets.grade.jdbc.CandidateRating;
import com.ailk.sets.grade.jdbc.CandidateRatingPK;

@Repository
public class CandidateRatingDaoImpl implements
		ICandidateRatingDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public CandidateRating get(
			CandidateRatingPK candidateRatingPK) {
		Session session = sessionFactory.getCurrentSession();

		return (CandidateRating) session.get(
				CandidateRating.class, candidateRatingPK);
	}

	@Override
	public void save(CandidateRating candidateRating) {
		Session session = sessionFactory.getCurrentSession();

		session.save(candidateRating);
	}

	@Override
	public void update(CandidateRating candidateRating) {
		Session session = sessionFactory.getCurrentSession();

		session.update(candidateRating);
	}

	@Override
	public void saveOrUpdate(CandidateRating candidateRating) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(candidateRating);
	}

}
