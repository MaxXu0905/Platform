package com.ailk.sets.platform.intf.model.monitor;

import com.ailk.sets.platform.intf.common.PFResponse;

/**
 * 监控信息模型
 * 
 * @author 毕希研
 * 
 */
public class MonitorInfo extends PFResponse {

	private static final long serialVersionUID = 1568791297828618881L;

	private int configSwitchTimes;
	private int switchTimes;
	private int freshTimes;

	public int getConfigSwitchTimes() {
		return configSwitchTimes;
	}

	public void setConfigSwitchTimes(int configSwitchTimes) {
		this.configSwitchTimes = configSwitchTimes;
	}

	public int getSwitchTimes() {
		return switchTimes;
	}

	public void setSwitchTimes(int switchTimes) {
		this.switchTimes = switchTimes;
	}

	public int getFreshTimes() {
		return freshTimes;
	}

	public void setFreshTimes(int freshTimes) {
		this.freshTimes = freshTimes;
	}

}
