package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class CandidateTestSubjectPK implements Serializable, Cloneable {

	@Column(name = "test_id", nullable = false)
	private long testId;

	@Column(name = "question_id", nullable = false)
	private long questionId;

	@Column(name = "group_id", nullable = false)
	private int groupId;

	@Column(name = "case_id", nullable = false)
	private int caseId;

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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + caseId;
		result = prime * result + groupId;
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
		CandidateTestSubjectPK other = (CandidateTestSubjectPK) obj;
		if (caseId != other.caseId)
			return false;
		if (groupId != other.groupId)
			return false;
		if (questionId != other.questionId)
			return false;
		if (testId != other.testId)
			return false;
		return true;
	}

}
