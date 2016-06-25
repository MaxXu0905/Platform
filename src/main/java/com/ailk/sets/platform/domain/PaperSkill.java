package com.ailk.sets.platform.domain;

/**
 * PositionSkill entity. @author MyEclipse Persistence Tools
 */

public class PaperSkill implements java.io.Serializable {

	private static final long serialVersionUID = -4682881224822144541L;
	private PaperSkillId id;
	private Integer skillSeq;
	private Integer degreeId;
    private Integer questionNum;
	// Constructors

	/** default constructor */
	public PaperSkill() {
	}

	/** minimal constructor */
	public PaperSkill(PaperSkillId id) {
		this.id = id;
	}

	/** full constructor */
	public PaperSkill(PaperSkillId id, Integer skillSeq, Integer degreeId) {
		this.id = id;
		this.skillSeq = skillSeq;
		this.degreeId = degreeId;
	}

	// Property accessors
	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public PaperSkillId getId() {
		return this.id;
	}

	public void setId(PaperSkillId id) {
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

}