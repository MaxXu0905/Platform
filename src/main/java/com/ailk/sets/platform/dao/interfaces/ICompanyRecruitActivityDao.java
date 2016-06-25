package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;

public interface ICompanyRecruitActivityDao extends IBaseDao<CompanyRecruitActivity> {

	public CompanyRecruitActivity getRecruitActivity(int postionId, String passcode);
	
    /**
     * 使用职位id找到对应的活动Id列表
     * @param positionId
     * @return
     */
    public List<Integer> getActivityIds(Integer positionId);
    
    /**
     * 使用职位id找到对应的活动Id列表
     * @param positionId
     * @return
     */
    public List<CompanyRecruitActivity> getActivityList(Integer positionId);
    
    
    /**
     * 使用职位entry找到对应的列表
     * @param positionId
     * @return
     */
    public List<CompanyRecruitActivity> getActiveActivityListByEntry(String positionEntry);

    /**
     * 使用活动Id删除活动(只删除考试状态为未开启的活动)
     * @param activityId
     */
    public void deleteCompanyRecruitActivity(Integer activityId);

    /**
     * 使用活动id查询活动信息
     * @param activityId
     * @return 
     */
    public CompanyRecruitActivity getActivityById(Integer activityId);
    
    /**
     * 
     * @param employerId
     * @return
     */
	public List<CompanyRecruitActivity> getCompanyRecruitActivity(int employerId, Integer positionId);
	
	
	/**
	 * 获取使用此地址的宣讲会信息
	 * @param addressId
	 * @return
	 */
    public List<CompanyRecruitActivity> getCompanyRecruitActivitiesByAddressId(Integer addressId);
    

}
