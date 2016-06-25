package com.ailk.sets.grade.intf.report;

public interface IReportConfig {

	public static final String[] ITEM_NAMES = { "技能选择题", "编程题", "技能问答题", "智力题",
			"面试得分" };
	public static final int REPORT_COLUMN_UNKNOWN = -1;
	public static final int REPORT_COLUMN_BASIC = 0;
	public static final int REPORT_COLUMN_PROGRAM = 1;
	public static final int REPORT_COLUMN_TECH_ESSAY = 2;
	public static final int REPORT_COLUMN_INTELLIGENCE = 3;
	public static final int REPORT_COLUMN_QUALITY = 4;

	public int getReportIndex(int questionType, int category);

}
