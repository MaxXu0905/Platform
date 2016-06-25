package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;

public class PaperQuestionTypeInfo implements Serializable {
	private static final long serialVersionUID = 5497647350946644530L;
	private Integer questionNumber; //题目数
//	private Integer avgPoint; //平均分
	private String typeName;
	private String typeId;//typeId 前台根据这个显示不同样式


	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public PaperQuestionTypeInfo() {
	}
	
	

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Integer getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}
	/*public Integer getAvgPoint() {
		return avgPoint;
	}
	public void setAvgPoint(Integer avgPoint) {
		this.avgPoint = avgPoint;
	}*/
}
