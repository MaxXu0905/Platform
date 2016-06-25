package com.ailk.sets.grade.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

public class Similarity {

	/**
	 * 计算文档相似度
	 * 
	 * @param src
	 *            准备比较的文档
	 * @param dest
	 *            样例文档
	 * @return
	 */
	public static double calculateSimilary(Document src, Document dest) {
		Map<String, Integer> ifreq = src.getFreqMap();// 文档词项词频
		Map<String, Integer> jfreq = dest.getFreqMap();

		double ijSum = 0;
		for (Entry<String, Integer> entry : ifreq.entrySet()) {
			if (jfreq.containsKey(entry.getKey())) {
				double iw = Math.sqrt(entry.getValue());
				double jw = Math.sqrt(jfreq.get(entry.getKey()));
				ijSum += (iw * jw);
			}
		}

		double iPowSum = powSum(src);
		double jPowSum = powSum(dest);
		double similary = ijSum / (iPowSum * jPowSum);

		if (Double.isInfinite(similary))
			return 1.0;
		else if (Double.isNaN(similary))
			return 0.0;

		return similary;
	}

	/**
	 * 向量的模
	 * 
	 * @param document
	 * @return
	 */
	private static double powSum(Document document) {
		Map<String, Integer> mapfreq = document.getFreqMap();
		Collection<Integer> freqs = mapfreq.values();

		double sum = 0;
		for (int f : freqs) {
			double dw = Math.sqrt(f);
			sum += Math.pow(dw, 2);
		}

		return Math.sqrt(sum);
	}

}