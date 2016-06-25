package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "candidate_rating_question")
public class CandidateRatingQuestion implements Serializable, Cloneable {

	@EmbeddedId
	private CandidateRatingQuestionPK candidateRatingQuestionPK;

	@Column(name = "outcome", nullable = false)
	private double outcome;
	
	@Column(name = "order_id", nullable = false)
	private int orderId;

	public CandidateRatingQuestionPK getCandidateRatingQuestionPK() {
		return candidateRatingQuestionPK;
	}

	public void setCandidateRatingQuestionPK(
			CandidateRatingQuestionPK candidateRatingQuestionPK) {
		this.candidateRatingQuestionPK = candidateRatingQuestionPK;
	}

	public double getOutcome() {
		return outcome;
	}

	public void setOutcome(double outcome) {
		this.outcome = outcome;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
