package com.ailk.sets.grade.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface IWordService {
	
	/**
	 * 分析Word文档，生成试卷结构
	 * 
	 * @param doc
	 *            Word文档
	 * @return 试卷结构
	 */
	public PaperWord execute(XWPFDocument doc);

}
