package com.ailk.sets.platform.domain;

/**
 * StatQuestionId entity. @author MyEclipse Persistence Tools
 */

public class StatQuestionId implements java.io.Serializable {
	private static final long serialVersionUID = 8176145593607024677L;
	private Long questionId;
	private Integer statId;

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getStatId() {
		return this.statId;
	}

	public void setStatId(Integer statId) {
		this.statId = statId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StatQuestionId))
			return false;
		StatQuestionId castOther = (StatQuestionId) other;

		return ((this.getQuestionId() == castOther.getQuestionId()) || (this.getQuestionId() != null && castOther.getQuestionId() != null && this.getQuestionId().equals(castOther.getQuestionId())))
				&& ((this.getStatId() == castOther.getStatId()) || (this.getStatId() != null && castOther.getStatId() != null && this.getStatId().equals(castOther.getStatId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getQuestionId() == null ? 0 : this.getQuestionId().hashCode());
		result = 37 * result + (getStatId() == null ? 0 : this.getStatId().hashCode());
		return result;
	}

}