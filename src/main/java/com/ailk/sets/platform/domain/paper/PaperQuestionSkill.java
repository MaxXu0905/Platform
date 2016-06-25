package com.ailk.sets.platform.domain.paper;

/**
 * PaperQuestionSkill entity. @author MyEclipse Persistence Tools
 */

public class PaperQuestionSkill implements java.io.Serializable {

	private static final long serialVersionUID = 3229437928483025258L;
	private PaperQuestionSkillId id;

	// Constructors

	/** default constructor */
	public PaperQuestionSkill() {
	}

	/** full constructor */
	public PaperQuestionSkill(PaperQuestionSkillId id) {
		this.id = id;
	}

	// Property accessors

	public PaperQuestionSkillId getId() {
		return this.id;
	}

	public void setId(PaperQuestionSkillId id) {
		this.id = id;
	}

}