package com.ailk.sets.platform.domain.paper;

/**
 * PaperQuestionSkillId entity. @author MyEclipse Persistence Tools
 */

public class PaperQuestionSkillId implements java.io.Serializable {
	private static final long serialVersionUID = -8748480860484099836L;
	private Integer paperId;
	private Integer questionId;
	private String skillId;

	// Constructors

	/** default constructor */
	public PaperQuestionSkillId() {
	}

	/** full constructor */
	public PaperQuestionSkillId(Integer paperId, Integer questionId, String skillId) {
		this.paperId = paperId;
		this.questionId = questionId;
		this.skillId = skillId;
	}

	// Property accessors

	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
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
		if (!(other instanceof PaperQuestionSkillId))
			return false;
		PaperQuestionSkillId castOther = (PaperQuestionSkillId) other;

		return ((this.getPaperId() == castOther.getPaperId()) || (this.getPaperId() != null
				&& castOther.getPaperId() != null && this.getPaperId().equals(castOther.getPaperId())))
				&& ((this.getQuestionId() == castOther.getQuestionId()) || (this.getQuestionId() != null
						&& castOther.getQuestionId() != null && this.getQuestionId().equals(castOther.getQuestionId())))
				&& ((this.getSkillId() == castOther.getSkillId()) || (this.getSkillId() != null
						&& castOther.getSkillId() != null && this.getSkillId().equals(castOther.getSkillId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPaperId() == null ? 0 : this.getPaperId().hashCode());
		result = 37 * result + (getQuestionId() == null ? 0 : this.getQuestionId().hashCode());
		result = 37 * result + (getSkillId() == null ? 0 : this.getSkillId().hashCode());
		return result;
	}

}