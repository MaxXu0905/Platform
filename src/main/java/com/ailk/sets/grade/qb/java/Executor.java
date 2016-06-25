package com.ailk.sets.grade.qb.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.util.IOUtils;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.grade.execute.ResultHolder;
import com.ailk.sets.grade.grade.execute.TimedWaitFor;
import com.ailk.sets.grade.qb.IExecutor;
import com.ailk.sets.grade.utils.Env;
import com.ailk.sets.grade.utils.MyClassLoader;

/**
 * 根据配置，实例化、编译、运行等操作
 * 
 * @author xugq
 * 
 */
public class Executor implements IExecutor {

	private final List<String> options;
	private final String GNUSTEP_SYSTEM_ROOT;

	public Executor() {
		options = new ArrayList<String>();
		GNUSTEP_SYSTEM_ROOT = System.getenv("GNUSTEP_SYSTEM_ROOT");

		List<String> command = new ArrayList<String>();
		command.add("gnustep-config");
		command.add("--objc-flags");

		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);

		ResultHolder result = TimedWaitFor.execute(builder,
				TimedWaitFor.DFLT_TIMEOUT);
		if (result.getExitValue() != 0)
			return;

		for (String option : result.getOut().split(" ")) {
			options.add(option);
		}
	}

	@Override
	public void instantiate(QuestionContent questionContent) throws Exception {
	}

	@Override
	public ResultHolder compile(String root, int stage) throws Exception {
		String srcRoot;
		if (stage == GradeConst.STAGE_REFERENCE)
			srcRoot = root + GradeConst.REFERENCE_PREFIX;
		else
			srcRoot = root + GradeConst.CANDIDATE_PREFIX;

		File srcFile = new File(srcRoot);
		return compileDir(srcFile.getAbsolutePath(), srcFile);
	}

	@Override
	public ResultHolder init(String root, int stage) throws Exception {
		return new ResultHolder();
	}

	@Override
	public ResultHolder deploy(String root, int stage) throws Exception {
		return new ResultHolder();
	}

	@Override
	public Map<String, String> getenv() throws Exception {
		return null;
	}

	@Override
	public ResultHolder destroy(String root, int stage) throws Exception {
		return new ResultHolder();
	}

	@Override
	public ResultHolder execute(String root, int stage, int mode, int seq)
			throws Exception {
		ResultHolder result = new ResultHolder();

		MyClassLoader myClassLoader = MyClassLoader.getInstance();
		Class<?> clazz = myClassLoader.loadClass("com.ailk.Validator");
		Method method = clazz.getMethod("validate", int.class);

		long nanoTime = System.nanoTime();
		long totalMemory = Runtime.getRuntime().totalMemory();

		try {
			Object retval = method.invoke(null, seq);
			if (retval != null)
				result.setCheck(retval.toString());
		} catch (Throwable e) {
			result.setCheck(TraceManager.getTrace(e));
		}

		// 设置经过的时间
		result.setElapsed(System.nanoTime() - nanoTime);

		// 设置增加的内存
		long memBytes = Runtime.getRuntime().totalMemory() - totalMemory;
		if (memBytes >= 0)
			result.setMemBytes(memBytes);

		return result;
	}

	private ResultHolder compileDir(String root, File dir) throws Exception {
		List<String> javaFilenames = new ArrayList<String>();
		List<String> cFilenames = new ArrayList<String>();
		List<String> cppFilenames = new ArrayList<String>();
		boolean hasMFiles = false;
		File[] files = dir.listFiles();
		ResultHolder result = new ResultHolder();

		for (File file : files) {
			String filename = file.getName();

			if (file.isDirectory()) {
				result.merge(compileDir(root, file));
				if (result.getExitValue() != 0)
					return result;
			} else if (filename.endsWith(".java")) {
				javaFilenames.add(filename);
			} else if (filename.endsWith(".c")
					&& !filename.startsWith("Wrapper")
					&& !filename.equals("JniUtils.c")) {
				if (filename.endsWith("Utils.c"))
					cFilenames.add(wrapper(root, filename));
				else
					cFilenames.add(filename);
			} else if (filename.endsWith(".m")
					&& !filename.startsWith("Wrapper")) {
				if (filename.endsWith("Utils.m"))
					filename = wrapper(root, filename);

				result.merge(compileMFile(dir.getAbsolutePath(), filename));
				if (result.getExitValue() != 0)
					return result;

				hasMFiles = true;
				cFilenames.add(filename.substring(0, filename.length() - 1)
						+ "o");
			} else if (filename.endsWith(".cpp")) {
				cppFilenames.add(filename);
			}
		}

		if (!javaFilenames.isEmpty()) {
			result.merge(compileJavaFile(root, dir.getAbsolutePath(),
					javaFilenames));
			if (result.getExitValue() != 0)
				return result;
		}

		if (!cFilenames.isEmpty()) {
			result.merge(compileCFile(root, dir.getAbsolutePath(), cFilenames,
					hasMFiles));
			if (result.getExitValue() != 0)
				return result;
		}

		if (!cppFilenames.isEmpty()) {
			result.merge(compileCppFile(root, dir.getAbsolutePath(),
					cppFilenames));
			if (result.getExitValue() != 0)
				return result;
		}

		return result;
	}

	private ResultHolder compileJavaFile(String root, String parent,
			List<String> filenames) throws Exception {
		StringBuilder classPathBuilder = new StringBuilder();
		classPathBuilder.append(root);

		File rootDir = new File(Env.getGradeRoot());
		File[] rootFiles = rootDir.listFiles();
		for (File rootFile : rootFiles) {
			if (!rootFile.getAbsolutePath().endsWith(".jar"))
				continue;

			classPathBuilder.append(File.pathSeparator);
			classPathBuilder.append(rootFile.getAbsolutePath());
		}

		List<String> command = new ArrayList<String>();
		command.add("javac");
		command.add("-J-Dfile.encoding=" + GradeConst.ENCODING);
		command.add("-encoding");
		command.add(GradeConst.ENCODING);
		command.add("-cp");
		command.add(classPathBuilder.toString());
		for (String filename : filenames) {
			command.add(filename);
		}

		ProcessBuilder builder = new ProcessBuilder();
		builder.directory(new File(parent));
		builder.command(command);

		ResultHolder result = TimedWaitFor.execute(builder,
				TimedWaitFor.DFLT_TIMEOUT);
		result.removeDirectory(parent);
		return result;
	}

	private ResultHolder compileCFile(String root, String parent,
			List<String> filenames, boolean hasMFiles) throws Exception {
		// 首先添加工具文件
		InputStream in = Executor.class.getResourceAsStream("/JniUtilsC.c");
		if (in != null) {
			File file = new File(root + GradeConst.GRADE_NAMESPACE_PATH
					+ "JniUtils.c");
			OutputStream out = new FileOutputStream(file);
			IOUtils.copy(in, out);
			in.close();
			out.close();

			filenames.add(file.getName());
		}

		in = Executor.class.getResourceAsStream("/JniUtilsC.h");
		if (in != null) {
			OutputStream out = new FileOutputStream(new File(root
					+ GradeConst.GRADE_NAMESPACE_PATH + "JniUtils.h"));
			IOUtils.copy(in, out);
			in.close();
			out.close();
		}

		return compileCorCppFile("gcc", root, parent, filenames, hasMFiles);
	}

	private ResultHolder compileCppFile(String root, String parent,
			List<String> filenames) throws Exception {
		// 首先添加工具文件
		InputStream in = Executor.class.getResourceAsStream("/JniUtilsCpp.h");
		if (in != null) {
			OutputStream out = new FileOutputStream(new File(root
					+ GradeConst.GRADE_NAMESPACE_PATH + "JniUtils.h"));
			IOUtils.copy(in, out);
			in.close();
			out.close();
		}

		return compileCorCppFile("g++", root, parent, filenames, false);
	}

	private ResultHolder compileCorCppFile(String compiler, String root,
			String parent, List<String> filenames, boolean hasMFiles)
			throws Exception {
		List<String> command = new ArrayList<String>();
		command.add(compiler);
		command.add("-I.");

		File javaHome = new File(System.getProperty("java.home"));
		File includeFile = new File(javaHome.getAbsolutePath() + File.separator
				+ "include");
		if (!includeFile.exists()) {
			includeFile = new File(javaHome.getParent() + File.separator
					+ "include");
		}
		command.add("-I" + includeFile.getAbsolutePath());
		command.add("-I" + includeFile.getAbsolutePath() + File.separator
				+ "linux");

		File[] files = includeFile.listFiles();
		for (File file : files) {
			if (!file.isDirectory())
				continue;

			command.add("-I" + file.getAbsolutePath());
		}

		if (hasMFiles) {
			command.add("-L" + GNUSTEP_SYSTEM_ROOT + "/Library/Libraries");
			command.add("-lgnustep-base");
		}

		command.add("-Wall");
		command.add("-shared");
		command.add("-fPIC");
		command.add("-o");

		String osName = System.getProperty("os.name");
		if (osName.equals("Mac OS X"))
			command.add(root + File.separator + "libTest.dylib");
		else if (osName.equals("HP-UX"))
			command.add(root + File.separator + "libTest.sl");
		else
			command.add(root + File.separator + "libTest.so");

		for (String filename : filenames) {
			command.add(filename);
		}

		ProcessBuilder builder = new ProcessBuilder();
		Map<String, String> env = builder.environment();
		env.put("LANG", "C");
		env.put("LC_ALL", "C");

		builder.directory(new File(parent));
		builder.command(command);

		ResultHolder result = TimedWaitFor.execute(builder,
				TimedWaitFor.DFLT_TIMEOUT);
		result.removeDirectory(parent);
		return result;
	}

	private ResultHolder compileMFile(String parent, String filename)
			throws Exception {
		List<String> command = new ArrayList<String>();
		command.add("gcc");
		command.addAll(options);
		command.add("-c");
		command.add("-o");
		command.add(filename.substring(0, filename.length() - 1) + "o");
		command.add(filename);

		ProcessBuilder builder = new ProcessBuilder();
		Map<String, String> env = builder.environment();
		env.put("LANG", "C");
		env.put("LC_ALL", "C");

		builder.directory(new File(parent));
		builder.command(command);

		ResultHolder result = TimedWaitFor.execute(builder,
				TimedWaitFor.DFLT_TIMEOUT);
		result.removeDirectory(parent);
		return result;
	}

	private String wrapper(String root, String filename) throws Exception {
		InputStream in = Executor.class
				.getResourceAsStream("/MallocWrapperC.c");
		if (in != null) {
			File wrapperFile = new File(root + GradeConst.GRADE_NAMESPACE_PATH
					+ "Wrapper" + filename);
			OutputStream out = new FileOutputStream(wrapperFile);
			IOUtils.copy(in, out);

			String includeStr = "\n\n#include \"" + filename + "\"\n";
			out.write(includeStr.getBytes());
			in.close();
			out.close();

			return wrapperFile.getName();
		} else {
			return null;
		}
	}

	@Override
	public ResultHolder test(String root, int stage, int mode, String arg)
			throws Exception {
		ResultHolder result = new ResultHolder();

		MyClassLoader myClassLoader = MyClassLoader.getInstance();
		Class<?> clazz = myClassLoader.loadClass("com.ailk.Validator");
		Method method = clazz.getMethod("test", String.class);

		long nanoTime = System.nanoTime();
		long totalMemory = Runtime.getRuntime().totalMemory();

		try {
			Object retval = method.invoke(null, arg);
			if (retval != null)
				result.setOut(retval.toString());
		} catch (Throwable e) {
			result.setOut(TraceManager.getTrace(e));
		}

		// 设置经过的时间
		result.setElapsed(System.nanoTime() - nanoTime);

		// 设置增加的内存
		long memBytes = Runtime.getRuntime().totalMemory() - totalMemory;
		if (memBytes >= 0)
			result.setMemBytes(memBytes);

		return result;
	}

	@Override
	public ResultHolder testSample(String root, int stage, int mode,
			int sampleId) throws Exception {
		ResultHolder result = new ResultHolder();

		MyClassLoader myClassLoader = MyClassLoader.getInstance();
		Class<?> clazz = myClassLoader.loadClass("com.ailk.Validator");
		Method method = clazz.getMethod("testSample", int.class);

		long nanoTime = System.nanoTime();
		long totalMemory = Runtime.getRuntime().totalMemory();

		try {
			Object retval = method.invoke(null, sampleId);
			if (retval != null)
				result.setOut(retval.toString());
		} catch (Throwable e) {
			result.setOut(TraceManager.getTrace(e));
		}

		// 设置经过的时间
		result.setElapsed(System.nanoTime() - nanoTime);

		// 设置增加的内存
		long memBytes = Runtime.getRuntime().totalMemory() - totalMemory;
		if (memBytes >= 0)
			result.setMemBytes(memBytes);

		return result;
	}

}
