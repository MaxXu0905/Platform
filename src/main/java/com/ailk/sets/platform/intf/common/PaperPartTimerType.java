package com.ailk.sets.platform.intf.common;


public enum PaperPartTimerType {
	PAPER(1, "按试卷"), PART(2, "按部分"),QUESTION(3,"按题目"); 
	private int value;
	private String desc;
	
	public static final int MIXED = 9;//混合计时类型

	private PaperPartTimerType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue() {
		return value;
	}
/*	public String getDesc() {
		return desc;
	}*/
	

}
