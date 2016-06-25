package com.ailk.sets.platform.domain;

/**
 * QbDifficultyLevelId entity. @author MyEclipse Persistence Tools
 */

public class QbDifficultyLevelId implements java.io.Serializable {
	private static final long serialVersionUID = -7894619605895612388L;
	private String levelCode;
	private Integer positionLevel;

	// Constructors


	/** default constructor */
	public QbDifficultyLevelId() {
	}

	/** full constructor */
	public QbDifficultyLevelId(String levelCode, Integer positionLevel) {
		this.levelCode = levelCode;
		this.positionLevel = positionLevel;
	}

	// Property accessors

	public String getLevelCode() {
		return this.levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getPositionLevel() {
		return this.positionLevel;
	}

	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((levelCode == null) ? 0 : levelCode.hashCode());
		result = prime * result + ((positionLevel == null) ? 0 : positionLevel.hashCode());
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
		QbDifficultyLevelId other = (QbDifficultyLevelId) obj;
		if (levelCode == null) {
			if (other.levelCode != null)
				return false;
		} else if (!levelCode.equals(other.levelCode))
			return false;
		if (positionLevel == null) {
			if (other.positionLevel != null)
				return false;
		} else if (!positionLevel.equals(other.positionLevel))
			return false;
		return true;
	}


}