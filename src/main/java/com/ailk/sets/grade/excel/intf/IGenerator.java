package com.ailk.sets.grade.excel.intf;

import com.ailk.sets.grade.grade.config.QuestionContent;

public interface IGenerator {

	/**
	 * 生成配置文件
	 * @return 题目内容
	 * @throws Exception
	 */
	public QuestionContent generate() throws Exception;
	
}
