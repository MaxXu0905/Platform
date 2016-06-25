package com.ailk.sets.grade.excel;

@SuppressWarnings("serial")
public class IgnoredException extends Exception {

	private String errorDesc;

	public IgnoredException() {
	}

	public IgnoredException(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
