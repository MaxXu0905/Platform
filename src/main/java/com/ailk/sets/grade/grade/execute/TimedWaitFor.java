package com.ailk.sets.grade.grade.execute;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TimedWaitFor {

	public static final int DFLT_TIMEOUT = 15000;

	/**
	 * 执行命令
	 * 
	 * @param builder
	 *            进程构造器
	 * @param timeout
	 *            超时时长，单位毫秒
	 * @return 进程结果
	 */
	public static ResultHolder execute(ProcessBuilder builder, long timeout) {
		ResultHolder result = new ResultHolder();
		File outFile = null;
		File errFile = null;
		Process process = null;

		try {
			outFile = File.createTempFile("Grade", ".out");
			builder.redirectOutput(outFile);

			errFile = File.createTempFile("Grade", ".err");
			builder.redirectError(errFile);

			process = builder.start();

			long expire = System.currentTimeMillis() + timeout;
			while (System.currentTimeMillis() <= expire) {
				try {
					result.setExitValue(process.exitValue());
					result.setOut(outFile);
					result.setErr(errFile);
					return result;
				} catch (IllegalThreadStateException e) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
					}
				}
			}

			result.setExitValue(1);
			result.setErr("程序执行超时");
		} catch (IOException e) {
			result.setExitValue(1);
			result.setErr(e.getMessage());
		} finally {
			if (outFile != null)
				outFile.delete();
			if (errFile != null)
				errFile.delete();
			if (process != null)
				process.destroy();
		}

		return result;
	}

	/**
	 * 执行命令
	 * 
	 * @param command
	 *            命令
	 * @param timeout
	 *            超时时长，单位毫秒
	 * @return 进程结果
	 */
	public static ResultHolder execute(List<String> command, long timeout) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);

		return execute(builder, timeout);
	}

}
