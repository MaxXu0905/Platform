package com.ailk.sets.grade.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.ICandidateTestPartDao;
import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.intf.common.PaperPartTimerType;

@Repository
public class CandidateTestPartDaoImpl extends BaseDaoImpl<CandidateTestPart>
		implements ICandidateTestPartDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateTestPart> getList(long testId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CandidateTestPart WHERE id.testId = ?1 AND id.partSeq <= 20")
				.setLong("1", testId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateTestPart> getAllCandidateTestPartByTestId(long testId) {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery(
				"FROM CandidateTestPart WHERE id.testId = " + testId).list();
	}

	@Override
	public int getCandidateTestTimerType(long testId) {
		List<CandidateTestPart> parts = getList(testId);
		Map<Integer,Integer> types = new HashMap<Integer, Integer>();
		for(CandidateTestPart part : parts){
			if(part.getTimerType() != null){
				types.put(part.getTimerType(), part.getTimerType());
			}
		}
		if(types.size() > 1){
			return PaperPartTimerType.MIXED;
		}
		Iterator<Integer> its = types.values().iterator();
		return its.next();
	}

}
