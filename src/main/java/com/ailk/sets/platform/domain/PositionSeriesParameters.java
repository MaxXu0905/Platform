package com.ailk.sets.platform.domain;

/**
 * PositionSeriesParameters entity. @author MyEclipse Persistence Tools
 */

public class PositionSeriesParameters implements java.io.Serializable {

	private static final long serialVersionUID = -6518525558351957731L;
	private PositionSeriesParametersId id;
	private Integer questionLeast;
	private Integer questionMost;

	// Constructors

	/** default constructor */
	public PositionSeriesParameters() {
	}

	/** minimal constructor */
	public PositionSeriesParameters(PositionSeriesParametersId id) {
		this.id = id;
	}

	/** full constructor */
	public PositionSeriesParameters(PositionSeriesParametersId id, Integer questionLeast, Integer questionMost) {
		this.id = id;
		this.questionLeast = questionLeast;
		this.questionMost = questionMost;
	}

	// Property accessors

	public PositionSeriesParametersId getId() {
		return this.id;
	}

	public void setId(PositionSeriesParametersId id) {
		this.id = id;
	}

	public Integer getQuestionLeast() {
		return this.questionLeast;
	}

	public void setQuestionLeast(Integer questionLeast) {
		this.questionLeast = questionLeast;
	}

	public Integer getQuestionMost() {
		return this.questionMost;
	}

	public void setQuestionMost(Integer questionMost) {
		this.questionMost = questionMost;
	}

}