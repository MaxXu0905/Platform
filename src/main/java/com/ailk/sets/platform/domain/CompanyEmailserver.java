package com.ailk.sets.platform.domain;

/**
 * CompanyEmailserver entity. @author MyEclipse Persistence Tools
 */

public class CompanyEmailserver implements java.io.Serializable {

	private static final long serialVersionUID = 3943536541827698103L;
	private Integer companyId;
	private String companyEmail;
	private String emailServer;
	private String emailServerPort;
	private String emailAcct;
	private String emailPwd;

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyEmail() {
		return this.companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getEmailServer() {
		return this.emailServer;
	}

	public void setEmailServer(String emailServer) {
		this.emailServer = emailServer;
	}

	public String getEmailServerPort() {
		return this.emailServerPort;
	}

	public void setEmailServerPort(String emailServerPort) {
		this.emailServerPort = emailServerPort;
	}

	public String getEmailAcct() {
		return this.emailAcct;
	}

	public void setEmailAcct(String emailAcct) {
		this.emailAcct = emailAcct;
	}

	public String getEmailPwd() {
		return this.emailPwd;
	}

	public void setEmailPwd(String emailPwd) {
		this.emailPwd = emailPwd;
	}

}