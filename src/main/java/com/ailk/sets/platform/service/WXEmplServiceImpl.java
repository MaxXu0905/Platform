package com.ailk.sets.platform.service;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerQrCodeDao;
import com.ailk.sets.platform.domain.EmployerQrcode;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.WXInterfaceUrl;

/**
 * @author Administrator
 *
 */
@Transactional(rollbackFor = Exception.class)
public class WXEmplServiceImpl implements IWXEmplService {
	private Logger logger = LoggerFactory.getLogger(WXEmplServiceImpl.class);
	@Autowired
	private IEmployerDao employerDaoImpl;
	@Autowired
	private IEmployerQrCodeDao employerQrCodeDao;
	@Autowired
	private IConfigSysParamDao configSysParamDao;

	@Override
	public Employer bindingEmployer(int qrCodeId, String openId) throws PFServiceException {
		try {
			Candidate candidate = new Candidate();
			EmployerQrcode employerQrcode = employerQrCodeDao.getEntity(qrCodeId);
			Employer employer = employerDaoImpl.getEntity(employerQrcode.getEmployerId());
			employerDaoImpl.removeOpenId(openId);//扫描后，保持库中的openId唯一性
			logger.debug("get employerId " + employerQrcode.getEmployerId() + " openId " + openId);
			employer.getEmployerName();
			employer.setOpenId(openId);
			employerDaoImpl.saveOrUpdate(employer);
			//暂时不移除招聘者和二维码的绑定了
//			employerQrCodeDao.removeEmployerId(employerQrcode.getEmployerId());
			candidate.setCode(FuncBaseResponse.SUCCESS);
			return employer;
		} catch (Exception e) {
			logger.error("call bindingEmployer error", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public String getServiceBaseUrl() {
		try {
			return configSysParamDao.getConfigParamValue(ConfigSysParam.BASE_URL);
		} catch (PFDaoException e) {
			logger.error("can not find base url ", e);
		}
		return null;
	}

	@Override
	public PFResponse unBindingEmployer(String openId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			logger.debug("unBindingEmployer for openId {} ", openId);
			employerDaoImpl.removeOpenId(openId);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	@Override
	public void sendWelcome(String openId , String link) {
		try {
			String text = MessageFormat.format(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERWELCOME), link);
			logger.debug(text);
			WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, text), token);
		} catch (PFDaoException e) {
			logger.error("error send welcome text", e);
		}
	}
    
	/**
	 * 招聘者扫描二维码后关注回复
	 * 
	 */
	
	@Override
	public void sendScanwelcome(String openId, String candidateName , String link) {
           
		try {
			String text = configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERWELCOMESCAN);
		    text = MessageFormat.format(text, candidateName , link);
		    logger.debug(text);
		    WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, text), token);
		} catch (PFDaoException e) {
			logger.error("error send scanwelcome text",e);
		}
	}
    
	/**
	 * 招聘者点击进入百一时检测是否绑定 
	 * */
	
	@Override
	public String isBind(String openId) {
		Employer employer = employerDaoImpl.selectEmployerByOpenId(openId);
		try {
			if(employer==null)
			{
				String text = configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERWELCOME);
				return text;
			}
			else{
				String text = "";
				return text;
			}
		} catch (PFDaoException e) {
			logger.error("error  isBind text",e);
			return null;
		}
	}

	
	/**
	 * 点击按钮时回复信息（未绑定）
	 * */
	
	@Override
	public void defaultMessageSend(String openId, String content) {
		try {
			logger.debug("content is "+content);
			WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
			logger.debug("content is "+content);
			String sendTxt = WXInterfaceUrl.getSendText(openId, content);
			logger.debug("sendTxt is "+sendTxt);
			wxCommunicator.sendMessage(sendTxt, token);
		} catch (PFDaoException e) {
			e.printStackTrace();
		}
		
	}
    
	/**
	 * 点击按钮时回复信息（已绑定）
	 * */
	@Override
	public void messageSend(String openId, String content) {
		try {
			WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(openId, content), token);
		} catch (PFDaoException e) {
            logger.error(" error send click response ", e);
		}
		
	}
	
	public static void main(String[] args)
    {
        System.out.println(MessageFormat.format("你妹啊{0}!{1}!", "你", "妹"));
    }
}
