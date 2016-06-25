package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;

public class InvitationMail implements Serializable
{

    private static final long serialVersionUID = 5989282252565665309L;

    private String subject; // 邮件标题
    private String content; // 邮件内容

    // add by lipan 2014年7月5日14:41:21
    private Integer employerId; // 招聘者Id
    private Integer positionId; // 测评Id
    private String testPositionName; // 测评职位名称
    private String validDate; // 测评失效时间yyyy/MM/dd
    private Integer canWithOutCamera; // 是否允许考生在没有摄像头时进行考试 0:允许，1不允许
    private String currentDate; // 系统当前时间， validDate yyyy/MM/dd
    private Integer hasInterview; // 是否有面试题 1：有，  0 无
    private Integer totalTime; // 答题时间
    private Integer avgTime;//大约时长

    @Override
    public String toString()
    {
        return "InvitationMail [subject=" + subject + ", content=" + content + ", employerId="
                + employerId + ", positionId=" + positionId + ", testPositionName="
                + testPositionName + ", validDate=" + validDate + ", canWithOutCamera="
                + canWithOutCamera + ", currentDate=" + currentDate + "]";
    }

    
    public Integer getAvgTime() {
		return avgTime;
	}


	public void setAvgTime(Integer avgTime) {
		this.avgTime = avgTime;
	}


	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getValidDate()
    {
        return validDate;
    }

    public void setValidDate(String validDate)
    {
        this.validDate = validDate;
    }

    public String getTestPositionName()
    {
        return testPositionName;
    }

    public void setTestPositionName(String testPositionName)
    {
        this.testPositionName = testPositionName;
    }

    public Integer getEmployerId()
    {
        return employerId;
    }

    public void setEmployerId(Integer employerId)
    {
        this.employerId = employerId;
    }

    public Integer getPositionId()
    {
        return positionId;
    }

    public void setPositionId(Integer positionId)
    {
        this.positionId = positionId;
    }

    public Integer getCanWithOutCamera()
    {
        return canWithOutCamera;
    }

    public void setCanWithOutCamera(Integer canWithOutCamera)
    {
        this.canWithOutCamera = canWithOutCamera;
    }

    public String getCurrentDate()
    {
        return currentDate;
    }

    public void setCurrentDate(String currentDate)
    {
        this.currentDate = currentDate;
    }

    public Integer getHasInterview()
    {
        return hasInterview;
    }

    public void setHasInterview(Integer hasInterview)
    {
        this.hasInterview = hasInterview;
    }

}
