package com.ailk.sets.platform.intf.model.question;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.ailk.sets.platform.intf.model.Question;

public class InterviewGroup implements Question, Serializable {
	private static final long serialVersionUID = -3823580222787345723L;
	private Collection<Interview> qbQuestion; // 该职位的面试题列表
	private Boolean brandNew; // 面试题组是否有变化
	private Long questionId; // 面试题组id
	private String questionType; // 问题类型
	private Integer point;

	public Collection<Interview> getQbQuestion() {
		return qbQuestion;
	}

	public void setQbQuestion(Collection<Interview> qbQuestion) {
		this.qbQuestion = qbQuestion;
	}

	public Boolean getBrandNew() {
		return brandNew;
	}

	public void setBrandNew(Boolean brandNew) {
		this.brandNew = brandNew;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	@Override
	public Long getId() {
		return questionId;
	}

	@Override
	public String getProgramLang() {
		return null;
	}

	@Override
	public Integer getTime() {
		return null;
	}

	@Override
	public String getaDesc() {
		return null;
	}

	@Override
	public String getqDesc() {
		return null;
	}

	@Override
	public String getQuestionType() {
		return questionType;
	}

	@Override
	public List<String> getChoices() {
		return null;
	}

	@Override
	public Integer getRightChoiceNum() {
		return null;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

}
