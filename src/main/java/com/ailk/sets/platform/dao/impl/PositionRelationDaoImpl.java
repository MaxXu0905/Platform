package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPositionRelationDao;
import com.ailk.sets.platform.domain.PositionRelation;
@Repository
public class PositionRelationDaoImpl extends BaseDaoImpl<PositionRelation> implements IPositionRelationDao {

	@Override
	public List<PositionRelation> getPositionRelationByPositionGroupId(int positionGroupId) {
		Session session = getSession();
		Query q = session.createQuery("from PositionRelation where id.positionGroupId = " + positionGroupId);
		return (List<PositionRelation>)q.list();
	}
	
}
