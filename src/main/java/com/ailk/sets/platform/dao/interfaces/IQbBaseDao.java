package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.qb.QbBase;

public interface IQbBaseDao extends IBaseDao<QbBase> {

	/**
	 * 根据条件查询，按修改时间、创建时间倒序
	 * 
	 * @param createBy
	 *            创建者
	 * @param qbName
	 *            包含的部分题库名称
	 * @param page
	 *            页控制
	 * @return
	 */
	public List<QbBase> search(int createBy, String qbName, Page page);

	/**
	 * 获取创建者能看到的技术题库（包括百一题库）
	 * 
	 * @param createBy
	 *            创建者
	 * @return
	 */
	public List<QbBase> getQbBases(int createBy);

	/**
	 * 初始化面试题库、智力题题库
	 * 
	 * @param createBy
	 *            创建者
	 */
	public void initInterviewAndIntellQbBases(int createBy);

	/**
	 * 根据题库名称和创建者获取题库
	 * 
	 * @param qbName
	 *            题库名称
	 * @param createBy
	 *            创建者
	 * @return
	 */
	public QbBase getPaperQbBase(String qbName, int createBy);
	/**
	 * 获取自定义题库，不包括百一
	 * @param createBy
	 * @return
	 */
	public List<QbBase> getSelfQbBases(int createBy);
	
	/**
	 * 根据搜索条件获取题库总数
	 * @param param
	 * @return
	 */
	public Integer getQbBasesCount(int createBy, String qbName);
	

	/**
	 * 清除缓存
	 */
	public void evict();

}
