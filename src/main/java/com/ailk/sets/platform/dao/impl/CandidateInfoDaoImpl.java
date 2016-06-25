package com.ailk.sets.platform.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.ConfigCollege;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.model.Mapping;
import com.ailk.sets.platform.util.PFUtils;

/**
 * 扩展信息dao实现类
 * 
 * @author 毕希研
 * 
 */
@Repository
public class CandidateInfoDaoImpl extends BaseDaoImpl<CandidateInfoExt>
		implements ICandidateInfoDao {

	private Logger logger = LoggerFactory.getLogger(CandidateInfoDaoImpl.class);

	/**
	 * @return
	 * @throws PFDaoException
	 */
	@SuppressWarnings("unchecked")
	public List<PositionInfoExt> getCandConfigInfoExts(int employerId,
			Integer positionId) {
		logger.debug(
				"getCandConfigInfoExts the employerId {} , positionId {} ",
				employerId, positionId);
		Session session = null;
		List<PositionInfoExt> list;
		session = sessionFactory.getCurrentSession();
		if (positionId != null && positionId != -1) {
			Query posiConf = session
					.createQuery("from PositionInfoExt where id.positionId = "
							+ positionId + " and  id.employerId =" + employerId
							+ " order by seq");
			list = posiConf.list();
			logger.debug("the positioninfoext size is {}  for employerId {} "
					+ positionId, list.size(), employerId);
			if (list.size() > 0)
				return list;
		} else {
			Position lastPosition = (Position) session
					.createQuery(
							"from Position where employerId = " + employerId
									+ " order by publishDate desc ")
					.setMaxResults(1).uniqueResult();
			if (lastPosition != null) {
				Query posiConf = session
						.createQuery("from PositionInfoExt where id.positionId = "
								+ lastPosition.getPositionId()
								+ " and  id.employerId ="
								+ employerId
								+ " order by seq");
				list = posiConf.list();
				logger.debug(
						"the positioninfoext size is {}  for employerId {} "
								+ lastPosition.getPositionId(), list.size(),
						employerId);
				if (list.size() > 0)
					return list;
			}
		}
		logger.debug("get the default ...for employerId {} ", employerId);
		Query q = session
				.createQuery("from PositionInfoExt where id.employerId ="
						+ employerId + " and id.positionId = -1 order by seq");
		list = q.list();
		return list;
	}

	public int getEmployerId(long testId) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("from Position where positionId in (select positionId from Invitation where testId="
						+ testId + ")");
		Position p = (Position) q.uniqueResult();
		return (p == null) ? -1 : p.getEmployerId();
	}

	public Map<String, CandidateInfoExt> getCandidateInfoExts(String name,
			String email) {
		Session session = sessionFactory.getCurrentSession();
		Map<String, CandidateInfoExt> result = new HashMap<String, CandidateInfoExt>();
		Query q = session
				.createQuery("from CandidateInfoExt where id.candidateId in (select candidateId from Candidate where candidateName='"
						+ name + "' and candidateEmail='" + email + "')");
		@SuppressWarnings("unchecked")
		List<CandidateInfoExt> list = q.list();
		if (!CollectionUtils.isEmpty(list)) {
			for (CandidateInfoExt c : list)
				result.put(c.getId().getInfoId(), c);
		}
		return result;
	}

	/**
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mapping> getRangeMap(String sql) {
		System.out.println("sql=============" + sql);
		List<Mapping> result = new ArrayList<Mapping>();
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery(sql);

		List<Object[]> list = q.list();
		if (!CollectionUtils.isEmpty(list)) {
			for (Object[] o : list) {
				Mapping rm = new Mapping();
				rm.setKey(o[0].toString());
				rm.setValue(o[1].toString());
				result.add(rm);
			}
			System.out.println(result.size());
		}
		return result;
	}

	/**
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mapping> getQueryInfo(String sql) {
		logger.debug("query sql =========== {} ", sql);
		List<Mapping> result = new ArrayList<Mapping>();
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery(sql);

		List<Object[]> list = q.list();
		if (!CollectionUtils.isEmpty(list)) {
			for (Object[] o : list) {
				Mapping rm = new Mapping();
				rm.setKey(o[0].toString());
				rm.setValue(o[1].toString());
				result.add(rm);
			}
		}
		return result;
	}

	public Invitation getInvitation(long testId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Invitation.class);
		criteria.add(Restrictions.eq("testId", testId));
		return (Invitation) criteria.uniqueResult();
	}

	public boolean saveCandidateInfo(int candidateId,
			List<CandidateInfoExt> list) {
		Session session = sessionFactory.getCurrentSession();
		if (!CollectionUtils.isEmpty(list))
			for (CandidateInfoExt cie : list) {
				cie.getId().setCandidateId(candidateId);
				session.saveOrUpdate(cie);
			}
		// session.flush();
		return true;
	}

	public CandidateTest getCandidateTest(long paperInstId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateTest.class);
		criteria.add(Restrictions.eq("paperInstId", paperInstId));
		return (CandidateTest) criteria.uniqueResult();
	}

	@Override
	public Candidate getCandidateById(int candidateId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Candidate.class);
		criteria.add(Restrictions.eq("candidateId", candidateId));
		return (Candidate) criteria.uniqueResult();
	}

	public int getEmployerId(String companyWeixinId, String passcode) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("from CompanyRecruitActivity where passcode = '"
						+ passcode
						+ "' and companyId =  (select companyId from Company where weixinNo='"
						+ companyWeixinId + "')");
		CompanyRecruitActivity c = (CompanyRecruitActivity) q.uniqueResult();
		Position p = (Position) session.get(Position.class, c.getPositionId());
		return p.getEmployerId();
		// return (c == null) ? -1 : c.getEmployerId();
	}

	public Map<String, CandidateInfoExt> getCandidateInfoExts(String weixinId) {
		Session session = sessionFactory.getCurrentSession();
		Map<String, CandidateInfoExt> result = new HashMap<String, CandidateInfoExt>();
		Query q = session
				.createQuery("from CandidateInfoExt where id.candidateId in (select candidateId from Candidate where weixinNo='"
						+ weixinId + "')");
		@SuppressWarnings("unchecked")
		List<CandidateInfoExt> list = q.list();
		if (!CollectionUtils.isEmpty(list)) {
			for (CandidateInfoExt c : list)
				result.put(c.getId().getInfoId(), c);
		}
		return result;
	}

	public Candidate getCandiateByWeixinNo(String weixinNo) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Candidate.class);
		criteria.add(Restrictions.eq("weixinNo", weixinNo));
		List<Candidate> list =   (List<Candidate>)criteria.list();
		Candidate candidate = null;
		if(list != null && list.size() >  0){
			candidate = list.get(0);
			if(list.size() > 1 ){
				logger.warn("the size is more than one for weixinNo {} ", weixinNo);
			}
		}
		return candidate;
	}

	public int getCandidateIdWithWeixin(String openId ,String name, String email,
			String weixinNo) {
		Session session = null;
		int id = -1;
		session = sessionFactory.getCurrentSession();
		/*
		 * Criteria criteria = session.createCriteria(Candidate.class);
		 * criteria.add(Restrictions.eq("weixinNo", weixinNo)); Candidate
		 * candidate = (Candidate) criteria.uniqueResult();
		 */

		Criteria criteria = session.createCriteria(Candidate.class);
		criteria.add(Restrictions.eq("candidateName", name));
		criteria.add(Restrictions.eq("candidateEmail", email));
		Candidate candidate = (Candidate) criteria.uniqueResult();
		if (candidate == null) {
			logger.debug("the candidate is null for weixinNo {}", weixinNo);
			candidate = new Candidate();
		} else {
			logger.debug(
					"the candidate is not null for weixinNo {}, so update email {} , name "
							+ name, weixinNo, email);
		}
		candidate.setCandidateName(name);
		candidate.setCandidateEmail(email);
		candidate.setWeixinNo(weixinNo);
		if(!StringUtils.isBlank(openId))
		{
		    candidate.setOpenId(openId);
		}
		session.saveOrUpdate(candidate);
		id = candidate.getCandidateId();
		return id;
	}

	/*public CompanyRecruitActivity getCompanyRecruitActivity(
			String companyWeixinNo, String passcode) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("from CompanyRecruitActivity where passcode = '"
						+ passcode
						+ "' and companyId =  (select companyId from CompanySocialNo where weixinNo='"
						+ companyWeixinNo + "')");
		CompanyRecruitActivity c = (CompanyRecruitActivity) q.uniqueResult();
		return c;
	}*/

	@SuppressWarnings("unchecked")
	public List<CandidateInfoExt> getCandidateInfoExts(List<Integer> ids,
			String infoId) {
		if(ids == null || ids.size() == 0){
			return new ArrayList<CandidateInfoExt>();
		}
		Session session = getSession();
		String hql = "from CandidateInfoExt where id.candidateId in ("
				+ PFUtils.getIdString(ids) + ") and id.infoId = '" + infoId
				+ "'";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public CandidateInfoExt getCandidateInfoExt(int id, String infoId) {
		Session session = getSession();
		String hql = "from CandidateInfoExt where id.candidateId =" + id
				+ " and id.infoId = '" + infoId + "'";
		Query query = session.createQuery(hql);
		return (CandidateInfoExt) query.uniqueResult();
	}

	public List<ConfigCollege> getConfigColleges(String searchTxt, int size) {
		searchTxt = searchTxt.toUpperCase();
		Session session = getSession();
		Criteria criteria = session.createCriteria(ConfigCollege.class);
		criteria.add(Restrictions.or(
				Restrictions.like("collegeName", searchTxt, MatchMode.ANYWHERE),
				Restrictions.like("matchName", searchTxt, MatchMode.ANYWHERE)));
		criteria.setMaxResults(size);
		return (List<ConfigCollege>) criteria.list();
	}
}
