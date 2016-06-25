package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "config_report_kpi")
public class ConfigReportKpi implements Serializable, Cloneable {

	@EmbeddedId
	private ConfigReportKpiPK configReportKpiPK;

	@Column(name = "kpi_code", nullable = false)
	private String kpiCode;

	@Column(name = "kpi_value_right", nullable = false, columnDefinition = "decimal(6,2)")
	private double kpiValueRight;

	@Column(name = "kpi_comment")
	private String kpiComment;

	@Column(name = "kpi_type")
	private int kpiType;

	public ConfigReportKpiPK getConfigReportKpiPK() {
		return configReportKpiPK;
	}

	public void setConfigReportKpiPK(ConfigReportKpiPK configReportKpiPK) {
		this.configReportKpiPK = configReportKpiPK;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public double getKpiValueRight() {
		return kpiValueRight;
	}

	public void setKpiValueRight(double kpiValueRight) {
		this.kpiValueRight = kpiValueRight;
	}

	public String getKpiComment() {
		return kpiComment;
	}

	public void setKpiComment(String kpiComment) {
		this.kpiComment = kpiComment;
	}

	public int getKpiType() {
		return kpiType;
	}

	public void setKpiType(int kpiType) {
		this.kpiType = kpiType;
	}

}
