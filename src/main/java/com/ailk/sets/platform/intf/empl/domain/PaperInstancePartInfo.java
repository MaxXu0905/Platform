package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;

public class PaperInstancePartInfo implements Serializable {
	private static final long serialVersionUID = -1571976114000573940L;
	private Integer partSeq;// 部分序号
	private String partName;// 部分名称
	private Integer partPoints;// 总分数
	private Integer suggestTime;// 总时间
	private Integer questionNum;// 总题目数

	public Integer getPartSeq() {
		return partSeq;
	}

	public void setPartSeq(Integer partSeq) {
		this.partSeq = partSeq;
	}

	public Integer getPartPoints() {
		return partPoints;
	}

	public void setPartPoints(Integer partPoints) {
		this.partPoints = partPoints;
	}

	public Integer getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	@Override
	public String toString() {
		return "PaperInstancePartInfo [partName=" + partName + ", partPoints=" + partPoints + ", partSeq=" + partSeq + ", questionNum=" + questionNum + ", suggestTime=" + suggestTime + "]";
	}
}
