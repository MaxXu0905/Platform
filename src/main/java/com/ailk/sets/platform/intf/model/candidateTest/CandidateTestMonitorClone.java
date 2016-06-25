package com.ailk.sets.platform.intf.model.candidateTest;

import java.io.Serializable;
import java.sql.Timestamp;

public class CandidateTestMonitorClone implements Serializable {

	private static final long serialVersionUID = 4673603515631124441L;

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
		return testId;
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
		return faceNum;
	}

	public void setFaceNum(Integer faceNum) {
		this.faceNum = faceNum;
	}

}
