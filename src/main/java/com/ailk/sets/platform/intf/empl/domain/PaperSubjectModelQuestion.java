package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
public class PaperSubjectModelQuestion implements Serializable{

	private static final long serialVersionUID = -1206636405920409106L;
	private String programLauguage;
	private Integer questionNum;
	public String getProgramLauguage() {
		return programLauguage;
	}
	public void setProgramLauguage(String programLauguage) {
		this.programLauguage = programLauguage;
	}
	public Integer getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
	
}
