package com.ailk.sets.platform.intf.model.wx.msg;

import java.util.ArrayList;
import java.util.List;

public class News {
	private List<Article> articles;

	public News() {
		this.articles = new ArrayList<Article>();
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
