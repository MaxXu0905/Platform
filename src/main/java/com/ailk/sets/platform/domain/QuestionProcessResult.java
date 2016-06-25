package com.ailk.sets.platform.domain;

import java.util.List;

import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;

public class QuestionProcessResult {
	private int sumSuggestTime;
	private List<PaperQuestionToSkills> questions;

	public int getSumSuggestTime() {
		return sumSuggestTime;
	}

	public void setSumSuggestTime(int sumSuggestTime) {
		this.sumSuggestTime = sumSuggestTime;
	}

	public List<PaperQuestionToSkills> getQuestions() {
		return questions;
	}

	public void setQuestions(List<PaperQuestionToSkills> questions) {
		this.questions = questions;
	}
}
