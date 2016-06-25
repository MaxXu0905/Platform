package com.ailk.sets.grade.grade.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class QuestionContent implements Serializable {

	private QuestionConf questionConf;
	private ExtraConf extraConf;
	private Map<String, String> data;
	
	public QuestionContent() {
		data = new HashMap<String, String>();
	}
	
	public QuestionConf getQuestionConf() {
		return questionConf;
	}

	public void setQuestionConf(QuestionConf questionConf) {
		this.questionConf = questionConf;
	}

	public ExtraConf getExtraConf() {
		return extraConf;
	}

	public void setExtraConf(ExtraConf extraConf) {
		this.extraConf = extraConf;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
