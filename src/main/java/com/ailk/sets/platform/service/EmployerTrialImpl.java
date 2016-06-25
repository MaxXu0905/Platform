package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.common.MailSenderInfo;
import com.ailk.sets.platform.dao.interfaces.ICompanyDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyDomainNameDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyEmailserverDao;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerOperationLogDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerTrialDao;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.domain.ConfigSysParameters;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.domain.CompanyDomainName;
import com.ailk.sets.platform.intf.empl.domain.EmployerAuthorizationIntf;
import com.ailk.sets.platform.intf.empl.domain.EmployerCompanyInfo;
import com.ailk.sets.platform.intf.empl.domain.EmployerOperationLog;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialActiveResponse;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApply;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApplyActivite;
import com.ailk.sets.platform.intf.empl.service.IEmployerTrial;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.util.MD5;
import com.ailk.sets.platform.util.SimpleMailSender;
import com.ailk.sets.platform.util.TemplateHost;

@Transactional(rollbackFor = Exception.class)
public class EmployerTrialImpl implements IEmployerTrial {
	private Logger logger = LoggerFactory.getLogger(EmployerTrialImpl.class);
	@Autowired
	private ICompanyEmailserverDao companyEmailserverDaoImpl;
	@Autowired
	IEmployerTrialDao employerTrialDao;
	
	@Autowired
	private ICompanyDao companyDaoImpl;
	
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	
	@Autowired
	private IConfigDao configDao;
	
	@Autowired
	private IQbBaseDao qbBaseDao;
	@Autowired
	private IEmployerDao employerDao;
	@Autowired
	private ICompanyDomainNameDao companyDomainNameDao;
	@Autowired
	private IEmployerOperationLogDao employerOperationLogDao;
	/**
	 * 申请试用
	 * @param apply
	 * @return
	 */
	public PFResponse registEmployerTrial(EmployerTrialApply apply) throws PFServiceException{
		PFResponse response = new PFResponse();
		logger.debug("registEmployer is {}", apply.toString());
		String mail = apply.getUserEmail();
		response = supportEmail(mail);
		if(!response.getCode().equals(FuncBaseResponse.SUCCESS)){
			return response;
		}
		/*Employer employer = employerTrialDao.getEmployerByEmail(mail);
		if(employer != null){
			logger.warn("the employer for email  {} has been used ", mail);
			response.setCode(FuncBaseResponse.ACCTREGISTERED);
			return response;
		}*/
		EmployerTrialApply old = employerTrialDao.getEmployerTrialByEmail(mail);
		if(old != null){
			logger.warn("the EmployerTrialApply for email  {}  has been used,but not actived", mail);
//			response.setCode(FuncBaseResponse.ACCTREGISTERED);
//			return response;
		}
		/*if(!supportEmail(mail)){
			response.setCode(FuncBaseResponse.EMAILNOTSUPPORT);
			return response;
		}*/
		/*String suffixMail = mail.substring(mail.indexOf("@") + 1);
		if(configDao.getConfigCode("NOT_SUPPORT_EMAIL", suffixMail) != null){
			logger.warn("the EmployerTrialApply for email  {}  not support ", mail);
			response.setCode(FuncBaseResponse.EMAILNOTSUPPORT);
			return response;
		}*/
		ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.ACTIVITEURL);
		String url = csp.getParamValue();
		
		apply.setActivationKey(MD5.toMD5(apply.getUserEmail()));
		EmployerTrialApplyActivite active = new EmployerTrialApplyActivite();
		active.setUserName(apply.getUserName());
		active.setUrl(url + "/" + apply.getActivationKey());
		active.setUserEmail(apply.getUserEmail());
		response = sendActiveEmail(active);
		if(!FuncBaseResponse.SUCCESS.equals(response.getCode())){
			return response;
		}
		
		apply.setApplyDate(new Timestamp(System.currentTimeMillis()));
		apply.setActivated(0);
		
		if(old != null){//不是第一次
			old.setApplyDate(new Timestamp(System.currentTimeMillis()));
			old.setUserName(apply.getUserName());
			employerTrialDao.saveOrUpdate(old);
		}else{
			employerTrialDao.saveOrUpdate(apply);
		}
		
		Employer employer= employerTrialDao.getEmployerByEmail(mail);
		if(employer != null){//有可能已经体验或者外部调用，授权程序注册了name
			logger.info("the employer has registed for mail {} , employer {} ", mail, employer);
			employer.setEmployerName(apply.getUserName());
			employer.setAcctRole(apply.getAcctRole());
			employerDao.saveOrUpdate(employer);
		}
		response.setCode(FuncBaseResponse.SUCCESS);
		return response;
	}
	
	public EmployerTrialActiveResponse activeEmployerTrail(String key,String password,String userName) throws PFServiceException{
		EmployerTrialActiveResponse response = new EmployerTrialActiveResponse();
		logger.debug("activeEmployerTrail , the key is {}, password is {}  ", key,password);
		EmployerTrialApply old = employerTrialDao.getEmployerTrialByKey(key);
		if(old.getActivated() == 1){
			logger.error("the key {} is actived ,can not again ",key);
			response.setCode(FuncBaseResponse.ACCTHASACTIVETED);
			return response;
		}
		old.setUserPwd(password);
		old.setActivated(1);
		employerTrialDao.saveOrUpdate(old);
		//保存公司
	/*	Company company = new Company();
		company.setCompanyName(old.getCompany());
		qbNoramlDaoImpl.saveOrUpdate(company);*/
		//保存招聘者
		Employer employer = employerTrialDao.getEmployerByEmail(old.getUserEmail());//有可能已经通过体验或者第三方注册进来
		boolean newEmployer = false;
		if(employer == null){
			employer = new Employer();
			employer.setCreateDate(new Date());
			newEmployer  = true;
		}else{
			if(employer.getAcctType() == Constants.SAMPLE_ACCT_TYPE){
				logger.info("the employer has registed {} by sample, transfer to normal ",old.getUserEmail());
				employer.setAcctType(Constants.NORMAL_ACCT_TYPE);
			}else{
				logger.warn("the employer has registed {}, but not sample, please check ",old.getUserEmail());
			}
		}
		
//		employer.setCompanyId(company.getCompanyId());
		employer.setEmployerName(old.getUserName());
		employer.setEmployerAcct(old.getUserEmail());
		employer.setEmployerPwd(password);
		employer.setState(1);
		employer.setAcctRole(old.getAcctRole());
		if(StringUtils.isEmpty(employer.getEmployerName()) && StringUtils.isNotEmpty(userName)){
			logger.debug("the name of employer {} is null so update {} ",employer.getEmployerAcct(), userName);
			employer.setEmployerName(userName);
		}
		employerDao.saveOrUpdate(employer);
		if(newEmployer){
			employerTrialDao.initCompanyInfoExt(employer);
			qbBaseDao.initInterviewAndIntellQbBases(employer.getEmployerId());
		}
		response.setEmployerId(employer.getEmployerId());
		response.setEmployerName(employer.getEmployerName());
		response.setCode(FuncBaseResponse.SUCCESS);
		
		return response;
	}
	
	@Override
	public PFResponse sendActiveEmail(EmployerTrialApplyActivite apply) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			Company company = companyDaoImpl.getEntity(1);
//			contactInfoDaoImpl.saveOrUpdate(contactInfo);
			MailSenderInfo mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(company.getCompanyId(), "companyId"));
			mailSenderInfo.setSubject("百一测评-帐号激活");
			TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();
			context.put("entity", apply);
			mailSenderInfo.setToAddress(apply.getUserEmail());
			mailSenderInfo.setContent(templateHost.makeFileString(TemplateHost.VM_PUBLICTEMPLATE));
			SimpleMailSender.sendHtmlMail(mailSenderInfo);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			logger.error("error to  sendActiveEmail ", e);
			Map<String, String> mailExceptionMap = configDao.getConfigCodeMap(ConfigCodeType.MAILEXCEPTION);
			String excName = e.getClass().getName();
			String errtxt = mailExceptionMap.get(excName);
			pfResponse.setCode(FuncBaseResponse.SENDMAILERROR);
			if (!StringUtils.isEmpty(errtxt))
				pfResponse.setMessage(errtxt);
			else
				pfResponse.setMessage("未知原因");
		}
		return pfResponse;
	}
	
	  @Override
	  public EmployerTrialApply getEmployerTrialByKey(String key){
			logger.debug("activeEmployerTrail , the key is {}, password is {}  ", key);
			EmployerTrialApply old = employerTrialDao.getEmployerTrialByKey(key);
			if(old == null){
				logger.debug("not found any employerTrial for key {} ", key);
				return old;
			}
			Employer employer = employerDao.getEmployerByEmail(old.getUserEmail(), null);//如果employer没有名次，激活时需要传名称
			if(employer != null && StringUtils.isEmpty(employer.getEmployerName())){
				old.setNeedUserName(true);
			}
			return old;
	  }
	  /**
	    * 获取账号公司信息
	    * @param employerId
	    * @return
	    */
	   public EmployerCompanyInfo getEmployerCompanyInfo(Integer employerId){
		    logger.debug("getEmployerCompanyInfo , the employerId is {}  ", employerId);
		    EmployerCompanyInfo info = employerTrialDao.getEmployerCompanyInfo(employerId);
			return info;
	   }
	   
	   /**
	    * 保存公司信息
	    * @param employerId
	    * @return
	    */
	   public PFResponse saveEmployerCompanyInfo(Integer employerId,EmployerCompanyInfo info){
		    PFResponse fp = new PFResponse();
		    //保存公司
			Company company = new Company();
			company.setCompanyName(info.getCompanyName());
			companyDaoImpl.saveOrUpdate(company);//保存公司
			Employer employer = employerDao.getEntity(employerId);
			employer.setCompanyId(company.getCompanyId());//更新empoyerId
			
			String email = employer.getEmployerAcct();
			String emailSuffix = email.substring(email.indexOf("@") + 1);
		    CompanyDomainName cdn = companyDomainNameDao.getEntity(emailSuffix);
		    if(cdn == null){
		    	cdn = new CompanyDomainName();
		    	cdn.setDomainName(emailSuffix);
		    }
		    cdn.setCompanyName(info.getCompanyName());
		    companyDomainNameDao.saveOrUpdate(cdn);
		    logger.debug("update CompanyDomainName name {} for domain {} ",info.getCompanyName(),emailSuffix);
			fp.setCode(FuncBaseResponse.SUCCESS);
			return fp;
	   }
	   
	   /**
	    * 保存招聘页面日志信息
	    * @param log
	    * @return
	    */
	   public PFResponse saveEmployerOperationLog(EmployerOperationLog log){
           logger.debug("saveEmployerOperationLog log {} ", log);		   
		   if(log != null && log.getOperType() == 3){
			   EmployerTrialApply apply = getEmployerTrialByKey(log.getEmployerAcct());
			   if(apply != null){
				   log.setEmployerAcct(apply.getUserEmail());
			   }
		   }
		   PFResponse fp = new PFResponse();
		   log.setOperDate(new Timestamp(System.currentTimeMillis()));
		   employerOperationLogDao.saveOrUpdate(log);
		   fp.setCode(FuncBaseResponse.SUCCESS);
		   return fp;
	   }

	@Override
	public PFResponse registEmployerQuickly(EmployerRegistInfo registInfo) {
		EmployerTrialActiveResponse response = new EmployerTrialActiveResponse();
		String mail = registInfo.getEmployerEmail();
		Employer employer = employerTrialDao.getEmployerByEmail(mail);
		if(employer != null){
			logger.info("the employer for email  {} has been used ", mail);
			response.setCode(FuncBaseResponse.ACCTREGISTERED);
			response.setEmployerId(employer.getEmployerId());
			registInfo.setEmployerId(employer.getEmployerId());
			return response;
		}
		//保存招聘者
	    employer = new Employer();
		//保存公司
		if(StringUtils.isNotEmpty(registInfo.getCompanyName())){
			Company company = new Company();
			company.setCompanyName(registInfo.getCompanyName());
			companyDaoImpl.saveOrUpdate(company);
			employer.setCompanyId(company.getCompanyId());
		}
		
		employer.setEmployerName(registInfo.getEmployerName());
		employer.setEmployerAcct(mail);
		employer.setEmployerPwd(MD5.toMD5("123456abc"));
		employer.setCreateDate(new Date());
		employer.setState(1);
		employer.setAcctType(Constants.SAMPLE_ACCT_TYPE);//第三方或者体验
		employerDao.saveOrUpdate(employer);
		
		registInfo.setEmployerId(employer.getEmployerId());

		employerTrialDao.initCompanyInfoExt(employer);
		qbBaseDao.initInterviewAndIntellQbBases(employer.getEmployerId());
		response.setEmployerId(employer.getEmployerId());
		response.setEmployerName(employer.getEmployerName());
		response.setCode(FuncBaseResponse.SUCCESS);
		return response;
	}

	@Override
	public PFResponse supportEmail(String mail) throws PFServiceException {
		PFResponse response = new PFResponse();
		Employer employer = employerTrialDao.getNormalEmployerByEmail(mail);//获取正常注册用户
		if(employer != null){
			logger.warn("the employer for email  {} has been used ", mail);
			response.setCode(FuncBaseResponse.ACCTREGISTERED);
			return response;
		}
		
		String suffixMail = mail.substring(mail.indexOf("@") + 1);
		if(configDao.getConfigCode("NOT_SUPPORT_EMAIL", suffixMail) != null){
			logger.warn("the EmployerTrialApply for email  {}  not support ", mail);
			response.setCode(FuncBaseResponse.EMAILNOTSUPPORT);
			return response;
		}
		response.setCode(FuncBaseResponse.SUCCESS);
		return response;
	}

	@Override
	public Employer getEmployerByEmail(String email, Integer acctType) {
		return employerDao.getEmployerByEmail(email, acctType);
	}
	
	@Override
	public PFResponse inviteEmployerJoin(EmployerAuthorizationIntf employerAuth) throws PFServiceException {
		String email = employerAuth.getEmailGranted();
		
		PFResponse res = supportEmail(email);
		if (!res.getCode().equals(FuncBaseResponse.SUCCESS)) {
			return res;
		}
		res = sendJoinEmail(employerAuth);
		if(!FuncBaseResponse.SUCCESS.equals(res.getCode())){
			return res;
		}
		
		EmployerRegistInfo info = new EmployerRegistInfo();
		info.setEmployerEmail(email);
		res = registEmployerQuickly(info);
		
		
		EmployerTrialApply old = employerTrialDao.getEmployerTrialByEmail(email);
		if(old != null){
			logger.warn("the EmployerTrialApply for email  {}  has been used,but not actived", email);
		}
		
		if(old != null){//不是第一次
			old.setApplyDate(new Timestamp(System.currentTimeMillis()));
//			old.setUserName(apply.getUserName());
			employerTrialDao.saveOrUpdate(old);
		}else{
			EmployerTrialApply apply = new EmployerTrialApply() ; 
			apply.setUserEmail(email);
			apply.setActivationKey(MD5.toMD5(email));
			apply.setApplyDate(new Timestamp(System.currentTimeMillis()));
			apply.setActivated(0);
			employerTrialDao.saveOrUpdate(apply);
		}
		res.setCode(FuncBaseResponse.SUCCESS);
		return res;
	}

	public PFResponse sendJoinEmail(EmployerAuthorizationIntf employerAuth) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			int employerId = employerAuth.getEmployerId();
			Employer employer = employerDao.getEntity(employerId);
			Company employerCompany = null;
			if(employer.getCompanyId() != null)
			   employerCompany = companyDaoImpl.getEntity(employer.getCompanyId());
			
			String email = employerAuth.getEmailGranted();
			Company company = companyDaoImpl.getEntity(1);
			// contactInfoDaoImpl.saveOrUpdate(contactInfo);
			MailSenderInfo mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(
					company.getCompanyId(), "companyId"));
			mailSenderInfo.setSubject(employer.getEmployerName() + "邀请您加入百一测评");
			TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();
			Map<String,String> param = new HashMap<String,String>();
			param.put("path", "employerJoin");
			param.put("employerMail", email);
			param.put("employerName", employer.getEmployerName());//邀请加入时没有姓名，直接显示邮箱
			String url = configSysParamDao.getConfigParamValue(ConfigSysParam.ACTIVITEURL);
			param.put("url", url + "/" + MD5.toMD5(email));
			
			if(employerCompany != null)
			param.put("companyName", employerCompany.getCompanyName());
			context.put("entity", param);
			mailSenderInfo.setToAddress(email);
			mailSenderInfo.setContent(templateHost.makeFileString(TemplateHost.VM_PUBLICTEMPLATE));
			SimpleMailSender.sendHtmlMail(mailSenderInfo);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			logger.error("error to  sendJoinEmail ", e);
			Map<String, String> mailExceptionMap = configDao.getConfigCodeMap(ConfigCodeType.MAILEXCEPTION);
			String excName = e.getClass().getName();
			String errtxt = mailExceptionMap.get(excName);
			pfResponse.setCode(FuncBaseResponse.SENDMAILERROR);
			if (!StringUtils.isEmpty(errtxt))
				pfResponse.setMessage(errtxt);
			else
				pfResponse.setMessage("未知原因");
		}
		return pfResponse;
	}
}
