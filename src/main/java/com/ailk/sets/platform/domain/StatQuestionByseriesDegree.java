package com.ailk.sets.platform.domain;

/**
 * StatQuestionByseriesDegree entity. @author MyEclipse Persistence Tools
 */

public class StatQuestionByseriesDegree implements java.io.Serializable {
	private static final long serialVersionUID = 7907832655794736452L;
	private StatQuestionByseriesDegreeId id;
	private Integer degreeLow;
	private Integer degreeHigh;
	private String degreeName;
	private Integer questionNum;

	// Constructors

	/** default constructor */
	public StatQuestionByseriesDegree() {
	}

	/** minimal constructor */
	public StatQuestionByseriesDegree(StatQuestionByseriesDegreeId id) {
		this.id = id;
	}

	/** full constructor */
	public StatQuestionByseriesDegree(StatQuestionByseriesDegreeId id, Integer degreeLow, Integer degreeHigh,
			String degreeName, Integer questionNum) {
		this.id = id;
		this.degreeLow = degreeLow;
		this.degreeHigh = degreeHigh;
		this.degreeName = degreeName;
		this.questionNum = questionNum;
	}

	// Property accessors

	public StatQuestionByseriesDegreeId getId() {
		return this.id;
	}

	public void setId(StatQuestionByseriesDegreeId id) {
		this.id = id;
	}

	public Integer getDegreeLow() {
		return this.degreeLow;
	}

	public void setDegreeLow(Integer degreeLow) {
		this.degreeLow = degreeLow;
	}

	public Integer getDegreeHigh() {
		return this.degreeHigh;
	}

	public void setDegreeHigh(Integer degreeHigh) {
		this.degreeHigh = degreeHigh;
	}

	public String getDegreeName() {
		return this.degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public Integer getQuestionNum() {
		return this.questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

}