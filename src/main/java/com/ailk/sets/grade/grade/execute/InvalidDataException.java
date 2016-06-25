package com.ailk.sets.grade.grade.execute;

public class InvalidDataException extends Exception {

	private static final long serialVersionUID = -1151060037189652435L;

	private int errCode;
	private String errDesc;

	public InvalidDataException(int errCode, String errDesc) {
		this.errCode = errCode;
		this.errDesc = errDesc;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

}
