package com.ailk.sets.platform.intf.empl.service;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.EmployerAuthorizationIntf;
import com.ailk.sets.platform.intf.empl.domain.EmployerCompanyInfo;
import com.ailk.sets.platform.intf.empl.domain.EmployerOperationLog;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialActiveResponse;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApply;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApplyActivite;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public interface IEmployerTrial  {
/**
 * 申请试用
 * @param apply
 * @return
 */
public PFResponse registEmployerTrial(EmployerTrialApply apply) throws PFServiceException;
/**
 * 激活
 * @param key
 * @param password
 * @param userName  如果原来没有输入名称，激活时需要输名称
 * @return
 * @throws PFServiceException
 */
public EmployerTrialActiveResponse activeEmployerTrail(String key,String password,String userName) throws PFServiceException;

public PFResponse sendActiveEmail(EmployerTrialApplyActivite apply) throws PFServiceException;

   public EmployerTrialApply getEmployerTrialByKey(String key);
   /**
    * 获取账号公司信息
    * @param employerId
    * @return
    */
   public EmployerCompanyInfo getEmployerCompanyInfo(Integer employerId);
 
   /**
    * 保存公司信息
    * @param employerId
    * @return
    */
   public PFResponse saveEmployerCompanyInfo(Integer employerId,EmployerCompanyInfo info);
   
   /**
    * 保存招聘页面日志信息
    * @param log
    * @return
    */
   public PFResponse saveEmployerOperationLog(EmployerOperationLog log);


   /**
    * 第三方快速注册接口
    * @param registInfo
    * @return
    */
   public PFResponse registEmployerQuickly(EmployerRegistInfo registInfo);
   
   
   /**
    * 
    * @param email
    * @return
    * @throws PFServiceException
    */
   public PFResponse supportEmail(String email) throws PFServiceException;
   
    /**
     * 获取体验用户信息
     * @param email
     * @param acctType
     * @return
     */
   public Employer getEmployerByEmail(String email, Integer acctType) ;
   
	/**
	 * 邀请加入
	 * @param employerAuth
	 * @return
	 */
	public PFResponse inviteEmployerJoin(EmployerAuthorizationIntf employerAuth) throws PFServiceException;
   
   
}
