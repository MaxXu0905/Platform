package com.ailk.sets.platform.intf.model.wx.msg;

import com.ailk.sets.platform.intf.model.wx.Constant;

public class TextPicMsg {
	private String touser;
	private String msgtype;
	private News news;

	public TextPicMsg(String touser) {
		this.msgtype = Constant.MSGTYPE_NEWS;
		this.touser = touser;
		this.news = new News();
	}

	public void addArticle(Article article) {
		news.getArticles().add(article);
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

}
