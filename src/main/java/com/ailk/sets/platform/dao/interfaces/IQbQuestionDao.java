package com.ailk.sets.platform.dao.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.QuestionTypeCountInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.param.GetQbGroupsParam;
import com.ailk.sets.platform.intf.model.param.GetQbQuestionsParam;
import com.ailk.sets.platform.intf.model.question.QbGroupInfo;
import com.ailk.sets.platform.intf.model.question.QbQuestionInfo;

public interface IQbQuestionDao extends IBaseDao<QbQuestion> {

	public int getSumPointsForQuestion(List<Long> ids);

	public List<PaperQuestionToSkills> getProgramQuestionsByLanguangeAndDegree(
			String programLanguage, int degree);

	public QbQuestion getTestSubjectQuestion(String programLanguage);

	public QbQuestion getTestInterviewQuestion();

	public List<PaperQuestionToSkills> getMotiObjectQuestions(
			List<String> skillIds, List<Integer> degrees);

	public QbQuestion getEntityWithoutCache(Serializable questionId);

	public List<QbQuestion> getList();

	public List<QbQuestion> getListByLanguage(String questionType,
			String programLanguage);

	public List<QbQuestion> getListByCreator(int createBy, String condition);

	public List<QbQuestion> getListByParams(int createBy, Integer qbId,
			int category);

	public List<QbQuestion> getListByQbId(int qbId);

	public long getNextQid();

	/**
	 * 搜索题目
	 * 
	 * @param employerId
	 * @param param
	 * @return
	 */
	public List<QbQuestionInfo> searchQuestions(int employerId,
			GetQbQuestionsParam param);

	/**
	 * 获得某题库下的所有选择题
	 * 
	 * @param qbId
	 * @return
	 */
	public List<QbQuestion> getChoiceQuestion(int qbId);

	/**
	 * 获取某题库下的所有编程题
	 * 
	 * @param qbId
	 * @return
	 */
	public List<QbQuestion> getProgramQuestion(int qbId);

	/**
	 * 查询某题库下的面试题组
	 * 
	 * @param employerId
	 *            创建者
	 * @param param
	 *            参数
	 * @return
	 */
	public List<QbGroupInfo> searchGroups(int employerId, GetQbGroupsParam param);

	/**
	 * 根据面试题组id获取面试题集合
	 * 
	 * @param qId
	 * @return
	 */
	public List<QbQuestion> getQbQuestionsByGroup(long qId);

	/**
	 * 随机一道可以推荐的题
	 * 
	 * @param category
	 * @return
	 */
	public QbQuestion getRandomQuestion(String category);

	/**
	 * 判断一道题是否来自系统
	 * 
	 * @param questionId
	 * @return
	 */
	// public boolean isFromSystem(long questionId);

	/**
	 * 废弃题目
	 * 
	 * @param skillId
	 */
	public void discardQuestion(String skillId);

	/**
	 * 按题目类型计算某个题库题目数
	 * 
	 * @param qbId
	 * @return
	 */
	public Map<String, Long> getQuestionNum(int qbId);

	/**
	 * 获取选择题数
	 * 
	 * @param qbId
	 * @return
	 */
	public Long getChoiceNum(int qbId);

	/**
	 * 获取编程题数
	 * 
	 * @param qbId
	 * @return
	 */
	public Long getProgramNum(int qbId);

	/**
	 * 获取问答题数
	 * 
	 * @param qbId
	 * @return
	 */
	public Long getEssayNum(int qbId);

	/**
	 * 获取题目总数
	 * 
	 * @param qbId
	 * @return
	 */
	public Long getTotalNum(int qbId);

	/**
	 * 更新题目的状态
	 * 
	 * @param questionId
	 * @param state
	 */
	public void updateState(long questionId, int state);

	/**
	 * 
	 * @param qbIds
	 * @return
	 */
	public Map<Integer, QuestionTypeCountInfo> getQuestionTypeCountInfo(
			List<Integer> qbIds);

	/**
	 * 根据搜索条件获取题目总数
	 * 
	 * @param employerId
	 * @param param
	 * @return
	 * @throws PFServiceException
	 */
	public Integer getQbQuestionsCount(int employerId, GetQbQuestionsParam param)
			throws PFServiceException;

	/**
	 * 此技能是否有题目
	 * 
	 * @param skillId
	 * @return
	 */
	public boolean hasQuestionOfSelfSkill(String skillId);

	/**
	 * 获取题目详情
	 * 
	 * @param qbQuestionInfo
	 * @param qbQuestion
	 * @param needDetail
	 * @return
	 */
	public QbQuestionInfo getQbQuestionInfo(QbQuestionInfo qbQuestionInfo,
			QbQuestion qbQuestion, boolean needDetail);

	public List<QbQuestion> getListByPaperId(int paperId);

	public void evict();

}
