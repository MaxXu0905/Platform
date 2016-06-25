/**
 * author :  lipan
 * filename :  SchoolInfoData.java
 * create_time : 2014年7月21日 下午9:00:04
 */
package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

/**
 * @author : lipan
 * @create_time : 2014年7月21日 下午9:00:04
 * @desc : 校招接口返回信息
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class SchoolInfoData implements Serializable
{
    private static final long serialVersionUID = -2138060398635103916L;
    
    private String companyName; // 公司名称
    private Integer activityId; // 活动id
    private Integer weixinCompany; //1.用公司微信号; 0-用百一微信号(默认)
    private String positionName; // 测评名称
    private Integer notifyScore; // 是否通知考生分数：0-不通知(默认)；1-通知
    
    public SchoolInfoData()
    {
    }

    public SchoolInfoData(Integer weixinCompany)
    {
        this.weixinCompany = weixinCompany;
    }

    public Integer getWeixinCompany()
    {
        return weixinCompany;
    }

    public void setWeixinCompany(Integer weixinCompany)
    {
        this.weixinCompany = weixinCompany;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public Integer getActivityId()
    {
        return activityId;
    }

    public void setActivityId(Integer activityId)
    {
        this.activityId = activityId;
    }

    public String getPositionName()
    {
        return positionName;
    }

    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public Integer getNotifyScore()
    {
        return notifyScore;
    }

    public void setNotifyScore(Integer notifyScore)
    {
        this.notifyScore = notifyScore;
    }

    
}
