package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.List;

public class PaperQuestionToSkills implements Serializable{
	private static final long serialVersionUID = 6816364246139468474L;
	private Long questionId; //题目id
	private List<String> skillIds;//通过哪些标签选出
	private Integer questionSeq;
	private Integer moti;
    private Integer degree;//难度，编程题需要根据难度排序，增加此字段
	
	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public PaperQuestionToSkills(){
		this.moti = 0;
	}
	
	public Integer getMoti() {
		return moti;
	}
	public void setMoti(Integer moti) {
		this.moti = moti;
	}
	public Integer getQuestionSeq() {
		return questionSeq;
	}
	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}
	public Long getQuestionId() {
		return questionId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		return result;
	}

	/**
	 * 重写equals方法，根据questionid，用于删除
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaperQuestionToSkills other = (PaperQuestionToSkills) obj;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		return true;
	}


	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public List<String> getSkillIds() {
		return skillIds;
	}

	public void setSkillIds(List<String> skillIds) {
		this.skillIds = skillIds;
	}
}
