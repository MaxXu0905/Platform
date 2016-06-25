package com.ailk.sets.grade.intf;

import java.util.ArrayList;
import java.util.List;

import com.ailk.sets.platform.intf.model.question.QbQuestionInfo;

@SuppressWarnings("serial")
public class LoadGroup extends LoadGroupKey {

	protected Integer serialNo; // 错误序列号，题组方式有值
	private List<LoadRowResponse> rows; // 记录列表

	private  List<QbQuestionInfo> questionList;
	
	public LoadGroup() {
	}
	public List<QbQuestionInfo> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QbQuestionInfo> questionList) {
		this.questionList = questionList;
	}
	public LoadGroup(int errorType, String group) {
		this.errorType = errorType;
		this.group = group;
	}

	public LoadGroup(LoadGroupKey key) {
		this.errorType = key.getErrorType();
		this.group = key.getGroup();
		rows = new ArrayList<LoadRowResponse>();
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public List<LoadRowResponse> getRows() {
		return rows;
	}

	public void setRows(List<LoadRowResponse> rows) {
		this.rows = rows;
	}

}
