package com.ailk.sets.platform.intf.empl.domain;

import java.sql.Timestamp;

/**
 * EmployerTrialApply entity. @author MyEclipse Persistence Tools
 */

public class EmployerTrialApply implements java.io.Serializable {
	@Override
	public String toString() {
		return "EmployerTrialApply [userName=" + userName + ", userEmail="
				+ userEmail + ", acctRole=" + acctRole + "]";
	}

	private static final long serialVersionUID = -750264039889686980L;
	private Integer applyId;
	private String userName;
	private String userEmail;
	private String userPwd;
	private Timestamp applyDate;
	private String activationKey;
	private Integer activated;
	
	private boolean needUserName; //如果employer没有名次，激活时需要传名称
	
	private Integer acctRole;//账户角色 1社招  2校招

	// Constructors


	/** default constructor */
	public EmployerTrialApply() {
		this.acctRole = 1;
	}

	public Integer getAcctRole() {
		return acctRole;
	}

	public void setAcctRole(Integer acctRole) {
		this.acctRole = acctRole;
	}

	public boolean isNeedUserName() {
		return needUserName;
	}

	public void setNeedUserName(boolean needUserName) {
		this.needUserName = needUserName;
	}

	/** minimal constructor */
	public EmployerTrialApply(Integer applyId, String userName,
			String userEmail, Timestamp applyDate,
			String activationKey) {
		this.applyId = applyId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.applyDate = applyDate;
		this.activationKey = activationKey;
	}

	/** full constructor */
	public EmployerTrialApply(Integer applyId, String userName,
			String userEmail, String userPwd,
			Timestamp applyDate, String activationKey, Integer activated) {
		this.applyId = applyId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPwd = userPwd;
		this.applyDate = applyDate;
		this.activationKey = activationKey;
		this.activated = activated;
	}

	// Property accessors

	public Integer getApplyId() {
		return this.applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}


	public Timestamp getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public String getActivationKey() {
		return this.activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public Integer getActivated() {
		return this.activated;
	}

	public void setActivated(Integer activated) {
		this.activated = activated;
	}

}