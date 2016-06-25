/**
 * author :  lipan
 * filename :  ConfigMobileRatioDaoImpl.java
 * create_time : 2014年11月12日 下午5:19:30
 */
package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigMobileRatioDao;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.ConfigMobileRatio;

/**
 * @author : lipan
 * @create_time : 2014年11月12日 下午5:19:30
 * @desc : 城市运营商终端占比
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
@Repository
public class ConfigMobileRatioDaoImpl extends BaseDaoImpl<ConfigMobileRatio> implements
        IConfigMobileRatioDao
{

    /**
     * 获得指定城市占比数据
     * 
     * @param city
     * @return
     */
    @Override
    public ConfigMobileRatio getCityRatio(String city)
    {
        return (ConfigMobileRatio) getSession()
                .createQuery("from ConfigMobileRatio where city=:city").setString("city", city)
                .uniqueResult();

    }

    /**
     * 获得默认的占比数据
     * 
     * @param city
     * @return
     */
    @Override
    public ConfigMobileRatio getDefaultRatio()
    {
        return (ConfigMobileRatio) getSession().createQuery(
                "from ConfigMobileRatio where city='DEFAULT'").uniqueResult();
    }

}
