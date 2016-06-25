package com.ailk.sets.grade.glicko;

public class Match {

	private Player player1; // 第一个选手
	private Player player2; // 第二个选手
	private int outcome; // 结果：0 = 失败，1 = 成功，0.5 = 平局

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public int getOutcome() {
		return outcome;
	}

	public void setOutcome(int outcome) {
		this.outcome = outcome;
	}

}
