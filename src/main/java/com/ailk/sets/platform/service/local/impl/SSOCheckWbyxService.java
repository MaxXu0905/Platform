package com.ailk.sets.platform.service.local.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
 * 沃百业兴单点登陆实现
 * @author panyl
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SSOCheckWbyxService implements ISSOCheckService {

	private Logger logger = LoggerFactory.getLogger(SSOCheckWbyxService.class);
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
//			String passcode = configSysParamDao.getConfigParamValue(OutCallConstants.MRJOB_PASSCODE);
			String interfaceUrl = configSysParamDao.getConfigParamValue(OutCallConstants.WBYX_GET_EMPLOYER_URL);
	        String url = MessageFormat.format(interfaceUrl,  token);
	        
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			// 显示结果
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				logger.debug("line is {} ", line);
				sb.append(line);
			}
			if (entity != null) {
				entity.consumeContent();
			}

	        String response = sb.toString();//httpClient.execute(new HttpGet(url), new BasicResponseHandler());
	        logger.debug("the response url is {} for token url {} ", response, url);
	        /*  //TODO 以下测试代码需要删除掉
	        response ="{\"status\":1,\"data\":{\"employerName\":\"张三\",\"employerEmail\":\"zhangsan123213dssf@163.com\"}}";*/
	        SSOEmployerRes res = new ObjectMapper().readValue(response, SSOEmployerRes.class);
	        logger.debug("the user info is {} for token {} ", res, token);
	        if(res.getCode() == OutCallConstants.OUT_STATUS_SUCCESS){
	        	employerInfo = res.getData();
	        	if(StringUtils.isEmpty(employerInfo.getCompanyName())){
	        		employerInfo.setCompanyName("沃百业兴");
	        	}
	        
	        	String email = employerInfo.getEmployerEmail();
				if("admin@101test.com".equals(email) || "empl@101test.com".equals(email)){
					 employerInfo.setCode(FuncBaseResponse.SSOERROR);
					 employerInfo.setMessage("account error ");
					 return employerInfo;
				}
					
					
	        	employerInfo.setCode(FuncBaseResponse.SUCCESS);
		        logger.debug("get EmployerRegistInfo {} by  token {} " , employerInfo, token);
		        PFResponse registRes = employerTrial.registEmployerQuickly(employerInfo);
		        if(registRes.getCode().equals(FuncBaseResponse.SUCCESS)){
		        	EmployerChannel channel = new EmployerChannel();
		        	EmployerChannelId id = new EmployerChannelId(employerInfo.getEmployerEmail(),SSOCheckServiceFactory.SSO_TYPE_WBYX);
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
