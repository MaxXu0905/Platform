package com.ailk.sets.grade.glicko;

import java.util.List;

public class Player {

	private PlayerKey playerKey;
	private double tau;
	private double rating;
	private double rd;
	private double vol;

	public Player() {
	}

	public Player(Settings settings) {
		this.tau = settings.getTau();
		this.setRealRating(settings.getRating(), settings);
		this.setRealRd(settings.getRd());
		this.vol = settings.getVol();
	}

	public PlayerKey getPlayerKey() {
		return playerKey;
	}

	public void setPlayerKey(PlayerKey playerKey) {
		this.playerKey = playerKey;
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

	public double getRealRating(Settings settings) {
		return Math.round(rating * Glicko.SCALING_FACTOR)
				+ settings.getRating();
	}

	public void setRealRating(double rating, Settings settings) {
		this.rating = (rating - settings.getRating()) / Glicko.SCALING_FACTOR;
	}

	public double getRd() {
		return rd;
	}

	public void setRd(double rd) {
		this.rd = rd;
	}

	public double getRealRd() {
		return rd * Glicko.SCALING_FACTOR;
	}

	public void setRealRd(double rd) {
		this.rd = rd / Glicko.SCALING_FACTOR;
	}

	public double getVol() {
		return vol;
	}

	public void setVol(double vol) {
		this.vol = vol;
	}

	/**
	 * Calculates the new rating and rating deviation of the player. Follows the
	 * steps of the algorithm described at
	 * http://www.glicko.net/glicko/glicko2.pdf
	 * 
	 * @param matchOpponents
	 *            比赛对方结果列表
	 */
	public void updateRank(List<MatchOpponent> matchOpponents) {
		if (matchOpponents == null) {
			// Applies only the Step 6 of the algorithm
			preRatingRd();
			return;
		}

		// Step 1 : done by Player initialization
		// Step 2 : done by setRating and setRd

		// Step 3
		double v = variance(matchOpponents);

		// Step 4
		double delta = delta(v, matchOpponents);

		// Step 5
		vol = volatilityAlgorithm(v, delta);

		// Step 6
		preRatingRd();

		// Step 7
		rd = 1 / Math.sqrt((1 / Math.pow(rd, 2)) + (1 / v));

		double tempSum = 0;
		for (MatchOpponent matchOpponent : matchOpponents) {
			tempSum += g(matchOpponent.getRd())
					* (matchOpponent.getOutcome() - E(
							matchOpponent.getRating(), matchOpponent.getRd()));
		}
		rating += Math.pow(rd, 2) * tempSum;

		// Step 8 : done by getRating and getRd
	}

	public double volatilityAlgorithm(double v, double delta) {
		return newProcedure(v, delta);
	}

	public double oldProcedure(double v, double delta) {
		double result;
		double upper = findUpperFalsep(rd, v, delta, tau);
		double a = Math.log(Math.pow(vol, 2));

		double y1 = equation(rd, v, 0, a, tau, delta);
		if (y1 > 0) {
			result = upper;
		} else {
			double x1 = 0;
			double x2 = x1;
			double y2 = y1;

			x1 = x1 - 1;
			y1 = equation(rd, v, x1, a, tau, delta);

			while (y1 < 0) {
				x2 = x1;
				y2 = y1;
				x1 = x1 - 1;
				y1 = equation(rd, v, x1, a, tau, delta);
			}

			for (int i = 0; i < 21; i++) {
				double x3 = y1 * (x1 - x2) / (y2 - y1) + x1;
				double y3 = equation(rd, v, x3, a, tau, delta);

				if (y3 > 0) {
					x1 = x3;
					y1 = y3;
				} else {
					x2 = x3;
					y2 = y3;
				}
			}

			if (Math.exp((y1 * (x1 - x2) / (y2 - y1) + x1) / 2) > upper)
				result = upper;
			else
				result = Math.exp((y1 * (x1 - x2) / (y2 - y1) + x1) / 2);
		}

		return result;
	}

	public double newProcedure(double v, double delta) {
		// Step 5.1
		double A = Math.log(Math.pow(vol, 2));
		MakeFunction makeFunction = new MakeFunction(delta, v, A);
		double epsilon = 0.0000001;

		// Step 5.2
		double B, k;
		if (Math.pow(delta, 2) > Math.pow(rd, 2) + v) {
			B = Math.log(Math.pow(delta, 2) - Math.pow(rd, 2) - v);
		} else {
			k = 1;
			while (makeFunction.func(A - k * tau) < 0) {
				k = k + 1;
			}
			B = A - k * tau;
		}

		// Step 5.3
		double fA = makeFunction.func(A);
		double fB = makeFunction.func(B);

		// Step 5.4
		double C, fC;
		while (Math.abs(B - A) > epsilon) {
			C = A + (A - B) * fA / (fB - fA);
			fC = makeFunction.func(C);
			if (fC * fB < 0) {
				A = B;
				fA = fB;
			} else {
				fA = fA / 2;
			}
			B = C;
			fB = fC;
		}

		// Step 5.5
		return Math.exp(A / 2);
	}

	public double newProcedureMod(double v, double delta) {
		// Step 5.1
		double A = Math.log(Math.pow(vol, 2));
		MakeFunction makeFunction = new MakeFunction(delta, v, A);
		double epsilon = 0.0000001;

		// Step 5.2
		double B, k;
		if (delta > Math.pow(rd, 2) + v) {
			B = Math.log(delta - Math.pow(rd, 2) - v);
		} else {
			k = 1;
			while (makeFunction.func(A - k * tau) < 0)
				k = k + 1;
			B = A - k * tau;
		}

		// Step 5.3
		double fA = makeFunction.func(A);
		double fB = makeFunction.func(B);

		// Step 5.4
		double C, fC;
		while (Math.abs(B - A) > epsilon) {
			C = A + (A - B) * fA / (fB - fA);
			fC = makeFunction.func(C);
			if (fC * fB < 0) {
				A = B;
				fA = fB;
			} else {
				fA = fA / 2;
			}
			B = C;
			fB = fC;
		}
		// Step 5.5
		return Math.exp(A / 2);
	}

	public double oldProcedureSimple(double v, double delta) {
		double a = Math.log(Math.pow(vol, 2));
		double x0 = a;
		double x1 = 0;

		while (Math.abs(x0 - x1) > 0.00000001) {
			// New iteration, so x(i) becomes x(i-1)
			x0 = x1;
			double d = Math.pow(rating, 2) + v + Math.exp(x0);
			double h1 = -(x0 - a) / Math.pow(tau, 2) - 0.5 * Math.exp(x0) / d
					+ 0.5 * Math.exp(x0) * Math.pow(delta / d, 2);
			double h2 = -1 / Math.pow(tau, 2) - 0.5 * Math.exp(x0)
					* (Math.pow(rating, 2) + v) / Math.pow(d, 2) + 0.5
					* Math.pow(delta, 2) * Math.exp(x0)
					* (Math.pow(rating, 2) + v - Math.exp(x0)) / Math.pow(d, 3);
			x1 = x0 - (h1 / h2);
		}

		return Math.exp(x1 / 2);
	}

	private double equation(double phi, double v, double x, double a,
			double tau, double delta) {
		double d = Math.pow(phi, 2) + v + Math.exp(x);

		return -(x - a) / Math.pow(tau, 2) - 0.5 * Math.exp(x) / d + 0.5
				* Math.exp(x) * Math.pow((delta / d), 2);
	}

	private double Dequation(double phi, double v, double x, double tau,
			double delta) {
		double d = Math.pow(phi, 2) + v + Math.exp(x);

		return -1 / Math.pow(tau, 2) - 0.5 * Math.exp(x) / d + 0.5
				* Math.exp(x) * (Math.exp(x) + Math.pow(delta, 2))
				/ Math.pow(d, 2) - Math.pow(Math.exp(x), 2)
				* Math.pow(delta, 2) / Math.pow(d, 3);
	}

	private double findUpperFalsep(double phi, double v, double delta,
			double tau) {
		double y1 = Dequation(phi, v, 0, tau, delta);

		if (y1 < 0)
			return 1;

		double x1 = 0;
		double x2 = x1;
		double y2 = y1;

		x1 = x1 - 1;
		y1 = Dequation(phi, v, x1, tau, delta);

		while (y1 > 0) {
			x2 = x1;
			y2 = y1;
			x1 = x1 - 1;
			y1 = Dequation(phi, v, x1, tau, delta);
		}

		for (int i = 0; i < 21; i++) {
			double x3 = y1 * (x1 - x2) / (y2 - y1) + x1;
			double y3 = Dequation(phi, v, x3, tau, delta);

			if (y3 > 0) {
				x1 = x3;
				y1 = y3;
			} else {
				x2 = x3;
				y2 = y3;
			}
		}

		return Math.exp((y1 * (x1 - x2) / (y2 - y1) + x1) / 2);
	}

	/**
	 * Calculates and updates the player's rating deviation for the beginning of
	 * a rating period.
	 * 
	 */
	private void preRatingRd() {
		rd = Math.sqrt(Math.pow(rd, 2) + Math.pow(vol, 2));
	}

	/**
	 * Calculation of the estimated variance of the player's rating based on
	 * game outcomes
	 * 
	 * @return
	 */
	private double variance(List<MatchOpponent> matchOpponents) {
		double tempSum = 0;

		for (MatchOpponent matchOpponent : matchOpponents) {
			double tempE = E(matchOpponent.getRating(), matchOpponent.getRd());

			tempSum += Math.pow(g(matchOpponent.getRd()), 2) * tempE
					* (1 - tempE);
		}

		return 1 / tempSum;
	}

	/**
	 * The Glicko E function.
	 * 
	 * @param opponentRating
	 *            对手Rating
	 * @param opponentRd
	 *            对手Rd
	 * @return
	 */
	private double E(double opponentRating, double opponentRd) {
		return 1 / (1 + Math
				.exp(-1 * g(opponentRd) * (rating - opponentRating)));
	}

	/**
	 * The Glicko2 g(RD) function.
	 * 
	 * @param RD
	 * @return
	 */
	private static double g(double RD) {
		return 1 / Math.sqrt(1 + 3 * Math.pow(RD, 2) / Math.pow(Math.PI, 2));
	}

	/**
	 * The delta function of the Glicko2 system. Calculation of the estimated
	 * improvement in rating (step 4 of the algorithm)
	 * 
	 * @param v
	 * @return
	 */
	private double delta(double v, List<MatchOpponent> matchOpponents) {
		double tempSum = 0;

		for (MatchOpponent matchOpponent : matchOpponents) {
			tempSum += g(matchOpponent.getRd())
					* (matchOpponent.getOutcome() - E(
							matchOpponent.getRating(), matchOpponent.getRd()));
		}
		return v * tempSum;
	}

	public class MakeFunction {
		private double delta;
		private double v;
		private double a;

		public MakeFunction(double delta, double v, double a) {
			this.delta = delta;
			this.v = v;
			this.a = a;
		}

		public double func(double x) {
			return Math.exp(x)
					* (Math.pow(delta, 2) - Math.pow(rd, 2) - v - Math.exp(x))
					/ (2 * Math.pow(Math.pow(rd, 2) + v + Math.exp(x), 2))
					- (x - a) / Math.pow(tau, 2);
		}
	}

}