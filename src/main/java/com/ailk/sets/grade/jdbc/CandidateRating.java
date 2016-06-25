package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "candidate_rating")
public class CandidateRating implements Serializable, Cloneable {

	@EmbeddedId
	private CandidateRatingPK candidateRatingPK;

	@Column(name = "tau", nullable = false)
	private double tau;

	@Column(name = "rating", nullable = false)
	private double rating;

	@Column(name = "rd", nullable = false)
	private double rd;

	@Column(name = "vol", nullable = false)
	private double vol;

	public CandidateRatingPK getCandidateRatingPK() {
		return candidateRatingPK;
	}

	public void setCandidateRatingPK(CandidateRatingPK candidateRatingPK) {
		this.candidateRatingPK = candidateRatingPK;
	}

	public double getTau() {
		return tau;
	}

	public void setTau(double tau) {
		this.tau = tau;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getRd() {
		return rd;
	}

	public void setRd(double rd) {
		this.rd = rd;
	}

	public double getVol() {
		return vol;
	}

	public void setVol(double vol) {
		this.vol = vol;
	}

}
