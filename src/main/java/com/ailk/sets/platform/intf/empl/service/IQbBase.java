package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.grade.intf.LoadRequest;
import com.ailk.sets.grade.intf.LoadResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.PFCountInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.param.GetQbBasesParam;
import com.ailk.sets.platform.intf.model.param.GetQbGroupsParam;
import com.ailk.sets.platform.intf.model.param.GetQbQuestionsParam;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.ailk.sets.platform.intf.model.qb.QbBaseInfo;
import com.ailk.sets.platform.intf.model.qb.QbBaseResponse;
import com.ailk.sets.platform.intf.model.qb.QbProLangInfo;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.ailk.sets.platform.intf.model.qb.QbSkillInfo;
import com.ailk.sets.platform.intf.model.qb.QbSkillResponse;
import com.ailk.sets.platform.intf.model.qb.QbSkillStatistics;
import com.ailk.sets.platform.intf.model.question.QbGroupInfo;
import com.ailk.sets.platform.intf.model.question.QbQuestionInfo;

/**
 * 题库服务接口
 * 
 * @author 毕希研
 * 
 */
public interface IQbBase {

	/**
	 * 新建题库
	 * 
	 * @param employerId
	 * @param qbBase
	 * @return
	 * @throws PFServiceException
	 */
	public QbBaseResponse createQbBase(QbBase qbBase) throws PFServiceException;

	/**
	 * 获取题库名称
	 * 
	 * @param qbId
	 * @return
	 */
	public String getQbName(int qbId);

	/**
	 * 获取题库列表
	 * 
	 * @param param
	 * @return
	 * @throws PFServiceException
	 */
	public List<QbBaseInfo> getQbBases(GetQbBasesParam param)
			throws PFServiceException;

	/**
	 * 获取面试题组列表
	 * 
	 * @param employerId
	 * @param param
	 * @return
	 * @throws PFServiceException
	 */
	public List<QbGroupInfo> getQbGroups(int employerId, GetQbGroupsParam param)
			throws PFServiceException;

	/**
	 * 编辑试题
	 * 
	 * @param emloyerId
	 * @param qbId
	 * @param questionId
	 * @param similarityLimit
	 * @param checkTime
	 * @param request
	 * @return
	 * @throws PFServiceException
	 */
	public LoadResponse editQuestion(int emloyerId, int qbId, long questionId,
			double similarityLimit, boolean checkTime, LoadRequest request)
			throws PFServiceException;

	/**
	 * 获取题库题目信息
	 * 
	 * @param employerId
	 * @param param
	 * @return
	 * @throws PFServiceException
	 */
	public List<QbQuestionInfo> getQbQuestions(int employerId,
			GetQbQuestionsParam param) throws PFServiceException;

	/**
	 * 删除题目
	 * 
	 * @param qbId
	 * @param questionId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse deleteQuestion(int employerId, int qbId, long questionId)
			throws PFServiceException;

	/**
	 * 删除技能
	 * 
	 * @param employerId
	 * @param skillId
	 * @return
	 */
	public PFResponse deleteQbSkill(String skillId) throws PFServiceException;

	/**
	 * 修改技能名称
	 * 
	 * @param qbSkillInfo
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateQbSkill(QbSkillInfo qbSkillInfo)
			throws PFServiceException;

	/**
	 * 添加技能
	 * 
	 * @param qbSkillInfo
	 * @return
	 * @throws PFServiceException
	 */
	public QbSkillResponse createQbSkill(QbSkillInfo qbSkillInfo)
			throws PFServiceException;

	/**
	 * 获取题库的所有技能
	 * 
	 * @return
	 * @throws PFServiceException
	 */
	public List<QbSkill> getQbBaseSkills(int qbId) throws PFServiceException;

	/**
	 * 获取选择题的所有技能难度题数分布
	 * 
	 * @param qbId
	 * @return
	 * @throws PFServiceException
	 */
	public QbSkillStatistics getSkillLevelNums(int qbId)
			throws PFServiceException;

	/**
	 * 获取编程题按编程语言难度题数分布
	 * 
	 * @param qbId
	 * @return
	 * @throws PFServiceException
	 */
	public QbProLangInfo getProgramLevelNums(int qbId)
			throws PFServiceException;

	/**
	 * 更新题库的编辑时间
	 * 
	 * @param qbId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateModifyDate(int qbId) throws PFServiceException;
	/**
	 * 根据搜索条件获取题库总数
	 * @param param
	 * @return
	 */
	public PFCountInfo getQbBasesCount(GetQbBasesParam param);
	
	
	/**
	 * 根据搜索条件获取题目总数
	 * @param employerId
	 * @param param
	 * @return
	 * @throws PFServiceException
	 */
	public PFCountInfo getQbQuestionsCount(int employerId,
			GetQbQuestionsParam param) throws PFServiceException;
	
	/**
	 * 根据题库id获取统计信息
	 * @param qbId
	 * @return
	 * @throws PFServiceException
	 */
	public 	List<PaperQuestionTypeInfo> getPaperQuestionTypeInfoByQbId(int qbId)  throws PFServiceException;
}
