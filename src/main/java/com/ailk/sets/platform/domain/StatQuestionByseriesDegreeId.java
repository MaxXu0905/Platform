package com.ailk.sets.platform.domain;

/**
 * StatQuestionByseriesDegreeId entity. @author MyEclipse Persistence Tools
 */

public class StatQuestionByseriesDegreeId implements java.io.Serializable {
	private static final long serialVersionUID = -8384043391542345202L;
	private Integer seriesId;
	private Integer positionLevel;
	private String skillId;
	private Integer degreeId;
	private String questionType;

	// Constructors

	/** default constructor */
	public StatQuestionByseriesDegreeId() {
	}

	/** full constructor */
	public StatQuestionByseriesDegreeId(Integer seriesId, Integer positionLevel, String skillId, Integer degreeId,
			String questionType) {
		this.seriesId = seriesId;
		this.positionLevel = positionLevel;
		this.skillId = skillId;
		this.degreeId = degreeId;
		this.questionType = questionType;
	}

	// Property accessors

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public Integer getPositionLevel() {
		return this.positionLevel;
	}

	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
	}

	public String getSkillId() {
		return this.skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public Integer getDegreeId() {
		return this.degreeId;
	}

	public void setDegreeId(Integer degreeId) {
		this.degreeId = degreeId;
	}

	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StatQuestionByseriesDegreeId))
			return false;
		StatQuestionByseriesDegreeId castOther = (StatQuestionByseriesDegreeId) other;

		return ((this.getSeriesId() == castOther.getSeriesId()) || (this.getSeriesId() != null
				&& castOther.getSeriesId() != null && this.getSeriesId().equals(castOther.getSeriesId())))
				&& ((this.getPositionLevel() == castOther.getPositionLevel()) || (this.getPositionLevel() != null
						&& castOther.getPositionLevel() != null && this.getPositionLevel().equals(
						castOther.getPositionLevel())))
				&& ((this.getSkillId() == castOther.getSkillId()) || (this.getSkillId() != null
						&& castOther.getSkillId() != null && this.getSkillId().equals(castOther.getSkillId())))
				&& ((this.getDegreeId() == castOther.getDegreeId()) || (this.getDegreeId() != null
						&& castOther.getDegreeId() != null && this.getDegreeId().equals(castOther.getDegreeId())))
				&& ((this.getQuestionType() == castOther.getQuestionType()) || (this.getQuestionType() != null
						&& castOther.getQuestionType() != null && this.getQuestionType().equals(
						castOther.getQuestionType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getSeriesId() == null ? 0 : this.getSeriesId().hashCode());
		result = 37 * result + (getPositionLevel() == null ? 0 : this.getPositionLevel().hashCode());
		result = 37 * result + (getSkillId() == null ? 0 : this.getSkillId().hashCode());
		result = 37 * result + (getDegreeId() == null ? 0 : this.getDegreeId().hashCode());
		result = 37 * result + (getQuestionType() == null ? 0 : this.getQuestionType().hashCode());
		return result;
	}

}