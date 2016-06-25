package com.ailk.sets.platform.service;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IConfigChannelDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerChannelDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.domain.EmployerChannel;
import com.ailk.sets.platform.domain.EmployerChannelId;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.empl.domain.ConfigChannel;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.TokenInfo;
import com.ailk.sets.platform.intf.empl.service.IEmployerTrial;
import com.ailk.sets.platform.intf.empl.service.ISSOLogin;
import com.ailk.sets.platform.service.local.ISSOCheckService;
import com.ailk.sets.platform.service.local.impl.SSOCheckServiceFactory;
import com.ailk.sets.platform.util.PassportGenerator;
import com.alibaba.dubbo.common.utils.StringUtils;

@Transactional(rollbackFor = Exception.class)
public class SSOLoginImpl implements ISSOLogin {
    private Logger logger = LoggerFactory.getLogger(SSOLoginImpl.class);
    @Autowired
	private SSOCheckServiceFactory checkServiceFactory;
	
	@Autowired
	private IEmployerChannelDao employerChannelDao;
    @Autowired
	private IEmployerTrial employerTrial;
    
    @Autowired
    private IEmployerDao employerDao;
    
    @Autowired
    private IConfigChannelDao configChannelDao;
    
	@Override
	public EmployerRegistInfo ssoLogin(String tocken, int type) {
		logger.debug("ssoLogin for token {} type {} ", tocken, type);
		ISSOCheckService checkService = checkServiceFactory.getSSOCheckService(type);
		EmployerRegistInfo res = checkService.login(tocken);
		return res;
	}
	
	@Override
	public PFResponseData<TokenInfo> getToken(EmployerRegistInfo registInfo) {
		PFResponseData<TokenInfo> data = new  PFResponseData<TokenInfo>();
		data.setCode(FuncBaseResponse.SUCCESS);
        PFResponse registRes = employerTrial.registEmployerQuickly(registInfo);
        if(registRes.getCode().equals(FuncBaseResponse.SUCCESS)){
        	EmployerChannel channel = new EmployerChannel();
        	EmployerChannelId id = new EmployerChannelId(registInfo.getEmployerEmail(),registInfo.getChannelType());
        	channel.setId(id);
        	channel.setEmployerName(registInfo.getEmployerName());
        	employerChannelDao.saveOrUpdate(channel);
        }
        int employerId = registInfo.getEmployerId();
        Employer employer = employerDao.getEntity(employerId);
        if(StringUtils.isEmpty(employer.getEmployerToken())){
        	String token = PassportGenerator.getRandomPassport(30);
        	employer.setEmployerToken(token);
        	employer.setTokenExpiredTime(new Timestamp(Integer.MAX_VALUE * 1000L));
        	employerDao.saveOrUpdate(employer);
        }
        TokenInfo tokenInfo = new TokenInfo();
        logger.debug("token is {} for employerId {} ", employer.getEmployerToken(), employer.getEmployerId());
        tokenInfo.setToken(employer.getEmployerToken());
        tokenInfo.setExpiredTime(employer.getTokenExpiredTime());
        data.setData(tokenInfo);
		return data;
	}

	@Override
	public PFResponseData<Employer> getEmployerByToken(String token) {
		PFResponseData<Employer>  res = new PFResponseData<Employer> ();
		 res.setCode(FuncBaseResponse.SUCCESS);
		logger.debug("getEmployerByTOken  token is {} ", token);
        Employer employer = employerDao.getEmployerByToken(token);
        if(employer == null){
        	logger.error("error get employer for token  {} ", token);
        	res.setCode(FuncBaseResponse.FAILED);
        	res.setMessage("token错误");
        	return res;
        }
		if (employer.getTokenExpiredTime().before(new Timestamp(System.currentTimeMillis()))) {
			logger.error("token is empired for time {} token  {} ", employer.getTokenExpiredTime(), token);
			res.setCode(FuncBaseResponse.FAILED);
			res.setMessage("token错误");
			return res;
		}
        res.setData(employer);
		return res;
	}

	@Override
	public PFResponseData<ConfigChannel> getConfigChannelByClient(int id, String secret) {
		 PFResponseData<ConfigChannel> res = new  PFResponseData<ConfigChannel>();
		 res.setCode(FuncBaseResponse.SUCCESS);
		 ConfigChannel configChannel = configChannelDao.getEntity(id);
		 if(configChannel == null){
			 logger.warn("not found any config for id {} ", id);
			 res.setCode(FuncBaseResponse.FAILED);
			 return res;
		 }
		 if(!secret.equals(configChannel.getSecret())){
			 logger.warn(" config for id {} is not right for secret {} ", id, secret);
			 res.setCode(FuncBaseResponse.FAILED);
			 return res;
		 }
		 res.setData(configChannel);
		 return res;
	}


}
