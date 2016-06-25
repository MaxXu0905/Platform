package com.ailk.sets.platform.service.local.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.service.local.ISSOCheckService;
@Transactional(rollbackFor = Exception.class)
@Service
public class SSOCheckServiceFactory {
	
	private Logger logger = LoggerFactory.getLogger(SSOCheckServiceFactory.class);
	public static final int SSO_TYPE_ASIA = 1;
	public static final int SSO_TYPE_WBYX = 5;
	public static final int TOKEN_TYPE = 201;//百一获取token注册
    public static final int SSO_TYPE_CHINAHR = 11;
	    @Autowired
		@Qualifier("SSOCheckMrJobService")
		private ISSOCheckService ssoCheckMrJobService;
	    @Autowired
	 	@Qualifier("SSOCheckWbyxService")
	 	private ISSOCheckService ssoCheckWbyxService;
	    @Autowired
		@Qualifier("SSOCheckChinaHrService")
		private ISSOCheckService ssoCheckChinaHrService;
	    
	    public ISSOCheckService getSSOCheckService(int type){
	    	if(type == SSO_TYPE_ASIA)
	    		return ssoCheckMrJobService;
	    	if(type == SSO_TYPE_WBYX)
	    		return ssoCheckWbyxService;
	    	if(type == SSO_TYPE_CHINAHR)
	    		return ssoCheckChinaHrService;
	    	logger.error("not support type {} , please check ", type);
	    	return null;
	    	
	    }
}
