package com.ailk.sets.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IInviteDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionRelationDao;
import com.ailk.sets.platform.domain.PositionRelation;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.param.GetInvitationInfoParam;
import com.ailk.sets.platform.util.PFUtils;
import com.alibaba.dubbo.common.utils.StringUtils;

@Repository
public class InviteDaoImpl extends BaseDaoImpl<Invitation> implements
		IInviteDao {
	@Autowired
	private IInvitationDao invitationDao;
	@Autowired
	private IPositionDao positionDao;
	
	@Autowired
	private IPositionRelationDao positionRelationDao;
	@Override
	public void saveInvitations(List<Invitation> list) {
		Session session = sessionFactory.getCurrentSession();
		if (!CollectionUtils.isEmpty(list))
			for (Invitation invitation : list) {
				session.saveOrUpdate(invitation);
			}
	}

	public long getInvitationFailedNumber(Integer employerId, int positionId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select count(*) from Invitation where  ( positionId = " + positionId +" or positionId in (select id.positionId from PositionRelation where id.positionGroupId ="+ positionId +"))"
				+ " and invitationState=0";
		hql += invitationDao.getInvitationFilterByEmployer(positionId, employerId);
		Query query = session
				.createQuery(hql);
		Long result = (Long) query.uniqueResult();
		return result == null ? 0 : result;
	}

	public Company getCompanyInfo(int employerId) {
		Session session = getSession();
		Query query = session
				.createQuery("from Company where companyId = (select companyId from Employer where employerId = "
						+ employerId + ")");
		return (Company) query.uniqueResult();
	}

	@Override
	public long getInvitationNumber(Integer employerId, int positionId) {
		Session session = getSession();
		String hql = "select count(*) from Invitation where  ( positionId = " + positionId +" or positionId in (select id.positionId from PositionRelation where id.positionGroupId ="+ positionId +"))" ;
		hql += invitationDao.getInvitationFilterByEmployer(positionId, employerId);
		Query query = session.createQuery(hql);
		Long result = (Long) query.uniqueResult();
		return result == null ? 0 : result;
	}

	@SuppressWarnings("unchecked")
	public List<Invitation> getInvitaionInfo(GetInvitationInfoParam param) {
		Page page = param.getPage();
		Criteria criteria = getCriteria();
//		criteria.add(Restrictions.eq("employerId", param.getEmployerId()));
		if (param.getInvitationState() != null)
			criteria.add(Restrictions.eq("invitationState",
					param.getInvitationState()));
		
		Position position = positionDao.getEntity(param.getPositionId());
		if(position.getGroupFlag() == Constants.GROUP_FLAG_NORMAL)
		   criteria.add(Restrictions.eq("positionId", param.getPositionId()));
		else if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
			List<PositionRelation> relations = positionRelationDao.getPositionRelationByPositionGroupId(param.getPositionId());
			List<Integer> ids = new ArrayList<Integer>();
			for(PositionRelation relation : relations){
				ids.add(relation.getId().getPositionId());
			}
			criteria.add(Restrictions.in("positionId", ids));
		}else{
			throw new RuntimeException("the child position can not access for positionId " + param.getPositionId());
		}
		if (!position.getEmployerId().equals(param.getEmployerId())) {// 查询人不是测评创建人
			criteria.add(Restrictions.eq("employerId", param.getEmployerId()));
		}
		if (param.getLowerDate() != null)
			criteria.add(Restrictions.ge("invitationDate", param.getLowerDate()));
		criteria.addOrder(Order.desc("invitationDate"));
		criteria.setFirstResult((page.getRequestPage() - 1)
				* page.getPageSize());
		criteria.setMaxResults(page.getPageSize());
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Invitation getInvitation(String address) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Invitation.class);
		criteria.add(Restrictions.eq("candidateEmail", address));
		criteria.add(Restrictions.eq("invitationState", 1));
		criteria.addOrder(Order.desc("invitationDate"));

		List<Invitation> list = criteria.list();
		if (!CollectionUtils.isEmpty(list))
			return list.get(0);
		else
			return null;
	}

	@Override
	public void delInvitations(List<Integer> ids, int employerId) {
		String idStr = PFUtils.getIdString(ids);
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete Invitation where testId in ("
				+ idStr + ") and employerId = " + employerId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invitation> getInvitations(List<Integer> ids) {
		String idStr = PFUtils.getIdString(ids);
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Invitation where testId in ("
				+ idStr + ")");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invitation> getInvitations(int positionId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Invitation where positionId="
				+ positionId);
		return query.list();
	}

	@Override
	public void clearInvitationErrtxt(Invitation invitation) {
		Session session = sessionFactory.getCurrentSession();
		invitation.setInvitationErrtxt("");
		session.update(invitation);
	}

	/**
	 * 获取已邀请列表
	 * 
	 * @param positionId
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Invitation> getInvitationInfo(int employerId, int positionId,
			Page page) {
		Session session = getSession();
		String hql = " from Invitation where invitationState != 0 and " +
		     " ( positionId = " + positionId +" or positionId in (select id.positionId from PositionRelation where id.positionGroupId ="+ positionId +"))" ;
		hql += invitationDao.getInvitationFilterByEmployer(positionId, employerId);
		hql += " order by invitationDate desc ";
		Query query = session.createQuery(hql);
		query.setFirstResult((page.getRequestPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}

	@Override
	public List<Invitation> getFailedInvitations(int employerId, int positionId, String candidateName, String candidateEmail) {
		Session session = getSession();
		String hql = " from Invitation where invitationState = 0 " ;
		if(!StringUtils.isEmpty(candidateName) && !StringUtils.isEmpty(candidateEmail)){
			hql +="  and candidateName = :candidateName and candidateEmail = :candidateEmail " ;
		}
			hql += "  and ( positionId = " + positionId +" or positionId in (select id.positionId from PositionRelation where id.positionGroupId ="+ positionId +"))";
        Query q =  session.createQuery(hql);
		if (!StringUtils.isEmpty(candidateName) && !StringUtils.isEmpty(candidateEmail)) {
			q.setString("candidateName", candidateName);
			q.setString("candidateEmail", candidateEmail);
		}
        return q.list();
	}

}
