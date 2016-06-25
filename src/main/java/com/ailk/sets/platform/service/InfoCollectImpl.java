package com.ailk.sets.platform.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.dao.interfaces.IConfigInfoExtDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerQrCodeDao;
import com.ailk.sets.platform.domain.EmployerQrcode;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExt;
import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExtEx;
import com.ailk.sets.platform.intf.empl.domain.PositionInfoConfig;
import com.ailk.sets.platform.intf.empl.service.IInfoCollect;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.WXInterfaceUrl;

@Transactional(rollbackFor = Exception.class)
public class InfoCollectImpl implements IInfoCollect {
	private Logger logger = LoggerFactory.getLogger(InfoCollectImpl.class);
	@Autowired
	private IConfigInfoExtDao configInfoExtDao;
	@Autowired
	private ICandidateInfoDao candidateInfoDao;
	@Autowired
	private IEmployerQrCodeDao employerQrCodeDao;
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	@Autowired
	private IEmployerDao employerDaoImpl;

	public Collection<ConfigInfoExtEx> getInfoExt(int employerId,Integer positionId) throws PFServiceException {
		try {
			List<ConfigInfoExt> list = configInfoExtDao.getAll();
			List<PositionInfoExt> companyInfoList = candidateInfoDao.getCandConfigInfoExts(employerId,positionId);
			Map<String, PositionInfoExt> map = new HashMap<String, PositionInfoExt>();
			if (!CollectionUtils.isEmpty(companyInfoList))
				for (PositionInfoExt cie : companyInfoList)
					map.put(cie.getId().getInfoId(), cie);
			Map<Integer, ConfigInfoExtEx> ResultMap = new TreeMap<Integer, ConfigInfoExtEx>();//按key排序
			for (ConfigInfoExt ce : list) {
				ConfigInfoExtEx configInfoExtEx = new ConfigInfoExtEx();
//				BeanUtils.copyProperties(configInfoExtEx, ce);
				configInfoExtEx.setInfoId(ce.getInfoId());
				configInfoExtEx.setInfoName(ce.getInfoName());
				configInfoExtEx.setIsMandatory(ce.getIsMandatory());
				if (map.containsKey(ce.getInfoId())) {
					configInfoExtEx.setChoosed(true);
					ResultMap.put(map.get(ce.getInfoId()).getSeq(), configInfoExtEx);
				} else {
					configInfoExtEx.setChoosed(false);
					// 当不是被选中的设置信息，序号从被选中的设置信息以外开始
					ResultMap.put(ce.getSeq() + companyInfoList.size(), configInfoExtEx);
				}
				/*configInfoExtEx.setValueLength(null);
				configInfoExtEx.setVerifyExp(null);
				configInfoExtEx.setDataType(null);
				configInfoExtEx.setValueSql(null);
				configInfoExtEx.setSeq(null);
				configInfoExtEx.setValueType(null);
				configInfoExtEx.setIsDefault(null);*/
			}
			return ResultMap.values();
		} catch (Exception e) {
			logger.error("error call getInfoExt", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	
	/**
	 * 保存职位常规信息
	 * @param employerId
	 * @param infoSeq
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse saveInfo(int employerId, int positionId, List<PositionInfoConfig> configInfo) throws PFServiceException{
		logger.debug("save info  employerId {} , positionId {} , configInfo " + configInfo, employerId, positionId);
		PFResponse pfResponse = new PFResponse();
		List<PositionInfoConfig> allConfigInfo = processMustInfo(configInfo);
		Map<String,Integer> config = new HashMap<String, Integer>();
		int i =1;
		for(PositionInfoConfig info : allConfigInfo){
			if(config.containsKey(info.getInfoId())){
				logger.warn("contains more than once for infoId {} , please check ", info.getInfoId());
				continue;
//				throw new PFServiceException("contains more than once for infoId " +  info.getInfoId());
			}
			config.put(info.getInfoId(), i++);
		}
		configInfoExtDao.saveInfo(employerId, positionId, config);
		pfResponse.setCode(FuncBaseResponse.SUCCESS);
		return pfResponse;
	}
	
	private List<PositionInfoConfig> processMustInfo(List<PositionInfoConfig> configInfos){
		logger.debug("the old configInfos is {} ", configInfos);
		List<PositionInfoConfig> allConfig = new ArrayList<PositionInfoConfig>();
		List<ConfigInfoExt> mandatories = configInfoExtDao.getMandatoryInfoExt();
		for(ConfigInfoExt ext : mandatories){
			PositionInfoConfig config = new PositionInfoConfig();
			config.setInfoId(ext.getInfoId());
			allConfig.add(config);
		}
		allConfig.addAll(configInfos);
	    logger.debug("the new  allConfig  is {} ", allConfig);
	    return allConfig;
	}

	@Override
	public synchronized String getQRCodePicUrl(int employerId) throws PFServiceException {
		try {
			Employer employer = employerDaoImpl.getEntity(employerId);
			if (StringUtils.isNotEmpty(employer.getOpenId()))
				return null;
			EmployerQrcode employerQrcode = employerQrCodeDao.getEntity(employerId, "employerId");
			if (employerQrcode != null)
				return employerQrcode.getQrcodeUrl();
			employerQrcode = employerQrCodeDao.getAvailableQrCode();
			if (employerQrcode == null) {
				int maxId = employerQrCodeDao.getMaxId();
				if (maxId < 100000) {
					logger.debug("prepare to get new qrcode.........");
					employerQrcode = new EmployerQrcode();
					employerQrcode.setEmployerId(employerId);
					employerQrcode.setQrcodeId(maxId + 1);
					// employerQrCodeDao.saveOrUpdate(employerQrcode);// 必须先保存一下，为获得qrCodeId
					WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
					String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID),
							configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
					employerQrcode.setQrcodeUrl(wxCommunicator.getQRCodeUrl(WXInterfaceUrl.getQRCodeRequestJson(employerQrcode.getQrcodeId()), token));
					logger.debug("new qrcode url is " + employerQrcode.getQrcodeUrl());
					if (StringUtils.isNotEmpty(employerQrcode.getQrcodeUrl()))
						employerQrCodeDao.saveOrUpdate(employerQrcode);
				} else
					return null;
			} else {
				employerQrcode.setEmployerId(employerId);
				employerQrCodeDao.saveOrUpdate(employerQrcode);
			}
			return employerQrcode.getQrcodeUrl();
		} catch (Exception e) {
			logger.error("error call getQRCodePicUrl", e);
			throw new PFServiceException(e.getMessage());
		}
	}
}
