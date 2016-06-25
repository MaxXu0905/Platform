package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.model.Page;

public interface IInvitationDao extends IBaseDao<Invitation> {
	/**
	 * 根据职位获取邀请
	 * 
	 * @param positionId
	 * @return
	 *//*
	public List<Invitation> getInvitations(int positionId);

	*//**
	 * 根据邀请id集合获取邀请
	 * 
	 * @param ids
	 * @return
	 *//*
	public List<Invitation> getInvitations(List<Integer> ids);

	*//**
	 * 根据职位和状态码获取邀请某状态下的邀请，如果state是空，则返回所有状态下的邀请
	 * @param positionId
	 * @param state
	 * @param page
	 * @return
	 *//*
	public List<Invitation> getInvitations(int positionId, String state, Page page);

	*//**
	 * 根据邀请id删除邀请
	 * 
	 * @param ids
	 *//*
	public void delInvitations(List<Integer> ids);

	*//**
	 * 获取失效邀请信息
	 * 
	 * @param positionId
	 * @param page
	 * @return
	 * @throws PFDaoException
	 *//*
	public List<Invitation> getInvitaionFailed(int positionId, Page page);

	*//**
	 * 根据职位和状态码获取邀请某状态下的邀请数量，如果state是空，则返回所有状态下的邀请数量
	 * 
	 * @param positionId
	 * @param state
	 * @return
	 *//*
	public long getInvitationCount(int positionId, String state);*/
	/**
	 * 获取正在考试的邀请
	 * @return
	 */
	public List<Invitation> getInvitationOfAnswning();
	
	
	/**
	 * 获取candidateTest过滤hql
	 * @param positionId
	 * @param employerId
	 * @return
	 */
	public String getCandidateTestFilterByEmployer(int positionId, int employerId);
	/**
	 * 获取invitation 过滤hql
	 * @param employerId
	 * @return
	 */
	public String getInvitationFilterByEmployer(int positionId, Integer employerId);
	
	
	/**
	 * 获取candidateTest过滤sql
	 * @param positionId
	 * @param employerId
	 * @return
	 */
	public String getCandidateTestFilterSqlByEmployer(int positionId, int employerId);
	/**
	 * 获取invitation 过滤sql
	 * @param employerId
	 * @return
	 */
	public String getInvitationFilterSqlByEmployer(int positionId, Integer employerId);

}
