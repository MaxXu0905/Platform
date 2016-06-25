package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.domain.QbDifficultyLevel;

public interface IQbDifficultyLevelDao {

	public static final String LEVEL_CODE_LOW = "EASY";
	public static final String LEVEL_CODE_MEDIUM = "MEDIUM";
	public static final String LEVEL_CODE_HIGH = "HARD";

	public static final int IDX_UNKNOWN = -1;
	public static final int IDX_LOW = 0;
	public static final int IDX_MEDIUM = 1;
	public static final int IDX_HIGH = 2;
	public static final int IDX_TOTAL = 3;

	public static class DifficultyLevel {
		private int low;
		private int high;

		public int getLow() {
			return low;
		}

		public void setLow(int low) {
			this.low = low;
		}

		public int getHigh() {
			return high;
		}

		public void setHigh(int high) {
			this.high = high;
		}
	}

	public QbDifficultyLevel getDifficultyLevel(String levelCode,
			int positionLevel);

	public DifficultyLevel[] getDifficultyLevel(int positionLevel);

	public void evict();

}