package com.ailk.sets.grade.glicko;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GlickoTest {

	@Test
	public void glicko() {
		Glicko glicko = new Glicko();
		Settings settings = glicko.getSettings();
		Player player1;
		Player player2;

		PlayerKey playerKey = new PlayerKey();
		playerKey.setId(0);
		playerKey.setType(0);

		Player player = new Player(settings);
		player.setPlayerKey(playerKey);
		glicko.addPlayer(player);
		player1 = player;

		playerKey = new PlayerKey();
		playerKey.setId(1);
		playerKey.setType(0);

		player = new Player(settings);
		player.setPlayerKey(playerKey);
		player.setRealRating(1400, settings);
		player.setRealRd(30);
		player.setVol(0.06);
		glicko.addPlayer(player);
		player2 = player;

		playerKey = new PlayerKey();
		playerKey.setId(2);
		playerKey.setType(0);

		player = new Player(settings);
		player.setPlayerKey(playerKey);
		player.setRealRating(1550, settings);
		player.setRealRd(100);
		player.setVol(0.06);
		glicko.addPlayer(player);

		playerKey = new PlayerKey();
		playerKey.setId(1);
		playerKey.setType(0);

		player = new Player(settings);
		player.setPlayerKey(playerKey);
		player.setRealRating(1700, settings);
		player.setRealRd(300);
		player.setVol(0.06);
		glicko.addPlayer(player);

		List<Match> matches = new ArrayList<Match>();
		for (int i = 0; i < 10; i++) {
			Match match = new Match();
			match.setPlayer1(player1);
			match.setPlayer2(player2);
			match.setOutcome(1);
			matches.add(match);
		}

		glicko.updateRatings(matches);

		System.out.println("Rating1: " + player1.getRealRating(settings));
		System.out.println("Rd1: " + player1.getRealRd());
		System.out.println("Vol1: " + player1.getVol());
		System.out.println("Rating2: " + player2.getRealRating(settings));
		System.out.println("Rd2: " + player2.getRealRd());
		System.out.println("Vol2: " + player1.getVol());
	}

}
