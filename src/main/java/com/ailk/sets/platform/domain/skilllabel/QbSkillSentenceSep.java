package com.ailk.sets.platform.domain.skilllabel;

/**
 * QbSkillSentenceSep entity. @author MyEclipse Persistence Tools
 */

public class QbSkillSentenceSep implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1904614230294560412L;
	private String separator;

	// Constructors

	/** default constructor */
	public QbSkillSentenceSep() {
	}

	/** full constructor */
	public QbSkillSentenceSep(String separator) {
		this.separator = separator;
	}

	// Property accessors

	public String getSeparator() {
		return this.separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

}