package com.ailk.sets.platform.intf.cand.service;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.wx.IWXService;

public interface IWXCandService extends IWXService {
	/**
	 * 绑定应聘者的微信号
	 * 
	 * @param testId
	 * @param openId
	 * @return
	 * @throws PFServiceException
	 */
	public Candidate bindingCandidate(long testId, String openId) throws PFServiceException;
	
	
	/**
	 * 手动绑定应聘者的微信号
	 * @param openId 
	 * 
	 * @param testId
	 * @param openId
	 * @return
	 * @throws PFServiceException
	 */
	public Candidate bindingHandCandidate(String userName, String email, String openId) throws PFServiceException;

	/**
	 * 解绑定应聘者的微信号
	 * 
	 * @param openId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse unBindingCandidate(String openId) throws PFServiceException;

	/**
	 * 发送欢迎语
	 * @param openId
	 */
	public void sendWelcome(String openId);
	
	/**
	 *扫描关注二维码 
	 * @param openId
	 * @param candidateName 
	 */
	public void sendScanwelcome(String openId, String candidateName);
	
	/**
	 * 手动绑定成功回复
	 * */
	
	public void sendSuccessMessage(String openId,Candidate candidate);
	
	/**
	 * 手动绑定格式不正确
	 * */
	
	public String bindFailed(String openId);
	
	/**
	 * 手动绑定失败回复
	 * */
	
   public void sendFailedMessage(String openId);


    /**
     * 申请在线考试失败
     */
    void onlineReqFailed(String openId, String faildMsg);


    /**
     * 申请在线考试成功
     * @param candidate
     */
//    void onlineReqSuccess(String openId);


    /**
     * @param passport
     * @param username
     * @param email
     */
    public void onlineReqSave(String openId, String passport, String username, String email);
   
   
}
