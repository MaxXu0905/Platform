package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "qb_upload_error")
public class QbUploadError implements Serializable, Cloneable {

	@Id
	@GeneratedValue
	@Column(name = "serial_no", nullable = false)
	private int serialNo;

	@Column(name = "qb_id", nullable = false)
	private int qbId;

	@Column(name = "error_type", nullable = false)
	private int errorType;

	@Column(name = "group_name", nullable = true)
	private String groupName;

	@Column(name = "content", nullable = false)
	private String content;

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public int getQbId() {
		return qbId;
	}

	public void setQbId(int qbId) {
		this.qbId = qbId;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
