package com.ailk.sets.grade.grade.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TraceManager {

	private static final int MAX_ROWS = 20;

	public static String getFullTrace(Throwable e) {
		StringWriter stringWriter = null;
		PrintWriter writer = null;
		try {
			stringWriter = new StringWriter();
			writer = new PrintWriter(stringWriter);
			e.printStackTrace(writer);
			StringBuffer buffer = stringWriter.getBuffer();
			return buffer.toString();
		} finally {
			try {
				writer.close();
			} catch (Exception e1) {
			}

			try {
				stringWriter.close();
			} catch (Exception e1) {
			}
		}
	}

	public static String getTrace(Throwable e) {
		StringBuffer buffer = new StringBuffer();
		StackTraceElement[] stackTraceElements = e.getStackTrace();

		buffer.append(stackTraceElements[0].getClassName());
		buffer.append(" - ");
		buffer.append(e.getClass().getCanonicalName());
		if (e.getMessage() != null) {
			buffer.append(": ");
			buffer.append(e.getMessage());
		}

		int rows = 0;
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			buffer.append("\n\tat ");
			buffer.append(stackTraceElement.getClassName());
			buffer.append(".");
			buffer.append(stackTraceElement.getMethodName());
			buffer.append("(");
			buffer.append(stackTraceElement.getFileName());
			buffer.append(":");
			buffer.append(stackTraceElement.getLineNumber());
			buffer.append(")");

			if (++rows == MAX_ROWS)
				break;
		}

		if (stackTraceElements.length > MAX_ROWS) {
			buffer.append("\n\t... " + (stackTraceElements.length - MAX_ROWS)
					+ " more");
		}

		if (e.getCause() != null) {
			Throwable cause = e.getCause();

			buffer.append("\nCaused by: ");
			buffer.append(cause.getClass().getCanonicalName());
			if (cause.getMessage() != null) {
				buffer.append(": ");
				buffer.append(cause.getMessage());
			}

			rows = 0;
			for (StackTraceElement stackTraceElement : cause.getStackTrace()) {
				buffer.append("\n\tat ");
				buffer.append(stackTraceElement.getClassName());
				buffer.append(".");
				buffer.append(stackTraceElement.getMethodName());
				buffer.append("(");
				buffer.append(stackTraceElement.getFileName());
				buffer.append(":");
				buffer.append(stackTraceElement.getLineNumber());
				buffer.append(")");

				if (++rows == MAX_ROWS)
					break;
			}

			if (cause.getStackTrace().length > MAX_ROWS) {
				buffer.append("\n\t... "
						+ (cause.getStackTrace().length - MAX_ROWS) + " more");
			}
		}

		return buffer.toString();
	}
}
