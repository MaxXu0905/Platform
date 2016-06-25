package com.ailk.sets.grade.excel.intf;

import com.ailk.sets.grade.intf.ExportPositionResponse;
import com.ailk.sets.grade.intf.GetGroupResponse;
import com.ailk.sets.grade.intf.GetQuestionResponse;
import com.ailk.sets.grade.intf.LoadResponse;
import com.ailk.sets.platform.intf.model.param.GetReportParam;

public interface IExportExcel {

	/**
	 * 导出Excel格式的题库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbId
	 *            题库ID
	 * @param category
	 *            类别
	 * @param isXSSF
	 *            是否为xlsx格式
	 * @return
	 * @throws Exception
	 */
	public byte[] exportExcel(int createBy, Integer qbId, int category,
			boolean isXSSF) throws Exception;

	/**
	 * 导出试卷模板
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param isXSSF
	 *            是否为xlsx格式
	 * @return
	 * @throws Exception
	 */
	public byte[] exportPaper(int paperId, boolean isXSSF) throws Exception;

	/**
	 * 是否有错误记录
	 * 
	 * @param qbId
	 *            题库ID
	 * @return
	 */
	public boolean isEmpty(int qbId);

	/**
	 * 导出错误题库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbId
	 *            题库ID
	 * @return
	 * @throws Exception
	 */
	public LoadResponse getErrorQb(int createBy, int qbId) throws Exception;

	/**
	 * 导出Excel格式的错误题库
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbId
	 *            题库ID
	 * @param isXSSF
	 *            是否为xlsx格式
	 * @return
	 * @throws Exception
	 */
	public byte[] exportErrorExcel(int createBy, int qbId, boolean isXSSF)
			throws Exception;

	/**
	 * 获取试题
	 * 
	 * @param questionId
	 *            试题ID
	 * @return
	 */
	public GetQuestionResponse getQuestion(long questionId);

	/**
	 * 获取题组
	 * 
	 * @param groupId
	 *            组ID
	 * @return
	 */
	public GetGroupResponse getGroup(long groupId);

	/**
	 * 导出职位相关的测评
	 * 
	 * @param positionId
	 *            职位ID
	 * @return
	 * @throws Exception
	 */
	public ExportPositionResponse exportPosition(GetReportParam param)
			throws Exception;

}
