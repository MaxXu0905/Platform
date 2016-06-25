package com.ailk.sets.platform.domain;

/**
 * PositionSkillRecommendId entity. @author MyEclipse Persistence Tools
 */

public class PositionSkillRecommendId implements java.io.Serializable {

	// Fields

	private Integer seriesId;
	private Integer positionLevel;
	private String skillId;

	// Constructors

	/** default constructor */
	public PositionSkillRecommendId() {
	}

	/** full constructor */
	public PositionSkillRecommendId(Integer seriesId, Integer positionLevel, String skillId) {
		this.seriesId = seriesId;
		this.positionLevel = positionLevel;
		this.skillId = skillId;
	}

	// Property accessors

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public Integer getPositionLevel() {
		return this.positionLevel;
	}

	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
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
		if (!(other instanceof PositionSkillRecommendId))
			return false;
		PositionSkillRecommendId castOther = (PositionSkillRecommendId) other;

		return ((this.getSeriesId() == castOther.getSeriesId()) || (this.getSeriesId() != null
				&& castOther.getSeriesId() != null && this.getSeriesId().equals(castOther.getSeriesId())))
				&& ((this.getPositionLevel() == castOther.getPositionLevel()) || (this.getPositionLevel() != null
						&& castOther.getPositionLevel() != null && this.getPositionLevel().equals(
						castOther.getPositionLevel())))
				&& ((this.getSkillId() == castOther.getSkillId()) || (this.getSkillId() != null
						&& castOther.getSkillId() != null && this.getSkillId().equals(castOther.getSkillId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getSeriesId() == null ? 0 : this.getSeriesId().hashCode());
		result = 37 * result + (getPositionLevel() == null ? 0 : this.getPositionLevel().hashCode());
		result = 37 * result + (getSkillId() == null ? 0 : this.getSkillId().hashCode());
		return result;
	}

}