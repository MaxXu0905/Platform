package com.ailk.sets.grade.excel;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ExcelConf implements Serializable {

	public static final String[] COLUMN_NAMES = { "sn", "mode", "html", "type",
			"title", "summary", "code", "option", "level", "param", "data",
			"tag", "answer", "include", "exclude", "sample", "status",
			"Responses", "Wrong Ratio", "suggest_time", "score", "category",
			"testSamples", "技能", "难度", "作答时长\n（秒）", "作答时长\n（分钟）", "题目描述", "选项",
			"正确选项", "参考答案", "需要补充解释", "补充解释的参考答案", "编程语言", "面试题组" };
	public static final int COLUMN_ID = 0;
	public static final int COLUMN_MODE = 1;
	public static final int COLUMN_HTML = 2;
	public static final int COLUMN_TYPE = 3;
	public static final int COLUMN_TITLE = 4;
	public static final int COLUMN_SUMMARY = 5;
	public static final int COLUMN_CODE = 6;
	public static final int COLUMN_OPTION = 7;
	public static final int COLUMN_LEVEL = 8;
	public static final int COLUMN_PARAM = 9;
	public static final int COLUMN_DATA = 10;
	public static final int COLUMN_TAG = 11;
	public static final int COLUMN_ANSWER = 12;
	public static final int COLUMN_INCLUDE = 13;
	public static final int COLUMN_EXCLUDE = 14;
	public static final int COLUMN_SAMPLE = 15;
	public static final int COLUMN_STATUS = 16;
	public static final int COLUMN_ANSWER_NUM = 17;
	public static final int COLUMN_FAIL_PERCENT = 18;
	public static final int COLUMN_SUGGEST_TIME = 19;
	public static final int COLUMN_SCORE = 20;
	public static final int COLUMN_CATEGORY = 21;
	public static final int COLUMN_SAMPLES = 22;
	public static final int COLUMN_TEMPLATE_SKILL = 23;
	public static final int COLUMN_TEMPLATE_LEVEL = 24;
	public static final int COLUMN_TEMPLATE_SUGGEST_SECONDS = 25;
	public static final int COLUMN_TEMPLATE_SUGGEST_MINUTES = 26;
	public static final int COLUMN_TEMPLATE_TITLE = 27;
	public static final int COLUMN_TEMPLATE_OPTION = 28;
	public static final int COLUMN_TEMPLATE_CORRECT_OPTIONS = 29;
	public static final int COLUMN_TEMPLATE_REF_ANSWER = 30;
	public static final int COLUMN_TEMPLATE_EXPLAIN_REQUIRED = 31;
	public static final int COLUMN_TEMPLATE_REF_EXPLAIN = 32;
	public static final int COLUMN_TEMPLATE_MODE = 33;
	public static final int COLUMN_TEMPLATE_GROUP = 34;

	private long id; // 题ID
	private String mode; // 模式
	private boolean html; // 标题、选项是否为html格式
	private String type; // 题类型
	private String title; // 题标题
	private String summary; // 题概述
	private String code; // 代码
	private List<String> options; // 选项列表
	private int level; // 级别
	private String param; // 参数
	private String data; // 数据
	private List<String> tags; // 标签列表
	private int answer; // 答案个数
	private String include; // 需要包含的关键字
	private String exclude; // 需要排除的关键字
	private boolean sample; // 是否为样例
	private int status; // 状态
	private int answerNum; // 答题总人数
	private int failPercent; // 失败百分百
	private int suggestTime; // 时间限制
	private int score; // 分值
	private int category; // 归类
	private int samples; // 测试用例个数
	private String correctOptions; // 正确选项
	private boolean explainRequired; // 需要解释
	private String group; // 组

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public boolean isSample() {
		return sample;
	}

	public void setSample(boolean sample) {
		this.sample = sample;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}

	public int getFailPercent() {
		return failPercent;
	}

	public void setFailPercent(int failPercent) {
		this.failPercent = failPercent;
	}

	public int getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(int suggestTime) {
		this.suggestTime = suggestTime;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSamples() {
		return samples;
	}

	public void setSamples(int samples) {
		this.samples = samples;
	}

	public String getCorrectOptions() {
		return correctOptions;
	}

	public void setCorrectOptions(String correctOptions) {
		this.correctOptions = correctOptions;
	}

	public boolean isExplainRequired() {
		return explainRequired;
	}

	public void setExplainRequired(boolean explainRequired) {
		this.explainRequired = explainRequired;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
