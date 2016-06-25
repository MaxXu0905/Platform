package com.ailk.sets.grade.grade.config;

import java.util.List;

import com.ailk.sets.grade.grade.common.GradeConst;

/**
 * 选项题配置管理
 * 
 * @author xugq
 * 
 */
public class QuestionConf {

	private String qbName; // 题库
	private String mode; // 标题编辑器
	private boolean html; // 标题、选项是否为HTML格式
	private String type; // 题型
	private int typeInt; // 题型编码
	private boolean sample; // 是否为例子
	private String executorClass; // 执行class
	private String title; // 标题
	private String summary; // 概述
	private int category; // 归类
	private int samples; // 测试用例个数
	private List<String> options; // 选项
	private Integer answer; // 答案数
	private String optAnswers; // 选项答案
	private String optDesc; // 选项说明
	private MatrixElement matrix; // 矩阵
	private List<ParamElement> params; // 参数

	/**
	 * 初始化后设置值
	 */
	public void postInit() {
		typeInt = GradeConst.toQuestionTypeInt(type);

		if (samples <= 0)
			samples = 1;

		if (params != null) {
			for (ParamElement param : params) {
				param.toType();
			}
		}
	}

	public String getQbName() {
		return qbName;
	}

	public void setQbName(String qbName) {
		this.qbName = qbName;
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

	public int getTypeInt() {
		return typeInt;
	}

	public void setTypeInt(int typeInt) {
		this.typeInt = typeInt;
	}

	public boolean isSample() {
		return sample;
	}

	public void setSample(boolean sample) {
		this.sample = sample;
	}

	public String getExecutorClass() {
		return executorClass;
	}

	public void setExecutorClass(String executorClass) {
		this.executorClass = executorClass;
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

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public String getOptAnswers() {
		return optAnswers;
	}

	public void setOptAnswers(String optAnswers) {
		this.optAnswers = optAnswers;
	}

	public String getOptDesc() {
		return optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

	public MatrixElement getMatrix() {
		return matrix;
	}

	public void setMatrix(MatrixElement matrix) {
		this.matrix = matrix;
	}

	public List<ParamElement> getParams() {
		return params;
	}

	public void setParams(List<ParamElement> params) {
		this.params = params;
	}

}
