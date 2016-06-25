package com.ailk.sets.platform.intf.domain;

import com.ailk.sets.platform.intf.common.PFResponse;
public class PFCountInfo extends PFResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int count; //总数

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
