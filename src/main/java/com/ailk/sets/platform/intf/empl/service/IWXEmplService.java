package com.ailk.sets.platform.intf.empl.service;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.wx.IWXService;

public interface IWXEmplService extends IWXService {

	/**
	 * 绑定招聘者微信openId
	 * 
	 * @param qrCodeId
	 * @param openId
	 * @return
	 * @throws PFServiceException
	 */
	public Employer bindingEmployer(int qrCodeId, String openId) throws PFServiceException;

	/**
	 * 解绑定招聘者微信openId
	 * 
	 * @param openId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse unBindingEmployer(String openId) throws PFServiceException;

	/**
	 * 获取一个配置的url
	 * 
	 * @return
	 */
	public String getServiceBaseUrl();

	/**
	 * 发送欢迎信息
	 * @param openId
	 */
	public void sendWelcome(String openId , String link);
	
	/**
	 * 扫描关注绑定
	 * */
	
	public void sendScanwelcome(String openId, String candidateName ,String link);
	
	/**
	 * 点击按钮返回消息（未绑定）
	 * @param openId
	 * @param content
	 * */
	
	public void defaultMessageSend (String openId, String content);
	
	/**
	 * 点击按钮返回信息（已绑定）
	 * @param openId
	 * @param content
	 */
	
	public void messageSend (String openId , String content);
	
	/**
	 * 点击进入百一时判断用户是否进行过绑定
	 * */
	
	public String isBind(String openId);

}
