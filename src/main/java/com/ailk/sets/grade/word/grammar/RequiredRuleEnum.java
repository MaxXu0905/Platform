package com.ailk.sets.grade.word.grammar;

/**
 * 必填项
 * 
 * @author xugq
 *
 */
public enum RequiredRuleEnum {

	DESCRIPTION("【设置】为必填项"), PAPER_NAME("【试卷名称】为必填项"), DESC_TIME(
			"【限时】为必填项"), DESC_POINT("【分值】为必填项"), SKILL_REORDER("【考点乱序】为必填项"), QUESTION_REORDER(
			"【题目乱序】为必填项"), CHOICE_REORDER("【选项乱序】为必填项"), BACKWARD("【可回溯】为必填项");

	private String message;

	private RequiredRuleEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
