package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IInvitationCopytoDao;
import com.ailk.sets.platform.intf.domain.InvitationCopyto;

@Repository
public class InvitationCopytoDaoImpl extends BaseDaoImpl<InvitationCopyto> implements IInvitationCopytoDao {

	@Override
	public List<InvitationCopyto> getInvitationCopytosByTestId(long testId) {
		Session session = getSession();
		Query q = session.createQuery("from InvitationCopyto where  id.testId = " + testId);		
		return q.list();
	}
	
}

