package com.ailk.sets.grade.intf.report;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.grade.intf.BaseResponse;

@SuppressWarnings("serial")
public class GetComparedReportsResponse extends BaseResponse {

	private List<ComparedItem> comparedItems; // 比较的列

	public List<ComparedItem> getComparedItems() {
		return comparedItems;
	}

	public void setComparedItems(List<ComparedItem> comparedItems) {
		this.comparedItems = comparedItems;
	}

	public static class ComparedItem implements Serializable {
		private String name; // 比较列名
		private int level; // 列层级
		private Integer anchor; // 锚点
		private List<RowItem> rowItems; // 行记录

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public Integer getAnchor() {
			return anchor;
		}

		public void setAnchor(Integer anchor) {
			this.anchor = anchor;
		}

		public List<RowItem> getRowItems() {
			return rowItems;
		}

		public void setRowItems(List<RowItem> rowItems) {
			this.rowItems = rowItems;
		}
	}

	public static class RowItem implements Serializable {
		private boolean editable; // 是否允许修改
		private Double score; // 得分

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

}
