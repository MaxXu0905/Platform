package com.ailk.sets.platform.domain;

/**
 * QbQuestionSkillId entity. @author MyEclipse Persistence Tools
 */

public class QbQuestionSkillId implements java.io.Serializable {

	private static final long serialVersionUID = -9120231974265237172L;
	private Long questionId;
	private String skillId;

	// Constructors

	/** default constructor */
	public QbQuestionSkillId() {
	}

	/** full constructor */
	public QbQuestionSkillId(Long questionId, String skillId) {
		this.questionId = questionId;
		this.skillId = skillId;
	}

	// Property accessors

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
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
		if (!(other instanceof QbQuestionSkillId))
			return false;
		QbQuestionSkillId castOther = (QbQuestionSkillId) other;

		return ((this.getQuestionId() == castOther.getQuestionId()) || (this.getQuestionId() != null
				&& castOther.getQuestionId() != null && this.getQuestionId().equals(castOther.getQuestionId())))
				&& ((this.getSkillId() == castOther.getSkillId()) || (this.getSkillId() != null
						&& castOther.getSkillId() != null && this.getSkillId().equals(castOther.getSkillId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getQuestionId() == null ? 0 : this.getQuestionId().hashCode());
		result = 37 * result + (getSkillId() == null ? 0 : this.getSkillId().hashCode());
		return result;
	}

}