package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IStateSmsSendDao;
import com.ailk.sets.platform.domain.StateSmsSend;

@Repository
public class StateSmsSendDaoImpl extends BaseDaoImpl<StateSmsSend> implements IStateSmsSendDao {

	@Override
	public StateSmsSend getLastStateSmsSend(int employerId, String phone, int type) {

        Session session = getSession();
        Query q = session.createQuery("from StateSmsSend where sender = :employerId and receiver = :phone and type = :type order by lastUpdate desc ");
        q.setParameter("employerId", employerId + "");
        q.setParameter("phone", phone);
        q.setParameter("type", type);
        List<StateSmsSend> list = q.list();
        if(list.size() > 0)
        return list.get(0);
        return null;
	}
	
}
