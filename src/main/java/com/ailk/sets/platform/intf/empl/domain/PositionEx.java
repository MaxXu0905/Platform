package com.ailk.sets.platform.intf.empl.domain;


public class PositionEx extends Position {

	private static final long serialVersionUID = -1024061977367412070L;

	private String levelString;
	private String pLString;
	private String publishDateDesc;

	public String getPublishDateDesc() {
		return publishDateDesc;
	}

	public void setPublishDateDesc(String publishDateDesc) {
		this.publishDateDesc = publishDateDesc;
	}

	public String getLevelString() {
		return levelString;
	}

	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}

	public String getpLString() {
		return pLString;
	}

	public void setpLString(String pLString) {
		this.pLString = pLString;
	}

}
