package com.ailk.sets.grade.intf.report;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class Interview implements Serializable {

	private int anchor;
	private InterviewInfo interviewInfo;
	private List<InterviewItem> items;

	public Interview() {
		anchor = IReportConfig.REPORT_COLUMN_QUALITY;
	}

	public int getAnchor() {
		return anchor;
	}

	public void setAnchor(int anchor) {
		this.anchor = anchor;
	}

	public InterviewInfo getInterviewInfo() {
		return interviewInfo;
	}

	public void setInterviewInfo(InterviewInfo interviewInfo) {
		this.interviewInfo = interviewInfo;
	}

	public List<InterviewItem> getItems() {
		return items;
	}

	public void setItems(List<InterviewItem> items) {
		this.items = items;
	}

	/**
	 * 面试信息
	 * 
	 * @author xugq
	 * 
	 */
	public static class InterviewItem implements Serializable {
		private static final long serialVersionUID = 1L;

		private String groupId;
		private String infoId;
		private String value;
		private String realValue;

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getInfoId() {
			return infoId;
		}

		public void setInfoId(String infoId) {
			this.infoId = infoId;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getRealValue() {
			return realValue;
		}

		public void setRealValue(String realValue) {
			this.realValue = realValue;
		}
	}

}
