package com.ailk.sets.platform.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;

/**
 * 未来考虑加入到缓存中 系统配置表的dao访问接口
 * 
 * @author 毕希研
 * 
 */
public interface IConfigDao extends IBaseDao<ConfigCodeName>{

	/**
	 * 根据代码类型获取配置代码
	 * 
	 * @param codeType
	 * @return
	 * @throws PFDaoException
	 */
	public List<ConfigCodeName> getConfigCode(String codeType);

	/**
	 * 根据代码类型获取配置代码映射
	 * 
	 * @param codeType
	 * @return
	 * @throws PFDaoException
	 */
	public Map<String, String> getConfigCodeMap(String codeType);

	/**
	 * 获取配置代码名称
	 * 
	 * @param codeType
	 * @param codeId
	 * @return
	 * @throws PFDaoException
	 */
	public String getConfigCodeName(String codeType, String codeId);
	/**
	 * 获取配置代码
	 * @param codeType
	 * @param codeId
	 * @return
	 */
	public ConfigCodeName getConfigCode(String codeType, String codeId);
}
