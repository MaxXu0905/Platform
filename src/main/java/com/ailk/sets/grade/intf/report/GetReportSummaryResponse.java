package com.ailk.sets.grade.intf.report;

import java.util.List;

import com.ailk.sets.grade.intf.BaseResponse;

@SuppressWarnings("serial")
public class GetReportSummaryResponse extends BaseResponse {

	private double score; // 总分
	private List<OverallItem> items; // 列表项
	private List<Part> parts; // 部分

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public List<OverallItem> getItems() {
		return items;
	}

	public void setItems(List<OverallItem> items) {
		this.items = items;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

}
