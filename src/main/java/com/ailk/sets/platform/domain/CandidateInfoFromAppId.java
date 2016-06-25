package com.ailk.sets.platform.domain;

/**
 * CandidateInfoFromAppId entity. @author MyEclipse Persistence Tools
 */

public class CandidateInfoFromAppId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4064364127729126637L;
	private String passcode;
	private String candidateName;
	private String candidateEmail;

	// Constructors

	/** default constructor */
	public CandidateInfoFromAppId() {
	}

	/** full constructor */
	public CandidateInfoFromAppId(String passcode, String candidateName, String candidateEmail) {
		this.passcode = passcode;
		this.candidateName = candidateName;
		this.candidateEmail = candidateEmail;
	}

	// Property accessors

	public String getPasscode() {
		return this.passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public String getCandidateName() {
		return this.candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return this.candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CandidateInfoFromAppId))
			return false;
		CandidateInfoFromAppId castOther = (CandidateInfoFromAppId) other;

		return ((this.getPasscode() == castOther.getPasscode()) || (this.getPasscode() != null
				&& castOther.getPasscode() != null && this.getPasscode().equals(castOther.getPasscode())))
				&& ((this.getCandidateName() == castOther.getCandidateName()) || (this.getCandidateName() != null
						&& castOther.getCandidateName() != null && this.getCandidateName().equals(
						castOther.getCandidateName())))
				&& ((this.getCandidateEmail() == castOther.getCandidateEmail()) || (this.getCandidateEmail() != null
						&& castOther.getCandidateEmail() != null && this.getCandidateEmail().equals(
						castOther.getCandidateEmail())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPasscode() == null ? 0 : this.getPasscode().hashCode());
		result = 37 * result + (getCandidateName() == null ? 0 : this.getCandidateName().hashCode());
		result = 37 * result + (getCandidateEmail() == null ? 0 : this.getCandidateEmail().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "CandidateInfoFromAppId [passcode=" + passcode + ", candidateName=" + candidateName
				+ ", candidateEmail=" + candidateEmail + "]";
	}

}