package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.util.PFUtils;

@Repository
public class InvitationDaoImpl extends BaseDaoImpl<Invitation> implements IInvitationDao {
	
	@Autowired
	private IPositionDao positionDao;

	/*@Override
	public List<Invitation> getInvitations(int positionId) {
		return getList(positionId, "positionId");
	}

	@Override
	public List<Invitation> getInvitations(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids))
			return null;
		String hql = "from Invitation where testId in (" + PFUtils.getIdString(ids) + ")";
		return getList(hql);
	}*/

	/*@Override
	public List<Invitation> getInvitations(int positionId, String state, Page page) {
		StringBuilder hql = new StringBuilder("from Invitation where positionId=" + positionId);
		if (StringUtils.isNotEmpty(state))
			hql.append(" and positionState in (" + state + ")");
		return getList(hql.toString(), page);
	}*/

	/*@Override
	public void delInvitations(List<Integer> ids) {
		if (!CollectionUtils.isEmpty(ids)) {
			String hql = "delete Invitation where testId in (" + PFUtils.getIdString(ids) + ")";
			excuteUpdate(hql);
		}
	}*/

	/*@Override
	public List<Invitation> getInvitaionFailed(int positionId, Page page) {
		String hql = "from Invitation where invitationState=0 and positionId=" + positionId;
		return getList(hql, page);
	}

	@Override
	public long getInvitationCount(int positionId, String state) {
		StringBuilder hql = new StringBuilder("select count(*) from Invitation where positionId=" + positionId);
		if (StringUtils.isNotEmpty(state))
			hql.append(" and positionState in (" + state + ")");
		Query query = getSession().createQuery(hql.toString());
		return (Long) query.uniqueResult();
	}
	*/
	/**
	 * 获取正在考试的邀请
	 * @return
	 */
	@Override
	public List<Invitation> getInvitationOfAnswning(){
		String hql = "from Invitation where invitationState = 1 and "
				+ "testId in (select testId from CandidateTest where testState = 1 " + ")";
		return getSession().createQuery(hql).list();
	}

	@Override
	public String getCandidateTestFilterByEmployer(int positionId, int employerId) {
		Position position = positionDao.getEntity(positionId);
		if (position == null || !position.getEmployerId().equals(employerId)) {//不是测评创建人
				return " and testId in (select testId from Invitation where employerId = " + employerId +")";
		} else{
			return "";
		}
	}
	
	@Override
	public String getInvitationFilterByEmployer(int positionId, Integer employerId) {
		if(employerId == null){
			return "";
		}
		Position position = positionDao.getEntity(positionId);
		if (position == null || !position.getEmployerId().equals(employerId)) {//不是测评创建人
				return " and employerId = " + employerId ;
		} else{
			return "";
		}
	}
	@Override
	public String getCandidateTestFilterSqlByEmployer(int positionId, int employerId) {
		Position position = positionDao.getEntity(positionId);
		if (position == null || !position.getEmployerId().equals(employerId)) {//不是测评创建人
				return " and test_id in (select test_id from Invitation where employer_id = " + employerId +")";
		} else{
			return "";
		}
	}
	
	@Override
	public String getInvitationFilterSqlByEmployer(int positionId, Integer employerId) {
		if(employerId == null){
			return "";
		}
		Position position = positionDao.getEntity(positionId);
		if (position == null || !position.getEmployerId().equals(employerId)) {//不是测评创建人
				return " and employer_id = " + employerId ;
		} else{
			return "";
		}
	}
}
