package com.ailk.sets.grade.excel;

import com.ailk.sets.grade.excel.intf.IGenerator;
import com.ailk.sets.grade.grade.common.GradeConst;

public class GeneratorFactory {

	public static IGenerator createFactory(ExcelConf conf) {
		String type = conf.getType();
		int typeInt = GradeConst.toQuestionTypeInt(type);

		switch (typeInt) {
		case GradeConst.QUESTION_TYPE_S_CHOICE:
		case GradeConst.QUESTION_TYPE_M_CHOICE:
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			return new ChoiceGenerator(conf);
		case GradeConst.QUESTION_TYPE_PROGRAM:
			switch (GradeConst.toModeInt(conf.getMode())) {
			case GradeConst.MODE_JAVA:
			case GradeConst.MODE_JS:
			case GradeConst.MODE_SHELL:
			case GradeConst.MODE_PHP:
			case GradeConst.MODE_CPP:
			case GradeConst.MODE_C:
			case GradeConst.MODE_OBJECTIVE_C:
			case GradeConst.MODE_PYTHON:
				return new JavaGenerator(conf);
			default:
				return null;
			}
		case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
		case GradeConst.QUESTION_TYPE_ESSAY:
			return new TextGenerator(conf);
		default:
			return null;
		}
	}

}
