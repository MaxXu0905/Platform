package com.ailk.sets.platform.intf.empl.domain;

/**
 * PaperGradeId entity. @author MyEclipse Persistence Tools
 */

public class PaperGradeId implements java.io.Serializable {

	// Fields

	private Integer degree;
	private Integer paperInstId;
	private String questionType;
	private String skillId;

	// Constructors

	/** default constructor */
	public PaperGradeId() {
	}

	/** full constructor */
	public PaperGradeId(Integer degree, Integer paperInstId, String questionType, String skillId) {
		this.degree = degree;
		this.paperInstId = paperInstId;
		this.questionType = questionType;
		this.skillId = skillId;
	}

	// Property accessors

	public Integer getDegree() {
		return this.degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public Integer getPaperInstId() {
		return this.paperInstId;
	}

	public void setPaperInstId(Integer paperInstId) {
		this.paperInstId = paperInstId;
	}

	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
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
		if (!(other instanceof PaperGradeId))
			return false;
		PaperGradeId castOther = (PaperGradeId) other;

		return ((this.getDegree() == castOther.getDegree()) || (this.getDegree() != null
				&& castOther.getDegree() != null && this.getDegree().equals(castOther.getDegree())))
				&& ((this.getPaperInstId() == castOther.getPaperInstId()) || (this.getPaperInstId() != null
						&& castOther.getPaperInstId() != null && this.getPaperInstId().equals(
						castOther.getPaperInstId())))
				&& ((this.getQuestionType() == castOther.getQuestionType()) || (this.getQuestionType() != null
						&& castOther.getQuestionType() != null && this.getQuestionType().equals(
						castOther.getQuestionType())))
				&& ((this.getSkillId() == castOther.getSkillId()) || (this.getSkillId() != null
						&& castOther.getSkillId() != null && this.getSkillId().equals(castOther.getSkillId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getDegree() == null ? 0 : this.getDegree().hashCode());
		result = 37 * result + (getPaperInstId() == null ? 0 : this.getPaperInstId().hashCode());
		result = 37 * result + (getQuestionType() == null ? 0 : this.getQuestionType().hashCode());
		result = 37 * result + (getSkillId() == null ? 0 : this.getSkillId().hashCode());
		return result;
	}

}