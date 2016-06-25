package com.ailk.sets.platform.intf.model;

import com.ailk.sets.platform.intf.common.PFResponse;

public class Times extends PFResponse {
	private static final long serialVersionUID = 823488853560300165L;
	private int times;

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
