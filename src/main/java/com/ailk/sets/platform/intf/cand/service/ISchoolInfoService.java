package com.ailk.sets.platform.intf.cand.service;

import java.util.List;

import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.InfoNeeded;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.SchoolInfoData;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public interface ISchoolInfoService {
	public List<InfoNeeded> getCandConfigInfoExts(String weixinId,int activityId) throws PFServiceException;
	
	public boolean saveCandidateInfo(String openId ,String weixinId,List<CandidateInfoExt> list);
	
//	public CompanyRecruitActivity getCompanyRecruitActivity(String companyWeixinNo,String passcode);
	
	public Invitation generateInitInvitation(String weixinId,int activityId) throws Exception;
	
	public String getServiceBaseUrl();
	
	public Company getCompanyById(int companyId);
	
	public List<CompanyRecruitActivity> getCompanyRecruitActivitysActive(String entry);

	   
    /**
     * 使用entry查询测评
     * @param entry url口令
     * @return
     * @throws PFServiceException 
     */
    public Position queryCampusByEntry(String entry) throws PFServiceException;
    
    /**
     * 使用passport查询测评
     * @param passport 百一考试口令
     * @return
     * @throws PFServiceException 
     */
    public String queryCampusByPassport(String passport) throws PFServiceException;
    /**
     * 根据职位entry获取公司信息
     * @param positionEntry
     * @return
     * @throws PFServiceException
     */
    public SchoolInfoData getCompanyByPositionEntry(String positionEntry) throws PFServiceException;

}
