/**
 * author :  lipan
 * filename :  WXErrorInfo.java
 * create_time : 2014年7月29日 上午8:46:31
 */
package com.ailk.sets.platform.intf.model.wx.model;

/**
 * @author : lipan
 * @create_time : 2014年7月29日 上午8:46:31
 * @desc : 微信请求异常信息
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class WXErrorInfo
{
    private String errcode;
    private String errmsg;
    
    public String getErrcode()
    {
        return errcode;
    }
    public void setErrcode(String errcode)
    {
        this.errcode = errcode;
    }
    public String getErrmsg()
    {
        return errmsg;
    }
    public void setErrmsg(String errmsg)
    {
        this.errmsg = errmsg;
    }
    
}
