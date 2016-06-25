package com.ailk.sets.platform.intf.school.domain;

import java.io.Serializable;

import com.ailk.sets.platform.intf.model.Page;

public class SchoolReportCondition implements Serializable {


	@Override
	public String toString() {
		return "SchoolReportCondition [activityId=" + activityId + ", page=" + page + ", searchTxt=" + searchTxt
				+ ", testResult=" + testResult + ", intentPos=" + intentPos + ", positionId=" + positionId
				+ ", employerId=" + employerId + "]";
	}

	private static final long serialVersionUID = -2494153342649363172L;

	private Integer activityId;
	private Page page;
	private String searchTxt;
	private Integer testResult;// 测试状态 0-待定; 1-通过; 2-淘汰;3-复试
	private String intentPos;
    private Integer positionId;//测评id 社招过滤条件，  校招过滤条件为activityId
    private Integer employerId;//
    
    
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

	public Integer getTestResult() {
		return testResult;
	}

	public void setTestResult(Integer testResult) {
		this.testResult = testResult;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getSearchTxt() {
		return searchTxt;
	}

	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}

	public String getIntentPos() {
		return intentPos;
	}

	public void setIntentPos(String intentPos) {
		this.intentPos = intentPos;
	}

}
