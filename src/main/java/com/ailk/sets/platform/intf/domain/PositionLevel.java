package com.ailk.sets.platform.intf.domain;

/**
 * PositionLevel entity. @author MyEclipse Persistence Tools
 */

public class PositionLevel implements java.io.Serializable {

	private static final long serialVersionUID = 5833752631222368977L;
	private PositionLevelId id;
	private String levelName;
	private String levelAlias;
	private Double degreeLow;
	private Double degreeHigh;
	private Integer subQuestionRatio;
	private String description;

	// Constructors

	/** default constructor */
	public PositionLevel() {
	}

	/** minimal constructor */
	public PositionLevel(PositionLevelId id) {
		this.id = id;
	}

	/** full constructor */
	public PositionLevel(PositionLevelId id, String levelName, String levelAlias, Double degreeLow, Double degreeHigh, Integer subQuestionRatio) {
		this.id = id;
		this.levelName = levelName;
		this.levelAlias = levelAlias;
		this.degreeLow = degreeLow;
		this.degreeHigh = degreeHigh;
		this.subQuestionRatio = subQuestionRatio;
	}

	// Property accessors

	public PositionLevelId getId() {
		return this.id;
	}

	public void setId(PositionLevelId id) {
		this.id = id;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelAlias() {
		return this.levelAlias;
	}

	public void setLevelAlias(String levelAlias) {
		this.levelAlias = levelAlias;
	}

	public Double getDegreeLow() {
		return this.degreeLow;
	}

	public void setDegreeLow(Double degreeLow) {
		this.degreeLow = degreeLow;
	}

	public Double getDegreeHigh() {
		return this.degreeHigh;
	}

	public void setDegreeHigh(Double degreeHigh) {
		this.degreeHigh = degreeHigh;
	}

	public Integer getSubQuestionRatio() {
		return this.subQuestionRatio;
	}

	public void setSubQuestionRatio(Integer subQuestionRatio) {
		this.subQuestionRatio = subQuestionRatio;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}