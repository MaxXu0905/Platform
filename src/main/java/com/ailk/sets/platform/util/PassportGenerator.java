package com.ailk.sets.platform.util;

import java.util.Random;

public class PassportGenerator {
	private static final char[] seeds = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static String getRandomPassport(int passportLength) {
		int seedLength = seeds.length;
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < passportLength; i++)
			sb.append(seeds[r.nextInt(Integer.MAX_VALUE) % seedLength]);
		return sb.toString();
	}
}
