package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

public class QuestionExt  implements Serializable{
	private Integer leftTime;//题目答题剩余时间 用于面试题倒计时

	public Integer getLeftTime() {
		return leftTime;
	}

	public void setLeftTime(Integer leftTime) {
		this.leftTime = leftTime;
	}
}
