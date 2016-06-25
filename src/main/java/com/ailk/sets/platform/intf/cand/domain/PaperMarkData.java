package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;
import java.util.List;

public class PaperMarkData implements Serializable {
	private static final long serialVersionUID = 5302341849258662343L;
	private Integer partSeq;//part序号   21,"试答题客观";22,"试答题主观"   1, "客观题"; 2, "主观题";3, "面试题";
	private List<Long> questionIds;
	public Integer getPartSeq() {
		return partSeq;
	}
	public void setPartSeq(Integer partSeq) {
		this.partSeq = partSeq;
	}
	public List<Long> getQuestionIds() {
		return questionIds;
	}
	public void setQuestionIds(List<Long> questionIds) {
		this.questionIds = questionIds;
	}

}
