package com.ailk.sets.grade.excel;

@SuppressWarnings("serial")
public class TimeException extends Exception {

	private int suggestTime;
	private String errDesc;

	public TimeException(int suggestTime, String errDesc) {
		this.suggestTime = suggestTime;
		this.errDesc = errDesc;
	}

	public int getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(int suggestTime) {
		this.suggestTime = suggestTime;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

}
