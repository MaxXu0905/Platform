package com.ailk.sets.platform.intf.empl.domain;

import com.ailk.sets.platform.intf.common.PFResponse;

public class SmsSendResult extends PFResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1138979026550304958L;
	
	private Long timeLeft;//还剩多长时间才可以重新发送
    private Integer errorCode ;
    private String context;
    
    
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Long getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(Long timeLeft) {
		this.timeLeft = timeLeft;
	}

}
