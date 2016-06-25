package com.ailk.sets.grade.utils;

import java.math.BigDecimal;

public class MathUtils {

	public static double round(double value, int scale) {
		return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

}
