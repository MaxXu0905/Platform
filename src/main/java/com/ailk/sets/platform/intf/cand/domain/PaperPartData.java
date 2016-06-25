package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;
import java.util.List;

public class PaperPartData implements Serializable {
	private static final long serialVersionUID = 5302341849258662343L;
	private Integer partSeq;//part序号   21,"试答题客观";22,"试答题主观"   1, "客观题"; 2, "主观题";3, "面试题";
	private String partDesc;//part名称 
//	private Integer questionNum;
	private Integer suggestTime;
	private List<PaperQuestionInfo> questionIds;
	private Integer questionLength;
	 private Integer timerType;//试卷计时类型
	 
	public void setQuestionLength(Integer questionLength) {
		this.questionLength = questionLength;
	}
	public Integer getQuestionLength() {
		return questionLength;
	}
	public Integer getPartSeq() {
		return partSeq;
	}
	public void setPartSeq(Integer partSeq) {
		this.partSeq = partSeq;
	}
	public String getPartDesc() {
		return partDesc;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public Integer getSuggestTime() {
		return suggestTime;
	}
	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}
	public List<PaperQuestionInfo> getQuestionIds() {
		return questionIds;
	}
	public void setQuestionIds(List<PaperQuestionInfo> questionIds) {
		this.questionIds = questionIds;
		this.questionLength = questionIds == null ? 0 : questionIds.size();
	}
	public Integer getTimerType() {
		return timerType;
	}
	public void setTimerType(Integer timerType) {
		this.timerType = timerType;
	}
	
}
