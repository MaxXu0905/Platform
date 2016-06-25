package com.ailk.sets.platform.intf.empl.domain;

import com.ailk.sets.platform.intf.common.PFResponse;

public class EmployerTrialActiveResponse extends PFResponse {
	private Integer employerId;
	private String employerName;
	
	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}
}
