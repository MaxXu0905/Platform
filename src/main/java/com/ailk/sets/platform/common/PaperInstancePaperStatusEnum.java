package com.ailk.sets.platform.common;
/**
 * 试卷实例测试状态
 * @author panyl
 *
 */
public enum PaperInstancePaperStatusEnum {
	UNUSED(0, "未使用"), ANSWERED(1, "已答卷"),JUDGED(2,"已交卷"),REPORTED(3,"已出报告");
	private int value;
	private String desc;
    
	private PaperInstancePaperStatusEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}

}
