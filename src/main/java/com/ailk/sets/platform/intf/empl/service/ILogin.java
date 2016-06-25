package com.ailk.sets.platform.intf.empl.service;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.ContactInfo;
import com.ailk.sets.platform.intf.model.employer.LoginInfo;
import com.ailk.sets.platform.intf.model.employer.RegisterInfo;

/**
 * 登录验证服务
 * 
 * @author 毕希研
 * 
 */
public interface ILogin {
	/**
	 * 寻求商业合作
	 * 
	 * @param contactInfo
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse consultCooperation(ContactInfo contactInfo) throws PFServiceException;

	/**
	 * 招聘人登录验证
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @throws PFServiceException
	 */
	public LoginInfo certify(String username, String password) throws PFServiceException;

	/**
	 * 招聘人登录验证
	 * @param username
	 * @param password
	 * @param ticket
	 * @return
	 * @throws PFServiceException
	 */
	public LoginInfo certify(String username, String password, String ticket) throws PFServiceException;

	/**
	 * 招聘人申请账号
	 * 
	 * @param employer
	 * @return
	 * @throws PFServiceException
	 */
	public RegisterInfo register(Employer employer) throws PFServiceException;

	/**
	 * 重设密码
	 * 
	 * @param employerId
	 * @param passwordOld
	 * @param passwordNew
	 * @return
	 * @throws PFServiceException
	 */
	public LoginInfo resetPassword(int employerId, String passwordOld, String passwordNew) throws PFServiceException;
	

    /**
     * 更新openId
     * @param employerId
     * @param openId
     * @return
     * @throws PFServiceException
     */
	public PFResponse updateOpenId(int employerId, String openId) throws PFServiceException;
	

	/**
	 * 创建新密码
	 * 
	 * @param uuid
	 * @param passwordNew
	 * @return
	 * @throws PFServiceException
	 */
	public LoginInfo resetPassword(String uuid, String passwordNew) throws PFServiceException;

	/**
	 * 忘记密码
	 * 
	 * @param email
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse forgetPassword(String email) throws PFServiceException;

	/**
	 * 验证忘记密码后重设链接的有效性
	 * 
	 * @param uuid
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse validateForgetPass(String uuid) throws PFServiceException;
	/**
	 * 根据openId获取employer
	 * @param openId
	 * @return
	 */
	public Employer getEmployerByOpenId(String openId, int employerId);
	
	/**
	 * 根据openId获取employer
	 * @param openId
	 * @return
	 */
	public Employer getEmployerByOpenId(String openId);
	
	public Employer getEmployerByEmployerId(Integer employerId);
	
	
	/**
	 * demo用户根据ticket登陆
	 * @param demoTicket
	 * @return
	 * @throws PFServiceException
	 */
	public LoginInfo certifyDemo(String demoTicket) throws PFServiceException;
}
