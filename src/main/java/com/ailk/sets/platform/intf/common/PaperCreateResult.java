package com.ailk.sets.platform.intf.common;

public class PaperCreateResult extends PFResponse {
	
	private static final long serialVersionUID = 1L;
	private Integer timerType;

	public Integer getTimerType() {
		return timerType;
	}

	public void setTimerType(Integer timerType) {
		this.timerType = timerType;
	}
    
}
