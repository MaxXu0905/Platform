package com.ailk.sets.platform.intf.cand.domain;

/**
 * CandidateInfoExtId entity. @author MyEclipse Persistence Tools
 */

public class CandidateInfoExtId implements java.io.Serializable {

	// Fields

	private Integer candidateId;
	private String infoId;

	// Constructors

	/** default constructor */
	public CandidateInfoExtId() {
	}

	/** full constructor */
	public CandidateInfoExtId(Integer candidateId, String infoId) {
		this.candidateId = candidateId;
		this.infoId = infoId;
	}

	// Property accessors

	public Integer getCandidateId() {
		return this.candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CandidateInfoExtId))
			return false;
		CandidateInfoExtId castOther = (CandidateInfoExtId) other;

		return ((this.getCandidateId() == castOther.getCandidateId()) || (this.getCandidateId() != null && castOther.getCandidateId() != null && this.getCandidateId().equals(
				castOther.getCandidateId())))
				&& ((this.getInfoId() == castOther.getInfoId()) || (this.getInfoId() != null && castOther.getInfoId() != null && this.getInfoId().equals(castOther.getInfoId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCandidateId() == null ? 0 : this.getCandidateId().hashCode());
		result = 37 * result + (getInfoId() == null ? 0 : this.getInfoId().hashCode());
		return result;
	}

}