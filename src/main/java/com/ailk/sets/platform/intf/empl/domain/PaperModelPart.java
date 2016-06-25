package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.List;

public class PaperModelPart implements Serializable {
	private static final long serialVersionUID = -1571976114000573940L;
	private String partName;// 部分名称
	private Integer questionNum;// 总题目数
    private List<GetQInfoResponseModel> selfQuestions;//可以看到的题目信息
	public List<GetQInfoResponseModel> getSelfQuestions() {
		return selfQuestions;
	}

	public void setSelfQuestions(List<GetQInfoResponseModel> selfQuestions) {
		this.selfQuestions = selfQuestions;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	@Override
	public String toString() {
		return "PaperInstancePartInfo [partName=" + partName + ", questionNum=" + questionNum + "]";
	}
}
