package com.ailk.sets.platform.domain;

import java.sql.Timestamp;

/**
 * CandidateInfoFromApp entity. @author MyEclipse Persistence Tools
 */

public class CandidateInfoFromApp implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1913941165803829102L;
	private CandidateInfoFromAppId id;
	private String batchName;
	private String candidatePhone;
	private Timestamp createDate;
	private Long testId;
	private String openId;

	// Constructors

	/** default constructor */
	public CandidateInfoFromApp() {
	}

	/** minimal constructor */
	public CandidateInfoFromApp(CandidateInfoFromAppId id) {
		this.id = id;
	}

	/** full constructor */
	public CandidateInfoFromApp(CandidateInfoFromAppId id, String batchName, Timestamp createDate, Long testId) {
		this.id = id;
		this.batchName = batchName;
		this.createDate = createDate;
		this.testId = testId;
	}

	// Property accessors

	public CandidateInfoFromAppId getId() {
		return this.id;
	}

	public void setId(CandidateInfoFromAppId id) {
		this.id = id;
	}

	public String getBatchName() {
		return this.batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Long getTestId() {
		return this.testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getCandidatePhone() {
		return candidatePhone;
	}

	public void setCandidatePhone(String candidatePhone) {
		this.candidatePhone = candidatePhone;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "CandidateInfoFromApp [id=" + id + ", batchName=" + batchName + ", candidatePhone=" + candidatePhone
				+ ", createDate=" + createDate + ", testId=" + testId + ", openId="+openId +"]";
	}

	
}