package com.ailk.sets.platform.domain.paper;

import java.math.BigDecimal;

import com.ailk.sets.platform.intf.common.PaperPartTimerType;

/**
 * PaperPart entity. @author MyEclipse Persistence Tools
 */

public class PaperPart implements java.io.Serializable {
	private static final long serialVersionUID = 7483024730039961637L;
	private PaperPartId id;
	private String partDesc;
	private Integer questionType;
	private Integer questionNum;
	private Integer suggestTime;
	private Integer partPoints;
	private Integer  timerType;
	
	public PaperPart(){
		this.timerType = PaperPartTimerType.PART.getValue();
	}
	public Integer getSuggestMinutes() {
		BigDecimal time = new BigDecimal(suggestTime/60.0).setScale(0, BigDecimal.ROUND_HALF_UP);
		return time.intValue();
	}

	public PaperPartId getId() {
		return this.id;
	}

	public void setId(PaperPartId id) {
		this.id = id;
	}

	public String getPartDesc() {
		return this.partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public Integer getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public Integer getQuestionNum() {
		return this.questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public Integer getSuggestTime() {
		return this.suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}

	public Integer getPartPoints() {
		return this.partPoints;
	}

	public void setPartPoints(Integer partPoints) {
		this.partPoints = partPoints;
	}

	public Integer getTimerType() {
		return timerType;
	}

	public void setTimerType(Integer timerType) {
		this.timerType = timerType;
	}

}