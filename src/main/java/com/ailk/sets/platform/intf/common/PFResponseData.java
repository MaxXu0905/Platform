package com.ailk.sets.platform.intf.common;

public class PFResponseData<T> extends PFResponse{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4910250214163716494L;
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
