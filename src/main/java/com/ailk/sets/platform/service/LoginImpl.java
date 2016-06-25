package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.common.MailSenderInfo;
import com.ailk.sets.platform.dao.interfaces.ICompanyContactDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyEmailserverDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IContactInfoDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerLostPwd;
import com.ailk.sets.platform.dao.interfaces.IPasscodeAvailableDao;
import com.ailk.sets.platform.domain.EmployerLostPwd;
import com.ailk.sets.platform.domain.ForgetPassEntity;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.empl.service.ILogin;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.ContactInfo;
import com.ailk.sets.platform.intf.model.employer.LoginInfo;
import com.ailk.sets.platform.intf.model.employer.RegisterInfo;
import com.ailk.sets.platform.util.MD5;
import com.ailk.sets.platform.util.SimpleMailSender;
import com.ailk.sets.platform.util.TemplateHost;

/**
 * 登录验证服务实现类
 * 
 * @author 毕希研
 * 
 */
@Transactional(rollbackFor = Exception.class)
public class LoginImpl implements ILogin {
	private Logger logger = LoggerFactory.getLogger(LoginImpl.class);
	@Autowired
	private IEmployerLostPwd employerLostPwdDaoImpl;
	@Autowired
	private IEmployerDao employerDaoImpl;
	@Autowired
	private ICompanyDao companyDaoImpl;
	@Autowired
	private IContactInfoDao contactInfoDaoImpl;
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	@Autowired
	private ICompanyEmailserverDao companyEmailserverDaoImpl;
	@Autowired
	private ICompanyContactDao companyContactDaoImpl;    
	@Autowired
    private IPasscodeAvailableDao passcodeAvailableDao;

	public LoginInfo certify(String username, String password) throws PFServiceException {
		try {
			LoginInfo li = employerDaoImpl.certify(username, password);
			if (li.getCode().equals(FuncBaseResponse.SUCCESS)) {
				Employer employer = li.getEmployer();
				if (employer.getInitFlag() == null) {
					employer.setInitFlag(1);
					employerDaoImpl.saveOrUpdate(employer);
				}
				if (StringUtils.isEmpty(employer.getTicket())) {
					employer.setTicket(MD5.toMD5(employer.getEmployerId() + employer.getEmployerAcct()));
					employerDaoImpl.saveOrUpdate(employer);
				}
			}
			return li;
		} catch (Exception e) {
			logger.error("call employ dao certify error", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	public LoginInfo certify(String username, String password, String ticket) throws PFServiceException {
		try {
			LoginInfo li = employerDaoImpl.certify(username, password);
			if (li.getCode().equals(FuncBaseResponse.SUCCESS)) {
				Employer employer = li.getEmployer();
				if (StringUtils.isEmpty(ticket) || (!employer.getTicket().equals(ticket))) {
					li.setCode(FuncBaseResponse.PARAMETERERR);
				}
			}
			return li;
		} catch (Exception e) {
			logger.error("call employ dao certify error", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public RegisterInfo register(Employer employer) throws PFServiceException {
		try {
			RegisterInfo ri = employerDaoImpl.register(employer);
			// 发送申请帐号邮件
			// if (ri.getPfResponse().getCode() == FuncBaseResponse.SUCCESS) {
			// Company company = inviteDaoImpl.getCompanyInfo(employer.getEmployerId());
			// }
			return ri;
		} catch (Exception e) {
			logger.error("call register service error", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public LoginInfo resetPassword(int employerId, String passwordOld, String passwordNew) throws PFServiceException {
		LoginInfo li = new LoginInfo();
		Employer employer = employerDaoImpl.getEntity(employerId);
		if (!passwordOld.equalsIgnoreCase(employer.getEmployerPwd())) {
			li.setCode(FuncBaseResponse.PASSWORDERROR);
		} else {
			employer.setEmployerPwd(passwordNew);
			employerDaoImpl.saveOrUpdate(employer);
			li.setCode(FuncBaseResponse.SUCCESS);
		}
		return li;
	}

	@Override
	public PFResponse consultCooperation(ContactInfo contactInfo) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			Company company = companyDaoImpl.getEntity(1);
			contactInfo.setContactDate(new Timestamp(System.currentTimeMillis()));
			contactInfoDaoImpl.saveOrUpdate(contactInfo);
			MailSenderInfo mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(company.getCompanyId(), "companyId"));
			mailSenderInfo.setSubject(contactInfo.getCompany() + "(" + contactInfo.getName() + ")申请商务合作");
			TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();
			context.put("entity", contactInfo);
			mailSenderInfo.setToAddress(companyContactDaoImpl.getEntity(company.getCompanyId(), "companyId").getContactEmail());
			mailSenderInfo.setContent(templateHost.makeFileString(TemplateHost.VM_COOPERATION));
			SimpleMailSender.sendHtmlMail(mailSenderInfo);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (MessagingException e) {
			logger.error("error call consultCooperation ", e);
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	@Override
	public PFResponse forgetPassword(String email) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			Company company = companyDaoImpl.getEntity(1);
			Employer employer = employerDaoImpl.getEntity(email, "employerAcct");
			if (employer == null || (employer.getAcctType() != null && employer.getAcctType() == Constants.SAMPLE_ACCT_TYPE))//体验,委托,外部账号也认为不存在
				pfResponse.setCode(FuncBaseResponse.ACCTNOTEXIST);
			else {
				String uuid = UUID.randomUUID().toString();
				EmployerLostPwd elp = new EmployerLostPwd();
				elp.setApplyDate(new Timestamp(System.currentTimeMillis()));
				elp.setEmployerAcct(email);
				elp.setUuid(uuid);
				employerLostPwdDaoImpl.deleteAll(email);
				employerLostPwdDaoImpl.saveOrUpdate(elp);
				MailSenderInfo mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(company.getCompanyId(), "companyId"));
				ForgetPassEntity fpe = new ForgetPassEntity();
				fpe.setUsername(employer.getEmployerName());
				String url = configSysParamDao.getConfigSysParameters(ConfigSysParam.FORGETPASS).getParamValue() + "/" + uuid;
				fpe.setUrl(url);
				mailSenderInfo.setSubject(fpe.getSubject());
				TemplateHost templateHost = new TemplateHost();
				VelocityContext context = templateHost.getContext();
				context.put("entity", fpe);
				mailSenderInfo.setContent(templateHost.makeFileString(TemplateHost.VM_PUBLICTEMPLATE));
				mailSenderInfo.setToAddress(email);
				SimpleMailSender.sendHtmlMail(mailSenderInfo);
				pfResponse.setCode(FuncBaseResponse.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("error call forgetPassword ", e);
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	@Override
	public PFResponse validateForgetPass(String uuid) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		EmployerLostPwd elp = employerLostPwdDaoImpl.getEntity(uuid);
		if (elp == null)
			pfResponse.setCode(FuncBaseResponse.NONEEXIST);
		else {
			Calendar c = Calendar.getInstance();
			c.setTime(new Timestamp(System.currentTimeMillis()));
			c.add(Calendar.DAY_OF_YEAR, -1);
			if (c.getTime().after(elp.getApplyDate()))
				pfResponse.setCode(FuncBaseResponse.TIMEOUT);
			else
				pfResponse.setCode(FuncBaseResponse.SUCCESS);
		}
		return pfResponse;
	}

	@Override
	public LoginInfo resetPassword(String uuid, String passwordNew) throws PFServiceException {
		LoginInfo loginInfo = new LoginInfo();
		try {
			EmployerLostPwd elp = employerLostPwdDaoImpl.getEntity(uuid);
			Employer employer = employerDaoImpl.getEntity(elp.getEmployerAcct(), "employerAcct");
			employerLostPwdDaoImpl.deleteAll(elp.getEmployerAcct());
			employer.setEmployerPwd(passwordNew);
			employerDaoImpl.saveOrUpdate(employer);
			loginInfo.setEmployer(employer);
			loginInfo.setCode(FuncBaseResponse.SUCCESS);
			return loginInfo;
		} catch (Exception e) {
			logger.error("error call resetPassword", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	/**
	 * 更新openId
	 * 
	 * @param employerId
	 * @param openId 为空串表示退出时调用  清除绑定
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateOpenId(int employerId, String openId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		Employer employer = employerDaoImpl.getEntity(employerId);
		if (!openId.equalsIgnoreCase(employer.getOpenId())) {//
			if(StringUtils.isNotEmpty(openId)){
				employerDaoImpl.removeOpenId(openId); //清除原绑定的openId
			}
			logger.debug("the openid for employerId {} update openId from {} to " + openId, employerId, employer.getOpenId());
			employer.setOpenId(openId);
			employerDaoImpl.saveOrUpdate(employer);
		}
		pfResponse.setCode(FuncBaseResponse.SUCCESS);
		return pfResponse;
	}
	
	public Employer getEmployerByOpenId(String openId, int employerId){
		logger.debug("get employer by openId and empoyer {}, employerId {}  ", openId, employerId);
		if(StringUtils.isEmpty(openId)){
			return null;
		}
		Employer e = employerDaoImpl.getEmployerByOpenId(openId,employerId);
		if (null!=e && StringUtils.isBlank(e.getAuthCode()))
        {
            e.setAuthCode(passcodeAvailableDao.getAvailableCSPassCode());
            employerDaoImpl.saveOrUpdate(e);
        }
		return e;
	}
	
	/**
	 * 根据openId获取employer
	 * @param openId
	 * @return
	 */
	public Employer getEmployerByOpenId(String openId){
		logger.debug("get employer by openId {}  ", openId);
		if(StringUtils.isEmpty(openId)){
			return null;
		}
		Employer e = employerDaoImpl.getEmployerByOpenId(openId);
		if (null!=e && StringUtils.isBlank(e.getAuthCode()))
        {
            e.setAuthCode(passcodeAvailableDao.getAvailableCSPassCode());
            employerDaoImpl.saveOrUpdate(e);
        }
		return e;
	}

	@Override
	public Employer getEmployerByEmployerId(Integer employerId) {
	    Employer employer = employerDaoImpl.getEntity(employerId);
	    if (null!=employer && StringUtils.isBlank(employer.getAuthCode()))
        {
	        employer.setAuthCode(passcodeAvailableDao.getAvailableCSPassCode());
	        employerDaoImpl.saveOrUpdate(employer);
        }
		return employer;
	}

	@Override
	public LoginInfo certifyDemo(String demoTicket) throws PFServiceException {
		try{
			LoginInfo info = new LoginInfo();
			String ticket = configSysParamDao.getConfigParamValue(ConfigSysParam.DEMOLOGINTICKET);
			logger.debug("certifyDemo the param is {} , srcTicket is {} ", demoTicket, ticket);
			if(demoTicket.equals(ticket)){
				info.setCode(FuncBaseResponse.SUCCESS);
				Employer employer = employerDaoImpl.getEmployerByEmail("demo@101test.com", null);
				if(employer == null){
					logger.warn("not the employer {} ", "demo@101test.com");
					info.setCode(FuncBaseResponse.ACCTNOTEXIST);
				}else{
					info.setEmployer(employer);
				}
				
			}else{
				info.setCode(FuncBaseResponse.PARAMETERERR);
			}
			return info;
		}catch(Exception e){
			throw new PFServiceException(e);
		}
	}
}
