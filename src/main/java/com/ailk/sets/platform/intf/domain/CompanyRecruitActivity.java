package com.ailk.sets.platform.intf.domain;

import java.sql.Timestamp;
import java.util.List;


/**
 * CompanyRecruitActivity entity. @author MyEclipse Persistence Tools
 */

public class CompanyRecruitActivity implements java.io.Serializable {
    
    private static final long serialVersionUID = -335968635470486602L;
    public static final int TEST_STATE_NORMAL =  0; // 考试状态,    0-未开始
    public static final int TEST_STATE_USING =  1; // 考试状态, 1-已开始
    public static final int TEST_STATE_DONE =  2; // 考试状态,  2-已结束
    
    /** company_recruit_activity 表对应字段  **/
    private Integer activityId;// 活动id
    private String addressId; // 活动地址信息 多地址
    private String activityDate;// 活动日期
    private String beginTime;// 开始时间
    private String endTime;// 结束时间
    private Integer testState;// 测试状态
    private Integer positionId;// 测评id
    private String passcode; // passport 用于兼容历史数据，统计参考人数
    private Integer notifyTestResult; //是否通知考点测试结果
    
	/** activity_recruit_address 表对应字段  **/
    private String city;// 城市
    private String college;// 大学
    private String address;// 详细地址
    private Integer seatNumber;// 座位数量
    private Integer signalStrength;// 号强度
    private Timestamp createDate;// 创建日期
    
    private List<String> addresses;//可以添加多个详细地址
    /** 考试信息 **/
    private Long total; // 参考人数
    private Long todo; // 待处理人数
    private Long recommend; // 已推荐人数
    private Long weedOut; // 已淘汰人数
    
    /**是否是离当前时间最近的宣讲会**/
    private Integer isCurrent; // 1是；0否
    
    private String passport;//position的口令，如果是用百一微信公众号 用于hr活动列表显示，
    
    private String minDate;//最早开始时间  发送勘测邮件去重地址时使用 
    private String minTime;//最早开始时间  发送勘测邮件去重地址时使用 
    
    
    public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public CompanyRecruitActivity(){
    	this.notifyTestResult = 0;
    }
    
    public Integer getNotifyTestResult() {
		return notifyTestResult;
	}
	public void setNotifyTestResult(Integer notifyTestResult) {
		this.notifyTestResult = notifyTestResult;
	}
	public List<String> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
    public Integer getActivityId()
    {
        return activityId;
    }
    public void setActivityId(Integer activityId)
    {
        this.activityId = activityId;
    }
    public Integer getPositionId()
    {
        return positionId;
    }
    public void setPositionId(Integer positionId)
    {
        this.positionId = positionId;
    }
    
    public String getAddressId()
    {
        return addressId;
    }
    public void setAddressId(String addressId)
    {
        this.addressId = addressId;
    }
    
    public String getBeginTime()
    {
        return beginTime;
    }
    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }
    public String getEndTime()
    {
        return endTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    public Integer getTestState()
    {
        return testState;
    }
    public void setTestState(Integer testState)
    {
        this.testState = testState;
    }
    public String getActivityDate()
    {
        return activityDate;
    }
    public void setActivityDate(String activityDate)
    {
        this.activityDate = activityDate;
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
    public Integer getSignalStrength()
    {
        return signalStrength;
    }
    public void setSignalStrength(Integer signalStrength)
    {
        this.signalStrength = signalStrength;
    }
    public Timestamp getCreateDate()
    {
        return createDate;
    }
    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getTodo() {
		return todo;
	}
	public void setTodo(Long todo) {
		this.todo = todo;
	}
	public Long getRecommend() {
		return recommend;
	}
	public void setRecommend(Long recommend) {
		this.recommend = recommend;
	}
	public Long getWeedOut() {
		return weedOut;
	}
	public void setWeedOut(Long weedOut) {
		this.weedOut = weedOut;
	}
    public Integer getIsCurrent()
    {
        return isCurrent;
    }
    public void setIsCurrent(Integer isCurrent)
    {
        this.isCurrent = isCurrent;
    }
    
    public void copyPropertiesFromAddress(ActivityRecruitAddress address){
        this.city = address.getCity();
        this.address = address.getAddress();
    	this.college = address.getCollege();
    	this.seatNumber = address.getSeatNumber();
//    	this.seatNumber     = address.getSeatNumber();
    
    }

	@Override
	public String toString() {
		return "CompanyRecruitActivity [addressId=" + addressId + ", college=" + college + ", address=" + address
				+ ", seatNumber=" + seatNumber + "]";
	}
    
}