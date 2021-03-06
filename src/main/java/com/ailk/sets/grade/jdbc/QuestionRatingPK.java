package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class QuestionRatingPK implements Serializable, Cloneable {

	@Column(name = "qb_id", nullable = false)
	private int qbId;

	@Column(name = "question_id", nullable = false)
	private long questionId;

	public int getQbId() {
		return qbId;
	}

	public void setQbId(int qbId) {
		this.qbId = qbId;
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
		result = prime * result + qbId;
		result = prime * result + (int) (questionId ^ (questionId >>> 32));
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
		QuestionRatingPK other = (QuestionRatingPK) obj;
		if (qbId != other.qbId)
			return false;
		if (questionId != other.questionId)
			return false;
		return true;
	}

}
