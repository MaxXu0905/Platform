package com.ailk.sets.platform.service;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IStateSmsSendDao;
import com.ailk.sets.platform.domain.StateSmsSend;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.empl.domain.SmsSendResult;
import com.ailk.sets.platform.intf.empl.service.ISystemService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.util.SmsSend;
@Transactional(rollbackFor = Exception.class)
public class SystemService implements ISystemService {
	private Logger logger = LoggerFactory.getLogger(SystemService.class);
	
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	@Autowired
	private IStateSmsSendDao stateSmsSendDao;
	public SmsSendResult sendSms(int employerId, String phone) throws PFServiceException{
		SmsSendResult result = new SmsSendResult();
		try {
			logger.debug("begin send sms for employerId {} , phone {} ", employerId, phone);
			int maxInterval =  Integer.valueOf(configSysParamDao.getConfigParamValue(ConfigSysParam.SMS_APP_MAX_INTERVAL));
			StateSmsSend stateSmsSend = stateSmsSendDao.getLastStateSmsSend(employerId, phone,StateSmsSend.APP_TYPE);
			if(stateSmsSend != null){
				Timestamp lastUpdate = stateSmsSend.getLastUpdate();
				long lastUpdateTime = lastUpdate.getTime();
				long now = System.currentTimeMillis();
				long interval = (now - lastUpdateTime)/1000;
				if(maxInterval - interval >= 5){
					result.setCode("TOFREQUENTLY");
					result.setTimeLeft(maxInterval - interval);
					result.setMessage("发送太频繁");
					logger.debug("the context is frequently for employerId {} , phone {} ", employerId, phone);
					return result;
				}
			}
			String signals = configSysParamDao.getConfigParamValue(ConfigSysParam.SMS_APP_SIGNALS);
//			String android = configSysParamDao.getConfigParamValue(ConfigSysParam.SMS_APP_ANDROID);
			String ios = configSysParamDao.getConfigParamValue(ConfigSysParam.SMS_APP_IOS);
		    result = SmsSend.sendSmsByPlatform(phone, signals, ios, null);
		    StateSmsSend newSend = new StateSmsSend();
		    newSend.setErrorCode(result.getErrorCode());
		    newSend.setErrorDetail(result.getMessage());
		    newSend.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		    newSend.setReceiver(phone);
		    newSend.setRetries(0);
		    newSend.setSender(employerId + "");
		    newSend.setState(result.getErrorCode() == 0 ? 1 : 2);
		    newSend.setType(StateSmsSend.APP_TYPE);
		    newSend.setContent(result.getContext());//res.idertifier记录为context
		    stateSmsSendDao.save(newSend);
			return result;
		} catch (PFDaoException e) {
			logger.error("error sendSms " ,e);
			throw new PFServiceException(e);
		}
	}
     
}
