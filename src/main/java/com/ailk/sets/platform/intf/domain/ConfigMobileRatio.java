/**
 * author :  lipan
 * filename :  configMobileRatio.java
 * create_time : 2014年11月12日 下午5:04:23
 */
package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;

/**
 * @author : lipan
 * @create_time : 2014年11月12日 下午5:04:23
 * @desc : 城市终端占比
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class ConfigMobileRatio implements Serializable
{
    private static final long serialVersionUID = -132969406009410870L;
    // 城市
    private String city;
    // 移动终端占比
    private Double mobileRatio;
    // 联通终端占比
    private Double unicomRatio;
    // 电信终端占比
    private Double telecomRatio;

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Double getMobileRatio()
    {
        return mobileRatio;
    }

    public void setMobileRatio(Double mobileRatio)
    {
        this.mobileRatio = mobileRatio;
    }

    public Double getUnicomRatio()
    {
        return unicomRatio;
    }

    public void setUnicomRatio(Double unicomRatio)
    {
        this.unicomRatio = unicomRatio;
    }

    public Double getTelecomRatio()
    {
        return telecomRatio;
    }

    public void setTelecomRatio(Double telecomRatio)
    {
        this.telecomRatio = telecomRatio;
    }

}
