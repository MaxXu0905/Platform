/**
 * author :  lipan
 * filename :  SignUpSetsRsp.java
 * create_time : 2014年10月15日 下午3:42:01
 */
package com.ailk.sets.platform.intf.model.campus;

import java.io.Serializable;
import java.sql.Timestamp;

import com.ailk.sets.grade.intf.BaseResponse;

/**
 * @author : lipan
 * @create_time : 2014年10月15日 下午3:42:01
 * @desc : 报名在线考试设置响应
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class SignUpOnlineSetsRsp extends BaseResponse implements Serializable
{
    private static final long serialVersionUID = -26033525803872199L;

    private String passport; // 口令
    // private String currentTime; // 当前时间 yyyy-MM-dd-HH-mm
    private String onlineReqEndDate; // 考试申请关闭时间
    private String examEndDate; // 考试关闭时间

    public String getPassport()
    {
        return passport;
    }

    public void setPassport(String passport)
    {
        this.passport = passport;
    }

    public String getOnlineReqEndDate()
    {
        return onlineReqEndDate;
    }

    public void setOnlineReqEndDate(String onlineReqEndDate)
    {
        this.onlineReqEndDate = onlineReqEndDate;
    }

    public String getExamEndDate()
    {
        return examEndDate;
    }

    public void setExamEndDate(String examEndDate)
    {
        this.examEndDate = examEndDate;
    }


}
