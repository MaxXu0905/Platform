package com.ailk.sets.platform.intf.common;
/**
 * 试卷实例部分状态 对应paper_instance_part表的part_state字段
 * @author panyl
 *
 */
public enum PaperInstancePartStateEnum {
	NOTANSWER(0,"未作答"),ANSWERING(1,"作答中"),COMMIT_MANUAL(2,"前台手动提交"),COMMIT_AUTO(3,"后台超时提交");
	private int value;
	private String desc;
	private PaperInstancePartStateEnum(int value,String desc){
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
