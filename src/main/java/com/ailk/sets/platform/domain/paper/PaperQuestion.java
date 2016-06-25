package com.ailk.sets.platform.domain.paper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;


/**
 * PaperQuestion entity. @author MyEclipse Persistence Tools
 */

public class PaperQuestion implements java.io.Serializable {
	private static final long serialVersionUID = 2949164455188839866L;
	private PaperQuestionId id;
	private Integer partSeq;
	private Integer questionSeq;
	private String relSkills;

	// Constructors

	/** default constructor */
	public PaperQuestion() {
	}

	/** minimal constructor */
	public PaperQuestion(PaperQuestionId id, Integer partSeq) {
		this.id = id;
		this.partSeq = partSeq;
	}

	/** full constructor */
	public PaperQuestion(PaperQuestionId id, Integer partSeq, Integer questionSeq, String relSkills) {
		this.id = id;
		this.partSeq = partSeq;
		this.questionSeq = questionSeq;
		this.relSkills = relSkills;
	}

	// Property accessors

	public PaperQuestionId getId() {
		return this.id;
	}

	public void setId(PaperQuestionId id) {
		this.id = id;
	}

	public Integer getPartSeq() {
		return this.partSeq;
	}

	public void setPartSeq(Integer partSeq) {
		this.partSeq = partSeq;
	}

	public Integer getQuestionSeq() {
		return this.questionSeq;
	}

	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String getRelSkills() {
		return this.relSkills;
	}

	public void setRelSkills(String relSkills) {
		this.relSkills = relSkills;
	}
	
	public List<String> getRelSkillsArray(){
		if(StringUtils.isEmpty(this.relSkills)){
			return new ArrayList<String>();
		}
		return  Arrays.asList(this.relSkills.split(","));
	}

}