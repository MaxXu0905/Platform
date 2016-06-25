package com.ailk.sets.platform.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.ConfigReport;
import com.ailk.sets.platform.intf.empl.domain.ConfigReportPart;
import com.ailk.sets.platform.intf.empl.domain.PaperGrade;
import com.ailk.sets.platform.intf.empl.domain.PaperInstancePartInfo;
import com.ailk.sets.platform.intf.model.param.GetReportParam;

/**
 * 报告操作dao
 * 
 * @author 毕希研
 * 
 */
public interface IReportDao extends IBaseDao<CandidateReport>{

	/**
	 * @param paperInstId
	 * @return
	 */
	public List<PaperGrade> getPaperGrade(int paperInstId);

	/**
	 * 获取试卷部分信息,只包括客观题和自己的主观题 ,不包括附加题和面试题.
	 * 
	 * @param id
	 * @param type
	 *            1实例 2试卷模板
	 * @return
	 */
	public List<PaperInstancePartInfo> getPaperInstancePartInfo(int id, int type);

	/**
	 * 获取推荐结果
	 * 
	 * @param testId
	 * @return
	 */
	public int getTestResult(long testId);

	/**
	 * 根据试卷实例id获取题目id，只包括客观题和编程题
	 * 
	 * @param paperInstanceId
	 * @param type
	 *            1实例 2试卷模板
	 * @return
	 */
	// public List<Integer> getPaperInstanceQuestionIds(int paperInstanceId,int type);

	/**
	 * 获取报告配置
	 * 
	 * @return
	 */
	public List<ConfigReport> getConfigReport();

	/**
	 * 获取报告配置部分
	 * 
	 * @return
	 */
	public List<ConfigReportPart> getConfigReportPart();

	/**
	 * 根据应聘人id获取报告
	 * 
	 * @param candidateId
	 * @return
	 */
	public List<CandidateReport> getReportByCandidate(int candidateId);

	/**
	 * @param positionId
	 * @param testResult
	 * @param page
	 * @return
	 * @throws PFDaoException
	 */
	public List<CandidateReport> getReport(GetReportParam param);

	/**
	 * 设置报告状态
	 * 
	 * @param testId
	 * @param state
	 */
	public void setReportState(long testId, int state);

	/**
	 * 获取题目汇总信息
	 * 
	 * @param sql
	 * @return
	 */
	public Map<String, Integer> getCountQuestionNumber(String inStr);
	
	/**
	 * 获取题目汇总信息
	 * 
	 * @param sql
	 * @return
	 */
	public Map<String, Integer> getCountQuestionNumberOfSubject(String inStr);
	
	/**
	 * 获取样例体验报告
	 * @param positionId
	 * @return
	 */
	public List<CandidateReport> getCandidateReportOfSample(int positionId);
	
	/**
	 * 获取大于某一数量的报告数
	 * @param score
	 * @return
	 */
	public  List<CandidateReport> getCandidateReportsLargerThanScore(double score, int positionId);


}
