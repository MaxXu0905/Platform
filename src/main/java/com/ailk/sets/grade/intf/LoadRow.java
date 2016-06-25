package com.ailk.sets.grade.intf;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class LoadRow implements Serializable {

	protected Integer serialNo; // 错误序列号
	protected String skill; // 技能
	protected String level; // 难度
	protected String suggestSeconds; // 答题时间（秒）
	protected String suggestMinutes; // 答题时间（分钟）
	protected String title; // 题目描述
	protected List<String> options; // 选项
	protected String correctOptions; // 正确选项
	protected String refAnswer; // 参考答案
	protected String explainReqired; // 需要补充解释
	protected String refExplain; // 补充解释的参考答案
	protected String mode; // 编程语言
	protected Boolean html; // 标题、选项是否为HTML格式

	public LoadRow() {
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getCorrectOptions() {
		return correctOptions;
	}

	public void setCorrectOptions(String correctOptions) {
		this.correctOptions = correctOptions;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSuggestSeconds() {
		return suggestSeconds;
	}

	public void setSuggestSeconds(String suggestSeconds) {
		this.suggestSeconds = suggestSeconds;
	}

	public String getSuggestMinutes() {
		return suggestMinutes;
	}

	public void setSuggestMinutes(String suggestMinutes) {
		this.suggestMinutes = suggestMinutes;
	}

	public String getRefAnswer() {
		return refAnswer;
	}

	public void setRefAnswer(String refAnswer) {
		this.refAnswer = refAnswer;
	}

	public String getExplainReqired() {
		return explainReqired;
	}

	public void setExplainReqired(String explainReqired) {
		this.explainReqired = explainReqired;
	}

	public String getRefExplain() {
		return refExplain;
	}

	public void setRefExplain(String refExplain) {
		this.refExplain = refExplain;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Boolean getHtml() {
		return html;
	}

	public void setHtml(Boolean html) {
		this.html = html;
	}

	@Override
	public String toString() {
		return "LoadRow [serialNo=" + serialNo + ", skill=" + skill + ", level=" + level + ", suggestSeconds="
				+ suggestSeconds + ", suggestMinutes=" + suggestMinutes + ", title=" + title + ", options=" + options
				+ ", correctOptions=" + correctOptions + ", refAnswer=" + refAnswer + ", explainReqired="
				+ explainReqired + ", refExplain=" + refExplain + ", mode=" + mode + ", html=" + html + "]";
	}

	
}
