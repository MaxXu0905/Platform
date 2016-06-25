package com.ailk.sets.grade.excel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExcelColumn implements Serializable {

	private int index; // 列索引
	private String value; // 列值

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
