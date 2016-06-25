package com.ailk.sets.platform.intf.model.param;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.model.Question;

public class RandomQuestionParam implements Serializable{
	private static final long serialVersionUID = 2212135223732909791L;
	private String category;
	private Position position;
	private Question question;
	private List<String> skillIds;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<String> getSkillIds() {
		return skillIds;
	}

	public void setSkillIds(List<String> skillIds) {
		this.skillIds = skillIds;
	}

}
