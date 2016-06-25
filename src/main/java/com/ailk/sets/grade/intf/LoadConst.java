package com.ailk.sets.grade.intf;

public class LoadConst {

	// 内部使用的表格
	public static final int SHEET_TYPE_INTERNAL_CHOICE = 0;
	public static final int SHEET_TYPE_INTERNAL_PROGRAM = 1;
	public static final int SHEET_TYPE_INTERNAL_EXTRA_PROGRAM = 2;

	// 外部导入的表格
	public static final int SHEET_TYPE_EXTERNAL_MIN = 3;
	public static final int SHEET_TYPE_TECH_CHOICE = 3;
	public static final int SHEET_TYPE_PROGRAM = 4;
	public static final int SHEET_TYPE_TECH_ESSAY = 5;
	public static final int SHEET_TYPE_INTEL_CHOICE = 6;
	public static final int SHEET_TYPE_INTEL_ESSAY = 7;
	public static final int SHEET_TYPE_INTERVIEW = 8;
	public static final int SHEET_TYPE_EXTERNAL_MAX = 8;

	public static final int TYPE_SUCCESS = 0; // 成功
	public static final int TYPE_SIMILARITY = 1; // 相似度
	public static final int TYPE_TIME = 2; // 时间调整
	public static final int TYPE_FORMAT = 3; // 格式错误
	public static final int TYPE_IGNORED = 4; // 忽略

}
