package com.ailk.sets.grade.intf;

@SuppressWarnings("serial")
public class ExportReportResponse extends BaseResponse {

	private String title;
	private byte[] data;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
