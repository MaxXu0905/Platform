package com.ailk.sets.grade.intf;

import java.util.List;

@SuppressWarnings("serial")
public class GetSuggestTimeRequest extends BaseRequest {

	private String title; // 标题
	private List<String> options; // 选项列表

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

}
