package com.ailk.sets.grade.intf;


@SuppressWarnings("serial")
public class GetQuestionResponse extends BaseResponse {

	private int sheetType;
	private String group;
	private LoadRow row;

	public int getSheetType() {
		return sheetType;
	}

	public void setSheetType(int sheetType) {
		this.sheetType = sheetType;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public LoadRow getRow() {
		return row;
	}

	public void setRow(LoadRow row) {
		this.row = row;
	}

}
