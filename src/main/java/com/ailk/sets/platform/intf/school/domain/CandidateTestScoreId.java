package com.ailk.sets.platform.intf.school.domain;

/**
 * CandidateTestScoreId entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestScoreId implements java.io.Serializable {

	private static final long serialVersionUID = 9055087601685698007L;
	private Long testId;
	private String skillId;

	// Constructors

	/** default constructor */
	public CandidateTestScoreId() {
	}

	/** full constructor */
	public CandidateTestScoreId(Long testId, String skillId) {
		this.testId = testId;
		this.skillId = skillId;
	}

	// Property accessors

	public Long getTestId() {
		return this.testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getSkillId() {
		return this.skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CandidateTestScoreId))
			return false;
		CandidateTestScoreId castOther = (CandidateTestScoreId) other;

		return ((this.getTestId() == castOther.getTestId()) || (this.getTestId() != null && castOther.getTestId() != null && this.getTestId().equals(castOther.getTestId())))
				&& ((this.getSkillId() == castOther.getSkillId()) || (this.getSkillId() != null && castOther.getSkillId() != null && this.getSkillId().equals(castOther.getSkillId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTestId() == null ? 0 : this.getTestId().hashCode());
		result = 37 * result + (getSkillId() == null ? 0 : this.getSkillId().hashCode());
		return result;
	}

}