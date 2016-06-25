package com.ailk.sets.grade.word;

public interface IWordGenerator {

	/**
	 * 生成试卷
	 * 
	 * @param paperWord
	 *            试卷结构
	 * @param createdBy
	 *            创建者
	 * @return 题库id
	 * @throws Exception
	 */
	public int generate(PaperWord paperWord, int createdBy) throws Exception;

}
