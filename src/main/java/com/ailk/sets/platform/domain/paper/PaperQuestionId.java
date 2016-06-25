package com.ailk.sets.platform.domain.paper;

/**
 * PaperQuestionId entity. @author MyEclipse Persistence Tools
 */

public class PaperQuestionId implements java.io.Serializable {
	private static final long serialVersionUID = -1827151671595826906L;
	private Integer paperId;
	private Long questionId;

	// Constructors

	/** default constructor */
	public PaperQuestionId() {
	}

	/** full constructor */
	public PaperQuestionId(Integer paperId, Long questionId) {
		this.paperId = paperId;
		this.questionId = questionId;
	}

	// Property accessors

	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PaperQuestionId))
			return false;
		PaperQuestionId castOther = (PaperQuestionId) other;

		return ((this.getPaperId() == castOther.getPaperId()) || (this.getPaperId() != null
				&& castOther.getPaperId() != null && this.getPaperId().equals(castOther.getPaperId())))
				&& ((this.getQuestionId() == castOther.getQuestionId()) || (this.getQuestionId() != null
						&& castOther.getQuestionId() != null && this.getQuestionId().equals(castOther.getQuestionId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPaperId() == null ? 0 : this.getPaperId().hashCode());
		result = 37 * result + (getQuestionId() == null ? 0 : this.getQuestionId().hashCode());
		return result;
	}

}