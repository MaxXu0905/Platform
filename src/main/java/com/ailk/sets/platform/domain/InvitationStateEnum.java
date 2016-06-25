package com.ailk.sets.platform.domain;

public enum InvitationStateEnum {
	SEND_FAIL(0, "SEND_FAIL"), SEND_SUCCESS(1, "SEND_SUCCESS"), FINISHED(2, "FINISHED"),AUTO_FINISHED(3,"AUTO_FINISHED"),//主观题包括编程题和附加编程题
	CANCEL(5,"CANCEL"); 
	private int value;
	private String desc;

	private InvitationStateEnum(int value, String desc) {
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
