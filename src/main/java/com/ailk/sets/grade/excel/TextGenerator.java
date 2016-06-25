package com.ailk.sets.grade.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ailk.sets.grade.excel.intf.IGenerator;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.ExtraConf;
import com.ailk.sets.grade.grade.config.MatrixElement;
import com.ailk.sets.grade.grade.config.MatrixFile;
import com.ailk.sets.grade.grade.config.ParamElement;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.qb.text.Executor;

public class TextGenerator implements IGenerator {

	private ExcelConf excelConf;

	public TextGenerator(ExcelConf excelConf) {
		this.excelConf = excelConf;
	}

	@Override
	public QuestionContent generate() throws Exception {
		QuestionContent questionContent = new QuestionContent();

		genQuestionConf(questionContent);
		genExtraConf(questionContent);
		return questionContent;
	}

	/**
	 * 生成Java基于方法的试题的配置文件
	 * 
	 * @param questionContent
	 * @throws Exception
	 */
	private void genQuestionConf(QuestionContent questionContent)
			throws Exception {
		QuestionConf questionConf = new QuestionConf();

		if (excelConf.getTags() == null || excelConf.getTags().isEmpty()) {
			questionConf
					.setQbName(GradeConst.toQbName(excelConf.getCategory()));
		} else {
			questionConf.setQbName(excelConf.getTags().get(0));
		}
		questionConf.setMode(excelConf.getMode());
		questionConf.setHtml(excelConf.isHtml());
		questionConf.setType(excelConf.getType());
		questionConf.setSample(excelConf.isSample());
		questionConf.setExecutorClass(Executor.class.getName());
		questionConf.setTitle(excelConf.getTitle());
		questionConf.setSummary(excelConf.getSummary());
		questionConf.setCategory(excelConf.getCategory());
		questionConf.setSamples(excelConf.getSamples());

		MatrixElement matrix = new MatrixElement();
		matrix.setConsole(false);
		questionConf.setMatrix(matrix);

		List<MatrixFile> files = loadFiles(excelConf.getType(),
				questionContent.getData());
		matrix.setFiles(files);

		questionConf.postInit();
		questionContent.setQuestionConf(questionConf);
	}

	/**
	 * 生成Java基于方法的试题的额外配置文件
	 * 
	 * @param questionContent
	 * @throws Exception
	 */
	private void genExtraConf(QuestionContent questionContent) throws Exception {
		ExtraConf extraConf = new ExtraConf();

		extraConf.setMethodName("validate");
		extraConf.setClassName("com.ailk.Validator");
		extraConf.setInclude(excelConf.getInclude());
		extraConf.setExclude(excelConf.getExclude());

		String paramData = excelConf.getParam();
		if (paramData != null) {
			List<ParamElement> params = new ArrayList<ParamElement>();
			extraConf.setParams(params);

			String[] items = paramData.split("[\r\n]", -1);
			for (String item : items) {
				String[] fields = item.split(";", -1);
				if (fields.length != 2) {
					System.out
							.println("给定的param字段数不是2，id=" + excelConf.getId());
					System.exit(1);
				}

				ParamElement param = new ParamElement();
				param.setName(fields[0]);
				param.setOrigType(fields[1]);
				params.add(param);
			}
		}

		extraConf.postInit();
		questionContent.setExtraConf(extraConf);
	}

	private List<MatrixFile> loadFiles(String questionType,
			Map<String, String> data) throws Exception {
		List<MatrixFile> result = new ArrayList<MatrixFile>();

		String filename;
		switch (GradeConst.toQuestionTypeInt(questionType)) {
		case GradeConst.QUESTION_TYPE_PROGRAM:
		case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
			filename = "代码";
			break;
		case GradeConst.QUESTION_TYPE_ESSAY:
			filename = "答案";
			break;
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
			filename = "解释";
			break;
		default:
			filename = "文本";
			break;
		}
		data.put(GradeConst.REFERENCE_PREFIX + GradeConst.GRADE_NAMESPACE_PATH
				+ filename, excelConf.getCode());
		data.put(GradeConst.CANDIDATE_PREFIX + GradeConst.GRADE_NAMESPACE_PATH
				+ filename, "");

		MatrixFile matrixFile = new MatrixFile();
		matrixFile.setMode(excelConf.getMode());
		matrixFile.setFilename(GradeConst.GRADE_NAMESPACE_PATH + filename);
		result.add(matrixFile);

		return result;
	}
}
