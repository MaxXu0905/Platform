package com.ailk.sets.platform.intf.model.qb;

import java.sql.Timestamp;

/**
 * QbBase entity. @author MyEclipse Persistence Tools
 */

public class QbBase implements java.io.Serializable {
	private static final long serialVersionUID = -2734292352529135454L;
	private Integer qbId;
	private String qbName;
	private String qbDesc;
	private Integer category;
	private Integer createBy;
	private Timestamp createDate;
	private Timestamp modifyDate;
	private Integer prebuilt;

	public Integer getQbId() {
		return this.qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public String getQbName() {
		return this.qbName;
	}

	public void setQbName(String qbName) {
		this.qbName = qbName;
	}

	public String getQbDesc() {
		return this.qbDesc;
	}

	public void setQbDesc(String qbDesc) {
		this.qbDesc = qbDesc;
	}

	public Integer getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Integer getPrebuilt() {
		return prebuilt;
	}

	public void setPrebuilt(Integer prebuilt) {
		this.prebuilt = prebuilt;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

}