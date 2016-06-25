package com.ailk.sets.platform.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.ConfigCollege;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.model.Mapping;

/**
 * 获取考生信息dao
 * 
 * @author 毕希研
 * 
 */
public interface ICandidateInfoDao extends IBaseDao<CandidateInfoExt>{
	/**
	 * 获取招聘人常规信息设置
	 * 
	 * @param employerId
	 * @return
	 */
	public List<PositionInfoExt> getCandConfigInfoExts(int employerId,Integer positionId);

	public int getEmployerId(long testId);

	public List<Mapping> getRangeMap(String sql);

	public Invitation getInvitation(long testId);

	public Map<String, CandidateInfoExt> getCandidateInfoExts(String name, String email);

	/**
	 * 根据candidateId和InfoId获取信息
	 * @param ids
	 * @param infoId
	 * @return
	 */
	public List<CandidateInfoExt> getCandidateInfoExts(List<Integer> ids, String infoId);
	
	public CandidateInfoExt getCandidateInfoExt(int id,String infoId);

	public boolean saveCandidateInfo(int candidateId, List<CandidateInfoExt> list);

	public CandidateTest getCandidateTest(long paperInstId);

	/**
	 * 获取应聘者对象
	 * 
	 * @param candidateId
	 * @return
	 * @throws PFDaoException
	 */
	public Candidate getCandidateById(int candidateId);

	public List<Mapping> getQueryInfo(String sql);

	/**
	 * 根据微信获取招聘人
	 * 
	 * @param companyWeixinId
	 * @param passcode
	 * @return
	 */
	public int getEmployerId(String companyWeixinId, String passcode);

	public Map<String, CandidateInfoExt> getCandidateInfoExts(String weixinId);

	public Candidate getCandiateByWeixinNo(String weixinNo);

	public int getCandidateIdWithWeixin(String openId , String name, String email, String weixinNo);

//	public CompanyRecruitActivity getCompanyRecruitActivity(String companyWeixinNo, String passcode);
	
	public List<ConfigCollege> getConfigColleges(String searchTxt,int size);
}
