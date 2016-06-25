package com.ailk.sets.grade.intf;

import java.util.List;

@SuppressWarnings("serial")
public class LoadRequest extends BaseRequest {

	private int sheetType; // 表格类型
	private String group; // 分组
	private Integer serialNo; // 错误序列号，分组可用
	private List<LoadRow> rows; // 结果

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

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public List<LoadRow> getRows() {
		return rows;
	}

	public void setRows(List<LoadRow> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "LoadRequest [sheetType=" + sheetType + ", group=" + group + ", serialNo=" + serialNo + ", rows=" + rows
				+ "]";
	}

}
