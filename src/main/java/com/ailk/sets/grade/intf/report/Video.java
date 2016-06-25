package com.ailk.sets.grade.intf.report;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Video implements Serializable {

	private String title; // 标题
	private String url; // 视频URL

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
