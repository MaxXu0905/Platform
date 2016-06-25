package com.ailk.sets.platform.intf.model.param;

import java.io.Serializable;

import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.condition.Interval;

public class GetReportParam implements Serializable {

	private static final long serialVersionUID = -4648994742433388033L;
	private Integer employerId;
	private Integer positionId;
	private String testResult;
	private Page page;
	private Interval score;

	// add lipan 2014年7月11日15:41:21
	private String passport; // 校招活动唯一标识...
	
	// add by lipan 2014年10月24日 报告列表查询条件
    private String commitPaperFromDate; // "2014-10-20"交卷开始时间
    private String commitPaperToDate; // "2014-10-21"交卷截止时间
    private String inputKey; // 输入关键字
    private String positionIntent; // 职位意向 1 开发类; 2 测试类; 3 运维类; 4 设计类; 99 其他类
    
    private Integer orderByPositionId;//排序字段  按哪一个子测评的测评id排序 组测评新加需求
    private Integer filterScorePositionId;//测评组时需要指定哪一个子测评作为过滤条件
    
    private Integer activityId; //活动id  当校招时可以以宣讲会搜索
	
    
	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public GetReportParam(Integer positionId, String testResult, Page page,
			Interval score) {
		this.positionId = positionId;
		this.testResult = testResult;
		this.page = page;
		this.score = score;
	}
	
	public GetReportParam() {
	}
	
	
	public Integer getFilterScorePositionId() {
		return filterScorePositionId;
	}

	public void setFilterScorePositionId(Integer filterScorePositionId) {
		this.filterScorePositionId = filterScorePositionId;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Interval getScore() {
		return score;
	}

	public void setScore(Interval score) {
		this.score = score;
	}

    public String getCommitPaperFromDate()
    {
        return commitPaperFromDate;
    }

    public void setCommitPaperFromDate(String commitPaperFromDate)
    {
        this.commitPaperFromDate = commitPaperFromDate;
    }

    public String getCommitPaperToDate()
    {
        return commitPaperToDate;
    }

    public void setCommitPaperToDate(String commitPaperToDate)
    {
        this.commitPaperToDate = commitPaperToDate;
    }

    public String getInputKey()
    {
        return inputKey;
    }

    public void setInputKey(String inputKey)
    {
        this.inputKey = inputKey;
    }

    public String getPositionIntent()
    {
        return positionIntent;
    }

    public void setPositionIntent(String positionIntent)
    {
        this.positionIntent = positionIntent;
    }

	public Integer getOrderByPositionId() {
		return orderByPositionId;
	}

	public void setOrderByPositionId(Integer orderByPositionId) {
		this.orderByPositionId = orderByPositionId;
	}

}
