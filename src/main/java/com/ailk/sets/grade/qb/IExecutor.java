package com.ailk.sets.grade.qb;

import java.util.Map;

import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.grade.execute.ResultHolder;

/**
 * 根据配置，实例化、编译、运行等操作
 * 
 * @author xugq
 * 
 */
public interface IExecutor {

	/**
	 * 实例化
	 * 
	 * @param questionContent
	 *            原试题内容
	 * @throws Exception
	 */
	public void instantiate(QuestionContent questionContent) throws Exception;

	/**
	 * 编译
	 * 
	 * @param root
	 *            实例化题的根目录
	 * @param stage
	 *            阶段
	 * @throws Exception
	 */
	public ResultHolder compile(String root, int stage) throws Exception;

	/**
	 * 初始化
	 * 
	 * @param root
	 *            实例化题的根目录
	 * @param stage
	 *            阶段
	 * @throws Exception
	 */
	public ResultHolder init(String root, int stage) throws Exception;

	/**
	 * 部署
	 * 
	 * @param root
	 *            实例化题的根目录
	 * @param stage
	 *            阶段
	 * @throws Exception
	 */
	public ResultHolder deploy(String root, int stage) throws Exception;

	/**
	 * 获取执行前需要设置的环境变量列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getenv() throws Exception;

	/**
	 * 销毁
	 * 
	 * @param root
	 *            实例化题的根目录
	 * @param stage
	 *            阶段
	 * @throws Exception
	 */
	public ResultHolder destroy(String root, int stage) throws Exception;

	/**
	 * 执行
	 * 
	 * @param root
	 *            实例化题的根目录
	 * @param stage
	 *            阶段
	 * @param mode
	 *            模式
	 * @param seq
	 *            使用第几组测试数据，从0开始
	 * @throws Exception
	 * @return 执行结果
	 */
	public ResultHolder execute(String root, int stage, int mode, int seq)
			throws Exception;

	/**
	 * 执行
	 * 
	 * @param root
	 *            实例化题的根目录
	 * @param stage
	 *            阶段
	 * @param mode
	 *            模式
	 * @param arg
	 *            测试参数
	 * @throws Exception
	 * @return 执行结果
	 */
	public ResultHolder test(String root, int stage, int mode, String arg)
			throws Exception;

	/**
	 * 执行
	 * 
	 * @param root
	 *            实例化题的根目录
	 * @param stage
	 *            阶段
	 * @param mode
	 *            模式
	 * @param sampleId
	 *            使用第几组测试数据，从0开始
	 * @throws Exception
	 * @return 执行结果
	 */
	public ResultHolder testSample(String root, int stage, int mode,
			int sampleId) throws Exception;

}
