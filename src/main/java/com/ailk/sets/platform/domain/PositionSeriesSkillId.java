package com.ailk.sets.platform.domain;

/**
 * PositionSeriesSkillId entity. @author MyEclipse Persistence Tools
 */

public class PositionSeriesSkillId implements java.io.Serializable {

	private static final long serialVersionUID = -8845291477344110099L;
	private Integer seriesId;
	private Integer qbId;
	private String skillId;

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public Integer getQbId() {
		return this.qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public String getSkillId() {
		return this.skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PositionSeriesSkillId))
			return false;
		PositionSeriesSkillId castOther = (PositionSeriesSkillId) other;

		return ((this.getSeriesId() == castOther.getSeriesId()) || (this.getSeriesId() != null && castOther.getSeriesId() != null && this.getSeriesId().equals(castOther.getSeriesId())))
				&& ((this.getQbId() == castOther.getQbId()) || (this.getQbId() != null && castOther.getQbId() != null && this.getQbId().equals(castOther.getQbId())))
				&& ((this.getSkillId() == castOther.getSkillId()) || (this.getSkillId() != null && castOther.getSkillId() != null && this.getSkillId().equals(castOther.getSkillId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getSeriesId() == null ? 0 : this.getSeriesId().hashCode());
		result = 37 * result + (getQbId() == null ? 0 : this.getQbId().hashCode());
		result = 37 * result + (getSkillId() == null ? 0 : this.getSkillId().hashCode());
		return result;
	}

}