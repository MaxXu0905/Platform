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

import com.ailk.sets.grade.dao.intf.ICandidateInfoExtDao;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;

@Repository
public class CandidateInfoExtDaoImpl implements ICandidateInfoExtDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public Map<String, String> getMap(int candidateId) {
		Session session = sessionFactory.getCurrentSession();

		Map<String, String> candidateInfoExtMap = new HashMap<String, String>();
		List<?> list = session
				.createQuery("FROM CandidateInfoExt Where id.candidateId = ?1")
				.setInteger("1", candidateId).list();
		Iterator<?> iter = list.iterator();
		while (iter.hasNext()) {
			CandidateInfoExt candidateInfoExt = (CandidateInfoExt) iter.next();
			String infoId = candidateInfoExt.getId().getInfoId();

			candidateInfoExtMap.put(infoId, candidateInfoExt.getRealValue());
		}

		return candidateInfoExtMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Map<String, CandidateInfoExt>> getCandidateInfoExts(
			List<Integer> candidateIds) {
		Session session = sessionFactory.getCurrentSession();

		Map<Integer, Map<String, CandidateInfoExt>> candidateMap = new HashMap<Integer, Map<String, CandidateInfoExt>>();
		List<CandidateInfoExt> list = session
				.createQuery(
						"FROM CandidateInfoExt Where id.candidateId in (:candidateIds)")
				.setParameterList("candidateIds", candidateIds).list();
		for (CandidateInfoExt candidateInfoExt : list) {
			Integer candidateId = candidateInfoExt.getId().getCandidateId();
			Map<String, CandidateInfoExt> candidateInfoExtMap = candidateMap
					.get(candidateId);
			if (candidateInfoExtMap == null) {
				candidateInfoExtMap = new HashMap<String, CandidateInfoExt>();
				candidateMap.put(candidateId, candidateInfoExtMap);
			}
			String infoId = candidateInfoExt.getId().getInfoId();
			candidateInfoExtMap.put(infoId, candidateInfoExt);
		}

		return candidateMap;
	}

}
