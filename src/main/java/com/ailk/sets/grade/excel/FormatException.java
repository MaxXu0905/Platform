package com.ailk.sets.grade.excel;

@SuppressWarnings("serial")
public class FormatException extends Exception {

	private String errDesc;

	public FormatException(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

}
