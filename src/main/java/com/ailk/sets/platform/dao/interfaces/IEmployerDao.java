package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.model.employer.LoginInfo;
import com.ailk.sets.platform.intf.model.employer.RegisterInfo;

/**
 * 招聘人表访问接口
 * 
 * @author 毕希研
 * 
 */
public interface IEmployerDao extends IBaseDao<Employer> {

	/**
	 * 验证招聘人的账户
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws PFDaoException
	 */
	public LoginInfo certify(String username, String password);

	/**
	 * 用户注册
	 * 
	 * @param employer
	 * @return
	 * @throws PFDaoException
	 */
	public RegisterInfo register(Employer employer);

	/**
	 * 删除招聘者的openId
	 * 
	 * @param openId
	 */
	public void removeOpenId(String openId);
	
	public Employer getEmployerByOpenId(String openId, int employerId);
	
	public Employer getEmployerByOpenId(String openId);
	
	/**
	 * 查询招聘者账号是否存在
	 * 
	 * @param openId
	 * */
	
	public Employer selectEmployerByOpenId(String openId);
	
	/**
	 * 
	 * @param email
	 * @param acctType
	 * @return
	 */
	public Employer getEmployerByEmail(String email, Integer acctType);
	
	/**
	 * 根据token获取用户
	 * @param employerToken
	 * @return
	 */
	public Employer getEmployerByToken(String employerToken);
}
