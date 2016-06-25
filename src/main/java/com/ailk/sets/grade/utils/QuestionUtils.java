package com.ailk.sets.grade.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.ExtraConf;
import com.ailk.sets.grade.grade.config.MatrixElement;
import com.ailk.sets.grade.grade.config.MatrixFile;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.service.intf.AnswerWrapper;
import com.google.gson.Gson;

public class QuestionUtils {

	private static final Gson gson = new Gson();

	public static class FileInfo {
		private String mode; // 模式
		private String filename; // 文件名，不含前缀
		private String refAnswer; // 参考答案
		private String answer; // 测评答案

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public String getRefAnswer() {
			return refAnswer;
		}

		public void setRefAnswer(String refAnswer) {
			this.refAnswer = refAnswer;
		}

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}
	}

	/**
	 * 保存答题环境数据
	 * 
	 * @param root
	 *            根目录
	 * @param questionContent
	 *            题目内容
	 * @param answerWrapper
	 *            答案对象
	 * @param referencePermission
	 *            参考人权限
	 * @param candidatePermission
	 *            候选人权限
	 * 
	 * @return
	 */
	public static void save(String root, QuestionContent questionContent,
			AnswerWrapper answerWrapper, String referencePermission,
			String candidatePermission) throws Exception {
		File rootFile = new File(root);
		if (!rootFile.exists())
			rootFile.mkdirs();

		// 设置目录权限，只能本用户访问
		FileUtils.chmod("0700", root);
		deleteSubDirs(rootFile);

		// 创建运行测试时需要的元数据
		String filename;
		File file;
		QuestionConf questionConf = questionContent.getQuestionConf();
		if (questionConf != null) {
			filename = root + File.separator + "question.gson";
			FileUtils.setContent(filename, gson.toJson(questionConf));
			FileUtils.chmod("0700", filename);
		}

		ExtraConf extraConf = questionContent.getExtraConf();
		if (questionConf != null) {
			filename = root + File.separator + "extra.gson";
			FileUtils.setContent(filename, gson.toJson(extraConf));
			FileUtils.chmod("0700", filename);
		}

		// 创建参考目录
		filename = root + GradeConst.REFERENCE_PREFIX;
		file = new File(filename);
		file.mkdir();
		FileUtils.chmod(referencePermission, filename);

		// 创建测评目录
		filename = root + GradeConst.CANDIDATE_PREFIX;
		file = new File(filename);
		file.mkdir();
		FileUtils.chmod(candidatePermission, filename);

		Map<String, String> data = questionContent.getData();
		Map<String, String> answerData = null;
		if (answerWrapper != null)
			answerData = answerWrapper.getData();

		for (Entry<String, String> entry : data.entrySet()) {
			filename = root + entry.getKey();
			file = new File(filename);
			File parent = file.getParentFile();
			if (!parent.exists())
				parent.mkdirs();

			String content;
			String key = entry.getKey();

			if (key.startsWith(GradeConst.CANDIDATE_PREFIX)) {
				key = key.substring(GradeConst.CANDIDATE_PREFIX.length());

				content = null;
				if (answerData != null)
					content = answerData.get(key);
				if (content == null)
					content = entry.getValue();
			} else {
				content = entry.getValue();
			}

			FileUtils.setContent(filename, content);
		}

		// 设置目录权限
		FileUtils.chmod(candidatePermission, root);
	}

	/**
	 * 删除目录及其文件
	 * 
	 * @param root
	 *            根目录
	 */
	public static void delete(String root) {
		File rootFile = new File(root);
		if (!rootFile.exists())
			return;

		delete(rootFile);

		// 如果父目录为空，则删除
		File parent = rootFile.getParentFile();
		File[] children = parent.listFiles();
		if (children != null && children.length == 0)
			parent.delete();
	}

	/**
	 * 删除目录及其文件
	 * 
	 * @param root
	 *            根目录
	 */
	public static void delete(File root) {
		deleteSubDirs(root);
		root.delete();
	}

	/**
	 * 删除目录下的所有文件
	 * 
	 * @param root
	 *            根目录
	 */
	public static void deleteSubDirs(File root) {
		if (root.isFile())
			return;

		File[] files = root.listFiles();
		if (files != null) {
			for (File file : files) {
				delete(file);
			}
		}
	}

	/**
	 * 根据答案个数获取答案字符串
	 * 
	 * @param answers
	 *            答案个数
	 * @return
	 */
	public static String getAnswers(Integer answers) {
		if (answers == null)
			return null;

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < answers; i++)
			builder.append((char) ('A' + i));

		return builder.toString();
	}

	/**
	 * 获取文件内容
	 * 
	 * @param questionContent
	 * @return
	 */
	public static FileInfo getFileInfo(QuestionContent questionContent) {
		QuestionConf questionConf = questionContent.getQuestionConf();
		MatrixElement matrixElement = questionConf.getMatrix();
		if (matrixElement == null)
			return null;

		List<MatrixFile> files = matrixElement.getFiles();
		if (files == null || files.isEmpty())
			return null;

		MatrixFile file = files.get(0);
		Map<String, String> contentMap = questionContent.getData();
		if (contentMap == null)
			return null;

		FileInfo fileInfo = new FileInfo();
		fileInfo.setMode(file.getMode());
		fileInfo.setFilename(file.getFilename());
		fileInfo.setRefAnswer(contentMap.get(GradeConst.REFERENCE_PREFIX
				+ fileInfo.getFilename()));
		fileInfo.setAnswer(contentMap.get(GradeConst.CANDIDATE_PREFIX
				+ fileInfo.getFilename()));
		return fileInfo;
	}

	public static String escape(String data, boolean html) {
		if (!html)
			return "<pre>" + StringEscapeUtils.escapeXml(data) + "</pre>";
		else
			return data;
	}

	public static List<String> escape(List<String> data, boolean html) {
		if (data == null)
			return null;

		List<String> result = new ArrayList<String>();
		for (String item : data) {
			result.add(escape(item, html));
		}

		return result;
	}

}
