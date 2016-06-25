package com.ailk.sets.grade.intf.report;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Completion implements Serializable {

	private List<Double> skillRatios; // // skill比率
	private List<Integer> skillTimes; // skill完成时间
	private List<Integer> avgSkillTimes; // skill平均时间
	private int spentTime; // 答题总时间
	private List<Double> intelSkillRatios; // // skill比率
	private List<Integer> intelSkillTimes; // skill完成时间
	private List<Integer> intelAvgSkillTimes; // skill平均时间
	private int intelSpentTime; // 答题总时间

	public List<Double> getSkillRatios() {
		return skillRatios;
	}

	public void setSkillRatios(List<Double> skillRatios) {
		this.skillRatios = skillRatios;
	}

	public List<Integer> getSkillTimes() {
		return skillTimes;
	}

	public void setSkillTimes(List<Integer> skillTimes) {
		this.skillTimes = skillTimes;
	}

	public List<Integer> getAvgSkillTimes() {
		return avgSkillTimes;
	}

	public void setAvgSkillTimes(List<Integer> avgSkillTimes) {
		this.avgSkillTimes = avgSkillTimes;
	}

	public int getSpentTime() {
		return spentTime;
	}

	public void setSpentTime(int spentTime) {
		this.spentTime = spentTime;
	}

	public List<Double> getIntelSkillRatios() {
		return intelSkillRatios;
	}

	public void setIntelSkillRatios(List<Double> intelSkillRatios) {
		this.intelSkillRatios = intelSkillRatios;
	}

	public List<Integer> getIntelSkillTimes() {
		return intelSkillTimes;
	}

	public void setIntelSkillTimes(List<Integer> intelSkillTimes) {
		this.intelSkillTimes = intelSkillTimes;
	}

	public List<Integer> getIntelAvgSkillTimes() {
		return intelAvgSkillTimes;
	}

	public void setIntelAvgSkillTimes(List<Integer> intelAvgSkillTimes) {
		this.intelAvgSkillTimes = intelAvgSkillTimes;
	}

	public int getIntelSpentTime() {
		return intelSpentTime;
	}

	public void setIntelSpentTime(int intelSpentTime) {
		this.intelSpentTime = intelSpentTime;
	}

}