package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "analyse_result")
public class AnalyseResult implements Serializable, Cloneable {

	@EmbeddedId
	private AnalyseResultPK analyseResultPK;

	@Column(name = "email")
	private String email;

	@Column(name = "end_timestamp", nullable = false)
	private long endTimestamp;

	@Column(name = "millis", nullable = false)
	private long millis;

	public AnalyseResultPK getAnalyseResultPK() {
		return analyseResultPK;
	}

	public void setAnalyseResultPK(AnalyseResultPK analyseResultPK) {
		this.analyseResultPK = analyseResultPK;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public long getMillis() {
		return millis;
	}

	public void setMillis(long millis) {
		this.millis = millis;
	}

}
