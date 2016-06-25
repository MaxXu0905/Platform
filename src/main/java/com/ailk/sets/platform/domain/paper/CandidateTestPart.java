package com.ailk.sets.platform.domain.paper;

import java.util.Date;

import com.ailk.sets.platform.intf.common.PaperPartTimerType;

/**
 * PaperInstancePart entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestPart  implements java.io.Serializable {
	private static final long serialVersionUID = -5911536132526173413L;
	private CandidateTestPartId id;
	private String partDesc;
	private String questionType;
	private Integer questionNum;
	private Integer partPoints;
	private Integer suggestTime;
	private Date beginTime;
	private Date endTime;
	private Integer partState;
	private Integer rightNum;
	private Integer wrongNum;
	private Integer partGetPoints;
	private Integer  timerType;
	
	// Constructors

	/** default constructor */
	public CandidateTestPart() {
		this.timerType = PaperPartTimerType.PART.getValue();
	}

	/** minimal constructor */
	public CandidateTestPart(CandidateTestPartId id) {
		this.id = id;
	}

	/** full constructor */
	public CandidateTestPart(CandidateTestPartId id, String partDesc, String questionType, Integer questionNum,
			Integer partPoints, Integer suggestTime, Date beginTime, Date endTime, Integer partState,
			Integer rightNum, Integer wrongNum, Integer partGetPoints) {
		this.id = id;
		this.partDesc = partDesc;
		this.questionType = questionType;
		this.questionNum = questionNum;
		this.partPoints = partPoints;
		this.suggestTime = suggestTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.partState = partState;
		this.rightNum = rightNum;
		this.wrongNum = wrongNum;
		this.partGetPoints = partGetPoints;
	}

	// Property accessors

	public CandidateTestPartId getId() {
		return this.id;
	}

	public void setId(CandidateTestPartId id) {
		this.id = id;
	}

	public String getPartDesc() {
		return this.partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Integer getQuestionNum() {
		return this.questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public Integer getPartPoints() {
		return this.partPoints;
	}

	public void setPartPoints(Integer partPoints) {
		this.partPoints = partPoints;
	}

	public Integer getSuggestTime() {
		return this.suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getPartState() {
		return this.partState;
	}

	public void setPartState(Integer partState) {
		this.partState = partState;
	}

	public Integer getRightNum() {
		return this.rightNum;
	}

	public void setRightNum(Integer rightNum) {
		this.rightNum = rightNum;
	}

	public Integer getWrongNum() {
		return this.wrongNum;
	}

	public void setWrongNum(Integer wrongNum) {
		this.wrongNum = wrongNum;
	}

	public Integer getPartGetPoints() {
		return this.partGetPoints;
	}

	public void setPartGetPoints(Integer partGetPoints) {
		this.partGetPoints = partGetPoints;
	}

	public Integer getTimerType() {
		return timerType;
	}

	public void setTimerType(Integer timerType) {
		this.timerType = timerType;
	}

}