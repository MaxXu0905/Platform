package com.ailk.sets.platform.intf.cand.domain;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Employer entity. @author MyEclipse Persistence Tools
 */

public class Employer implements java.io.Serializable {

	private static final long serialVersionUID = -4372854701897714252L;
	private Integer employerId;
	private String employerName;
	private String employerNumber;
	private Integer companyId;
	private String deptName;
	private String employerPwd;
	private Integer acctType;
	private Integer state;
	private Date createDate;
	private String contactType;
	private String contactPhone;
	private String employerAcct;
	private Integer initFlag;
	private String ticket;
	private String openId;
	private String authCode;

	private Integer acctRole;//账户角色 1社招  2校招
	private String employerToken;//token 用户百一token 第三方接口注册时获取token
	private Timestamp tokenExpiredTime;//过期时间
	// Constructors

	

	/** default constructor */
	public Employer() {
		this.acctRole = 1;
	}

	


	public Timestamp getTokenExpiredTime() {
		return tokenExpiredTime;
	}

	public void setTokenExpiredTime(Timestamp tokenExpiredTime) {
		this.tokenExpiredTime = tokenExpiredTime;
	}




	public String getEmployerToken() {
		return employerToken;
	}

	public void setEmployerToken(String employerToken) {
		this.employerToken = employerToken;
	}

	/** full constructor */
	public Employer(String employerName, String employerNumber, Integer companyId, String deptName, String employerPwd, Integer acctType, Integer state, Date createDate, String contactType,
			String contactPhone, String employerAcct, Integer initFlag) {
		this.employerName = employerName;
		this.employerNumber = employerNumber;
		this.companyId = companyId;
		this.deptName = deptName;
		this.employerPwd = employerPwd;
		this.acctType = acctType;
		this.state = state;
		this.createDate = createDate;
		this.contactType = contactType;
		this.contactPhone = contactPhone;
		this.employerAcct = employerAcct;
		this.initFlag = initFlag;
	}

	// Property accessors
	public Integer getAcctRole() {
		return acctRole;
	}

	public void setAcctRole(Integer acctRole) {
		this.acctRole = acctRole;
	}
	public Integer getEmployerId() {
		return this.employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getEmployerName() {
		return this.employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getEmployerNumber() {
		return this.employerNumber;
	}

	public void setEmployerNumber(String employerNumber) {
		this.employerNumber = employerNumber;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmployerPwd() {
		return this.employerPwd;
	}

	public void setEmployerPwd(String employerPwd) {
		this.employerPwd = employerPwd;
	}

	public Integer getAcctType() {
		return this.acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getEmployerAcct() {
		return this.employerAcct;
	}

	public void setEmployerAcct(String employerAcct) {
		this.employerAcct = employerAcct;
	}

	public Integer getInitFlag() {
		return this.initFlag;
	}

	public void setInitFlag(Integer initFlag) {
		this.initFlag = initFlag;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

    public String getAuthCode()
    {
        return authCode;
    }

    public void setAuthCode(String authCode)
    {
        this.authCode = authCode;
    }

	@Override
	public String toString() {
		return "Employer [employerId=" + employerId + ", employerName=" + employerName + ", employerNumber="
				+ employerNumber + ", companyId=" + companyId + ", deptName=" + deptName + ", employerPwd="
				+ employerPwd + ", acctType=" + acctType + ", state=" + state + ", createDate=" + createDate
				+ ", contactType=" + contactType + ", contactPhone=" + contactPhone + ", employerAcct=" + employerAcct
				+ ", initFlag=" + initFlag + ", ticket=" + ticket + ", openId=" + openId + ", authCode=" + authCode
				+ "]";
	}
	
    
}