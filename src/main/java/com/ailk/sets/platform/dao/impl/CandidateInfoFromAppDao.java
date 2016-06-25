package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoFromAppDao;
import com.ailk.sets.platform.domain.CandidateInfoFromApp;
@Repository
public class CandidateInfoFromAppDao extends BaseDaoImpl<CandidateInfoFromApp> implements ICandidateInfoFromAppDao {

	@Override
	public List<CandidateInfoFromApp> getNeedSendInvitationCandidates() {
		Session session = getSession();
		Query q = session.createQuery("from CandidateInfoFromApp where testId is null");
	    return q.list();
	}
	
	
}
