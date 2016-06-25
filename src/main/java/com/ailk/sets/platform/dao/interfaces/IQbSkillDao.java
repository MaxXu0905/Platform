package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.model.qb.QbSkill;

public interface IQbSkillDao extends IBaseDao<QbSkill> {

	/**
	 * 获取题目列表的技能对象
	 * 
	 * @param qids
	 *            题目列表
	 * @return
	 */
	public List<QbSkill> getQbSkills(List<Long> qids);

	/**
	 * 获取题目的技能对象
	 * 
	 * @param qid
	 *            题目
	 * @return
	 */
	public List<QbSkill> getQbSkills(long qid);

	/**
	 * 获取题库的最小等级技能列表
	 * 
	 * @param qbId
	 *            题库
	 * @return
	 */
	public List<QbSkill> getQbSkillsOfLowLevel(int qbId);

	/**
	 * 根据名称获取技能对象
	 * @param skillName 技能名称
	 * @param parentId 父技能
	 * @param qbId 题库ID
	 * @return
	 */
	public QbSkill getByName(String skillName, String parentId, int qbId);

	/**
	 * 获取下一个技能ID
	 * @return
	 */
	public String getNextSkillId();

	/**
	 * 清空缓存
	 */
	public void evict();

}
