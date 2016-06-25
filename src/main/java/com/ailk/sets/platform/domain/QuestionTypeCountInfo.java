package com.ailk.sets.platform.domain;

import java.util.HashMap;
import java.util.Map;

public class QuestionTypeCountInfo {
	
	private Map<String, Integer> typeToCount = new HashMap<String, Integer>();

	public void addTypeToCount(String type, Integer count) {
		Integer cou = typeToCount.get(type);
		if (cou == null) {
			cou = count;
		} else {
			cou += count;
		}
		typeToCount.put(type, cou);
	}

	public int getCountOfType(String type) {
		Integer r = typeToCount.get(type);
		if (r == null)
			return 0;
		return r;
	}

}
