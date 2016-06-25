package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.grade.intf.report.InterviewInfo.Mapping;
import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.domain.Candidate;

/**
 * Candidate模型dao
 * 
 * @author 毕希研
 * 
 */
public interface ICandidateDao extends IBaseDao<Candidate> {

	public Candidate getByTestId(long testId);

	/**
	 * 根据应聘人的姓名和邮箱获取应聘人id
	 * 
	 * @param name
	 * @param email
	 * @return
	 */
	public int getCandidateId(String name, String email);

	/**
	 * 删除应聘者的openId
	 * 
	 * @param openId
	 * @return
	 */
	public int removeOpenId(String openId);

	/**
	 * 根据应聘人的姓名,邮箱去查询用户是否绑定过
	 * 
	 * @param name
	 *            姓名
	 * @param email
	 *            邮箱
	 * @param openId
	 *            openid
	 * @return
	 */
	public Candidate getCandidate(String userName, String email,
			String openId);

	public List<Mapping> getMapping(String sql);
	
	
	public Candidate getCandidateByOpenId(String openId);

	public void evict();

}
