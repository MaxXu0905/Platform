package com.ailk.sets.platform.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.util.NumberConvert;

public class InvationEntity implements Serializable {
	private static final long serialVersionUID = 6044257854080918367L;
	private DateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
	private String companyName;
	private String candidateName;
	private String url;
	private int effDay;
	private String deadline;
	private String seriesName;
	private String level;
	private String positionName;
	private Date effDate;//生效日期
	private Date expDate;//失效日期
    private String selfContext;//自定义内容
	private Integer hasInterview = Constants.NEGATIVE; //是否有面试题;默认无
    private String paperContent; // 试卷内容，如：选择题（10道）、编程题（2道）、面试题（1道）
    private Integer paperSize; // 试卷一共包含几部分
	private Integer totalTime; // 试卷大致时间
	private Integer canWithOutCamera = Constants.POSITIVE; // 是否允许考生在没有摄像头时进行考试 0:允许，1不允许
	
	
	public String getSelfContext() {
		return selfContext;
	}

	public void setSelfContext(String selfContext) {
		this.selfContext = selfContext;
	}

	public String getExpDate() {
		if(expDate == null)
			return null;
		return df.format(expDate);
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	// 邮件主题
	public String getSubject() {
		return candidateName + "，您好，" + companyName + "邀您参加在线技术测评";
	}

	public String getSubjectForCand() {
		return companyName + "-" + getPosition() + "测评";
	}
	
	/**
     * 校招候选人考试标题
     * @return
     */
    public String getCuzSubjectForCand() {
        return companyName + "-" + getPositionName() + "测评";
    }

	public String getPosition() {
		return seriesName + level + "工程师";// positionName;
	}

	public String getEffDate() {
		if(effDate == null)
			return null;
		return df.format(effDate);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getEffDay() {
		return effDay;
	}

	public void setEffDay(int effDay) {
		this.effDay = effDay;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

    public String getSeriesName()
    {
        return seriesName;
    }

    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public Integer getHasInterview()
    {
        return hasInterview;
    }

    public void setHasInterview(Integer hasInterview)
    {
        this.hasInterview = hasInterview;
    }

    public String getPaperContent()
    {
        return paperContent;
    }

    public void setPaperContent(String paperContent)
    {
        this.paperContent = paperContent;
    }

    public Integer getPaperSize()
    {
        return paperSize;
    }

    public void setPaperSize(Integer paperSize)
    {
        this.paperSize = paperSize;
    }

    public Integer getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime)
    {
        this.totalTime = totalTime;
    }

    public Integer getCanWithOutCamera()
    {
        return canWithOutCamera;
    }

    public void setCanWithOutCamera(Integer canWithOutCamera)
    {
        this.canWithOutCamera = canWithOutCamera;
    }

}
