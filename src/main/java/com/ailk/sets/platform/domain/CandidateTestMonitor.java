package com.ailk.sets.platform.domain;

import java.sql.Timestamp;

/**
 * CandidateTestMonitor entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestMonitor implements java.io.Serializable {

	private static final long serialVersionUID = -1925657444349560256L;
	private Integer picId;
	private Long testId;
	private String picFile;
	private Integer picType;
	private Timestamp createTime;
	private Integer isAbnormal;
	private Integer faceNum;

	public Integer getPicId() {
		return this.picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public Long getTestId() {
		return this.testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getPicFile() {
		return this.picFile;
	}

	public void setPicFile(String picFile) {
		this.picFile = picFile;
	}

	public Integer getPicType() {
		return this.picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getIsAbnormal() {
		return this.isAbnormal;
	}

	public void setIsAbnormal(Integer isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public Integer getFaceNum() {
		return this.faceNum;
	}

	public void setFaceNum(Integer faceNum) {
		this.faceNum = faceNum;
	}

}