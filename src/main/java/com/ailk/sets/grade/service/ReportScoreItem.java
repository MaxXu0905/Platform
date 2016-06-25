package com.ailk.sets.grade.service;

public class ReportScoreItem {

	private boolean exist; // 是否存在
	private boolean editable; // 是否可编辑
	private boolean edited; // 是否已编辑
	private double score; // 分值

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}