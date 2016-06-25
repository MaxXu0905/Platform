package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class StatQuestionTestPK implements Serializable, Cloneable {

	@Column(name = "question_id", nullable = false)
	private long questionId;

	@Column(name = "position_level", nullable = false)
	private int positionLevel;

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public int getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(int positionLevel) {
		this.positionLevel = positionLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + positionLevel;
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
		StatQuestionTestPK other = (StatQuestionTestPK) obj;
		if (positionLevel != other.positionLevel)
			return false;
		if (questionId != other.questionId)
			return false;
		return true;
	}

}
