/**
 * author :  lipan
 * filename :  ConfigMobileRatio.java
 * create_time : 2014年11月12日 下午5:17:55
 */
package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.domain.ConfigMobileRatio;

/**
 * @author : lipan
 * @create_time : 2014年11月12日 下午5:17:55
 * @desc : 城市运营商终端占比
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public interface IConfigMobileRatioDao extends IBaseDao<ConfigMobileRatio> 
{
    
    /**
     * 获得指定城市占比数据
     * @param city
     * @return
     */
    public ConfigMobileRatio getCityRatio(String city);
    
    /**
     * 获得默认的占比数据
     * @param city
     * @return
     */
    public ConfigMobileRatio getDefaultRatio();
}
