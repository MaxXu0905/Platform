package com.ailk.sets.platform.domain;

import java.util.List;

import com.ailk.sets.platform.domain.skilllabel.PaperSkillUnknown;

public class PositionAnalysisResult {
	private List<DegreeToSkillLabels> degreeToSkillLableses;
	private List<PaperSkillUnknown> unknowns;
	
	public List<DegreeToSkillLabels> getDegreeToSkillLableses() {
		return degreeToSkillLableses;
	}
	public void setDegreeToSkillLableses(List<DegreeToSkillLabels> degreeToSkillLableses) {
		this.degreeToSkillLableses = degreeToSkillLableses;
	}
	public List<PaperSkillUnknown> getUnknowns() {
		return unknowns;
	}
	public void setUnknowns(List<PaperSkillUnknown> unknowns) {
		this.unknowns = unknowns;
	}
}
