package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.List;

public class PaperSubjectModelInfo extends PaperModelPart implements Serializable {
	private static final long serialVersionUID = -1571976114000573940L;
	
	private List<PaperSubjectModelQuestion> questions;

	public List<PaperSubjectModelQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<PaperSubjectModelQuestion> questions) {
		this.questions = questions;
	}
}