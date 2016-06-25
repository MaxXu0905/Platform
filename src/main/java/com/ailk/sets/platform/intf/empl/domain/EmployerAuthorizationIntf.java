package com.ailk.sets.platform.intf.empl.domain;


/**
 * EmployerAuthorizationIntf entity. @author MyEclipse Persistence Tools
 */

public class EmployerAuthorizationIntf implements java.io.Serializable {

	private static final long serialVersionUID = 2771585193917945496L;
	private Integer employerId;
	private String emailGranted; //授权email
    private Integer positionGranted;
	private Integer privilege;
	
	
	public Integer getEmployerId() {
		return employerId;
	}
	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}
	public String getEmailGranted() {
		return emailGranted;
	}
	public void setEmailGranted(String emailGranted) {
		this.emailGranted = emailGranted;
	}
	public Integer getPositionGranted() {
		return positionGranted;
	}
	public void setPositionGranted(Integer positionGranted) {
		this.positionGranted = positionGranted;
	}
	public Integer getPrivilege() {
		return privilege;
	}
	public void setPrivilege(Integer privilege) {
		this.privilege = privilege;
	}
	@Override
	public String toString() {
		return "EmployerAuthorizationIntf [employerId=" + employerId + ", emailGranted=" + emailGranted
				+ ", positionGranted=" + positionGranted + ", privilege=" + privilege + "]";
	}
	

}