package com.ailk.sets.platform.domain;

import java.sql.Timestamp;

/**
 * EmployerLostPwd entity. @author MyEclipse Persistence Tools
 */

public class EmployerLostPwd implements java.io.Serializable {

	private static final long serialVersionUID = 6563919743431342013L;
	private String uuid;
	private Timestamp applyDate;
	private String employerAcct;

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Timestamp getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public String getEmployerAcct() {
		return this.employerAcct;
	}

	public void setEmployerAcct(String employerAcct) {
		this.employerAcct = employerAcct;
	}

}