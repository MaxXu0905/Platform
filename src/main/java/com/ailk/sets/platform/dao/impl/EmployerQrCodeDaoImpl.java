package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IEmployerQrCodeDao;
import com.ailk.sets.platform.domain.EmployerQrcode;

@Repository
public class EmployerQrCodeDaoImpl extends BaseDaoImpl<EmployerQrcode> implements IEmployerQrCodeDao {

	@Override
	@SuppressWarnings("unchecked")
	public EmployerQrcode getAvailableQrCode() {
		Session session = getSession();
		String hql = "from EmployerQrcode eq where eq.employerId is null";
		Query query = session.createQuery(hql);
		query.setFetchSize(1);
		
		List<EmployerQrcode> list = query.list();
		if (CollectionUtils.isNotEmpty(list))
			return list.get(0);
		else
			return null;
	}

	@Override
	public int getMaxId() {
		Session session = getSession();
		String hql = "select max(qrcodeId) from EmployerQrcode";
		Query query = session.createQuery(hql);
		Integer maxId = (Integer) query.uniqueResult();
		return maxId == null ? 0 : maxId;
	}

	@Override
	public void removeEmployerId(int employerId) {
		Session session = getSession();
		String hql = "update EmployerQrcode set employerId = null where employerId=" + employerId;
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

}
