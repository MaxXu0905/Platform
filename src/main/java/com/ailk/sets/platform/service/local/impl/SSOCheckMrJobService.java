package com.ailk.sets.platform.service.local.impl;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerChannelDao;
import com.ailk.sets.platform.domain.EmployerChannel;
import com.ailk.sets.platform.domain.EmployerChannelId;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.SSOEmployerRes;
import com.ailk.sets.platform.intf.empl.service.IEmployerTrial;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.OutCallConstants;
import com.ailk.sets.platform.service.local.ISSOCheckService;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 职酷单点登陆实现
 * @author panyl
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SSOCheckMrJobService implements ISSOCheckService {

	private Logger logger = LoggerFactory.getLogger(SSOCheckMrJobService.class);
	@Autowired
	private IEmployerTrial employerTrial;
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	@Autowired
	private IEmployerChannelDao employerChannelDao;
	@Override
	public EmployerRegistInfo login(String token) {
		return getEmployerInfoByToken(token);
	}

	@Override
	public EmployerRegistInfo getEmployerInfoByToken(String token) {
		EmployerRegistInfo employerInfo = null;
		try{
			HttpClient httpClient = HttpClientUtil.getNormalHttpClient();
			String passcode = configSysParamDao.getConfigParamValue(OutCallConstants.MRJOB_PASSCODE);
			String interfaceUrl = configSysParamDao.getConfigParamValue(OutCallConstants.MRJOB_GET_EMPLOYER_URL);
	        String url = MessageFormat.format(interfaceUrl, passcode, token);
	        String response = httpClient.execute(new HttpGet(url), new BasicResponseHandler());
	        logger.debug("the response url is {} for token url {} ", response, url);
	        /*  //TODO 以下测试代码需要删除掉
	        response ="{\"status\":1,\"data\":{\"employerName\":\"张三\",\"employerEmail\":\"zhangsan123213dssf@163.com\"}}";*/
	        SSOEmployerRes res = new ObjectMapper().readValue(response, SSOEmployerRes.class);
	        logger.debug("the user info is {} for token {} ", res, token);
	        if(res.getStatus() == OutCallConstants.SUCCESS_STATUS){
	        	employerInfo = res.getData();
	        	if(StringUtils.isEmpty(employerInfo.getCompanyName())){
	        		employerInfo.setCompanyName("亚信科技(中国)有限公司");
	        	}
	        	employerInfo.setCode(FuncBaseResponse.SUCCESS);
		        logger.debug("get EmployerRegistInfo {} by  token {} " , employerInfo, token);
		        PFResponse registRes = employerTrial.registEmployerQuickly(employerInfo);
		        if(registRes.getCode().equals(FuncBaseResponse.SUCCESS)){
		        	EmployerChannel channel = new EmployerChannel();
		        	EmployerChannelId id = new EmployerChannelId(employerInfo.getEmployerEmail(),SSOCheckServiceFactory.SSO_TYPE_ASIA);
		        	channel.setId(id);
		        	channel.setEmployerName(employerInfo.getEmployerName());
		        	employerChannelDao.saveOrUpdate(channel);
		        }
		        return employerInfo;
	        }
	        employerInfo = new EmployerRegistInfo();
	        employerInfo.setCode(FuncBaseResponse.SSOERROR);
	        employerInfo.setMessage("token check failed from mrjob");
	        logger.warn("error check info for token {}, res {} ", token, res);
		}catch(Exception e){
			logger.error("error to get getEmployerInfoByToken for token " + token , e);
			throw new RuntimeException(e);
		}
        return employerInfo;
	}

}
