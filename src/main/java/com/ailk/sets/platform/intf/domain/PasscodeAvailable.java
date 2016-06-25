/**
 * author :  lipan
 * filename :  PasscodeAvailable.java
 * create_time : 2014年7月12日 上午10:41:28
 */
package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;

/**
 * @author : lipan
 * @create_time : 2014年7月12日 上午10:41:28
 * @desc : 校招微信测评passcode_available表的domain类
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class PasscodeAvailable implements Serializable
{
    private static final long serialVersionUID = 5242460967304950115L;

    private Integer id;
    private String passcode; // passcode
    private Integer status; // 是否可用，状态: 0-未使用, 1-已使用

    public String getPasscode()
    {
        return passcode;
    }

    public void setPasscode(String passcode)
    {
        this.passcode = passcode;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

}
