package com.ailk.sets.grade.glicko;

import java.util.TreeMap;

public class RatingSession {

	private Player player;
	private TreeMap<Double, Player> playerMap;
	private long qid;
	private String answer;
	private int orderId;

	public RatingSession() {
		qid = -1L;
		orderId = 0;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public TreeMap<Double, Player> getPlayerMap() {
		return playerMap;
	}

	public void setPlayerMap(TreeMap<Double, Player> playerMap) {
		this.playerMap = playerMap;
	}

	public long getQid() {
		return qid;
	}

	public void setQid(long qid) {
		this.qid = qid;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
