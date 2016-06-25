package com.ailk.sets.platform.domain;

/**
 * PositionLevelDegreeTimeId entity. @author MyEclipse Persistence Tools
 */

public class PositionLevelDegreeTimeId implements java.io.Serializable {
	private static final long serialVersionUID = 2605205753698104662L;
	private Integer level;
	private String questionType;
	private Integer questionDegree;
	private String positionLanguage;

	// Constructors

	public String getPositionLanguage() {
		return positionLanguage;
	}

	public void setPositionLanguage(String positionLanguage) {
		this.positionLanguage = positionLanguage;
	}

	/** default constructor */
	public PositionLevelDegreeTimeId() {
	}

	/** full constructor */
	public PositionLevelDegreeTimeId(Integer level, String questionType, Integer questionDegree) {
		this.level = level;
		this.questionType = questionType;
		this.questionDegree = questionDegree;
	}

	// Property accessors

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Integer getQuestionDegree() {
		return this.questionDegree;
	}

	public void setQuestionDegree(Integer questionDegree) {
		this.questionDegree = questionDegree;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PositionLevelDegreeTimeId))
			return false;
		PositionLevelDegreeTimeId castOther = (PositionLevelDegreeTimeId) other;

		return ((this.getLevel() == castOther.getLevel()) || (this.getLevel() != null && castOther.getLevel() != null && this
				.getLevel().equals(castOther.getLevel())))
				&& ((this.getQuestionType() == castOther.getQuestionType()) || (this.getQuestionType() != null
						&& castOther.getQuestionType() != null && this.getQuestionType().equals(
						castOther.getQuestionType())))
				&& ((this.getQuestionDegree() == castOther.getQuestionDegree()) || (this.getQuestionDegree() != null
						&& castOther.getQuestionDegree() != null && this.getQuestionDegree().equals(
						castOther.getQuestionDegree())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLevel() == null ? 0 : this.getLevel().hashCode());
		result = 37 * result + (getQuestionType() == null ? 0 : this.getQuestionType().hashCode());
		result = 37 * result + (getQuestionDegree() == null ? 0 : this.getQuestionDegree().hashCode());
		return result;
	}

}