package com.ailk.sets.grade.word;

@SuppressWarnings("serial")
public class PaperException extends Exception {

	public static enum Level {
		WARNING, ERROR, FATAL
	}

	private Level level; // 错误级别
	private String desc; // 错误描述

	public PaperException(Level level, String desc) {
		this.level = level;
		this.desc = desc;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
