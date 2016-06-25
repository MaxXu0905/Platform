package com.ailk.sets.grade.service.intf;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class AnswerWrapper implements Serializable {

	private Map<String, String> data;
	private String optAnswer;
	private String optDesc;

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String getOptAnswer() {
		return optAnswer;
	}

	public void setOptAnswer(String optAnswer) {
		this.optAnswer = optAnswer;
	}

	public String getOptDesc() {
		return optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

	public String getAnswer(String filename) {
		if (data == null)
			return null;

		return data.get(filename);
	}

}
