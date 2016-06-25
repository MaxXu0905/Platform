package com.ailk.sets.platform.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IConfigRegionDao;
import com.ailk.sets.grade.dao.intf.IPositionInfoExtDao;
import com.ailk.sets.grade.intf.report.InterviewInfo.ConfigRegionInfo;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IConfigInfoExtDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.domain.PositionInfoExtId;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.InfoNeeded;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.service.ICandidateInfoService;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.ConfigCollege;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExt;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Mapping;

@Transactional(rollbackFor = Exception.class)
public class CandidateInfoServiceImpl implements ICandidateInfoService {
	private Logger logger = LoggerFactory.getLogger(CandidateInfoServiceImpl.class);
	@Autowired
	private ICandidateInfoDao candidateInfoDao;
	@Autowired
	private ICandidateDao candidateDao;
	@Autowired
	private ICandidateTestDao candidateTestDao;
	@Autowired
	private IPositionInfoExtDao positionInfoExtDao;

	@Autowired
	private IConfigInfoExtDao configInfoExtDao;
	@Autowired
	private IConfigRegionDao configRegionDao;
	@Autowired
	private IPositionDao positionDao;

	public List<InfoNeeded> getCandConfigInfoExts(long testId) throws PFServiceException {
		try {
			Invitation invitation = candidateInfoDao.getInvitation(testId);
			Position position = positionDao.getEntity(invitation.getPositionId());
			List<PositionInfoExt> list = candidateInfoDao.getCandConfigInfoExts(position.getEmployerId(),invitation.getPositionId());//测评创建人的常规信息
			Map<String, CandidateInfoExt> infoMap = candidateInfoDao.getCandidateInfoExts(invitation.getCandidateName(), invitation.getCandidateEmail());
			CandidateInfoExt email = new CandidateInfoExt();
			email.setValue(invitation.getCandidateEmail());
			infoMap.put(Constants.EMAIL, email);
			CandidateInfoExt name = new CandidateInfoExt();
			name.setValue(invitation.getCandidateName());
			infoMap.put(Constants.FULL_NAME, name);
			List<InfoNeeded> result = new ArrayList<InfoNeeded>();
			try {
				for (PositionInfoExt cie : list) {
					InfoNeeded infoNeeded = new InfoNeeded();
					BeanUtils.copyProperties(infoNeeded, cie);
					infoNeeded.setInfoId(cie.getId().getInfoId());
					if (Constants.ADDRESS_TYPE.equalsIgnoreCase(cie.getValueType())) {
						infoNeeded.setExtendInfo(getConfigRegionInfos());
					}
					/*
					 * if(Constants.COLLEGE.equals(cie.getId().getInfoId())){ infoNeeded.setExtendInfo(getConfigCollegeInfos()); }
					 */
					if (!StringUtils.isEmpty(cie.getValueSql())) {
						infoNeeded.setRange(candidateInfoDao.getRangeMap(cie.getValueSql()));
					}
					if (infoMap.containsKey(infoNeeded.getInfoId())) {
						infoNeeded.setValue(infoMap.get(infoNeeded.getInfoId()).getValue());
						infoNeeded.setRealValue(infoMap.get(infoNeeded.getInfoId()).getRealValue());
					}
					result.add(infoNeeded);
				}
			} catch (IllegalAccessException e) {
				logger.error("CandidateInfoServiceImpl has an BeanUtils IllegalAccessException error", e);
			} catch (InvocationTargetException e) {
				logger.error("CandidateInfoServiceImpl has an BeanUtils InvocationTargetException error", e);
			}
			return result;
		} catch (Exception e) {
			logger.error("get info error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	public boolean saveCandidateInfo(long testId, List<CandidateInfoExt> list) {
		logger.debug("saveCandidateInfo start ...for testId {} ", testId);
		if (list == null || list.size() == 0) {
			logger.error("the CandidateInfoExt is null or size is 0, please check...");
		}
		String name = null;
		String email = null;
		CandidateInfoExt nameInfo = null;
		CandidateInfoExt emailInfo = null;
		for (CandidateInfoExt info : list) {
			if (Constants.FULL_NAME.equals(info.getId().getInfoId())) {
				name = info.getValue();
				nameInfo = info;
			}
			if (Constants.EMAIL.equals(info.getId().getInfoId())) {
				email = info.getValue();
				emailInfo = info;
			}
		}
		if (nameInfo == null || emailInfo == null) {
			logger.error("not found email or name for extInfo, please check....");
			return false;
		}
		list.remove(nameInfo);
		list.remove(emailInfo);
		int id = candidateDao.getCandidateId(name, email);
		if (id < 0) {
			logger.error("get CandidateId error,info could not be saved");
			return false;
		}
		CandidateTest test = candidateTestDao.getCandidateTest(testId);
		test.setCandidateId(id);
		candidateTestDao.saveOrUpdate(test);
//		normalDaoImpl.saveOrUpdate(test);

		logger.debug("saveCandidateInfo end  ... {} ", testId);
		return candidateInfoDao.saveCandidateInfo(id, list);
	}

	public CandidateTest getCandidateTest(long paperInstId) {
		return candidateInfoDao.getCandidateTest(paperInstId);
	}

	public List<ConfigRegionInfo> getConfigRegionInfos() throws Exception {
//		List<ConfigRegionInfo> list = new ArrayList<ConfigRegionInfo>();
		
		List<ConfigRegionInfo> list = configRegionDao.getConfigRegionInfos();
	/*	List<ConfigRegion> provinces = normalDaoImpl.getNoramlListByHql("from ConfigRegion where regionLevel = 2 ", ConfigRegion.class);
		for (ConfigRegion province : provinces) {
			ConfigRegionInfo proInfo = new ConfigRegionInfo();
			BeanUtils.copyProperties(proInfo, province);
			List<ConfigRegion> citys = normalDaoImpl.getNoramlListByHql("from ConfigRegion where parent_id =  " + province.getRegionId(), ConfigRegion.class);
			List<ConfigRegionInfo> proChild = new ArrayList<ConfigRegionInfo>();
			for (ConfigRegion city : citys) {
				ConfigRegionInfo cityInfo = new ConfigRegionInfo();
				BeanUtils.copyProperties(cityInfo, city);
				proChild.add(cityInfo);
			}
			proInfo.setChildren(proChild);
			list.add(proInfo);
		}*/
		return list;
	}

	/*public List<ConfigCollegeInfo> getConfigCollegeInfos() throws Exception {
		List<ConfigCollegeInfo> list = new ArrayList<ConfigCollegeInfo>();
		List<ConfigCollege> cols = normalDaoImpl.getNoramlListByHql("from ConfigCollege  ", ConfigCollege.class);
		for (ConfigCollege col : cols) {
			ConfigCollegeInfo colInfo = new ConfigCollegeInfo();
			BeanUtils.copyProperties(colInfo, col);
			list.add(colInfo);
		}
		return list;
	}*/
	public List<ConfigCollege> getConfigColleges(String searchTxt,int size) throws Exception {
		logger.debug("the search txt is {} ", searchTxt);
		return candidateInfoDao.getConfigColleges(searchTxt,size);
		
	}
	public List<Mapping> getQueryInfoBySearchName(long testId, String infoId, String searchName, int size) {
		logger.debug("begin getQueryInfoBySearchName  for testId {}, infoId {} , searchName is " + searchName, testId, infoId);
		PositionInfoExtId id = new PositionInfoExtId(candidateInfoDao.getEmployerId(testId),candidateInfoDao.getInvitation(testId).getPositionId(), infoId);
		PositionInfoExt infoExt = positionInfoExtDao.getEntity(id);
		String valueSql = null;
		if (infoExt == null) {
			logger.warn("not found any info for testId {}, infoId {} ", testId, infoId);
			ConfigInfoExt confExt = configInfoExtDao.getEntity(infoId);
			if (confExt == null) {
				logger.warn("not found any info for infoId {} ", infoId);
				return null;
			}
			valueSql = confExt.getValueSql();
		}else{
			valueSql = infoExt.getValueSql();
		}
		if (!StringUtils.isEmpty(valueSql)) {
			List<Mapping> list = candidateInfoDao.getQueryInfo(valueSql.replaceAll(Constants.SEARCHNAME, searchName) + " limit " + size);
			logger.debug("query info size is {} ", list.size());
			return list;
		}
		return null;
	}
	
	
	public List<Mapping> getQueryInfoBySearchName(String infoId, String searchName,int size){
		logger.debug("begin getQueryInfoBySearchName infoId {} , searchName is {} " ,infoId,searchName);
		ConfigInfoExt infoExt = configInfoExtDao.getEntity(infoId);
		if (infoExt == null) {
			logger.warn("not found any info for infoId {} ", infoId);
			return null;
		}
		if (!StringUtils.isEmpty(infoExt.getValueSql())) {
			List<Mapping> list = candidateInfoDao.getQueryInfo(infoExt.getValueSql().replaceAll(Constants.SEARCHNAME, searchName) + " limit " + size);
			logger.debug("query info size is {} ", list.size());
			return list;
		}
		return null;
	}

	@Override
	public Candidate getCandidateByOpenId(String openId) {
		return candidateDao.getCandidateByOpenId(openId);
	}

}
