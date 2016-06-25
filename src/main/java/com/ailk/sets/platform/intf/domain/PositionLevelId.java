package com.ailk.sets.platform.intf.domain;

/**
 * PositionLevelId entity. @author MyEclipse Persistence Tools
 */

public class PositionLevelId implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 9218854084399075247L;
	private Integer seriesId;
	private Integer level;

	// Constructors

	/** default constructor */
	public PositionLevelId() {
	}

	/** full constructor */
	public PositionLevelId(Integer seriesId, Integer level) {
		this.seriesId = seriesId;
		this.level = level;
	}

	// Property accessors

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PositionLevelId))
			return false;
		PositionLevelId castOther = (PositionLevelId) other;

		return ((this.getSeriesId() == castOther.getSeriesId()) || (this.getSeriesId() != null
				&& castOther.getSeriesId() != null && this.getSeriesId().equals(castOther.getSeriesId())))
				&& ((this.getLevel() == castOther.getLevel()) || (this.getLevel() != null
						&& castOther.getLevel() != null && this.getLevel().equals(castOther.getLevel())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getSeriesId() == null ? 0 : this.getSeriesId().hashCode());
		result = 37 * result + (getLevel() == null ? 0 : this.getLevel().hashCode());
		return result;
	}

}