package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.exception.PFServiceException;

/**
 * 系统配置服务
 * 
 * @author 毕希研
 * 
 */
public interface IConfig {
	/**
	 * 根据代码类型获取配置码
	 * @param codeType
	 * @return
	 * @throws PFServiceException 
	 */
	public List<ConfigCodeName> getConfigCode(String codeType) throws PFServiceException;
}
