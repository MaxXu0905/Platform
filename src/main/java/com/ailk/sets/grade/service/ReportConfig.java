package com.ailk.sets.grade.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.intf.report.IReportConfig;

@Service
public class ReportConfig implements IReportConfig {

	private final Map<Key, Integer> itemMap;

	public ReportConfig() {
		itemMap = new HashMap<Key, Integer>();

		// 技术基础
		Key key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_S_CHOICE);
		key.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_BASIC);

		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_M_CHOICE);
		key.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_BASIC);

		// 编程能力
		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_PROGRAM);
		key.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_PROGRAM);

		// 技术问答
		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_ESSAY);
		key.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_TECH_ESSAY);

		// 智力
		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_S_CHOICE);
		key.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_INTELLIGENCE);

		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_M_CHOICE);
		key.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_INTELLIGENCE);

		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_ESSAY);
		key.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_INTELLIGENCE);

		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_S_CHOICE_PLUS);
		key.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_INTELLIGENCE);

		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_M_CHOICE_PLUS);
		key.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_INTELLIGENCE);

		// 附加编程
		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_EXTRA_PROGRAM);
		key.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_PROGRAM);

		// 面试
		key = new Key();
		key.setQuestionType(GradeConst.QUESTION_TYPE_VIDEO);
		key.setCategory(GradeConst.CATEGORY_INTERVIEW);
		itemMap.put(key, IReportConfig.REPORT_COLUMN_QUALITY);
	}

	@Override
	public int getReportIndex(int questionType, int category) {
		Key key = new Key();
		key.setQuestionType(questionType);
		key.setCategory(category);

		Integer value = itemMap.get(key);
		if (value == null)
			return REPORT_COLUMN_UNKNOWN;
		else
			return value;
	}

	private static class Key {
		private int questionType;
		private int category;

		public void setQuestionType(int questionType) {
			this.questionType = questionType;
		}

		public void setCategory(int category) {
			this.category = category;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + category;
			result = prime * result + questionType;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (category != other.category)
				return false;
			if (questionType != other.questionType)
				return false;
			return true;
		}
	}

}
