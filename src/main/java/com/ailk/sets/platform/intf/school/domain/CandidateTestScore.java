package com.ailk.sets.platform.intf.school.domain;

/**
 * CandidateTestScore entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestScore implements java.io.Serializable {

	private static final long serialVersionUID = -8260033617675322834L;
	private CandidateTestScoreId id;
	private Integer candidateId;
	private Integer employerId;
	private Double score;

	// Constructors

	/** default constructor */
	public CandidateTestScore() {
	}

	/** full constructor */
	public CandidateTestScore(CandidateTestScoreId id, Integer candidateId,
			Integer employerId, Double score) {
		this.id = id;
		this.candidateId = candidateId;
		this.employerId = employerId;
		this.score = score;
	}

	// Property accessors

	public CandidateTestScoreId getId() {
		return this.id;
	}

	public void setId(CandidateTestScoreId id) {
		this.id = id;
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

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}