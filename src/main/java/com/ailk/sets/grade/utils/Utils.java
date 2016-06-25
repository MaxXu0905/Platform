package com.ailk.sets.grade.utils;

import java.util.Random;

public class Utils {

	private static final Random random = new Random();
	private static final int PASSPORT_LENGTH = 30;

	public static String getPassport() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < PASSPORT_LENGTH; i++) {
			int value = random.nextInt(62);
			if (value < 10)
				builder.append((char) ('0' + value));
			else if (value < 36)
				builder.append((char) ('A' + value - 10));
			else
				builder.append((char) ('a' + value - 36));
		}

		return builder.toString();
	}

}
