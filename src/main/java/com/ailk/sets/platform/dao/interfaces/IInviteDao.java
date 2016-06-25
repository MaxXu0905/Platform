package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.param.GetInvitationInfoParam;

public interface IInviteDao extends IBaseDao<Invitation> {
	public void saveInvitations(List<Invitation> list);

	public Company getCompanyInfo(int employerId);

	public long getInvitationFailedNumber(Integer employerId, int positionId);

	public long getInvitationNumber(Integer employerId, int positionId);

	/**
	 * 获取某个邮箱地址最近的一条记录
	 * 
	 * @param address
	 * @return
	 * @throws PFDaoException
	 */
	public Invitation getInvitation(String address);

	/**
	 * 获取邀请信息
	 * 
	 * @param positionId
	 * @param page
	 * @return
	 * @throws PFDaoException
	 */
	public List<Invitation> getInvitaionInfo(GetInvitationInfoParam param);

	/**
	 * 删除失败的邀请
	 * 
	 * @param ids
	 * @throws PFDaoException
	 */
	public void delInvitations(List<Integer> ids, int employerId);

	/**
	 * 获取邀请
	 * 
	 * @param ids
	 * @return
	 */
	public List<Invitation> getInvitations(List<Integer> ids);

	/**
	 * 获取邀请
	 * 
	 * @param employerId
	 * @param positionId
	 * @return
	 */
	public List<Invitation> getInvitations(int positionId);

	public void clearInvitationErrtxt(Invitation invitation);

	/**
	 * 获取已邀请列表
	 * 
	 * @param positionId
	 * @param page
	 * @return
	 */
	public List<Invitation> getInvitationInfo(int employerId, int positionId, Page page);
	
	/**
	 * 获取某些人下的失败的邀请
	 * @param employerId
	 * @param positionId
	 * @param candidateName
	 * @param candidateEmail
	 * @return
	 */
	public List<Invitation> getFailedInvitations(int employerId, int positionId, String candidateName, String candidateEmail);
}
