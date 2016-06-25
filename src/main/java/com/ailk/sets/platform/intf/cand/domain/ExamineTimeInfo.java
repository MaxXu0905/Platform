package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;

/**
 * 考试时间
 * @author panyl
 *
 */
public class ExamineTimeInfo implements Serializable {
	private static final long serialVersionUID = -4671959192024663283L;
	private String timeLeft; //剩余时间 hh:mm:ss格式的时间

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}
}
