package com.ailk.sets.platform.intf.domain;

import com.ailk.sets.platform.intf.common.PFResponse;

/**
 * Candidate entity. @author MyEclipse Persistence Tools
 */

public class Candidate extends PFResponse implements java.io.Serializable {

	private static final long serialVersionUID = 1167391647355705785L;
	private Integer candidateId;
	private String candidateName;
	private String candidateEmail;
	private String weixinNo;
	private String openId;

	public Integer getCandidateId() {
		return this.candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return this.candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return this.candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getWeixinNo() {
		return weixinNo;
	}

	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}