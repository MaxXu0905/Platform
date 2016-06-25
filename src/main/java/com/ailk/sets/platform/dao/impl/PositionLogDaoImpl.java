package com.ailk.sets.platform.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPositionLogDao;
import com.ailk.sets.platform.domain.PositionLog;

@Repository
public class PositionLogDaoImpl extends BaseDaoImpl<PositionLog> implements IPositionLogDao {

	@Override
	public PositionLog getPositionLogByPosIdAndState(int positionId, int positionState) {
		Session session = null;
		session = getSession();
		Criteria criteria = session.createCriteria(PositionLog.class);
		criteria.add(Restrictions.eq("positionId", positionId));
		criteria.add(Restrictions.eq("positionState", positionState));
		return (PositionLog)criteria.uniqueResult() ;
	}

}
