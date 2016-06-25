package com.ailk.sets.platform.service.local;

import java.util.List;

import com.ailk.sets.platform.domain.AppStatusMonitor;
import com.ailk.sets.platform.domain.CandidateInfoFromApp;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.invatition.InviteResult;

public interface IAppMsgService {
	
	/**
	 * 获取需要发邀请的应聘人信息
	 * @return
	 */
  public List<CandidateInfoFromApp> getNeedSendInvitationCandidates();
  /**
   * 邀请应聘人
   * @param candidateInfoApp
   * @return
   */
  public InviteResult inviteByCandidateFromApp(CandidateInfoFromApp candidateInfoApp) throws PFServiceException;
  
  /**
   * 更新短信应聘者信息
   */
  public void updateCandidateInfoFromApp(CandidateInfoFromApp candidateInfoApp);
  /**
   * 
   * @param monitor
   */
  public void updateAppStatusMonitor(AppStatusMonitor monitor);
  
  /**
   * 获取最大休息时间
   * @return
   */
  public long getMaxSleepTimeForInvite() throws PFDaoException;
  
  /**
   * 获取监控对象
   * @return
   */
  public AppStatusMonitor getAppStatusMonitor();
  
  /**
   * 发送微信消息
   * @param lastNotifyStatus
   * @param nowStatus
   * @throws PFDaoException
   */
  public void sendWXNotifyMsg(int lastNotifyStatus,int nowStatus) throws Exception;
  
  /**
   * 失败邀请发送微信消息
   * @param lastNotifyStatus
   * @param nowStatus
   * @throws PFDaoException
   */
  public void sendWXInvitationFailedMsg(CandidateInfoFromApp candidateInfo, long testId) throws Exception;
  /**
   * 
   * @param candidateInfo
   * @param testId
   * @throws Exception
   */
  public void sendWXInvitationMsgForCan(CandidateInfoFromApp candidateInfo, long testId) throws Exception;
}
