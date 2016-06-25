package com.ailk.sets.grade.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Env {

	private static String gradeRoot;
	private static String examRoot;
	private static String subjectRoot;

	static {
		InputStream in = null;
		Properties properties = new Properties();
		try {
			in = Env.class.getClassLoader().getResourceAsStream(
					"platform.properties");
			properties.load(in);

			gradeRoot = properties.getProperty("grade.dir.root");
			examRoot = properties.getProperty("grade.dir.exam");
			subjectRoot = properties.getProperty("grade.dir.subject");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

	public static String getGradeRoot() {
		return gradeRoot;
	}

	public static String getExamRoot() {
		return examRoot;
	}

	public static String getSubjectRoot() {
		return subjectRoot;
	}

	/**
	 * 环境变量替换
	 * 
	 * @param input
	 * @param map
	 * @return
	 */
	public static String expandVar(String input, Map<String, String> map) {
		StringBuilder builder = new StringBuilder();
		boolean preIsDollor = false;

		for (int i = 0; i < input.length(); i++) {
			int ch = input.codePointAt(i);

			switch (ch) {
			case '$':
				if (preIsDollor) {
					builder.append("$");
					preIsDollor = false;
				} else {
					preIsDollor = true;
				}
				break;
			case '{':
				if (preIsDollor) {
					int pos = input.indexOf("}", i + 1);
					if (pos == -1) {
						builder.append(input.substring(i - 1));
						i = input.length();
					} else {
						String key = input.substring(i + 1, pos);
						String[] fields = key.split(",");
						boolean first = true;

						for (String field : fields) {
							String value = map.get(field.trim());
							if (value != null && !value.isEmpty()) {
								if (first)
									first = false;
								else
									builder.append("，");
								builder.append(value);
							}
						}
						i = pos;
					}
					preIsDollor = false;
				} else {
					builder.append("{");
				}
				break;
			default:
				if (preIsDollor) {
					builder.append("$");
					preIsDollor = false;
				}
				builder.append((char) ch);
				break;
			}
		}

		return builder.toString();
	}

	public static void getVar(String input, Set<String> skipSet,
			Set<String> result) {
		boolean preIsDollor = false;

		for (int i = 0; i < input.length(); i++) {
			int ch = input.codePointAt(i);

			switch (ch) {
			case '$':
				if (preIsDollor)
					preIsDollor = false;
				else
					preIsDollor = true;
				break;
			case '{':
				if (preIsDollor) {
					int pos = input.indexOf("}", i + 1);
					if (pos == -1) {
						i = input.length();
					} else {
						String key = input.substring(i + 1, pos);
						String[] fields = key.split(",");
						for (String field : fields) {
							field = field.trim();
							if (!skipSet.contains(field))
								result.add(field);
						}
						i = pos;
					}
					preIsDollor = false;
				}
				break;
			default:
				preIsDollor = false;
				break;
			}
		}
	}

}
