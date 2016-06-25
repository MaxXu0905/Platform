package com.ailk.sets.grade.cache;

import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

public interface IQbQuestionService {

	public QbQuestion get(long qid);

	public void evict();

	public enum EnumKey {
		A(1, "1"), B(2, "2");
		private int a;
		private String b;

		private EnumKey(int a, String b) {
			this.a = a;
			this.b = b;
		}

		public int getA() {
			return a;
		}

		public String getB() {
			return b;
		}
	}

	public long get(int degree, String skillId, EnumKey key);

}
