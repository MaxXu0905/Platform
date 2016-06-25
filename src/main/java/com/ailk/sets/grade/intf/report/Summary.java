package com.ailk.sets.grade.intf.report;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Summary implements Serializable {

	private String name; // 姓名
	private String email; // E-mail
	private String portrait; // 头像
	private List<UserInfo> infos; // 用户基本信息
	private double score; // 得分
	private Double basicScore; // 基础得分
	private Double sysBasicScore; // 百一基础得分
	private Double userBasicScore; // 自定义基础得分
	private int breakTimes; // 中断次数
	private int switchTimes; // 切换次数
	private List<String> skills; // 相关标签
	private double[] scoreRanges; // 分值范围
	private List<Double> skillScores; // 实际掌握程度
	private List<Double> avgScores; // 平均掌握程度
	private List<String> intelSkills; // 相关标签
	private double[] intelScoreRanges; // 分值范围
	private List<Double> intelSkillScores; // 实际掌握程度
	private List<Double> intelAvgScores; // 平均掌握程度

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public List<UserInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<UserInfo> infos) {
		this.infos = infos;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Double getBasicScore() {
		return basicScore;
	}

	public void setBasicScore(Double basicScore) {
		this.basicScore = basicScore;
	}

	public Double getSysBasicScore() {
		return sysBasicScore;
	}

	public void setSysBasicScore(Double sysBasicScore) {
		this.sysBasicScore = sysBasicScore;
	}

	public Double getUserBasicScore() {
		return userBasicScore;
	}

	public void setUserBasicScore(Double userBasicScore) {
		this.userBasicScore = userBasicScore;
	}

	public int getBreakTimes() {
		return breakTimes;
	}

	public void setBreakTimes(int breakTimes) {
		this.breakTimes = breakTimes;
	}

	public int getSwitchTimes() {
		return switchTimes;
	}

	public void setSwitchTimes(int switchTimes) {
		this.switchTimes = switchTimes;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public double[] getScoreRanges() {
		return scoreRanges;
	}

	public void setScoreRanges(double[] scoreRanges) {
		this.scoreRanges = scoreRanges;
	}

	public List<Double> getSkillScores() {
		return skillScores;
	}

	public void setSkillScores(List<Double> skillScores) {
		this.skillScores = skillScores;
	}

	public List<Double> getAvgScores() {
		return avgScores;
	}

	public void setAvgScores(List<Double> avgScores) {
		this.avgScores = avgScores;
	}

	public List<String> getIntelSkills() {
		return intelSkills;
	}

	public void setIntelSkills(List<String> intelSkills) {
		this.intelSkills = intelSkills;
	}

	public double[] getIntelScoreRanges() {
		return intelScoreRanges;
	}

	public void setIntelScoreRanges(double[] intelScoreRanges) {
		this.intelScoreRanges = intelScoreRanges;
	}

	public List<Double> getIntelSkillScores() {
		return intelSkillScores;
	}

	public void setIntelSkillScores(List<Double> intelSkillScores) {
		this.intelSkillScores = intelSkillScores;
	}

	public List<Double> getIntelAvgScores() {
		return intelAvgScores;
	}

	public void setIntelAvgScores(List<Double> intelAvgScores) {
		this.intelAvgScores = intelAvgScores;
	}

}