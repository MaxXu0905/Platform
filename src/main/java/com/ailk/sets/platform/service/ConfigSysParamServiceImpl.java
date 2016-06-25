/**
 * author :  lipan
 * filename :  ConfigSysParamServiceImpl.java
 * create_time : 2014年8月20日 下午5:02:51
 */
package com.ailk.sets.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.intf.empl.service.IConfigSysParamService;

/**
 * @author : lipan
 * @create_time : 2014年8月20日 下午5:02:51
 * @desc : 系统常量获取
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
@Transactional(rollbackFor = Exception.class)
public class ConfigSysParamServiceImpl implements IConfigSysParamService
{
//    private Logger logger = LoggerFactory.getLogger(ConfigSysParamServiceImpl.class);
    @Autowired
    private IConfigSysParamDao iConfigSysParamDao;
    
    @Override
    public String getConfigSysParam(String key)
    {
        return iConfigSysParamDao.getConfigSysParametersByOpenSession(key).getParamValue();
    }

}
