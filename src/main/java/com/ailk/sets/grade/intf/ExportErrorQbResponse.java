package com.ailk.sets.grade.intf;


@SuppressWarnings("serial")
public class ExportErrorQbResponse extends BaseResponse {

	private String qbName;
	private byte[] data;

	public String getQbName() {
		return qbName;
	}

	public void setQbName(String qbName) {
		this.qbName = qbName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
