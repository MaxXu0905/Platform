package com.ailk.sets.grade.grade.common;

public class DebugConfig {

	private static boolean keepTempFiles = false;

	public static boolean isKeepTempFiles() {
		return keepTempFiles;
	}

	public static void setKeepTempFiles(boolean keepTempFiles) {
		DebugConfig.keepTempFiles = keepTempFiles;
	}

}
