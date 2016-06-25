package com.ailk.sets.platform.intf.cand.domain;

/**
 * HelpCoding entity. @author MyEclipse Persistence Tools
 */

public class HelpCoding implements java.io.Serializable {
	private static final long serialVersionUID = 2785735136695635512L;
	private Integer keywordId;
	private String keywordCode;
	private String contentFile;
	private String programLanguage;

	// Constructors

	/** default constructor */
	public HelpCoding() {
	}

	/** minimal constructor */
	public HelpCoding(Integer keywordId) {
		this.keywordId = keywordId;
	}

	/** full constructor */
	public HelpCoding(Integer keywordId, String keywordCode, String contentFile, String programLanguage) {
		this.keywordId = keywordId;
		this.keywordCode = keywordCode;
		this.contentFile = contentFile;
		this.programLanguage = programLanguage;
	}

	// Property accessors

	public Integer getKeywordId() {
		return this.keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeywordCode() {
		return this.keywordCode;
	}

	public void setKeywordCode(String keywordCode) {
		this.keywordCode = keywordCode;
	}

	public String getContentFile() {
		return this.contentFile;
	}

	public void setContentFile(String contentFile) {
		this.contentFile = contentFile;
	}

	public String getProgramLanguage() {
		return this.programLanguage;
	}

	public void setProgramLanguage(String programLanguage) {
		this.programLanguage = programLanguage;
	}

}