package com.ailk.sets.grade.intf.report;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PartItem implements Serializable {

	private long questionId; // 试题ID
	private int questionType; // 题目类型
	private boolean readonly; // 是否只读（不能修改分值)
	private boolean html; // 标题、选项是否为HTML格式
	private Double score; // 得分
	private String title; // 标题
	private List<String> options; // 选择题选项
	private String optRefAnswer; // 选择题参考答案
	private String optAnswer; // 选择题正确答案
	private String answer; // 答案
	private String refAnswer; // 参考答案
	private String mode; // 模式
	private int answerTime; // 答题时间
	private int avgAnswerTime; // 平均答题时间
	private boolean prebuilt; // 是否内部题库
	private String skillName; // 技能名称

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
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

	public String getOptAnswer() {
		return optAnswer;
	}

	public void setOptAnswer(String optAnswer) {
		this.optAnswer = optAnswer;
	}

	public String getOptRefAnswer() {
		return optRefAnswer;
	}

	public void setOptRefAnswer(String optRefAnswer) {
		this.optRefAnswer = optRefAnswer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getRefAnswer() {
		return refAnswer;
	}

	public void setRefAnswer(String refAnswer) {
		this.refAnswer = refAnswer;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(int answerTime) {
		this.answerTime = answerTime;
	}

	public int getAvgAnswerTime() {
		return avgAnswerTime;
	}

	public void setAvgAnswerTime(int avgAnswerTime) {
		this.avgAnswerTime = avgAnswerTime;
	}

	public boolean isPrebuilt() {
		return prebuilt;
	}

	public void setPrebuilt(boolean prebuilt) {
		this.prebuilt = prebuilt;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

}
