package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;

import com.ailk.sets.platform.intf.common.PFResponse;

public class EmployerRegistInfo extends PFResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8973722801607839429L;

	private String employerEmail;
	private String companyName;
	private String employerName;
	private Integer employerId;
	
	private int channelType;//接口涞源  对应外部调用的clientId
	
	
	public int getChannelType() {
		return channelType;
	}
	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}
	public Integer getEmployerId() {
		return employerId;
	}
	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	
	
	public String getEmployerEmail() {
		return employerEmail;
	}
	public void setEmployerEmail(String employerEmail) {
		this.employerEmail = employerEmail;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return "EmployerRegistInfo [employerEmail=" + employerEmail + ", companyName=" + companyName
				+ ", employerName=" + employerName + ", employerId=" + employerId + "]";
	}
	
}
