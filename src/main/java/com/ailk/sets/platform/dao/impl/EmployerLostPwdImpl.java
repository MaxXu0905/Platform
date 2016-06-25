package com.ailk.sets.platform.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IEmployerLostPwd;
import com.ailk.sets.platform.domain.EmployerLostPwd;

@Repository
public class EmployerLostPwdImpl extends BaseDaoImpl<EmployerLostPwd> implements IEmployerLostPwd {

	@Override
	public void deleteAll(String emailAcct) {
		Session session = getSession();
		String sql = "delete from employer_lost_pwd where employer_acct = '" + emailAcct + "'";
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
}
