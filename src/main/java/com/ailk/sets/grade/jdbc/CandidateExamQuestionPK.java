package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class CandidateExamQuestionPK implements Serializable, Cloneable {

	@Column(name = "test_id", nullable = false)
	private long testId;

	@Column(name = "question_id", nullable = false)
	private long questionId;

	public long getTestId() {
		return testId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (questionId ^ (questionId >>> 32));
		result = prime * result + (int) (testId ^ (testId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CandidateExamQuestionPK other = (CandidateExamQuestionPK) obj;
		if (questionId != other.questionId)
			return false;
		if (testId != other.testId)
			return false;
		return true;
	}

}
