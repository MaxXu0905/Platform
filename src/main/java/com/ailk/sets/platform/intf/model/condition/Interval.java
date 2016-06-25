package com.ailk.sets.platform.intf.model.condition;

import java.io.Serializable;

public class Interval implements Serializable, ICondition {

	private static final long serialVersionUID = 7631889115276559894L;
	private String max;
	private String min;

	public Interval() {
	}

	public Interval(String min, String max) {
		this.min = min;
		this.max = max;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

}
