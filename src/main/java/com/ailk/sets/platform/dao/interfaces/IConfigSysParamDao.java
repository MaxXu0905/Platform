package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.domain.ConfigSysParameters;
import com.ailk.sets.platform.exception.PFDaoException;

public interface IConfigSysParamDao {
	public ConfigSysParameters getConfigSysParameters(String name);
	
	public String getConfigParamValue(String name) throws PFDaoException;
	
	public ConfigSysParameters getConfigSysParametersByOpenSession(String name) ;
	
	public int getIntValueByName(String name,int defaultValue);
}
