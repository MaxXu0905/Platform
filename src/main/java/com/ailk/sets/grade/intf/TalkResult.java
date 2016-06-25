package com.ailk.sets.grade.intf;


@SuppressWarnings("serial")
public class TalkResult extends TalkRow {

	private int errorType; // 错误类型
	private String cause; // 原因
	private Integer rowNum; // 行号

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

}
