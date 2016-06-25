package com.ailk.sets.platform.domain;

/**
 * PositionSeriesParametersId entity. @author MyEclipse Persistence Tools
 */

public class PositionSeriesParametersId implements java.io.Serializable {

	private static final long serialVersionUID = 7594437931988298554L;
	private Integer seriesId;
	private String questionType;

	// Constructors

	/** default constructor */
	public PositionSeriesParametersId() {
	}

	/** full constructor */
	public PositionSeriesParametersId(Integer seriesId, String questionType) {
		this.seriesId = seriesId;
		this.questionType = questionType;
	}

	// Property accessors

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
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
		if (!(other instanceof PositionSeriesParametersId))
			return false;
		PositionSeriesParametersId castOther = (PositionSeriesParametersId) other;

		return ((this.getSeriesId() == castOther.getSeriesId()) || (this.getSeriesId() != null
				&& castOther.getSeriesId() != null && this.getSeriesId().equals(castOther.getSeriesId())))
				&& ((this.getQuestionType() == castOther.getQuestionType()) || (this.getQuestionType() != null
						&& castOther.getQuestionType() != null && this.getQuestionType().equals(
						castOther.getQuestionType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getSeriesId() == null ? 0 : this.getSeriesId().hashCode());
		result = 37 * result + (getQuestionType() == null ? 0 : this.getQuestionType().hashCode());
		return result;
	}

}