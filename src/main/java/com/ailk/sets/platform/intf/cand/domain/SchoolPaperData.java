package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;
import java.util.List;

public class SchoolPaperData implements Serializable {
	private static final long serialVersionUID = -2855048971865692080L;
	private String timerType;
	private CandidateExamInfo examInfo;
	private List<PaperPartData> partDatas;
	private List<SchoolPaperSkillCount> skillCountInfos;
	private Long testId;
	private String passport;

	public List<SchoolPaperSkillCount> getSkillCountInfos() {
		return skillCountInfos;
	}

	public void setSkillCountInfos(List<SchoolPaperSkillCount> skillCountInfos) {
		this.skillCountInfos = skillCountInfos;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public List<PaperPartData> getPartDatas() {
		return partDatas;
	}

	public void setPartDatas(List<PaperPartData> partDatas) {
		this.partDatas = partDatas;
	}

	public String getTimerType() {
		return timerType;
	}

	public void setTimerType(String timerType) {
		this.timerType = timerType;
	}

	public CandidateExamInfo getExamInfo() {
		return examInfo;
	}

	public void setExamInfo(CandidateExamInfo examInfo) {
		this.examInfo = examInfo;
	}

}
