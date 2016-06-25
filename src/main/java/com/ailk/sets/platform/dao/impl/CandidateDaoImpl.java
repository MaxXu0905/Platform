package com.ailk.sets.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.grade.intf.report.InterviewInfo.Mapping;
import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.intf.domain.Candidate;

@Repository
public class CandidateDaoImpl extends BaseDaoImpl<Candidate> implements
		ICandidateDao {

	private Logger logger = LoggerFactory.getLogger(CandidateDaoImpl.class);
	@Override
	public Candidate getByTestId(long testId) {
		Session session = getSession();
		String hql = "from Candidate where candidateId = (select candidateId from CandidateTest where testId = "
				+ testId + ")";
		Query query = session.createQuery(hql);
		return (Candidate) query.uniqueResult();
	}

	public int getCandidateId(String name, String email) {
		int id = -1;
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("candidateName", name));
		criteria.add(Restrictions.eq("candidateEmail", email));
		Candidate candidate = (Candidate) criteria.uniqueResult();
		if (candidate == null) {
			Candidate c = new Candidate();
			c.setCandidateName(name);
			c.setCandidateEmail(email);
			saveOrUpdate(c);
			id = c.getCandidateId();
		} else
			id = candidate.getCandidateId();
		return id;
	}
	
	/**
	 * 绑定用户；如果存在用户更新openId，如果不存在用户，新增用户
	 * @param name
	 * @param email
	 * @param openId
	 * @return
	 */
	public Candidate getCandidate(String name, String email , String openId) {
	    Criteria criteria = getCriteria();
	    criteria.add(Restrictions.eq("candidateName", name.trim()));
	    criteria.add(Restrictions.eq("candidateEmail", email.trim()));
	    Candidate candidate = (Candidate) criteria.uniqueResult();
	    if (candidate == null) {
	        Candidate c = new Candidate();
	        c.setCandidateName(name);
	        c.setCandidateEmail(email);
	        c.setOpenId(openId);
	        saveOrUpdate(c);
	        return c;
	    } else
	    {
	        candidate.setOpenId(openId);
	        return candidate;
	    }
	}

	@Override
	public int removeOpenId(String openId) {
		Session session = getSession();
		String hql = "update Candidate set openId = null where openId='"
				+ openId + "'";
		Query query = session.createQuery(hql);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "sql")
	public List<Mapping> getMapping(String sql) {
		List<Mapping> result = new ArrayList<Mapping>();
		Session session = sessionFactory.getCurrentSession();

		List<Object[]> list = session.createSQLQuery(sql).list();
		if (!CollectionUtils.isEmpty(list)) {
			for (Object[] obj : list) {
				Mapping mapping = new Mapping();
				mapping.setKey(obj[0].toString());
				mapping.setValue(obj[1].toString());
				result.add(mapping);
			}
		}

		return result;
	}

	@Override
	@CacheEvict(value = "sql", allEntries = true)
	public void evict() {
	}

	@Override
	public Candidate getCandidateByOpenId(String openId) {
		Session session = getSession();
		String hql = "from Candidate where openId = :openId";
		Query query = session.createQuery(hql);
		query.setString("openId", openId);
		List<Candidate> candidates = query.list();
		if(candidates.size() > 0){
            if(candidates.size() > 1){
            	logger.warn("the candidates size is {} ", candidates.size());
            }
			return candidates.get(0);
		}
		return null;
	}

}
