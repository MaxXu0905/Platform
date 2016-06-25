/**
 * author :  lipan
 * filename :  OnlineExamReqResult.java
 * create_time : 2014年10月20日 下午5:34:06
 */
package com.ailk.sets.platform.intf.model.invatition;


/**
 * @author : lipan
 * @create_time : 2014年10月20日 下午5:34:06
 * @desc : 在线申测返回结果
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class OnlineExamReqResult{
    public static final String STATUS_SUCCESS = "1"; // 成功
    public static final String STATUS_REPEAT = "2"; //重复
    
    public OnlineExamReqResult()
    {
    }
    
    public OnlineExamReqResult(String status)
    {
        this.status = status;
    }

    public OnlineExamReqResult(String status, String passport)
    {
        this.status = status;
        this.passport = passport;
    }

    private String status; //1:成功；0失败；2已申测过
    private String passport; //考试口令
    
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getPassport()
    {
        return passport;
    }

    public void setPassport(String passport)
    {
        this.passport = passport;
    }
    
}
