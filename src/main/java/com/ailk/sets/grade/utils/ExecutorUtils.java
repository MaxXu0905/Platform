package com.ailk.sets.grade.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.grade.execute.ResultHolder;
import com.ailk.sets.grade.grade.execute.TimedWaitFor;

public class ExecutorUtils {

	private static final String PHP_INTERPRETER = "php";
	private static final String PHP_FILENAME = GradeConst.GRADE_NAMESPACE_PATH
			.substring(1) + "Validator.php";
	private static final String SHELL_INTERPRETER = "sh";
	private static final String SHELL_FILENAME = GradeConst.GRADE_NAMESPACE_PATH
			.substring(1) + "execute.sh";
	private static final String PYTHON_INTERPRETER = "python";
	private static final String PYTHON_FILENAME = GradeConst.GRADE_NAMESPACE_PATH
			.substring(1) + "Validator.py";

	public static class Console {

		public void log(String format, Object... args) {
			System.out.printf(format, args);
		}

		public void log(boolean b) {
			System.out.print(b);
		}

		public void log(char c) {
			System.out.print(c);
		}

		public void log(int i) {
			System.out.print(i);
		}

		public void log(long l) {
			System.out.print(l);
		}

		public void log(float f) {
			System.out.print(f);
		}

		public void log(double d) {
			System.out.print(d);
		}

		public void log(String s) {
			System.out.print(s);
		}

		public void log(Object obj) {
			System.out.print(obj);
		}

		public void log(char[] s) {
			System.out.print(s);
		}

	}

	public static Console console = new Console();

	/**
	 * 执行php代码
	 * 
	 * @param args
	 *            参数
	 * @return
	 */
	public static String executePhp(String... args) {
		return execute(PHP_INTERPRETER, PHP_FILENAME, args);
	}

	/**
	 * 执行Shell脚本
	 * 
	 * @param args
	 *            参数
	 * @return
	 */
	public static String executeShell(String... args) {
		return execute(SHELL_INTERPRETER, SHELL_FILENAME, args);
	}

	/**
	 * 执行python代码
	 * 
	 * @param args
	 *            参数
	 * @return
	 */
	public static String executePython(String... args) {
		// Python需要去掉最后的换行符
		String result = execute(PYTHON_INTERPRETER, PYTHON_FILENAME, args);
		if (result == null || result.isEmpty())
			return result;

		return result.substring(0, result.length() - 1);
	}

	/**
	 * 执行javascript代码
	 * 
	 * @param filename
	 *            文件名
	 * @param function
	 *            方法名
	 * @param args
	 *            参数
	 * @return
	 */
	public static String executeJs(String filename, String function,
			Object... args) {
		// 加载文件及构建JS执行脚本
		StringBuilder builder = new StringBuilder();
		builder.append(FileUtils.getContent(filename));

		builder.append("\n");
		builder.append(function);
		builder.append("(");
		if (args != null) {
			boolean first = true;
			for (Object arg : args) {
				if (first)
					first = false;
				else
					builder.append(", ");

				if (arg instanceof String) {
					builder.append("\"");
					builder.append(escape((String) arg));
					builder.append("\"");
				} else if (arg.getClass().isArray()) {
					if (arg instanceof boolean[]) {
						builder.append(Arrays.toString((boolean[]) arg));
					} else if (arg instanceof byte[]) {
						builder.append(Arrays.toString((byte[]) arg));
					} else if (arg instanceof char[]) {
						builder.append(Arrays.toString((char[]) arg));
					} else if (arg instanceof short[]) {
						builder.append(Arrays.toString((short[]) arg));
					} else if (arg instanceof int[]) {
						builder.append(Arrays.toString((int[]) arg));
					} else if (arg instanceof long[]) {
						builder.append(Arrays.toString((long[]) arg));
					} else if (arg instanceof float[]) {
						builder.append(Arrays.toString((float[]) arg));
					} else if (arg instanceof double[]) {
						builder.append(Arrays.toString((double[]) arg));
					} else {
						Object[] objs = (Object[]) arg;

						if (objs != null && objs.length > 0
								&& objs[0] instanceof String) {
							String[] strs = new String[objs.length];
							for (int i = 0; i < objs.length; i++) {
								strs[i] = "\"" + escape((String) objs[i])
										+ "\"";
							}

							builder.append(Arrays.toString(strs));
						} else {
							builder.append(Arrays.toString(objs));
						}
					}
				} else {
					builder.append(arg);
				}
			}
		}
		builder.append(");");

		Context ctx = Context.enter();
		try {
			Scriptable scope = ctx.initStandardObjects(null);
			Object jsOut = Context.javaToJS(System.out, scope);
			ScriptableObject.putProperty(scope, "out", jsOut);
			Object jsConsole = Context.javaToJS(console, scope);
			ScriptableObject.putProperty(scope, "console", jsConsole);

			Script script = ctx
					.compileString(builder.toString(), null, 0, null);
			Object obj = script.exec(ctx, scope);

			if (obj instanceof Undefined)
				return null;
			else
				return Context.toString(obj);
		} catch (Throwable e) {
			return TraceManager.getTrace(e);
		} finally {
			Context.exit();
		}
	}

	public static String toString(String[] args) {
		StringBuilder builder = new StringBuilder();

		for (String arg : args) {
			if (builder.length() > 0)
				builder.append(";");

			builder.append(arg);
		}

		return builder.toString();
	}

	/**
	 * 执行解释器
	 * 
	 * @param interpreter
	 *            解释器
	 * @param filename
	 *            文件
	 * @param args
	 *            参数类别
	 * @return
	 */
	private static String execute(String interpreter, String filename,
			String... args) {
		List<String> command = new ArrayList<String>();
		command.add(interpreter);
		command.add(filename);

		if (args != null && args.length > 0) {
			for (String arg : args)
				command.add(arg);
		}

		ProcessBuilder builder = new ProcessBuilder();
		Map<String, String> env = builder.environment();
		env.put("LANG", "C");
		env.put("LC_ALL", "C");

		builder.command(command);

		ResultHolder result = TimedWaitFor.execute(builder,
				TimedWaitFor.DFLT_TIMEOUT);
		if (result.getExitValue() != 0) {
			if (result.getErr() != null && !result.getErr().isEmpty()) {
				String errStr = result.getErr();
				String dir = System.getProperty("user.dir")
						+ GradeConst.GRADE_NAMESPACE_PATH;
				return errStr.replaceAll(dir, "");
			}

			return "执行失败，错误码：" + result.getExitValue();
		}

		return result.getOut();
	}

	private static String escape(String str) {
		return str.replace("'", "\\'").replace("\"", "\\\"")
				.replace("\\", "\\\\");
	}

}
