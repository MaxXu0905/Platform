package com.ailk.sets.platform.intf.empl.domain;

import java.util.List;

import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.intf.model.position.PosResponse;

public class PositionPaperAnalysisResult extends PosResponse{
	private static final long serialVersionUID = -1096403655233126608L;
	private List<String> positionSkills;
	private List<PaperQuestionToSkills> questions;
	private List<PaperPart>  paperParts;
	public List<String> getPositionSkills() {
		return positionSkills;
	}
	public void setPositionSkills(List<String> positionSkills) {
		this.positionSkills = positionSkills;
	}
	public List<PaperQuestionToSkills> getQuestions() {
		return questions;
	}
	public void setQuestions(List<PaperQuestionToSkills> questions) {
		this.questions = questions;
	}
	public List<PaperPart> getPaperParts() {
		return paperParts;
	}
	public void setPaperParts(List<PaperPart> paperParts) {
		this.paperParts = paperParts;
	}
}
