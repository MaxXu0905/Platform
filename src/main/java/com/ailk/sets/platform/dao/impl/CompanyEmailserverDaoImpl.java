package com.ailk.sets.platform.dao.impl;

import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICompanyEmailserverDao;
import com.ailk.sets.platform.domain.CompanyEmailserver;

@Repository
public class CompanyEmailserverDaoImpl extends BaseDaoImpl<CompanyEmailserver> implements ICompanyEmailserverDao{
	
	@Override
	public CompanyEmailserver getEntity(Object key, String keyName) {
		CompanyEmailserver ces = super.getEntity(key, keyName);
		if (ces == null)
			ces = super.getEntity(1, keyName);
		return ces;
	}
}
