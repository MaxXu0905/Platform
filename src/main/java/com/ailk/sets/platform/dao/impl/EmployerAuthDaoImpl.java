package com.ailk.sets.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IEmployerAuthDao;
import com.ailk.sets.platform.domain.EmployerAuthorization;

@Repository
public class EmployerAuthDaoImpl extends BaseDaoImpl<EmployerAuthorization> implements IEmployerAuthDao {

	@Override
	public List<Integer> getGrantedId(int employerId) {
		List<Integer> result = new ArrayList<Integer>();
		List<EmployerAuthorization> list = super.getList(employerId, "id.employerGranted");
		if (!CollectionUtils.isEmpty(list)) {
			for (EmployerAuthorization ea : list) {
				result.add(ea.getId().getPositionGranted());
			}
		}
		return result;
	}

	@Override
	public int deleteAuth(int positionId) {
		Session session = getSession();
		return session.createQuery("delete EmployerAuthorization where id.positionGranted="+ positionId).executeUpdate();
	}

	@Override
	public List<EmployerAuthorization> getEmployerAuthorizations(int employerId, int positionId) {
		Session session = getSession();
		Query q = session.createQuery("from EmployerAuthorization where id.positionGranted = " + positionId );
		return q.list();
	}
}
