package com.ailk.sets.grade.intf.report;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OverallItem implements Serializable {

	private Integer anchor; // 锚点
	private String name; // 名称
	private boolean editable; // 是否允许修改
	private Double score; // 得分

	public Integer getAnchor() {
		return anchor;
	}

	public void setAnchor(Integer anchor) {
		this.anchor = anchor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
