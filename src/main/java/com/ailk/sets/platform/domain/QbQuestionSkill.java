package com.ailk.sets.platform.domain;

/**
 * QbQuestionSkill entity. @author MyEclipse Persistence Tools
 */

public class QbQuestionSkill implements java.io.Serializable {

	private static final long serialVersionUID = -7342585432330389144L;
	private QbQuestionSkillId id;
	private Integer skillSeq;

	// Constructors

	/** default constructor */
	public QbQuestionSkill() {
	}

	/** minimal constructor */
	public QbQuestionSkill(QbQuestionSkillId id) {
		this.id = id;
	}

	/** full constructor */
	public QbQuestionSkill(QbQuestionSkillId id, Integer skillSeq) {
		this.id = id;
		this.skillSeq = skillSeq;
	}

	// Property accessors

	public QbQuestionSkillId getId() {
		return this.id;
	}

	public void setId(QbQuestionSkillId id) {
		this.id = id;
	}

	public Integer getSkillSeq() {
		return this.skillSeq;
	}

	public void setSkillSeq(Integer skillSeq) {
		this.skillSeq = skillSeq;
	}

}