package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "analyse")
public class Analyse implements Serializable, Cloneable {

	@EmbeddedId
	private AnalysePK analysePK;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((analysePK == null) ? 0 : analysePK.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Analyse other = (Analyse) obj;
		if (analysePK == null) {
			if (other.analysePK != null)
				return false;
		} else if (!analysePK.equals(other.analysePK))
			return false;
		return true;
	}

	public AnalysePK getAnalysePK() {
		return analysePK;
	}

	public void setAnalysePK(AnalysePK analysePK) {
		this.analysePK = analysePK;
	}

}
