package com.ailk.sets.grade.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.util.IOUtils;

import com.ailk.sets.grade.excel.intf.IGenerator;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.ExtraConf;
import com.ailk.sets.grade.grade.config.MatrixElement;
import com.ailk.sets.grade.grade.config.MatrixFile;
import com.ailk.sets.grade.grade.config.ParamElement;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.qb.java.Executor;
import com.ailk.sets.grade.utils.Env;
import com.ailk.sets.grade.utils.TemplateUtils;

public class JavaGenerator implements IGenerator {

	private ExcelConf excelConf;

	public JavaGenerator(ExcelConf excelConf) {
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
		matrix.setConsole(true);
		questionConf.setMatrix(matrix);

		List<MatrixFile> files = loadFiles(questionContent.getData());
		if (files.isEmpty()) {
			System.out.println("给定的文件有错误，不能生成文件矩阵，id=" + excelConf.getId());
			System.exit(1);
		}

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

	private List<MatrixFile> loadFiles(Map<String, String> data)
			throws Exception {
		String subjectRoot = Env.getSubjectRoot();
		File subjectRootFile = new File(subjectRoot);
		File[] subjectRootDirs = subjectRootFile.listFiles();
		File[] files = null;

		// 查找指定目录
		for (File subjectRootDir : subjectRootDirs) {
			if (!subjectRootDir.isDirectory())
				continue;

			String root = subjectRootDir.getAbsolutePath() + File.separator
					+ excelConf.getId();
			File dir = new File(root);
			if (dir.exists()) {
				files = dir.listFiles();
				break;
			}
		}

		List<MatrixFile> result = new ArrayList<MatrixFile>();

		String idStr = Long.toString(excelConf.getId());
		if (idStr.startsWith(GradeConst.JAVA_QID_PREFIX)) {
			loadFiles(files, data, result, true, true);
		} else {
			long javaQid = Long.parseLong(GradeConst.JAVA_QID_PREFIX
					+ idStr.substring(GradeConst.JAVA_QID_PREFIX.length()));
			String javaRoot = subjectRoot + File.separator + "java"
					+ File.separator + javaQid;
			File javaDir = new File(javaRoot);
			if (!javaDir.exists()) {
				loadFiles(files, data, result, true, true);
			} else {
				File[] javaFiles = javaDir.listFiles();

				loadFiles(files, data, result, false, true);
				loadFiles(javaFiles, data, result, true, false);
			}
		}

		return result;
	}

	private void loadFiles(File[] files, Map<String, String> data,
			List<MatrixFile> matrixFiles, boolean enableRef, boolean enableCand)
			throws Exception {
		for (File file : files) {
			TemplateUtils.Result template;
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				String content = new String(IOUtils.toByteArray(in),
						GradeConst.ENCODING);

				template = TemplateUtils.getTemplate(content);
				if (enableRef) {
					data.put(GradeConst.REFERENCE_PREFIX
							+ GradeConst.GRADE_NAMESPACE_PATH + file.getName(),
							content);
				}
				if (enableCand) {
					data.put(GradeConst.CANDIDATE_PREFIX
							+ GradeConst.GRADE_NAMESPACE_PATH + file.getName(),
							template.getContent());
				}
			} finally {
				try {
					in.close();
				} catch (Exception e) {
				}
			}

			if (template.isHide() || !enableCand)
				continue;

			MatrixFile matrixFile = new MatrixFile();
			matrixFile.setMode(excelConf.getMode());
			matrixFile.setFilename(GradeConst.GRADE_NAMESPACE_PATH
					+ file.getName());
			matrixFiles.add(matrixFile);
		}
	}

}
