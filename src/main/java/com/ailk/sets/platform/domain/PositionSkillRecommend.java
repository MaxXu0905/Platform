package com.ailk.sets.platform.domain;

/**
 * PositionSkillRecommend entity. @author MyEclipse Persistence Tools
 */

public class PositionSkillRecommend implements java.io.Serializable {

	private static final long serialVersionUID = -3117308893187437168L;
	private PositionSkillRecommendId id;
	private Integer skillSeq;
	private Integer degreeId;
	private Integer questionNum;

	// Constructors

	/** default constructor */
	public PositionSkillRecommend() {
	}

	/** minimal constructor */
	public PositionSkillRecommend(PositionSkillRecommendId id, Integer skillSeq, Integer degreeId) {
		this.id = id;
		this.skillSeq = skillSeq;
		this.degreeId = degreeId;
	}

	/** full constructor */
	public PositionSkillRecommend(PositionSkillRecommendId id, Integer skillSeq, Integer degreeId, Integer questionNum) {
		this.id = id;
		this.skillSeq = skillSeq;
		this.degreeId = degreeId;
		this.questionNum = questionNum;
	}

	// Property accessors

	public PositionSkillRecommendId getId() {
		return this.id;
	}

	public void setId(PositionSkillRecommendId id) {
		this.id = id;
	}

	public Integer getSkillSeq() {
		return this.skillSeq;
	}

	public void setSkillSeq(Integer skillSeq) {
		this.skillSeq = skillSeq;
	}

	public Integer getDegreeId() {
		return this.degreeId;
	}

	public void setDegreeId(Integer degreeId) {
		this.degreeId = degreeId;
	}

	public Integer getQuestionNum() {
		return this.questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

}