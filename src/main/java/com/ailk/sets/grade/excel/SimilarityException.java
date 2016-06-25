package com.ailk.sets.grade.excel;

import com.ailk.sets.grade.intf.LoadRow;

@SuppressWarnings("serial")
public class SimilarityException extends Exception {

	private String errDesc;
	private String qbName;
	private long questionId;
	private LoadRow row;

	public SimilarityException(String errDesc, String qbName, long questionId,
			LoadRow row) {
		this.errDesc = errDesc;
		this.qbName = qbName;
		this.questionId = questionId;
		this.row = row;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getQbName() {
		return qbName;
	}

	public void setQbName(String qbName) {
		this.qbName = qbName;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public LoadRow getRow() {
		return row;
	}

	public void setRow(LoadRow row) {
		this.row = row;
	}

}
