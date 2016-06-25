package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "qb_base_file")
public class QbBaseFile implements Serializable, Cloneable {

	@Id
	@Column(name = "file_id", nullable = false)
	private int fileId;

	@Column(name = "qb_id", nullable = false)
	private int qbId;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getQbId() {
		return qbId;
	}

	public void setQbId(int qbId) {
		this.qbId = qbId;
	}

}
