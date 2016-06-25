package com.ailk.sets.platform.domain;

import java.util.Date;

/**
 * QbQuestionView entity. @author MyEclipse Persistence Tools
 */

public class QbQuestionView implements java.io.Serializable {

	// Fields

	private QbQuestionViewId id;

	private String subjectCode;
	private Integer qbId;
	private String questionType;
	private String questionDesc;
	private Integer category;
	private String programLanguage;
	private Integer isSample;
	private Integer degree;
	private Integer point;
	private Integer suggestTime;
	private Integer autoCheck;
	private Integer state;
	private Integer answerNum;
	private Integer correctNum;
	private Integer createBy;
	private String employerName;
	private Date createDate;
	private Integer companyId;
	// Constructors

	/** default constructor */
	public QbQuestionView() {
	}

	/** full constructor */
	public QbQuestionView(QbQuestionViewId id) {
		this.id = id;
	}
	
	public QbQuestionViewId getId() {
		return id;
	}

	public void setId(QbQuestionViewId id) {
		this.id = id;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Integer getQbId() {
		return qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}


	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getProgramLanguage() {
		return programLanguage;
	}

	public void setProgramLanguage(String programLanguage) {
		this.programLanguage = programLanguage;
	}

	public Integer getIsSample() {
		return isSample;
	}

	public void setIsSample(Integer isSample) {
		this.isSample = isSample;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}

	public Integer getAutoCheck() {
		return autoCheck;
	}

	public void setAutoCheck(Integer autoCheck) {
		this.autoCheck = autoCheck;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}

	public Integer getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(Integer correctNum) {
		this.correctNum = correctNum;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QbQuestionView other = (QbQuestionView) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}