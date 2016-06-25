/**
 * author :  lipan
 * filename :  WXBaseInfo.java
 * create_time : 2014年7月28日 下午9:55:38
 */
package com.ailk.sets.platform.intf.model.wx.model;

/**
 * @author : lipan
 * @create_time : 2014年7月28日 下午9:55:38
 * @desc : 微信基本信息
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class WXBaseInfo
{
    private String appId;
    private String appSecret;
    
    public WXBaseInfo()
    {
    }
    
    public WXBaseInfo(String appId, String appSecret)
    {
        this.appId = appId;
        this.appSecret = appSecret;
    }
    public String getAppId()
    {
        return appId;
    }
    public void setAppId(String appId)
    {
        this.appId = appId;
    }
    public String getAppSecret()
    {
        return appSecret;
    }
    public void setAppSecret(String appSecret)
    {
        this.appSecret = appSecret;
    }
    
}
