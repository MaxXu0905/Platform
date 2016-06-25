package com.ailk.sets.platform.intf.empl.domain;

import java.util.Date;

/**
 * QbQuestion entity. @author MyEclipse Persistence Tools
 */

public class QbQuestion implements java.io.Serializable {
	private static final long serialVersionUID = -3895006555721849552L;
	private Long questionId;

	private Integer qbId;
	private String questionType;
	private String questionDesc;
	private String programLanguage;
	private Integer isSample;
	private Integer degree;
	private Integer point;
	private String subAsks;
	private Integer suggestTime;
	private Integer autoCheck;
	private Integer deriveFlag;
	private Integer state;
	private Integer createBy;
	private Date createDate;
	private Date modifyDate;
	private Integer answerNum;
	private Integer correctNum;
	private Integer category;
	private Integer html;
	private Integer prebuilt;

	// Constructors

	public Integer getPrebuilt() {
		return prebuilt;
	}

	public Integer getHtml() {
		return html;
	}

	public void setHtml(Integer html) {
		this.html = html;
	}

	public void setPrebuilt(Integer prebuilt) {
		this.prebuilt = prebuilt;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	/** default constructor */
	public QbQuestion() {
		this.suggestTime = 0;
		this.degree = 0;
		this.deriveFlag = 0;//
		this.isSample = 0;
		this.point = 0;
	}

	/** minimal constructor */
	public QbQuestion(Long questionId) {
		this.questionId = questionId;
	}

	/** full constructor */
	public QbQuestion(Long questionId, Integer qbId, String questionType,
			String questionDesc, String programLanguage, Integer isSample,
			Integer degree, Integer point, String subAsks, Integer suggestTime,
			Integer autoCheck, Integer deriveFlag, Integer state,
			Integer createBy, Date createDate, Date modifyDate) {
		this.questionId = questionId;
		this.qbId = qbId;
		this.questionType = questionType;
		this.questionDesc = questionDesc;
		this.programLanguage = programLanguage;
		this.isSample = isSample;
		this.degree = degree;
		this.point = point;
		this.subAsks = subAsks;
		this.suggestTime = suggestTime;
		this.autoCheck = autoCheck;
		this.deriveFlag = deriveFlag;
		this.state = state;
		this.createBy = createBy;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}

	// Property accessors
	public Integer getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getQbId() {
		return this.qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionDesc() {
		return this.questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public String getProgramLanguage() {
		return this.programLanguage;
	}

	public void setProgramLanguage(String programLanguage) {
		this.programLanguage = programLanguage;
	}

	public Integer getIsSample() {
		return this.isSample;
	}

	public void setIsSample(Integer isSample) {
		this.isSample = isSample;
	}

	public Integer getDegree() {
		return this.degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(Integer correctNum) {
		this.correctNum = correctNum;
	}

	public String getSubAsks() {
		return this.subAsks;
	}

	public void setSubAsks(String subAsks) {
		this.subAsks = subAsks;
	}

	public Integer getSuggestTime() {
		return this.suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}

	public Integer getAutoCheck() {
		return this.autoCheck;
	}

	public void setAutoCheck(Integer autoCheck) {
		this.autoCheck = autoCheck;
	}

	public Integer getDeriveFlag() {
		return this.deriveFlag;
	}

	public void setDeriveFlag(Integer deriveFlag) {
		this.deriveFlag = deriveFlag;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "QbQuestion [questionId=" + questionId + ", questionType="
				+ questionType + ", isSample=" + isSample + ", degree="
				+ degree + ", deriveFlag=" + deriveFlag + "]";
	}
}