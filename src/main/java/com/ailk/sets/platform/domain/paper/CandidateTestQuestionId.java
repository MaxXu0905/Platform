package com.ailk.sets.platform.domain.paper;

/**
 * PaperInstanceQuestionId entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestQuestionId implements java.io.Serializable {
	private static final long serialVersionUID = 8529051710629681690L;
	private Long testId;

	private Long questionId;

	// Constructors

	/** default constructor */
	public CandidateTestQuestionId() {
	}

	/** full constructor */
	public CandidateTestQuestionId(Long testId, Long questionId) {
		this.testId = testId;
		this.questionId = questionId;
	}

	// Property accessors

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
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
		if (!(other instanceof CandidateTestQuestionId))
			return false;
		CandidateTestQuestionId castOther = (CandidateTestQuestionId) other;

		return ((this.getTestId() == castOther.getTestId()) || (this
				.getTestId() != null
				&& castOther.getTestId() != null && this.getTestId()
				.equals(castOther.getTestId())))
				&& ((this.getQuestionId() == castOther.getQuestionId()) || (this
						.getQuestionId() != null
						&& castOther.getQuestionId() != null && this
						.getQuestionId().equals(castOther.getQuestionId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTestId() == null ? 0 : this.getTestId()
						.hashCode());
		result = 37
				* result
				+ (getQuestionId() == null ? 0 : this.getQuestionId()
						.hashCode());
		return result;
	}

}