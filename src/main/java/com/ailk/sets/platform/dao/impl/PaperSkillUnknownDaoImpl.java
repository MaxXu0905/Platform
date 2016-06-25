package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.domain.skilllabel.PaperSkillUnknown;

@Repository
public class PaperSkillUnknownDaoImpl {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<PaperSkillUnknown> getPaperSkillUnknowns(int positionId) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from PaperSkillUnknown where positionId = :positionId")
				.setParameter("positionId", positionId).list();
	}

}
