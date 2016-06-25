package com.ailk.sets.platform.intf.empl.domain;

import java.sql.Timestamp;

/**
 * CandidateReport entity. @author MyEclipse Persistence Tools
 */

public class CandidateReport implements java.io.Serializable {

	private static final long serialVersionUID = -4181349641640256858L;
	private Long testId;
	private Integer candidateId;
	private Integer employerId;
	private String reportPassport;
	private Timestamp reportDate;
	private String reportFile;
	private Integer reportState;
	private Double getScore;
	private String content;
	private Integer notified;
	private Integer sample;// 是否样例职位: 0-否,1-是
	
	
	public Integer getSample() {
		return sample;
	}

	public void setSample(Integer sample) {
		this.sample = sample;
	}

	public Long getTestId() {
		return this.testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Integer getCandidateId() {
		return this.candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public Integer getEmployerId() {
		return this.employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getReportPassport() {
		return this.reportPassport;
	}

	public void setReportPassport(String reportPassport) {
		this.reportPassport = reportPassport;
	}

	public Timestamp getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportFile() {
		return this.reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public Integer getReportState() {
		return this.reportState;
	}

	public void setReportState(Integer reportState) {
		this.reportState = reportState;
	}

	public Double getGetScore() {
		return this.getScore;
	}

	public void setGetScore(Double getScore) {
		this.getScore = getScore;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

}