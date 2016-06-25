package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.model.condition.Interval;

/**
 * 校招活动地址信息
 * @author : lipan
 * @create_time : 2014年7月1日 下午8:47:30
 * @desc : 
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public interface IActivityRecruitAddressDao extends IBaseDao<ActivityRecruitAddress> {
	
	/**
	 * 学校信号强度列表
	 * @param activity
	 * @return
	 */
	public List<ActivityRecruitAddress> getActivityAddressList(ActivityRecruitAddress address);

	
	/**
	 * 删除校招活动地址信息
	 * @param address
	 */
	public void deleteActivityAddress(ActivityRecruitAddress address);
	
	
	public void saveActivityAddress(ActivityRecruitAddress address);


    /**
     * 查询地址信息
     * @param address
     */
    public ActivityRecruitAddress getAddress(ActivityRecruitAddress address);
    
    /**
     * 测速App -使用城市、大学查询详细地址信息
     * @param address
     */
    public List<String> getDetailAddList(ActivityRecruitAddress address);
    
    /**
     * 测速App - 使用职位id查询所有未结束的宣讲会地址
     * @param positionId
     * @return
     */
    public List<ActivityRecruitAddress> getAddListByPositionId(Integer positionId);
    
    
    /**
     * 查询宣讲会地址信息
     * @param positionId
     * @return
     */
    public List<ActivityRecruitAddress> getAllAddressByPositionId(Integer positionId);
    
    /**
     * 获取未监测的地址
     * @return
     */
    public List<ActivityRecruitAddress> getUnTestActivityRecruitAddresses();
    
    
    /**
     * 获取未监测的地址
     * @return
     */
    List<ActivityRecruitAddress> getAddresseSignals(String keyword, Interval num);
}
