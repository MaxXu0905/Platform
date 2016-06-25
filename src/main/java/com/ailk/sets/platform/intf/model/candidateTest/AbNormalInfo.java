package com.ailk.sets.platform.intf.model.candidateTest;

import java.io.Serializable;
import java.util.List;

/**
 * 异常信息
 * @author 毕希研
 *
 */
public class AbNormalInfo implements Serializable {

	private static final long serialVersionUID = -5780513875326467990L;

	private List<String> picFiles;

	private int breakTimes;

	public List<String> getPicFiles() {
		return picFiles;
	}

	public void setPicFiles(List<String> picFiles) {
		this.picFiles = picFiles;
	}

	public int getBreakTimes() {
		return breakTimes;
	}

	public void setBreakTimes(int breakTimes) {
		this.breakTimes = breakTimes;
	}

}
