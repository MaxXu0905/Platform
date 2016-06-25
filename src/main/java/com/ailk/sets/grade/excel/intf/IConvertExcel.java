package com.ailk.sets.grade.excel.intf;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.ailk.sets.grade.intf.LoadRequest;
import com.ailk.sets.grade.intf.LoadResponse;

public interface IConvertExcel {

	/**
	 * 从文件加载题目到数据库
	 * 
	 * @param in
	 *            输入流
	 * @param fileBeginId
	 *            文件开始ID
	 * @param createBy
	 *            创建者
	 * @param category
	 *            类别
	 * @param similarityLimit
	 *            相似度阀值
	 * @param checkTime
	 *            是否检查时长
	 * @param testType
	 *            测试类型（社招、校招）
	 * @param isXSSF
	 *            是否为xlsx格式
	 * @return
	 * @throws Exception
	 */
	public LoadResponse loadFile(InputStream in, Long fileBeginId,
			int createBy, Integer qbId, double similarityLimit,
			boolean checkTime, int testType, boolean isXSSF) throws Exception;

	/**
	 * 加载题目列表到数据库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbId
	 *            题库ID
	 * @param skipQids
	 *            不需做相识度检查的题目列表
	 * @param similarityLimit
	 *            相似度阀值
	 * @param checkTime
	 *            是否检查时长
	 * @param request
	 *            请求
	 * @return
	 * @throws Exception
	 */
	public LoadResponse loadQuestions(int createBy, int qbId,
			Set<Long> skipQids, double similarityLimit, boolean checkTime,
			LoadRequest request) throws Exception;

	/**
	 * 获取建议时长
	 * 
	 * @param title
	 *            标题
	 * @param options
	 *            选项列表，可空
	 * @return
	 */
	public int getSuggestTime(String title, List<String> options);

}
