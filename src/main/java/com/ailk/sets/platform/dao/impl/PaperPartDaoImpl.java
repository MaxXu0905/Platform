package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPaperPartDao;
import com.ailk.sets.platform.domain.paper.PaperPart;

@Repository
public class PaperPartDaoImpl extends BaseDaoImpl<PaperPart> implements IPaperPartDao {
	public List<PaperPart> getPaperPartsByPaperId(int paperId){
		Session session = getSession();
		Query q = session.createQuery("from PaperPart where id.paperId = " + paperId);
		return q.list();
	}
}
