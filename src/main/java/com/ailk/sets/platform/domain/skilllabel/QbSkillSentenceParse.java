package com.ailk.sets.platform.domain.skilllabel;

/**
 * QbSkillSentenceParse entity. @author MyEclipse Persistence Tools
 */

public class QbSkillSentenceParse implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7576248036707117403L;
	private Integer id;
	private String beginWord;
	private Integer wordType;
	private Integer priority;

	// Constructors

	/** default constructor */
	public QbSkillSentenceParse() {
	}

	/** minimal constructor */
	public QbSkillSentenceParse(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public QbSkillSentenceParse(Integer id, String beginWord, String endWord, Integer wordType, Integer priority) {
		this.id = id;
		this.beginWord = beginWord;
		this.wordType = wordType;
		this.priority = priority;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBeginWord() {
		return this.beginWord;
	}

	public void setBeginWord(String beginWord) {
		this.beginWord = beginWord;
	}

	public Integer getWordType() {
		return this.wordType;
	}

	public void setWordType(Integer wordType) {
		this.wordType = wordType;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}