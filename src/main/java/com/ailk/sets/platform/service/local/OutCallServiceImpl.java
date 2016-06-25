package com.ailk.sets.platform.service.local;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.MessageFormat;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateReportDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerChannelDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.domain.EmployerChannel;
import com.ailk.sets.platform.domain.EmployerChannelId;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.OutResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.OutReportInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.TokenInfo;
import com.ailk.sets.platform.intf.empl.service.IEmployerTrial;
import com.ailk.sets.platform.intf.empl.service.IOutCallService;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.OutCallConstants;
import com.ailk.sets.platform.service.local.impl.SSOCheckServiceFactory;
import com.ailk.sets.platform.util.PassportGenerator;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Transactional(rollbackFor = Exception.class)
public class OutCallServiceImpl implements IOutCallService {
	private Logger logger = LoggerFactory.getLogger(OutCallServiceImpl.class);
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	
    @Autowired
    private ICandidateTestDao candidateTestDao;
    @Autowired
    private ICandidateReportDao candidateReportDao;
    @Autowired
    private ICandidateDao candidateDao;
    @Autowired
    private IPositionDao positionDao;

	private static final Gson gson = new Gson();

	@Override
	public OutResponse updateMrReportStatus(CandidateTest test, int status) {
		OutResponse res = new OutResponse();
		long testId = test.getTestId();
		try{
			Integer type = test.getChannelType();
			if(type == null){
				return null;
			}
			if(type.intValue() ==  SSOCheckServiceFactory.SSO_TYPE_ASIA){
				String urlInterface = configSysParamDao.getConfigParamValue(OutCallConstants.MRJOB_UPDATE_STATUS);
				String passcode = configSysParamDao.getConfigParamValue(OutCallConstants.MRJOB_PASSCODE);
				HttpClient httpClient = HttpClientUtil.getNormalHttpClient();
				String url = MessageFormat.format(urlInterface, passcode,testId,status);
				HttpGet httpGet = new HttpGet(url);
				logger.info("strat notice updateMrReportStatus,url:"+url);
				String response = httpClient.execute(httpGet, new BasicResponseHandler());
				logger.info("end notice updateMrReportStatus,status:"+status+",response:"+response);
				return new ObjectMapper().readValue(response, OutResponse.class);
			}
			logger.warn("not support type {} please check testId {} ", type, testId);
		}catch(Exception e){
			res.setStatus(1);
			logger.error("error to updateMrReportStatus " + testId +", " + status, e);
		}
		return res;
	}

	@Override
	public OutResponse giveMrReportData(long testId, OutReportInfo report)  {
		OutResponse res = new OutResponse();
		try{
			String urlInterface = configSysParamDao.getConfigParamValue(OutCallConstants.MRJOB_SET_REPORT);
			String passcode = configSysParamDao.getConfigParamValue(OutCallConstants.MRJOB_PASSCODE);
			HttpClient httpClient = HttpClientUtil.getNormalHttpClient();
			String url = MessageFormat.format(urlInterface, passcode);
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(gson.toJson(report));
	        entity.setContentEncoding(Constants.CHARSET_UTF8);
	        entity.setContentType("application/json");
	        httpPost.setEntity(entity);
	        logger.info("strat notice giveMrReportData,url:"+url+",report:"+report);
			String response = httpClient.execute(httpPost, new BasicResponseHandler());
			logger.info("end notice giveMrReportData,testId:"+testId+",response"+response);
			return new ObjectMapper().readValue(response, OutResponse.class);
		}catch(Exception e){
			res.setStatus(1);
			logger.error("error to giveMrReportData " + report, e);
		}
		return res;
	}
	@Override
	public OutResponse updateMrReportStatus(long testId,int status) {
		OutResponse out = null;
		if(testId>0 && status>0){
			//1.根据testId获取应聘人信息
	    	CandidateTest test = candidateTestDao.getCandidateTest(testId);
	    	//2.通知状态变更，1：答题中；2：答题完毕；3：已输出报告；4：推荐；5：淘汰
	    	out=updateMrReportStatus(test, status);
		}
		return out;
	}

	@Override
	public OutResponse giveMrReportData(long testId) {
		OutResponse result =null;
		if(testId>0){
			//1.判断是否第三方调用
			CandidateTest test = candidateTestDao.getCandidateTest((int) testId);
			if(null!=test){
				Integer type = test.getChannelType();
				if(type == null){
					return null;
				}
			}
			//2.封装返回数据对象
			 OutReportInfo report =  new OutReportInfo();
			 //2.1 testId
			 report.setTestId((int) testId);
			 CandidateReport candiReport = candidateReportDao.get((int) testId);
			 //2.2 score
			 if(null!=candiReport ){
				 report.setScore(candiReport.getGetScore());
			 }
			 //2.3 查看报告
			 try {
				String viewReportVal = configSysParamDao.getConfigParamValue(OutCallConstants.VIEW_REPORT);
				if(StringUtils.isNotEmpty(viewReportVal)){
					//替换参数
					String viewReportUrl = MessageFormat.format(viewReportVal,test.getPositionId(),String.valueOf(testId),candiReport.getReportPassport());
					report.setReportUrl(viewReportUrl); 
				}
			} catch (PFDaoException e) {
				logger.error("error to giveMrReportData :get VIEW_REPORT URL error" + e.getMessage());
			}
			 //2.4 下载报告
			try {
				 String downloadReportVal = configSysParamDao.getConfigParamValue(OutCallConstants.DOWNLOAD_REPORT);
				if(StringUtils.isNotEmpty(downloadReportVal)){
					//替换参数
					Position position = positionDao.getPosition(test.getPositionId());
					int candidateId = test.getCandidateId();
					Candidate candidate = candidateDao.getEntity(candidateId);
					String suffix = "";
					if(null!=candidate && StringUtils.isNotEmpty(candidate.getCandidateName())){
						suffix+=candidate.getCandidateName()+"的";
					}
					suffix +=position.getPositionName()+"报告.pdf";
					String tosuffix = URLEncoder.encode(suffix , "UTF-8");
					String downloadReportUrl = MessageFormat.format(downloadReportVal,String.valueOf(testId),candiReport.getReportPassport(),tosuffix);
					report.setDownloadUrl(downloadReportUrl);
				}
			} catch (Exception e) {
				logger.error("error to giveMrReportData :get DOWNLOAD_REPORT URL error" + e.getMessage());
			}
			 //3. 调用第三方
			 OutResponse res = giveMrReportData(testId,report);
			 result =res;
		}
		return result;
	}

}
