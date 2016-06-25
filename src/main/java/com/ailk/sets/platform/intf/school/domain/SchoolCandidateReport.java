package com.ailk.sets.platform.intf.school.domain;

import java.io.Serializable;

public class SchoolCandidateReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8538718669373029841L;
	private Long testId;
	private Double getScore;
	private String candidateName;
	private String candidateEmail;
	private String candidatePhone;
	private Integer candidateId;

	private Integer positionId;//测评id ， 组测评时需要知道是哪个测评的报告
	
	
	
	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Double getGetScore() {
		return getScore;
	}

	public void setGetScore(Double getScore) {
		this.getScore = getScore;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getCandidatePhone() {
		return candidatePhone;
	}

	public void setCandidatePhone(String candidatePhone) {
		this.candidatePhone = candidatePhone;
	}
}
