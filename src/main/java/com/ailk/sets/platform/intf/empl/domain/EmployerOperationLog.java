package com.ailk.sets.platform.intf.empl.domain;

import java.sql.Timestamp;

/**
 * EmployerOperationLog entity. @author MyEclipse Persistence Tools
 */

public class EmployerOperationLog implements java.io.Serializable {
	private static final long serialVersionUID = 5773915538712004786L;
	private Integer logId;
	private Timestamp operDate;
	private Integer operType;
	private String employerAcct;
	private String clientIp;
	private String os;
	private String browser;
	private String browserVersion;
	private String url;

	
	// Constructors

	@Override
	public String toString() {
		return "EmployerOperationLog [operType=" + operType + ", employerAcct=" + employerAcct + ", os=" + os
				+ ", browser=" + browser + ", browserVersion=" + browserVersion + "]";
	}

	/** default constructor */
	public EmployerOperationLog() {
	}

	/** minimal constructor */
	public EmployerOperationLog(Timestamp operDate) {
		this.operDate = operDate;
	}

	/** full constructor */
	public EmployerOperationLog(Timestamp operDate, Integer operType, String employerAcct, String clientIp, String os, String browser, String browserVersion, String url) {
		this.operDate = operDate;
		this.operType = operType;
		this.employerAcct = employerAcct;
		this.clientIp = clientIp;
		this.os = os;
		this.browser = browser;
		this.browserVersion = browserVersion;
		this.url = url;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Timestamp getOperDate() {
		return this.operDate;
	}

	public void setOperDate(Timestamp operDate) {
		this.operDate = operDate;
	}

	public Integer getOperType() {
		return this.operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public String getEmployerAcct() {
		return this.employerAcct;
	}

	public void setEmployerAcct(String employerAcct) {
		this.employerAcct = employerAcct;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return this.browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowserVersion() {
		return this.browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}