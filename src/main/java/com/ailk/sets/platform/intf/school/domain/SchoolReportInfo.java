package com.ailk.sets.platform.intf.school.domain;

import java.io.Serializable;

import com.ailk.sets.platform.intf.common.PFResponse;

public class SchoolReportInfo extends PFResponse implements Serializable{
	private static final long serialVersionUID = -8504312245034775534L;
	private Integer testResult;
	private Double  getScore;
	
	public Double getGetScore() {
		return getScore;
	}
	public void setGetScore(Double getScore) {
		this.getScore = getScore;
	}
	public Integer getTestResult() {
		return testResult;
	}
	public void setTestResult(Integer testResult) {
		this.testResult = testResult;
	}

}
