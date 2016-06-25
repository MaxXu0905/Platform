package com.ailk.sets.grade.word;

import java.util.ArrayList;
import java.util.List;

import com.ailk.sets.grade.grade.common.GradeConst;

public class PaperWord {

	public static final int PART_ID_TECH_CHOICE = 1;
	public static final int PART_ID_PROGRAM = 2;
	public static final int PART_ID_TECH_ESSAY = 3;
	public static final int PART_ID_INTEL = 4;
	public static final int PART_ID_INTERVIEW = 5;

	private String paperName; // 试卷名称
	private String paperDesc; // 试卷简介
	private int timeLimit; // 限时
	private List<Part> parts; // 部分列表
	private int errors; // 错误数

	public PaperWord() {
		errors = 0;
	}

	public void add(Part part) {
		if (parts == null)
			parts = new ArrayList<PaperWord.Part>();

		parts.add(part);
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getPaperDesc() {
		return paperDesc;
	}

	public void setPaperDesc(String paperDesc) {
		this.paperDesc = paperDesc;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "PaperWord [paperName=" + paperName + ", paperDesc=" + paperDesc
				+ ", timeLimit=" + timeLimit + ", parts=" + parts + ", errors="
				+ errors + "]";
	}

	public static class Part {
		private String partName; // 部分名称
		private int partId; // 部分id
		private int category; // 分类
		private int questionType; // 题型
		private int point; // 分值
		private int timeLimit; // 限时
		private boolean skillReorder; // 技能乱序
		private boolean questionReorder; // 题目乱序
		private boolean choiceReorder; // 选项乱序
		private boolean backword; // 是否可回溯
		private List<Skill> skills; // 技能部分列表
		private int questions; // 题目数

		public void add(Skill skill) {
			if (skills == null)
				skills = new ArrayList<PaperWord.Skill>();

			skills.add(skill);
		}

		public void inherit(PaperWord paperWord) {
		}

		public String getPartName() {
			return partName;
		}

		public void setPartName(String partName) {
			this.partName = partName;
		}

		public int getPartId() {
			return partId;
		}

		public void setPartId(int partId) {
			this.partId = partId;
		}

		public int getCategory() {
			return category;
		}

		public void setCategory(int category) {
			this.category = category;
		}

		public int getQuestionType() {
			return questionType;
		}

		public void setQuestionType(int questionType) {
			this.questionType = questionType;
		}

		public int getPoint() {
			return point;
		}

		public void setPoint(int point) {
			this.point = point;
		}

		public int getTimeLimit() {
			return timeLimit;
		}

		public void setTimeLimit(int timeLimit) {
			this.timeLimit = timeLimit;
		}

		public boolean isSkillReorder() {
			return skillReorder;
		}

		public void setSkillReorder(boolean skillReorder) {
			this.skillReorder = skillReorder;
		}

		public boolean isQuestionReorder() {
			return questionReorder;
		}

		public void setQuestionReorder(boolean questionReorder) {
			this.questionReorder = questionReorder;
		}

		public boolean isChoiceReorder() {
			return choiceReorder;
		}

		public void setChoiceReorder(boolean choiceReorder) {
			this.choiceReorder = choiceReorder;
		}

		public boolean isBackword() {
			return backword;
		}

		public void setBackword(boolean backword) {
			this.backword = backword;
		}

		public List<Skill> getSkills() {
			return skills;
		}

		public void setSkills(List<Skill> skills) {
			this.skills = skills;
		}

		public int getQuestions() {
			return questions;
		}

		public void setQuestions(int questions) {
			this.questions = questions;
		}

		@Override
		public String toString() {
			return "Part [partName=" + partName + ", partId=" + partId
					+ ", category=" + category + ", questionType="
					+ questionType + ", point=" + point + ", timeLimit="
					+ timeLimit + ", skillReorder=" + skillReorder
					+ ", questionReorder=" + questionReorder
					+ ", choiceReorder=" + choiceReorder + ", backword="
					+ backword + ", skills=" + skills + ", questions="
					+ questions + "]";
		}
	}

	public static class Skill {
		private int partId; // 部分id
		private String skillName; // 技能名称
		private int point; // 分值
		private int timeLimit; // 限时
		private int questionType; // 题型
		private List<Question> questions; // 题目列表

		public Skill(int partId) {
			this.partId = partId;
			questionType = GradeConst.QUESTION_TYPE_UNKNOWN;
		}

		public void add(Question question) {
			if (questions == null)
				questions = new ArrayList<PaperWord.Question>();

			questions.add(question);
		}

		public void inherit(Part part) {
			if (point == 0)
				point = part.getPoint();
			questionType = part.getQuestionType();
		}

		public int getPartId() {
			return partId;
		}

		public void setPartId(int partId) {
			this.partId = partId;
		}

		public String getSkillName() {
			return skillName;
		}

		public void setSkillName(String skillName) {
			this.skillName = skillName;
		}

		public int getPoint() {
			return point;
		}

		public void setPoint(int point) {
			this.point = point;
		}

		public int getTimeLimit() {
			return timeLimit;
		}

		public void setTimeLimit(int timeLimit) {
			this.timeLimit = timeLimit;
		}

		public int getQuestionType() {
			return questionType;
		}

		public void setQuestionType(int questionType) {
			this.questionType = questionType;
		}

		public List<Question> getQuestions() {
			return questions;
		}

		public void setQuestions(List<Question> questions) {
			this.questions = questions;
		}

		@Override
		public String toString() {
			return "Skill [partId=" + partId + ", skillName=" + skillName
					+ ", point=" + point + ", timeLimit=" + timeLimit
					+ ", questionType=" + questionType + ", questions="
					+ questions + "]";
		}
	}

	public static class Question {
		private String title; // 标题
		private int questionType; // 题型
		private int programLanguage; // 标题编程语言
		private String skillName; // 技能名称
		private List<String> options; // 选项
		private String optAnswer; // 选择答案
		private String textAnswer; // 文本答案
		private int point; // 分值
		private int difficulty; // 难度
		private boolean sample; // 是否样例
		private int timeLimit; // 时间限制
		private int endLines; // 结尾空行数
		private List<Question> subQuestions; // 子题目列表

		public Question() {
		}

		public void add(Question question) {
			if (subQuestions == null)
				subQuestions = new ArrayList<PaperWord.Question>();

			subQuestions.add(question);
		}

		public void inherit(Skill skill) {
			if (point == 0)
				point = skill.getPoint();
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getQuestionType() {
			return questionType;
		}

		public void setQuestionType(int questionType) {
			this.questionType = questionType;
		}

		public int getProgramLanguage() {
			return programLanguage;
		}

		public void setProgramLanguage(int programLanguage) {
			this.programLanguage = programLanguage;
		}

		public String getSkillName() {
			return skillName;
		}

		public void setSkillName(String skillName) {
			this.skillName = skillName;
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

		public String getTextAnswer() {
			return textAnswer;
		}

		public void setTextAnswer(String textAnswer) {
			this.textAnswer = textAnswer;
		}

		public int getPoint() {
			return point;
		}

		public void setPoint(int point) {
			this.point = point;
		}

		public int getDifficulty() {
			return difficulty;
		}

		public void setDifficulty(int difficulty) {
			this.difficulty = difficulty;
		}

		public boolean isSample() {
			return sample;
		}

		public void setSample(boolean sample) {
			this.sample = sample;
		}

		public int getTimeLimit() {
			return timeLimit;
		}

		public void setTimeLimit(int timeLimit) {
			this.timeLimit = timeLimit;
		}

		public int getEndLines() {
			return endLines;
		}

		public void setEndLines(int endLines) {
			this.endLines = endLines;
		}

		public List<Question> getSubQuestions() {
			return subQuestions;
		}

		public void setSubQuestions(List<Question> subQuestions) {
			this.subQuestions = subQuestions;
		}

		@Override
		public String toString() {
			return "Question [title=" + title + ", questionType="
					+ questionType + ", programLanguage=" + programLanguage
					+ ", skillName=" + skillName + ", options=" + options
					+ ", optAnswer=" + optAnswer + ", textAnswer=" + textAnswer
					+ ", point=" + point + ", difficulty=" + difficulty
					+ ", sample=" + sample + ", timeLimit=" + timeLimit
					+ ", endLines=" + endLines + ", subQuestions="
					+ subQuestions + "]";
		}
	}

}
