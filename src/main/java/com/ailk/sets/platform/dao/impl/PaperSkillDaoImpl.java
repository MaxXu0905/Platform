package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPaperSkillDao;
import com.ailk.sets.platform.domain.PaperSkill;

@Repository
public class PaperSkillDaoImpl extends BaseDaoImpl<PaperSkill> implements IPaperSkillDao {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<PaperSkill> getPaperSkills(int paperId) {
		Session session = sessionFactory.getCurrentSession();
		return session
				.createQuery("from PaperSkill where id.paperId = :paperId")
				.setParameter("paperId", paperId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperSkill> getList(int paperId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM PaperSkill WHERE id.paperId = ?1 ORDER BY skillSeq")
				.setInteger("1", paperId).list();
	}

}
