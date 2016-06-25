package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ConfigReportKpiPK implements Serializable, Cloneable {

	@Column(name = "kpi_name", nullable = false)
	private String kpiName;

	@Column(name = "kpi_value_left", nullable = false, columnDefinition = "decimal(6,2)")
	private double kpiValueLeft;

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public double getKpiValueLeft() {
		return kpiValueLeft;
	}

	public void setKpiValueLeft(double kpiValueLeft) {
		this.kpiValueLeft = kpiValueLeft;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kpiName == null) ? 0 : kpiName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(kpiValueLeft);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ConfigReportKpiPK other = (ConfigReportKpiPK) obj;
		if (kpiName == null) {
			if (other.kpiName != null)
				return false;
		} else if (!kpiName.equals(other.kpiName))
			return false;
		if (Double.doubleToLongBits(kpiValueLeft) != Double
				.doubleToLongBits(other.kpiValueLeft))
			return false;
		return true;
	}

}
