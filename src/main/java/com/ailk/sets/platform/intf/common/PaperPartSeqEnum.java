package com.ailk.sets.platform.intf.common;


public enum PaperPartSeqEnum {
	OBJECT(1, "选择题"), SUBJECT(4, "编程题"),ESSAY(6,"问答题"),EXTRA(8,"智力题"), INTEVEIW(12, "面试题"),//主观题包括编程题和附加编程题
	TEST_OBJECT(21,"试答选择题"),TEST_SUBJECT(22,"试答编程题"),TEST_INTERVIEW(23,"试答面试题"); 
	private int value;
	private String desc;

	private PaperPartSeqEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue() {
		return value;
	}
/*	public String getDesc() {
		return desc;
	}*/
	
	public static PaperPartSeqEnum valueOf(int value) {
		switch (value) {
		case 1:
			return OBJECT;
		case 4:
			return SUBJECT;
		case 6 :
			return ESSAY;
		case 8:
			return EXTRA;
		case 12:
			return INTEVEIW;
		case 21:
			 return TEST_OBJECT;
		case 22:
			 return TEST_SUBJECT;
		case 23:
			 return TEST_INTERVIEW;
		default:
			return OBJECT;
		}
	}

}
