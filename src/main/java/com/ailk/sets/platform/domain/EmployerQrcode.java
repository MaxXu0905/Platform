package com.ailk.sets.platform.domain;

/**
 * EmployerQrcode entity. @author MyEclipse Persistence Tools
 */

public class EmployerQrcode implements java.io.Serializable {

	private static final long serialVersionUID = -9096557297007339013L;
	private Integer qrcodeId;
	private String qrcodeUrl;
	private Integer employerId;

	public Integer getQrcodeId() {
		return this.qrcodeId;
	}

	public void setQrcodeId(Integer qrcodeId) {
		this.qrcodeId = qrcodeId;
	}

	public String getQrcodeUrl() {
		return this.qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public Integer getEmployerId() {
		return this.employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

}