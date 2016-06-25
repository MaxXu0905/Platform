package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IPositionInfoExtDao;
import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.domain.PositionInfoExt;

@Repository
public class PositionInfoExtDaoImpl extends BaseDaoImpl<PositionInfoExt>
		implements IPositionInfoExtDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<PositionInfoExt> getList(int employerId, int positionId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM PositionInfoExt Where id.employerId = ?1 AND id.positionId = ?2 ORDER BY seq")
				.setInteger("1", employerId).setInteger("2", positionId).list();
	}

}
