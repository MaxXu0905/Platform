package com.ailk.sets.platform.intf.model.qb;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

public class DummyQbQuestion extends QbQuestion implements Serializable {
	private static final long serialVersionUID = -278615115229410269L;
	private String aDesc; // 参考答案描述
	private Integer difficulty; // 难度
	private List<String> choices;
	private Integer rightChoiceNum;

	public String getaDesc() {
		return aDesc;
	}

	public void setaDesc(String aDesc) {
		this.aDesc = aDesc;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
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

}
