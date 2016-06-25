package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.QbQuestionSkill;

public interface IQbQuestionSkillDao extends IBaseDao<QbQuestionSkill> {

	/**
	 * 获取题目列表的技能对象列表
	 * 
	 * @param qids
	 *            题目列表
	 * @return
	 */
	public List<QbQuestionSkill> getQbQuestionSkills(List<Long> qids);

	/**
	 * 获取题目列表的技能ID列表（去重）
	 * 
	 * @param qids
	 *            题目列表
	 * @return
	 */
	public String[] getQbQuestionSkillIds(List<Long> qids);

	/**
	 * 获取题目的技能对象列表
	 * 
	 * @param qid
	 *            题目
	 * @return
	 */
	public List<QbQuestionSkill> getSkills(long qid);

	/**
	 * 获取题目的技能Id列表
	 * 
	 * @param qid
	 *            题目
	 * @return
	 */
	public List<String> getSkillIds(long qid);

	/**
	 * 删除题目相关的技能
	 * 
	 * @param questionId
	 *            题目
	 */
	public void deleteByQuestionId(long questionId);
	
	/**
	 * 获取题目ID列表
	 * @param skillId 技能ID
	 * @return
	 */
	public List<Long> getQuestionIds(String skillId);
	
	/**
	 * 清除缓存
	 */
	public void evict();

}
