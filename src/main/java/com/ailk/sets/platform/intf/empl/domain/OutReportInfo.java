package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;

public class OutReportInfo implements Serializable{
	
     /**
	 * 
	 */
	private static final long serialVersionUID = -6972159740722057815L;
	private long testId;
	private Double score;
	private String reportUrl;
	private String  downloadUrl;
	public long getTestId() {
		return testId;
	}
	public void setTestId(long testId) {
		this.testId = testId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getReportUrl() {
		return reportUrl;
	}
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	@Override
	public String toString() {
		return "OutReportInfo [testId=" + testId + ", score=" + score + ", reportUrl=" + reportUrl + ", downloadUrl="
				+ downloadUrl + "]";
	}
	
	
}
