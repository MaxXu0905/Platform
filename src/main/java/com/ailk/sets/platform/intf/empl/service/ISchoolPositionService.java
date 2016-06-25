package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.ActivityAddressSignal;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivityCount;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeNameWithCount;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionGroupInfo;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.campus.CampusRsp;
import com.ailk.sets.platform.intf.model.campus.SignUpOnlineSetsRsp;
import com.ailk.sets.platform.intf.model.condition.Interval;

public interface ISchoolPositionService {
	public List<CompanyRecruitActivity> getActivitiesByEmployerId(int employerId, Integer positionId);

	public PFResponse updateActivity(int activityId, int status);

	public CompanyRecruitActivityCount getCompanyRecruitActivityCount(int activityId);

	/**
	 * 获取职位意向报考人数
	 * @param activityId
	 * type 1测评 2活动
	 * @return
	 * @throws PFServiceException 
	 */
	public List<ConfigCodeNameWithCount> getPosIntention(int employerId,int posOrActId, int type) throws PFServiceException;
	
	
	/**
	 * 新增校招测评
	 * @param activityList 宣讲会list
	 * @return
	 * @throws PFServiceException 
	 */
	public CampusRsp createCampusPosition(Position position) throws PFServiceException;
	
	/**
	 * 测评组
	 * @param position
	 * @return
	 * @throws PFServiceException
	 */
	public CampusRsp createGroupPosition(PositionGroupInfo position) throws PFServiceException;
	
	
	/**
	 * 根据 城市和学校获取该学校地址的信号强度列表
	 * @param activity
	 * @return
	 * @throws PFServiceException
	 */
	public List<ActivityRecruitAddress> getActivityAddressList(ActivityRecruitAddress address) throws PFServiceException;
	
	
	/**
     * 查询校招测评
     * @param positionId 测评Id
     * @return
     * @throws PFServiceException 
     */
	public Position queryCampus(Integer employerId , Integer positionId) throws PFServiceException;
	
	
	/**
	 * 更新宣讲会地址信息
	 * @param passport
	 * @return
	 * @throws PFServiceException
	 */
	public void updateActivityRecruitAddress(CompanyRecruitActivity address) throws PFServiceException;
	
	/**
	 * 获取测评passport
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public CampusRsp getCampusPassport(Integer positionId) throws PFServiceException;
	
	/**
	 * 重新获取passport
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public CampusRsp refreshCampusPassport(Integer positionId) throws PFServiceException;
	
	/**
	 * 修改口令
	 * @param positionId
	 * @param passport
	 * @return
	 */
	public PFResponse updatePositionPassport(Integer positionId , String passport);
	/**
	 * 根据招聘人的passport获得所有未开始宣讲会的地址列表
	 * @param epPassport
	 * @return
	 * @throws PFServiceException
	 */
	public CampusRsp getAddListByEpPassport(String epPassport) throws PFServiceException;
	
	/**
	 * 根据城市、大学查询详细地址
	 * @param address
	 * @return
	 * @throws PFServiceException
	 */
	public List<String> getDetailAddList(ActivityRecruitAddress address) throws PFServiceException;
	
	/**
	 * 测速App － 新增学校地址 / 更新学校信息
	 * @param address
	 * @return
	 * @throws PFServiceException
	 */
	public CampusRsp saveAddress(ActivityRecruitAddress address) throws PFServiceException;
	
	/**
	 * 新增学校地址 / 更新学校信息
	 * @param address
	 * @return
	 * @throws PFServiceException
	 */
	public CampusRsp updateAddress(ActivityRecruitAddress address) throws PFServiceException;
	
	/**
	 * 保存学校手机测速日志数据
	 * @param signal
	 * @return
	 * @throws PFServiceException
	 */
	public CampusRsp saveSpeedTestLog(List<ActivityAddressSignal> signal) throws PFServiceException;
	
	
	/**
     * 查询校招转在线考试设置信息（考试终止时间）
     * @param positionId
     * @return
     * @throws PFServiceException
     */
    public SignUpOnlineSetsRsp querySets(Integer positionId) throws PFServiceException;
    
    /**
     * 更新校招转在线考试设置信息（考试终止时间）
     * @param signal
     * @return
     * @throws PFServiceException
     */
    public void updateSets(Integer positionId,String reqEndDate, String examEndDate) throws PFServiceException;
	
    /**
	 * 完成地址测试时调用， 此方法在有些宣讲会所有地址都测试完成后会发邮件
	 * @param addressId
	 */
    public  void finishedTestAddress(int addressId);
    /**
     * 获取未监测的地址
     * @return
     */
    public List<ActivityRecruitAddress> getUnTestActivityRecruitAddresses();
    /**
     * 获取大学信号信息；根据关键字/预计考试人数
     * @return
     */
    public List<ActivityRecruitAddress> getAddresseSignals(String keyword, Interval num);
}
