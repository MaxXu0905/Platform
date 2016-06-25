/**
 * author :  lipan
 * filename :  CampusCreateRsp.java
 * create_time : 2014年7月3日 下午5:43:54
 */
package com.ailk.sets.platform.intf.model.campus;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;

/**
 * @author : lipan
 * @create_time : 2014年7月3日 下午5:43:54
 * @desc : 校招测评创建相应对象
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class CampusRsp extends BaseResponse implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2500033046851025800L;

    private Integer positionId; // 职位ID
    private Integer weixinCompany; // 1.用公司微信号; 0-用百一微信号
    private String passport; // 口令
    private String entry; // entry
    private String activityIds; // 活动ID,使用竖线分割
    
    /** 测速App字段 **/
    private Integer addressId; // 地址ID
    private Integer num; // 处理记录数
    private Integer status; // app登录口令是否有效
    private Integer signalTester ; // 测试人Id
    private List<ActivityRecruitAddress> addressList; //地址列表
    
    @Override
    public String toString()
    {
        return "CampusRsp [positionId=" + positionId + ", weixinCompany=" + weixinCompany
                + ", passport=" + passport + ", entry=" + entry + ", activityIds=" + activityIds
                + ", addressId=" + addressId + ", num=" + num + ", status=" + status
                + ", signalTester=" + signalTester + ", addressList=" + addressList + "]";
    }
    public Integer getPositionId()
    {
        return positionId;
    }
    public void setPositionId(Integer positionId)
    {
        this.positionId = positionId;
    }
    public String getActivityIds()
    {
        return activityIds;
    }
    public void setActivityIds(String activityIds)
    {
        this.activityIds = activityIds;
    }
    public Integer getWeixinCompany()
    {
        return weixinCompany;
    }
    public void setWeixinCompany(Integer weixinCompany)
    {
        this.weixinCompany = weixinCompany;
    }
    public String getPassport()
    {
        return passport;
    }
    public void setPassport(String passport)
    {
        this.passport = passport;
    }
    public String getEntry()
    {
        return entry;
    }
    public void setEntry(String entry)
    {
        this.entry = entry;
    }
    public Integer getAddressId()
    {
        return addressId;
    }
    public void setAddressId(Integer addressId)
    {
        this.addressId = addressId;
    }
    public Integer getNum()
    {
        return num;
    }
    public void setNum(Integer num)
    {
        this.num = num;
    }
    public Integer getStatus()
    {
        return status;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    public List<ActivityRecruitAddress> getAddressList()
    {
        return addressList;
    }
    public void setAddressList(List<ActivityRecruitAddress> addressList)
    {
        this.addressList = addressList;
    }
    public Integer getSignalTester()
    {
        return signalTester;
    }
    public void setSignalTester(Integer signalTester)
    {
        this.signalTester = signalTester;
    }
    
}
