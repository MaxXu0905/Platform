package com.ailk.sets.grade.glicko;

public class Settings {

	private double tau; // Internal glicko2 parameter.
						// "Reasonable choices are between 0.3 and 1.2, though the system should be tested to decide which value results in greatest predictive accuracy."
	private double rating; // default rating
	private double rd; // Default rating deviation (small number = good
						// confidence on the rating accuracy)
	private double vol; // Default volatility (expected fluctation on the player
						// rating)
	
	public Settings() {
		tau = 0.5;
		rating = 1500;
		rd = 350;
		vol = 0.06;
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