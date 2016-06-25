package com.ailk.sets.grade.intf;

import java.util.Set;

import com.ailk.sets.platform.intf.model.param.GetReportParam;

public interface ILoadService {

	/**
	 * 获取试题的建议时间
	 * 
	 * @param request
	 *            请求
	 * @return
	 */
	public GetSuggestTimeResponse getSuggestTime(GetSuggestTimeRequest request);

	/**
	 * 加载excel文件中的题目到数据库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbId
	 *            题库ID
	 * @param similarityLimit
	 *            相似度阀值
	 * @param checkTime
	 *            是否检查时长
	 * @param data
	 *            数据
	 * @return
	 */
	public LoadResponse loadFile(int createBy, int qbId,
			double similarityLimit, boolean checkTime, byte[] data)
			throws Exception;

	/**
	 * 加载excel文件中的题目到数据库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbName
	 *            题库名称
	 * @param checkTime
	 *            是否检查时长
	 * @param testType
	 *            测试类型（校招、社招）
	 * @param data
	 *            数据
	 * @return
	 */
	public LoadResponse loadPaper(int createBy, String qbName,
			boolean checkTime, int testType, byte[] data) throws Exception;

	/**
	 * 加载Word文件中的试卷到数据库
	 * 
	 * @param createBy
	 *            创建者
	 * @param testType
	 *            测试类型（校招、社招）
	 * @param data
	 *            数据
	 * @return
	 * @throws Exception
	 */
	public LoadWordResponse loadPaperWord(int createBy, int testType,
			byte[] data) throws Exception;

	/**
	 * 下载错误文件
	 * 
	 * @param filename
	 *            文件名
	 * @return 字节流
	 * @throws Exception
	 */
	public byte[] downloadPaperWord(String filename) throws Exception;

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
	 */
	public LoadResponse loadQuestions(int createBy, int qbId,
			Set<Long> skipQids, double similarityLimit, boolean checkTime,
			LoadRequest request) throws Exception;

	/**
	 * 导出题库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbId
	 *            题库ID
	 * @return
	 */
	public ExportQbResponse exportQb(int createBy, int qbId) throws Exception;

	/**
	 * 判断题库是否有错
	 * 
	 * @param qbId
	 *            题库ID
	 * @return
	 * @throws Exception
	 */
	public HasErrorQbResponse hasErrorQb(int qbId) throws Exception;

	/**
	 * 删除导入错误的题
	 * 
	 * @param serialNo
	 *            错误序列号
	 * @return
	 * @throws Exception
	 */
	public BaseResponse deleteErrorQuestion(int serialNo) throws Exception;

	/**
	 * 输出错误题库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbId
	 *            题库ID
	 * @return
	 */
	public LoadResponse getErrorQb(int createBy, int qbId) throws Exception;

	/**
	 * 导出错误题库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbId
	 *            题库ID
	 * @return
	 */
	public ExportErrorQbResponse exportErrorQb(int createBy, int qbId)
			throws Exception;

	/**
	 * 获取试题
	 * 
	 * @param questionId
	 *            试题ID
	 * 
	 * @return
	 */
	public GetQuestionResponse getQuestion(long questionId) throws Exception;

	/**
	 * 获取题组
	 * 
	 * @param groupId
	 *            题组ID
	 * @return
	 */
	public GetGroupResponse getGroup(long groupId) throws Exception;

	/**
	 * 从excel获取宣讲会
	 * 
	 * @param data
	 *            数据
	 * @return
	 */
	public LoadTalksResponse loadTalks(byte[] data) throws Exception;

	/**
	 * 获取测试计划
	 * 
	 * @param request
	 *            请求
	 * @return
	 * @throws Exception
	 */
	public GetTestPlanResponse getTestPlan(GetTestPlanRequest request);

	/**
	 * 导出报告
	 * 
	 * @param testId
	 *            测试ID
	 * @param passport
	 *            通行证
	 * @param baseUrl
	 *            基础URL
	 * @return
	 */
	public ExportReportResponse exportReport(long testId, String passport,
			String baseUrl);

	/**
	 * 导出职位相关的报告
	 * 
	 * @param positionId
	 *            职位ID
	 * @return
	 */
	public ExportPositionResponse exportPosition(GetReportParam param);

}
