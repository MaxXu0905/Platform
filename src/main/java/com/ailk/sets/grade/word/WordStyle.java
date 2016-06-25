package com.ailk.sets.grade.word;

enum WordStyle {

	// 定义标题、目录、列表相关的样式
	HEADING_1("heading 1"), HEADING_2("heading 2"), LIST_PARAGRAPH(
			"List Paragraph"), NOOP("");

	private String name;

	private WordStyle(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static WordStyle getWordStyle(String name) {
		if (name.equals(HEADING_1.name))
			return HEADING_1;
		else if (name.equals(HEADING_2.name))
			return HEADING_2;
		else if (name.equals(LIST_PARAGRAPH.name))
			return LIST_PARAGRAPH;
		else
			return NOOP;
	}

}
