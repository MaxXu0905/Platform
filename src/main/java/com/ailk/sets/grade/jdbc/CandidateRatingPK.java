package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class CandidateRatingPK implements Serializable, Cloneable {

	@Column(name = "qb_id", nullable = false)
	private int qbId;

	@Column(name = "candidate_id", nullable = false)
	private int candidateId;

	public int getQbId() {
		return qbId;
	}

	public void setQbId(int qbId) {
		this.qbId = qbId;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + candidateId;
		result = prime * result + qbId;
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
		CandidateRatingPK other = (CandidateRatingPK) obj;
		if (candidateId != other.candidateId)
			return false;
		if (qbId != other.qbId)
			return false;
		return true;
	}

}
