package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;
/**
 * 试卷状态统计数量接口
 * @author panyl
 *
 */
public class ReportStatusCountInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 776764516185908470L;
	private Integer testResult;//testResult 0-待定; 1-通过; 2-淘汰;3-复试
	private Integer count;
	public Integer getTestResult() {
		return testResult;
	}
	public void setTestResult(Integer testResult) {
		this.testResult = testResult;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "ReportStatusCountInfo [testResult=" + testResult + ", count=" + count + "]";
	}

}
