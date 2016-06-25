package com.ailk.sets.grade.excel;

import java.util.ArrayList;
import java.util.List;

import com.ailk.sets.grade.excel.intf.IGenerator;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.ParamElement;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.qb.choice.Executor;

public class ChoiceGenerator implements IGenerator {

	private ExcelConf excelConf;

	public ChoiceGenerator(ExcelConf excelConf) {
		this.excelConf = excelConf;
	}

	@Override
	public QuestionContent generate() throws Exception {
		QuestionContent questionContent = new QuestionContent();

		genQuestionConf(questionContent);
		return questionContent;
	}

	/**
	 * 生成选择题的配置文件
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

		List<String> options = new ArrayList<String>();
		questionConf.setOptions(options);

		for (String option : excelConf.getOptions()) {
			options.add(option);
		}

		questionConf.setAnswer(excelConf.getAnswer());
		questionConf.setOptDesc(excelConf.getCode());

		String paramData = excelConf.getParam();
		if (paramData != null) {
			List<ParamElement> params = new ArrayList<ParamElement>();
			questionConf.setParams(params);

			String[] items = paramData.split("[\r\n]", -1);
			for (String item : items) {
				String[] fields = item.split(";", -1);
				if (fields.length < 2)
					continue;

				ParamElement param = new ParamElement();
				param.setName(fields[0]);
				param.setOrigType(fields[1]);
				if (fields.length >= 3 && !fields[2].isEmpty())
					param.setCapacity(Integer.parseInt(fields[2]));
				if (fields.length >= 4)
					param.setMin(fields[3]);
				if (fields.length >= 5)
					param.setMax(fields[4]);
				if (fields.length >= 6)
					param.setRegex(fields[5]);
				params.add(param);
			}
		}

		questionConf.postInit();
		questionContent.setQuestionConf(questionConf);
	}

}
