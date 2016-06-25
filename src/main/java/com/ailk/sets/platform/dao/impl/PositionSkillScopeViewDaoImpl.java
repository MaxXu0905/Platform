package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.domain.skilllabel.PositionSkillScopeView;
import com.ailk.sets.platform.domain.skilllabel.PositionSkillScopeViewId;

@Repository
public class PositionSkillScopeViewDaoImpl {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<PositionSkillScopeView> getPositionSeriesSkillView(String seriesId) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from PositionSkillScopeView where id.seriesId = '" + seriesId +"'");
		return q.list();
	}
	
	public PositionSkillScopeView getPositionSeriesSkillView(String seriesId,String skillId) {
		Session session = sessionFactory.getCurrentSession();
		return (PositionSkillScopeView)session.get(PositionSkillScopeView.class, new PositionSkillScopeViewId(skillId,seriesId));
	}

}
