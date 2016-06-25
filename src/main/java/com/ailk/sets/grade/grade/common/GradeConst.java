package com.ailk.sets.grade.grade.common;

import com.ailk.sets.grade.excel.FormatException;

public class GradeConst {

	public static final int MODE_UNKNOWN = 0;
	public static final int MODE_JAVA = 1;
	public static final int MODE_JS = 2;
	public static final int MODE_SHELL = 3;
	public static final int MODE_SQL = 4;
	public static final int MODE_HTML = 5;
	public static final int MODE_PHP = 6;
	public static final int MODE_CPP = 7;
	public static final int MODE_C = 8;
	public static final int MODE_OBJECTIVE_C = 9;
	public static final int MODE_PYTHON = 10;
	public static final int MODE_RHTML = 11;

	public static final int STAGE_REFERENCE = 0; // 参考答案
	public static final int STAGE_CANDIDATE = 1; // 候选人答案

	public static final String MODE_NAME_JAVA = "JAVA";
	public static final String MODE_NAME_JS = "JavaScript";
	public static final String MODE_NAME_SHELL = "SHELL";
	public static final String MODE_NAME_SQL = "SQL";
	public static final String MODE_NAME_HTML = "HTML"; // 以文本格式展示（应为TEXT，但由于历史原因，需要修改很多文件）
	public static final String MODE_NAME_PHP = "PHP";
	public static final String MODE_NAME_CPP = "C++";
	public static final String MODE_NAME_C = "C";
	public static final String MODE_NAME_OBJECTIVE_C = "ObjectiveC";
	public static final String MODE_NAME_PYTHON = "Python";

	public static final String GRADE_LOG4J_PREFIX = "log4j:";
	public static final String GRADE_RESULT_BEGIN = "[[[[begin]]]]";
	public static final String GRADE_RESULT_END = "[[[[end]]]]";
	public static final String GRADE_RESULT_ELAPSED = "[[[[elapsed=";
	public static final String GRADE_RESULT_MEM_BYTES = "[[[[memBytes=";

	public static final String QUESTION_TYPE_NAME_S_CHOICE = "s_choice"; // 单选
	public static final String QUESTION_TYPE_NAME_M_CHOICE = "m_choice"; // 多选
	public static final String QUESTION_TYPE_NAME_PROGRAM = "program"; // 编程
	public static final String QUESTION_TYPE_NAME_EXTRA_PROGRAM = "extra_program"; // 文本
	public static final String QUESTION_TYPE_NAME_ESSAY = "essay"; // 问答题
	public static final String QUESTION_TYPE_NAME_VIDEO = "video"; // 视频
	public static final String QUESTION_TYPE_NAME_S_CHOICE_PLUS = "s_choice_plus"; // 单选+文本
	public static final String QUESTION_TYPE_NAME_M_CHOICE_PLUS = "m_choice_plus"; // 多选+文本
	public static final String QUESTION_TYPE_NAME_GROUP = "group"; // 题组

	public static final int QUESTION_TYPE_UNKNOWN = -1; // 未知
	public static final int QUESTION_TYPE_S_CHOICE = 1; // 单选
	public static final int QUESTION_TYPE_M_CHOICE = 2; // 多选
	public static final int QUESTION_TYPE_PROGRAM = 3; // 编程
	public static final int QUESTION_TYPE_EXTRA_PROGRAM = 4; // 附加编程
	public static final int QUESTION_TYPE_ESSAY = 5; // 问答题
	public static final int QUESTION_TYPE_VIDEO = 6; // 视频
	public static final int QUESTION_TYPE_S_CHOICE_PLUS = 7; // 单选+文本
	public static final int QUESTION_TYPE_M_CHOICE_PLUS = 8; // 多选+文本
	public static final int QUESTION_TYPE_GROUP = 9; // 题组

	public static final int CATEGORY_TECHNOLOGY = 1; // 技术类
	public static final int CATEGORY_INTELLIGENCE = 3; // 智力类
	public static final int CATEGORY_INTERVIEW = 4; // 面试类
	public static final int CATEGORY_PAPER = 5; // 试卷类

	public static final int EDIT_TYPE_S_CHOICE = 1;
	public static final int EDIT_TYPE_M_CHOICE = 2;
	public static final int EDIT_TYPE_CODE_MIRROR = 3;
	public static final int EDIT_TYPE_TEXT = 4;
	public static final int EDIT_TYPE_S_CHOICE_TEXT = 5;
	public static final int EDIT_TYPE_M_CHOICE_TEXT = 6;
	public static final int EDIT_TYPE_VIDEO = 7;

	public static final int TEST_TYPE_COMMUNITY = 1; // 社招
	public static final int TEST_TYPE_CAMPUS = 2; // 校招

	public static final int MAX_FILE_ROWS = 100000; // 一个excel文件中的最大行数
	public static final String JAVA_QID_PREFIX = "10704000";

	public static final String ENCODING = "UTF-8";

	public static final String INTERVIEW_ID = "INTERVIEW_ROUND1";
	public static final String INTERVIEW_ITEM = "INTERVIEW_ITEM"; // 面试评分

	public static final String GRADE_NAMESPACE_PATH = "/com/ailk/";
	public static final int GRADE_INTERNAL_SCORE = 10; // 内部计分时的总分值
	public static final String CANDIDATE_PREFIX = "/candidate";
	public static final String REFERENCE_PREFIX = "/reference";

	// 对应QUESTION_TYPE
	public static final int[] PART_IDS = { 0, 1, 2, 3, 4, 5, 4, 4 };
	public static final String[] PART_NAMES = { "", "单选题", "多选题", "编程题", "附加题",
			"面试题" };

	public static final double SCORE_SCALE = 10.0;
	public static final double PERCENT_SCALE = 100.0;
	public static final double AVG_SCALE = SCORE_SCALE / 2.0;

	public static final String DES_KEY = "DhxSuvEI5aE=";

	public static final int CREATE_BY_SYS = 1;

	public static int toModeInt(String mode) {
		if (mode == null)
			return MODE_UNKNOWN;

		if (mode.equalsIgnoreCase(MODE_NAME_JAVA))
			return MODE_JAVA;
		else if (mode.equalsIgnoreCase(MODE_NAME_JS))
			return MODE_JS;
		else if (mode.equalsIgnoreCase(MODE_NAME_SHELL))
			return MODE_SHELL;
		else if (mode.equalsIgnoreCase(MODE_NAME_SQL))
			return MODE_SQL;
		else if (mode.equalsIgnoreCase(MODE_NAME_HTML))
			return MODE_HTML;
		else if (mode.equalsIgnoreCase(MODE_NAME_PHP))
			return MODE_PHP;
		else if (mode.equalsIgnoreCase(MODE_NAME_CPP))
			return MODE_CPP;
		else if (mode.equalsIgnoreCase(MODE_NAME_C))
			return MODE_C;
		else if (mode.equalsIgnoreCase(MODE_NAME_OBJECTIVE_C))
			return MODE_OBJECTIVE_C;
		else if (mode.equalsIgnoreCase(MODE_NAME_PYTHON))
			return MODE_PYTHON;
		else
			return MODE_UNKNOWN;
	}

	public static String standardMode(String mode) {
		if (mode == null)
			return null;

		if (mode.equalsIgnoreCase(MODE_NAME_JAVA))
			return MODE_NAME_JAVA;
		else if (mode.equalsIgnoreCase(MODE_NAME_JS))
			return MODE_NAME_JS;
		else if (mode.equalsIgnoreCase(MODE_NAME_SHELL))
			return MODE_NAME_SHELL;
		else if (mode.equalsIgnoreCase(MODE_NAME_SQL))
			return MODE_NAME_SQL;
		else if (mode.equalsIgnoreCase(MODE_NAME_HTML))
			return MODE_NAME_HTML;
		else if (mode.equalsIgnoreCase(MODE_NAME_PHP))
			return MODE_NAME_PHP;
		else if (mode.equalsIgnoreCase(MODE_NAME_CPP))
			return MODE_NAME_CPP;
		else if (mode.equalsIgnoreCase(MODE_NAME_C))
			return MODE_NAME_C;
		else if (mode.equalsIgnoreCase(MODE_NAME_OBJECTIVE_C))
			return MODE_NAME_OBJECTIVE_C;
		else if (mode.equalsIgnoreCase(MODE_NAME_PYTHON))
			return MODE_NAME_PYTHON;
		else
			return mode;
	}
	
	public static String toModeStr(int mode) {
		switch (mode) {
		case MODE_JAVA:
			return MODE_NAME_JAVA;
		case MODE_JS:
			return MODE_NAME_JS;
		case MODE_SHELL:
			return MODE_NAME_SHELL;
		case MODE_SQL:
			return MODE_NAME_SQL;
		case MODE_HTML:
			return MODE_NAME_HTML;
		case MODE_PHP:
			return MODE_NAME_PHP;
		case MODE_CPP:
			return MODE_NAME_CPP;
		case MODE_C:
			return MODE_NAME_C;
		case MODE_OBJECTIVE_C:
			return MODE_NAME_OBJECTIVE_C;
		case MODE_PYTHON:
			return MODE_NAME_PYTHON;
		default:
			return null;
		}
	}

	public static int toQuestionTypeInt(String questionType) {
		if (questionType.equalsIgnoreCase(QUESTION_TYPE_NAME_S_CHOICE))
			return QUESTION_TYPE_S_CHOICE;
		else if (questionType.equalsIgnoreCase(QUESTION_TYPE_NAME_M_CHOICE))
			return QUESTION_TYPE_M_CHOICE;
		else if (questionType.equalsIgnoreCase(QUESTION_TYPE_NAME_PROGRAM))
			return QUESTION_TYPE_PROGRAM;
		else if (questionType
				.equalsIgnoreCase(QUESTION_TYPE_NAME_EXTRA_PROGRAM))
			return QUESTION_TYPE_EXTRA_PROGRAM;
		else if (questionType.equalsIgnoreCase(QUESTION_TYPE_NAME_ESSAY))
			return QUESTION_TYPE_ESSAY;
		else if (questionType.equalsIgnoreCase(QUESTION_TYPE_NAME_VIDEO))
			return QUESTION_TYPE_VIDEO;
		else if (questionType
				.equalsIgnoreCase(QUESTION_TYPE_NAME_S_CHOICE_PLUS))
			return QUESTION_TYPE_S_CHOICE_PLUS;
		else if (questionType
				.equalsIgnoreCase(QUESTION_TYPE_NAME_M_CHOICE_PLUS))
			return QUESTION_TYPE_M_CHOICE_PLUS;
		else if (questionType.equalsIgnoreCase(QUESTION_TYPE_NAME_GROUP))
			return QUESTION_TYPE_GROUP;
		else
			return QUESTION_TYPE_UNKNOWN;
	}

	public static String toQuestionTypeString(int questionType) {
		switch (questionType) {
		case QUESTION_TYPE_S_CHOICE:
			return QUESTION_TYPE_NAME_S_CHOICE;
		case QUESTION_TYPE_M_CHOICE:
			return QUESTION_TYPE_NAME_M_CHOICE;
		case QUESTION_TYPE_PROGRAM:
			return QUESTION_TYPE_NAME_PROGRAM;
		case QUESTION_TYPE_EXTRA_PROGRAM:
			return QUESTION_TYPE_NAME_EXTRA_PROGRAM;
		case QUESTION_TYPE_ESSAY:
			return QUESTION_TYPE_NAME_ESSAY;
		case QUESTION_TYPE_VIDEO:
			return QUESTION_TYPE_NAME_VIDEO;
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
			return QUESTION_TYPE_NAME_S_CHOICE_PLUS;
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			return QUESTION_TYPE_NAME_M_CHOICE_PLUS;
		case QUESTION_TYPE_GROUP:
			return QUESTION_TYPE_NAME_GROUP;
		default:
			return "";
		}
	}

	public static int getEditType(int questionType, String mode) {
		switch (questionType) {
		case GradeConst.QUESTION_TYPE_S_CHOICE:
			return GradeConst.EDIT_TYPE_S_CHOICE;
		case GradeConst.QUESTION_TYPE_M_CHOICE:
			return GradeConst.EDIT_TYPE_M_CHOICE;
		case GradeConst.QUESTION_TYPE_PROGRAM:
			return GradeConst.EDIT_TYPE_CODE_MIRROR;
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
			return GradeConst.EDIT_TYPE_S_CHOICE_TEXT;
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			return GradeConst.EDIT_TYPE_M_CHOICE_TEXT;
		case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
			if (mode == null || mode.isEmpty())
				return GradeConst.EDIT_TYPE_TEXT;
			else
				return GradeConst.EDIT_TYPE_CODE_MIRROR;
		case GradeConst.QUESTION_TYPE_VIDEO:
			return GradeConst.EDIT_TYPE_VIDEO;
		case GradeConst.QUESTION_TYPE_ESSAY:
		default:
			return GradeConst.EDIT_TYPE_TEXT;
		}
	}

	public static String toQbName(int category) {
		switch (category) {
		case GradeConst.CATEGORY_TECHNOLOGY:
			return "技术";
		case GradeConst.CATEGORY_INTELLIGENCE:
			return "智力";
		case GradeConst.CATEGORY_INTERVIEW:
			return "面试";
		default:
			return "未知";
		}
	}

	public static int getLevel(String levelStr) throws FormatException {
		if (levelStr == null)
			return 0;
		else if (levelStr.equals("低难度"))
			return 2;
		else if (levelStr.equals("中难度"))
			return 4;
		else if (levelStr.equals("高难度"))
			return 6;
		else
			throw new FormatException("【难度】的取值范围为：低难度、中难度、高难度");
	}

	public static String getLevelString(int level) {
		if (level <= 2)
			return "低难度";
		else if (level <= 4)
			return "中难度";
		else
			return "高难度";
	}

}
