package com.ailk.sets.grade.glicko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Glicko {

	public static final double SCALING_FACTOR = 173.7178;

	public static final int PLAYER_TYPE_QUESTION = -1;

	private Settings settings;
	private Map<PlayerKey, Player> playerMap;
	private Map<Integer, List<Player>> qbPlayersMap;
	private Map<Player, List<MatchOpponent>> matchMap;

	public Glicko() {
		settings = new Settings();
		playerMap = new HashMap<PlayerKey, Player>();
		qbPlayersMap = new HashMap<Integer, List<Player>>();
		matchMap = new HashMap<Player, List<MatchOpponent>>();
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public List<Player> getQbPlayers(int qbId) {
		return qbPlayersMap.get(qbId);
	}

	public Map<PlayerKey, Player> getPlayerMap() {
		return playerMap;
	}

	public Player getPlayer(PlayerKey playerKey) {
		return playerMap.get(playerKey);
	}

	public void addPlayer(Player player) {
		playerMap.put(player.getPlayerKey(), player);
	}

	public void removePlayers() {
		playerMap.clear();
		qbPlayersMap.clear();
	}

	public void updateRatings(List<Match> matches) {
		matchMap.clear();

		for (Match match : matches) {
			addResult(match.getPlayer1(), match.getPlayer2(),
					match.getOutcome());
			addResult(match.getPlayer2(), match.getPlayer1(),
					1 - match.getOutcome());
		}

		for (Entry<Player, List<MatchOpponent>> entry : matchMap.entrySet()) {
			Player player = entry.getKey();
			List<MatchOpponent> matchOpponents = entry.getValue();

			player.updateRank(matchOpponents);
		}
	}

	/**
	 * Add a match result to be taken in account for the new rankings
	 * calculation
	 * 
	 * @param self
	 *            The first player
	 * @param opponent
	 *            The second player
	 * @param outcome
	 *            The outcome : 0 = defeat, 1 = victory, 0.5 = draw
	 */
	private void addResult(Player self, Player opponent, int outcome) {
		List<MatchOpponent> matchOpponents = matchMap.get(self);
		if (matchOpponents == null) {
			matchOpponents = new ArrayList<MatchOpponent>();
			matchMap.put(self, matchOpponents);
		}

		MatchOpponent matchOpponent = new MatchOpponent();
		matchOpponent.setRating(opponent.getRating());
		matchOpponent.setRd(opponent.getRd());
		matchOpponent.setOutcome(outcome);
		matchOpponents.add(matchOpponent);
	}
}
