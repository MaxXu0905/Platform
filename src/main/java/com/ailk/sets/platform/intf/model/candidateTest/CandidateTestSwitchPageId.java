package com.ailk.sets.platform.intf.model.candidateTest;

import java.sql.Timestamp;

/**
 * CandidateTestSwitchPageId entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestSwitchPageId implements java.io.Serializable {

	// Fields

	private Long testId;
	private Timestamp switchTime;

	// Constructors

	/** default constructor */
	public CandidateTestSwitchPageId() {
	}

	/** full constructor */
	public CandidateTestSwitchPageId(Long testId, Timestamp switchTime) {
		this.testId = testId;
		this.switchTime = switchTime;
	}

	// Property accessors

	public Long getTestId() {
		return this.testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Timestamp getSwitchTime() {
		return this.switchTime;
	}

	public void setSwitchTime(Timestamp switchTime) {
		this.switchTime = switchTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CandidateTestSwitchPageId))
			return false;
		CandidateTestSwitchPageId castOther = (CandidateTestSwitchPageId) other;

		return ((this.getTestId() == castOther.getTestId()) || (this.getTestId() != null && castOther.getTestId() != null && this.getTestId().equals(castOther.getTestId())))
				&& ((this.getSwitchTime() == castOther.getSwitchTime()) || (this.getSwitchTime() != null && castOther.getSwitchTime() != null && this.getSwitchTime().equals(castOther.getSwitchTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTestId() == null ? 0 : this.getTestId().hashCode());
		result = 37 * result + (getSwitchTime() == null ? 0 : this.getSwitchTime().hashCode());
		return result;
	}

}