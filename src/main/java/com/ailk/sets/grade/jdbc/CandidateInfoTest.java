package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "candidate_info_test")
public class CandidateInfoTest implements Serializable, Cloneable {

	@EmbeddedId
	private CandidateInfoTestPK candidateInfoTestPK;

	@Column(name = "value")
	private String value;

	@Column(name = "real_value")
	private String realValue;

	public CandidateInfoTestPK getCandidateInfoTestPK() {
		return candidateInfoTestPK;
	}

	public void setCandidateInfoTestPK(CandidateInfoTestPK candidateInfoTestPK) {
		this.candidateInfoTestPK = candidateInfoTestPK;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRealValue() {
		return realValue;
	}

	public void setRealValue(String realValue) {
		this.realValue = realValue;
	}

}
