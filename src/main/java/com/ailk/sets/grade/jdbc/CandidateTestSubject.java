package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "candidate_test_subject")
public class CandidateTestSubject implements Serializable, Cloneable {

	@EmbeddedId
	private CandidateTestSubjectPK candidateTestSubjectPK;

	@Column(name = "score", nullable = false,columnDefinition = "decimal(3,1)")
	private double score;

	@Column(name = "refer_elapsed", nullable = false)
	private long referElapsed;

	@Column(name = "refer_mem_bytes", nullable = false)
	private long referMemBytes;

	@Column(name = "cand_elapsed", nullable = false)
	private long candElapsed;

	@Column(name = "cand_mem_bytes", nullable = false)
	private long candMemBytes;

	public CandidateTestSubject() {
		candidateTestSubjectPK = new CandidateTestSubjectPK();
		score = 0.0;
		referElapsed = -1;
		referMemBytes = -1;
		candElapsed = -1;
		candMemBytes = -1;
	}

	public CandidateTestSubjectPK getCandidateTestSubjectPK() {
		return candidateTestSubjectPK;
	}

	public void setCandidateTestSubjectPK(
			CandidateTestSubjectPK candidateTestSubjectPK) {
		this.candidateTestSubjectPK = candidateTestSubjectPK;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public long getReferElapsed() {
		return referElapsed;
	}

	public void setReferElapsed(long referElapsed) {
		this.referElapsed = referElapsed;
	}

	public long getReferMemBytes() {
		return referMemBytes;
	}

	public void setReferMemBytes(long referMemBytes) {
		this.referMemBytes = referMemBytes;
	}

	public long getCandElapsed() {
		return candElapsed;
	}

	public void setCandElapsed(long candElapsed) {
		this.candElapsed = candElapsed;
	}

	public long getCandMemBytes() {
		return candMemBytes;
	}

	public void setCandMemBytes(long candMemBytes) {
		this.candMemBytes = candMemBytes;
	}

}
