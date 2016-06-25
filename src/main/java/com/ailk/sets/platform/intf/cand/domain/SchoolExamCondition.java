package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

public class SchoolExamCondition implements Serializable {

	@Override
	public String toString() {
		return "SchoolExamCondition [weixinId="
				+ weixinId + ", activityId=" + activityId + ",positionEntry="+ positionEntry +"]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7717271244215951612L;
	private String weixinId;
	private Integer activityId;
	private String locationInfo; // 位置信息  经度|纬度|精度
	private String positionEntry;// 测评entry  子测评或者普通测评的entry ，因为当为组测评时根据activityId是生成不了试卷的，activityid只能找到组测评


	public String getPositionEntry() {
		return positionEntry;
	}

	public void setPositionEntry(String positionEntry) {
		this.positionEntry = positionEntry;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}


	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

    public String getLocationInfo()
    {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo)
    {
        this.locationInfo = locationInfo;
    }

}
