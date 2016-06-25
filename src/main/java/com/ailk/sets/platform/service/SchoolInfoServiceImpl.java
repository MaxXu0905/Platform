package com.ailk.sets.platform.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.IActivityRecruitAddressDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyRecruitActivityDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.domain.ConfigSysParameters;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.cand.domain.InfoNeeded;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.SchoolInfoData;
import com.ailk.sets.platform.intf.cand.service.ISchoolInfoService;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.exception.PFServiceException;

@Transactional(rollbackFor = Exception.class)
public class SchoolInfoServiceImpl implements ISchoolInfoService {

	@Autowired
	private ICandidateInfoDao candidateInfoDao;
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	@Autowired
	private IPositionDao positionDao;
	@Autowired
	private  IActivityRecruitAddressDao activityRecruitAddressDao;
	
	@Autowired
	private ICompanyRecruitActivityDao companyRecruitActivityDao;
	@Autowired
	private ICompanyDao companyDao;
	@Autowired
	private IInvitationDao inviteDaoImpl;
    @Autowired
	private IEmployerDao employerDao;
	
	private Logger logger = LoggerFactory.getLogger(SchoolInfoServiceImpl.class);
	
	@Override
	public List<InfoNeeded> getCandConfigInfoExts(String weixinId,int activityId) throws PFServiceException {
		try {
			CompanyRecruitActivity acti = companyRecruitActivityDao.getEntity(activityId);
			List<PositionInfoExt> list = candidateInfoDao.getCandConfigInfoExts(positionDao.getEntity(acti.getPositionId()).getEmployerId(),acti.getPositionId());
			Map<String, CandidateInfoExt> infoMap = candidateInfoDao.getCandidateInfoExts(weixinId);
			Candidate candidate = candidateInfoDao.getCandiateByWeixinNo(weixinId);
			if(candidate != null){
				CandidateInfoExt email = new CandidateInfoExt();
				email.setValue(candidate.getCandidateEmail());
				infoMap.put(Constants.EMAIL, email);
				CandidateInfoExt name = new CandidateInfoExt();
				name.setValue(candidate.getCandidateName());
				infoMap.put(Constants.FULL_NAME, name);
			}
			List<InfoNeeded> result = new ArrayList<InfoNeeded>();
			try {
				for (PositionInfoExt cie : list) {
					InfoNeeded infoNeeded = new InfoNeeded();
					BeanUtils.copyProperties(infoNeeded, cie);
					infoNeeded.setInfoId(cie.getId().getInfoId());
					/*if(Constants.ADDRESS.equals(cie.getId().getInfoId())){
						infoNeeded.setExtendInfo(getConfigRegionInfos());
					}*/
					/*if(Constants.COLLEGE.equals(cie.getId().getInfoId())){//默认大学设置为宣讲会大学   0923去掉
						CandidateInfoExt college = infoMap.get(cie.getId().getInfoId());
						if(college == null || StringUtils.isEmpty(college.getValue())){
							logger.debug("the old colleage is null for weixinId {}, college is {}  ", weixinId,college);
							 ActivityRecruitAddress  address =	activityRecruitAddressDao.getEntity(acti.getAddressId());
							if(acti != null && StringUtils.isNotEmpty(address.getCollege())){
								infoNeeded.setValue(address.getCollege());
								infoNeeded.setRealValue(address.getCollege());
							}
						}
						
					}*/
					/*if(Constants.ADDRESS.equals(cie.getId().getInfoId())){//默认住址设置先居住证城市   0923去掉
						CandidateInfoExt addressInfo = infoMap.get(cie.getId().getInfoId());
						if(addressInfo == null || StringUtils.isEmpty(addressInfo.getValue())){
							logger.debug("the old address is null for weixinId {}, addressInfo is {}  ", weixinId,addressInfo);
							 ActivityRecruitAddress  address =	activityRecruitAddressDao.getEntity(acti.getAddressId());
							if(acti != null && StringUtils.isNotEmpty(address.getCity())){
								infoNeeded.setValue(address.getCity());
								infoNeeded.setRealValue(address.getCity());
							}
						}
						
					}*/
					if (!StringUtils.isEmpty(cie.getValueSql())) {
						infoNeeded.setRange(candidateInfoDao.getRangeMap(cie.getValueSql()));
					}
					if (infoMap.containsKey(infoNeeded.getInfoId())){
						infoNeeded.setValue(infoMap.get(infoNeeded.getInfoId()).getValue());
						infoNeeded.setRealValue(infoMap.get(infoNeeded.getInfoId()).getRealValue());
					}
					result.add(infoNeeded);
				}
			} catch (IllegalAccessException e) {
				logger.error("CandidateInfoServiceImpl has an BeanUtils IllegalAccessException error",e);
			} catch (InvocationTargetException e) {
				logger.error("CandidateInfoServiceImpl has an BeanUtils InvocationTargetException error", e);
			}
			return result;
		}catch(Exception e){
			logger.error("get info error ", e);
			throw new PFServiceException(e);
		}
	}

	public boolean saveCandidateInfo(String openId ,String weixinId,List<CandidateInfoExt> list){
		logger.debug("saveCandidateInfo start ... ");
		if(list == null || list.size() == 0){
			logger.error("the CandidateInfoExt is null or size is 0, please check...");
		}
		String name = null;
		String email = null;
		CandidateInfoExt nameInfo = null;
		CandidateInfoExt emailInfo =null;
		for(CandidateInfoExt info : list){
			if(Constants.FULL_NAME.equals(info.getId().getInfoId())){
				name = info.getValue();
				nameInfo = info;
			}
			if(Constants.EMAIL.equals(info.getId().getInfoId())){
				email = info.getValue();
				emailInfo = info;
			}
		}
		if(nameInfo == null || emailInfo == null){
			logger.error("not found email or name for extInfo, please check....");
			return false;
		}
		list.remove(nameInfo);
		list.remove(emailInfo);
		int id = candidateInfoDao.getCandidateIdWithWeixin(openId ,name, email,weixinId);
		if (id < 0) {
			logger.error("get CandidateId error,info could not be saved");
			return false;
		}
		logger.debug("saveCandidateInfo end  ... ");
		return candidateInfoDao.saveCandidateInfo(id, list);
	}
	
	
/*	public CompanyRecruitActivity getCompanyRecruitActivity(String companyWeixinNo,String passcode){
		logger.debug("getCompanyRecruitActivity for companyWeixinNo {}, passcode {} ",companyWeixinNo,passcode);
		return candidateInfoDao.getCompanyRecruitActivity(companyWeixinNo, passcode);
	}*/
	
	public List<CompanyRecruitActivity> getCompanyRecruitActivitysActive(String positionEntry){
		logger.debug("getCompanyRecruitActivity for Entry {}",positionEntry);
		List<CompanyRecruitActivity> result = companyRecruitActivityDao.getActiveActivityListByEntry(positionEntry);
	    logger.debug("the result size is {} for companyWeixinNo {} ", result == null ? 0 : result.size(),positionEntry);
		return result;
	}
	
	public Invitation generateInitInvitation(String weixinId,int activityId) throws Exception{
		Invitation invitation = new Invitation();
		CompanyRecruitActivity acti = companyRecruitActivityDao.getEntity(activityId);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(df.format(inviteDate)));
		calendar.add(Calendar.DAY_OF_MONTH, 100);
		Timestamp effDate = new Timestamp(calendar.getTimeInMillis());
		invitation.setPositionId(acti.getPositionId());
		invitation.setPassport(acti.getPasscode());
		invitation.setInvitationState(1);
		invitation.setInvitationDate(inviteDate);
		invitation.setExpDate(effDate);
		invitation.setStateDate(new Date(System.currentTimeMillis()));
		inviteDaoImpl.saveOrUpdate(invitation);
		return invitation;
	}
	public String getServiceBaseUrl(){
		ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.BASE_URL);
		logger.debug("the base url is {}", csp.getParamValue());
		return csp.getParamValue();
	}
	
	public Company getCompanyById(int companyId){
		logger.debug("the companyId is {} ", companyId);
		return companyDao.getEntity(companyId);
	}
	
	/**
     *使用url entry查询测评信息
     */
    @Override
    public Position queryCampusByEntry(String entry) throws PFServiceException
    {
        if(!StringUtils.isBlank(entry))
        {
            return positionDao.getPositionByEntry(entry);
        }
        return null;
    }

    /**
     *使用passport查询测评信息
     */
    @Override
    public String queryCampusByPassport(String passport) throws PFServiceException
    {
        if(!StringUtils.isBlank(passport))
        {
            return positionDao.getPositionByPassport(passport);
        }
        return "";
    }
    
    /**
     * 根据职位entry获取公司信息
     * @param positionEntry
     * @return
     * @throws PFServiceException
     */
    @Override
    public SchoolInfoData getCompanyByPositionEntry(String positionEntry) throws PFServiceException{
         SchoolInfoData info = new SchoolInfoData();
    	 Position position = positionDao.getPositionByEntry(positionEntry);
    	 if(position == null){
    		 logger.warn("not found position for entry {} ", positionEntry);
    		 return null;
    	 }
    	 // 测评名称
    	 info.setPositionName(position.getPositionName());
    	 // 公司微信号
    	 info.setWeixinCompany(position.getWeixinCompany());
    	 // 是否通知考生分数
    	 info.setNotifyScore(position.getNotifyScore());
    	 int employerId = position.getEmployerId();
    	 Employer employer = employerDao.getEntity(employerId);
    	 if(employer == null || employer.getCompanyId() == null){
    		 logger.warn("not found employer or companyId for employerId {} ", employerId);
    	 }else
    	 {
    	     Company company = getCompanyById(employer.getCompanyId());
    	     if (null != company)
             {
    	         info.setCompanyName(company.getCompanyName());
             }
    	 }
    	 return info;
    }

}
