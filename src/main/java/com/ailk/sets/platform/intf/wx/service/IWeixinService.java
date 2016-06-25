/**
 * author :  lipan
 * filename :  IWeixin.java
 * create_time : 2014年7月28日 下午9:48:45
 */
package com.ailk.sets.platform.intf.wx.service;

import com.ailk.sets.platform.intf.model.wx.model.WXAuthInfo;

/**
 * @author : lipan
 * @create_time : 2014年7月28日 下午9:48:45
 * @desc : 微信接口类
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public interface IWeixinService
{
    /**
     * 百一微测试(应聘者)微信号
     * 使用OAuth2.0授权后的得到的code获得授权信息(用户openId token ...)
     * 
     * @param code
     * @return
     * @throws Exception 
     */
    public WXAuthInfo getAuthInfoByCode(String code) throws Exception;
    
    /**
     * 百一测评(招聘者)微信号
     * 使用OAuth2.0授权后的得到的code获得授权信息(用户openId token ...)
     * 
     * @param code
     * @return
     * @throws Exception 
     */
    public WXAuthInfo getAuthInfoByCodeER(String code) throws Exception;
}
