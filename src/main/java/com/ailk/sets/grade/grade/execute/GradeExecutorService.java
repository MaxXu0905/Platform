package com.ailk.sets.grade.grade.execute;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ailk.sets.grade.grade.common.DebugConfig;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.grade.config.ExtraConf;
import com.ailk.sets.grade.grade.config.MatrixElement;
import com.ailk.sets.grade.grade.config.MatrixFile;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.jdbc.CandidateExamQuestion;
import com.ailk.sets.grade.jdbc.CandidateTestSubject;
import com.ailk.sets.grade.jdbc.CandidateTestSubjectPK;
import com.ailk.sets.grade.qb.ExecutorMain;
import com.ailk.sets.grade.qb.IExecutor;
import com.ailk.sets.grade.service.intf.AnswerWrapper;
import com.ailk.sets.grade.utils.Env;
import com.ailk.sets.grade.utils.FileUtils;
import com.ailk.sets.grade.utils.MyRefClassLoader;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.google.gson.Gson;

@Service
public class GradeExecutorService implements IGradeExecutorService {

	private static final Logger logger = Logger
			.getLogger(GradeExecutorService.class);
	private static final Gson gson = new Gson();

	@Value("${grade.user.enabled}")
	private boolean userEnabled;

	@Value("${grade.user.grade}")
	private String userGrade;

	@Value("${grade.user.prefix}")
	private String userPrefix;

	@Value("${grade.user.begin}")
	private int userBegin;

	@Value("${grade.user.end}")
	private int userEnd;

	@Value("${grade.user.reference.permission}")
	private String referencePermission;

	@Value("${grade.user.candidate.permission}")
	private String candidatePermission;

	private String javaExecutable;
	private Stack<String> userNameStack;

	@PostConstruct
	public void init() {
		javaExecutable = System.getenv("JAVA_HOME") + File.separator + "bin"
				+ File.separator + "java";

		if (userEnabled) {
			userNameStack = new Stack<String>();
			for (int i = userBegin; i < userEnd; i++) {
				String userName = userPrefix + i;
				userNameStack.add(userName);
			}
		}
	}

	public List<CandidateTestSubject> execute(
			CandidateExamQuestion candidateExamQuestion) throws Exception {
		long testId = candidateExamQuestion.getCandidateExamQuestionPK()
				.getTestId();
		long qid = candidateExamQuestion.getCandidateExamQuestionPK()
				.getQuestionId();
		List<CandidateTestSubject> results = new ArrayList<CandidateTestSubject>();
		String examRoot = Env.getExamRoot();
		String qRoot = examRoot + File.separator + testId + File.separator
				+ qid;

		// 获取该题的配置
		QuestionContent questionContent = gson.fromJson(
				candidateExamQuestion.getContent(), QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();
		int modeInt = GradeConst.toModeInt(questionConf.getMode());

		AnswerWrapper answerWrapper = null;
		if (candidateExamQuestion.getAnswer() != null) {
			answerWrapper = gson.fromJson(candidateExamQuestion.getAnswer(),
					AnswerWrapper.class);
		}

		switch (questionConf.getTypeInt()) {
		case GradeConst.QUESTION_TYPE_PROGRAM: {
			// 预先写入文件
			QuestionUtils.save(qRoot, questionContent, answerWrapper,
					referencePermission, candidatePermission);

			try {
				IExecutor executor = (IExecutor) Class.forName(
						questionConf.getExecutorClass()).newInstance();

				// 编译参考答案
				ResultHolder refer = executor.compile(qRoot,
						GradeConst.STAGE_REFERENCE);
				if (refer.getExitValue() != 0) {
					logger.fatal("系统异常，参考题答案编译错误，testId=" + testId + ", qid="
							+ qid + ", exitValue=" + refer.getExitValue()
							+ ", out=" + refer.getOut() + ", err="
							+ refer.getErr());
					break;
				}

				// 编译候选答案
				ResultHolder cand = executor.compile(qRoot,
						GradeConst.STAGE_CANDIDATE);
				if (cand.getExitValue() != 0)
					break;

				// 加载参数
				MyRefClassLoader myRefClassLoader = MyRefClassLoader
						.getInstance(qRoot);
				Class<?> clazz = myRefClassLoader
						.loadClass("com.ailk.Validator");
				Method getScoresMethod = clazz.getMethod("getScores");
				Method getGroupSizesMethod = clazz.getMethod("getGroupSizes");
				int[] scores = (int[]) getScoresMethod.invoke(null);
				int[] groupSizes = (int[]) getGroupSizesMethod.invoke(null);
				myRefClassLoader.close();

				if (scores.length != groupSizes.length) {
					logger.error("系统错误，scores 和 groupSizes 的数组个数不同，testId="
							+ testId + ", qid=" + qid);
					return results;
				}

				// 检查代码是否符合要求
				boolean valid = checkSource(qRoot);

				int totalScore = 0;
				int seq = 0;
				for (int i = 0; i < scores.length; i++) {
					double score = scores[i];
					int size = groupSizes[i];
					totalScore += scores[i];

					boolean correct = true;
					for (int j = 0; j < size; j++) {
						CandidateTestSubject result = new CandidateTestSubject();
						CandidateTestSubjectPK candidateTestSubjectPK = result
								.getCandidateTestSubjectPK();
						candidateTestSubjectPK.setGroupId(i);
						candidateTestSubjectPK.setCaseId(j);
						results.add(result);

						if (!valid)
							continue;

						List<String> args = new ArrayList<String>();
						args.add("-t");
						args.add("validate");
						args.add("-S");
						args.add(Integer.toString(seq));

						try {
							refer = run(executor, qRoot,
									GradeConst.STAGE_REFERENCE, modeInt, args,
									true);
							cand = run(executor, qRoot,
									GradeConst.STAGE_CANDIDATE, modeInt, args,
									true);

							result.setReferElapsed(refer.getElapsed());
							result.setReferMemBytes(refer.getMemBytes());
							result.setCandElapsed(cand.getElapsed());
							result.setCandMemBytes(cand.getMemBytes());

							if (refer.getExitValue() != 0) {
								logger.fatal("系统异常，参考题答案错误，testId=" + testId
										+ ", qid=" + qid + ", exitValue="
										+ refer.getExitValue() + ", out="
										+ refer.getOut() + ", err="
										+ refer.getErr());
								correct = false;
							} else if (!refer.equals(cand)) {
								correct = false;
							}
						} catch (Exception e) {
							correct = false;
							logger.error(TraceManager.getTrace(e));
						}

						seq++;
					}

					if (correct) {
						CandidateTestSubject result = results.get(results
								.size() - 1);
						result.setScore(score);
					}
				}

				if (totalScore != 10) {
					logger.error("系统错误，scores 的总和不等于10，testId=" + testId
							+ ", qid=" + qid);
					return results;
				}
			} finally {
				if (!DebugConfig.isKeepTempFiles())
					QuestionUtils.delete(qRoot);
			}
			break;
		}
		case GradeConst.QUESTION_TYPE_S_CHOICE:
		case GradeConst.QUESTION_TYPE_M_CHOICE:
		case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
		case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS: {
			CandidateTestSubject result = new CandidateTestSubject();
			results.add(result);

			if (answerWrapper != null
					&& questionConf.getOptAnswers().equals(
							answerWrapper.getOptAnswer()))
				result.setScore(10);
			break;
		}
		default:
			logger.error("考题类型不正确，testId=" + testId + ", qid=" + qid);
			break;
		}

		return results;
	}

	public ResultHolder test(int stage, int mode, String testId, long qid,
			String arg) throws Exception {
		String examRoot = Env.getExamRoot();
		String qRoot = examRoot + File.separator + testId + File.separator
				+ qid;

		// 获取该题的配置
		String configFile = qRoot + File.separator + "question.gson";
		String content = FileUtils.getContent(configFile);
		QuestionConf questionConf = gson.fromJson(content, QuestionConf.class);

		IExecutor executor = (IExecutor) Class.forName(
				questionConf.getExecutorClass()).newInstance();

		ResultHolder result = executor.compile(qRoot, stage);
		if (result.getExitValue() != 0) {
			logger.fatal("系统异常，编译错误，testId=" + testId + ", qid=" + qid
					+ ", exitValue=" + result.getExitValue() + ", out="
					+ result.getOut() + ", err=" + result.getErr());
			return result;
		}

		if (result.getOut() != null)
			System.out.println(result.getOut());
		if (result.getErr() != null)
			System.err.print(result.getErr());

		List<String> args = new ArrayList<String>();
		args.add("-t");
		args.add("test");
		args.add("-a");
		args.add(arg);

		try {
			return run(executor, qRoot, stage, mode, args, false);
		} catch (Exception e) {
			logger.error(TraceManager.getTrace(e));
			throw e;
		}
	}

	public ResultHolder testSample(int stage, int mode, long testId, long qid,
			int sampleId) throws Exception {
		String examRoot = Env.getExamRoot();
		String qRoot = examRoot + File.separator + testId + File.separator
				+ qid;

		// 获取该题的配置
		String configFile = qRoot + File.separator + "question.gson";
		String content = FileUtils.getContent(configFile);
		QuestionConf questionConf = gson.fromJson(content, QuestionConf.class);

		IExecutor executor = (IExecutor) Class.forName(
				questionConf.getExecutorClass()).newInstance();

		ResultHolder result = executor.compile(qRoot, stage);
		if (result.getExitValue() != 0) {
			logger.fatal("系统异常，编译错误，testId=" + testId + ", qid=" + qid
					+ ", exitValue=" + result.getExitValue() + ", out="
					+ result.getOut() + ", err=" + result.getErr());
			return result;
		}

		if (result.getOut() != null)
			System.out.println(result.getOut());
		if (result.getErr() != null)
			System.err.print(result.getErr());

		List<String> args = new ArrayList<String>();
		args.add("-t");
		args.add("testSample");
		args.add("-i");
		args.add(Integer.toString(sampleId));

		try {
			return run(executor, qRoot, stage, mode, args, false);
		} catch (Exception e) {
			logger.error(TraceManager.getTrace(e));
			throw e;
		}
	}

	public ResultHolder run(IExecutor executor, String qRoot, int stage,
			int mode, List<String> args, boolean grading) throws Exception {
		ResultHolder result = null;
		String userName = null;

		try {
			// 初始化
			result = executor.init(qRoot, stage);
			if (result.getExitValue() != 0)
				return result;

			// 部署
			result = executor.deploy(qRoot, stage);
			if (result.getExitValue() != 0)
				return result;

			StringBuilder classPathBuilder = new StringBuilder();

			// 执行，需要获取输出、错误，因此需要新起一个进程，另外，防止进程死循环
			String homeDir;
			if (stage == GradeConst.STAGE_REFERENCE)
				homeDir = qRoot + GradeConst.REFERENCE_PREFIX;
			else
				homeDir = qRoot + GradeConst.CANDIDATE_PREFIX;

			classPathBuilder.append(homeDir);

			for (String path : new String[] { Env.getGradeRoot(),
					Env.getGradeRoot() + File.separator + "lib" }) {
				File libDir = new File(path);
				File[] libs = libDir.listFiles();
				for (File lib : libs) {
					if (!lib.getAbsolutePath().endsWith(".jar"))
						continue;

					classPathBuilder.append(File.pathSeparator);
					classPathBuilder.append(lib.getAbsolutePath());
				}
			}

			// 调整类路径
			ProcessBuilder builder = new ProcessBuilder();
			Map<String, String> env = builder.environment();

			// 修改CLASSPATH
			env.put("CLASSPATH", classPathBuilder.toString());
			// 设置语言
			env.put("LANG", "C");
			env.put("LC_ALL", "C");

			Map<String, String> extraEnv = executor.getenv();
			if (extraEnv != null)
				env.putAll(extraEnv);

			List<String> command = new ArrayList<String>();
			if (userEnabled) {
				command.add("sudo");
				command.add("-E"); // 传递环境变量
				command.add("-H"); // 设置HOME环境变量
				command.add("-u"); // 设置要执行命令的用户

				if (grading) {
					command.add(userGrade);
				} else {
					long expires = System.currentTimeMillis()
							+ TimedWaitFor.DFLT_TIMEOUT * 1000;

					synchronized (userNameStack) {
						while (userNameStack.isEmpty()) {
							long timeout = expires - System.currentTimeMillis();
							if (timeout <= 0) {
								result = new ResultHolder();
								result.setExitValue(1);
								result.setErr("运行环境忙，请稍后重试");
								return result;
							}

							userNameStack.wait(timeout);
						}

						userName = userNameStack.pop();
					}

					command.add(userName);
				}
			}

			command.add(javaExecutable);
			command.add("-Dfile.encoding=" + GradeConst.ENCODING);
			command.add("-Djava.library.path=" + homeDir);
			command.add(ExecutorMain.class.getName());
			command.add("-e");
			command.add(executor.getClass().getName());
			command.add("-q");
			command.add(qRoot);
			command.add("-s");
			command.add(Integer.toString(stage));
			command.add("-m");
			command.add(Integer.toString(mode));
			command.addAll(args);
			builder.command(command);
			builder.directory(new File(homeDir));

			logger.debug(env.toString()
					+ StringUtils.join(command.toArray(), " "));
			return TimedWaitFor.execute(builder, TimedWaitFor.DFLT_TIMEOUT);
		} finally {
			if (userName != null) {
				synchronized (userNameStack) {
					boolean empty = userNameStack.isEmpty();
					userNameStack.add(userName);
					if (empty)
						userNameStack.notify();
				}
			}

			// 清理
			executor.destroy(qRoot, stage);
		}
	}

	// 检查编程题代码是否符合要求
	private boolean checkSource(String qRoot) throws Exception {
		String filename = qRoot + File.separator + "extra.gson";
		String content = FileUtils.getContent(filename);
		ExtraConf extraConf = gson.fromJson(content, ExtraConf.class);

		String include = extraConf.getInclude();
		String exclude = extraConf.getExclude();
		String[] includeKeys = null;
		String[] excludeKeys = null;
		boolean[] includeIndicators = null;

		if (include != null && !include.isEmpty()) {
			includeKeys = include.split(",");
			includeIndicators = new boolean[includeKeys.length];
		}
		if (exclude != null && !exclude.isEmpty())
			excludeKeys = exclude.split(",");

		filename = qRoot + File.separator + "question.gson";
		content = FileUtils.getContent(filename);
		QuestionConf questionConf = gson.fromJson(content, QuestionConf.class);

		MatrixElement matrixElement = questionConf.getMatrix();
		if (matrixElement != null) {
			List<MatrixFile> files = matrixElement.getFiles();

			for (MatrixFile file : files) {
				String fullname = qRoot + GradeConst.CANDIDATE_PREFIX
						+ file.getFilename();
				content = FileUtils.getContent(fullname);

				if (content != null) {
					if (includeKeys != null) {
						for (int i = 0; i < includeKeys.length; i++) {
							if (content.contains(includeKeys[i]))
								includeIndicators[i] = true;
						}
					}

					if (excludeKeys != null) {
						for (int i = 0; i < excludeKeys.length; i++) {
							// 如果包含任何一个排除的关键字，即判为错误
							if (content.contains(excludeKeys[i]))
								return false;
						}
					}
				}
			}

			if (includeIndicators != null) {
				for (boolean includeIndicator : includeIndicators) {
					// 没有包含其中一个关键字，即判为错误
					if (!includeIndicator)
						return false;
				}
			}
		}

		return true;
	}

}
