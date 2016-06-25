/**
 * author :  lipan
 * filename :  ActivityRecruitAddress.java
 * create_time : 2014年7月1日 下午8:04:54
 */
package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author : lipan
 * @create_time : 2014年7月1日 下午8:04:54
 * @desc : 宣讲会招聘地址信息
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class ActivityRecruitAddress implements Serializable
{
    private static final long serialVersionUID = -6319335561717961471L;
    private Integer signalTester;// 测试人，虽然表中无此字段，但仍然留下作为提交测速信息的验证字段
    private Integer addressId; // 主键
    private String city;// 城市
    private String college;// 大学
    private String address;// 详细地址
    private Integer seatNumber;// 座位数量
    // private Integer signalStrength;// 号强度
    // private Integer download;// 下载速度
    private Integer cmcNum; // 移动带宽
    private Integer cucNum; // 联通带宽
    private Integer ctcNum;// 电信带宽
    private Integer totalNum;// 电信带宽
    private Timestamp createDate;// 创建日期
    
    /**
     * 
     */
    public ActivityRecruitAddress()
    {
    }

    public Integer getAddressId()
    {
        return addressId;
    }

    public void setAddressId(Integer addressId)
    {
        this.addressId = addressId;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCollege()
    {
        return college;
    }

    public void setCollege(String college)
    {
        this.college = college;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Integer getSeatNumber()
    {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber)
    {
        this.seatNumber = seatNumber;
    }

    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }

    public Integer getCmcNum()
    {
        return cmcNum;
    }

    public void setCmcNum(Integer cmcNum)
    {
        this.cmcNum = cmcNum;
    }

    public Integer getCucNum()
    {
        return cucNum;
    }

    public void setCucNum(Integer cucNum)
    {
        this.cucNum = cucNum;
    }

    public Integer getCtcNum()
    {
        return ctcNum;
    }

    public void setCtcNum(Integer ctcNum)
    {
        this.ctcNum = ctcNum;
    }

    public Integer getSignalTester()
    {
        return signalTester;
    }

    public void setSignalTester(Integer signalTester)
    {
        this.signalTester = signalTester;
    }

    public Integer getTotalNum()
    {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum)
    {
        this.totalNum = totalNum;
    }

    public void copyPropertiesFromActivity(CompanyRecruitActivity activity){
        this.city = activity.getCity();
        this.address = activity.getAddress();
    	this.college = activity.getCollege();
    	this.seatNumber = activity.getSeatNumber();
//    	this.seatNumber     = address.getSeatNumber();
    
    }
}
