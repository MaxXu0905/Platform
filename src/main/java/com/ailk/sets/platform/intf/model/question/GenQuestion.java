package com.ailk.sets.platform.intf.model.question;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.model.Question;

/**
 * 一般问题
 * 
 * @author 毕希研
 * 
 */
public class GenQuestion implements Question, Serializable {
	private static final long serialVersionUID = -3603012040826113687L;
	private Long questionId; // id，仅当brandNew是false时才有正确的值
	private Integer time; // 答题时长
	private String qDesc; // 问题描述
	private String aDesc; // 参考答案描述
	private Boolean brandNew; // 是否是新的附加题

	private List<String> choices;
	private String questionType; // 问题类型
	private Integer rightChoiceNum;
	private Integer point;

	@Override
	public Long getId() {
		return questionId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getqDesc() {
		return qDesc;
	}

	public void setqDesc(String qDesc) {
		this.qDesc = qDesc;
	}

	public String getaDesc() {
		return aDesc;
	}

	public void setaDesc(String aDesc) {
		this.aDesc = aDesc;
	}

	public Boolean getBrandNew() {
		return brandNew;
	}

	public void setBrandNew(Boolean brandNew) {
		this.brandNew = brandNew;
	}

	@Override
	public String getProgramLang() {
		return null;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	public Integer getRightChoiceNum() {
		return rightChoiceNum;
	}

	public void setRightChoiceNum(Integer rightChoiceNum) {
		this.rightChoiceNum = rightChoiceNum;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

}
