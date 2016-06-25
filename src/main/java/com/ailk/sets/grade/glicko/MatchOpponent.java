package com.ailk.sets.grade.glicko;

public class MatchOpponent {

	private double rating; // 对手得分
	private double rd; // 对手RD
	private int outcome; // 结果：0 = 失败，1 = 成功，0.5 = 平局

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

	public int getOutcome() {
		return outcome;
	}

	public void setOutcome(int outcome) {
		this.outcome = outcome;
	}

}
