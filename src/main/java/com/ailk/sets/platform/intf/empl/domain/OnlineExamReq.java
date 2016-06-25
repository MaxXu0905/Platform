/**
 * author :  lipan
 * filename :  OnlineExamReq.java
 * create_time : 2014年10月20日 下午3:08:13
 */
package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author : lipan
 * @create_time : 2014年10月20日 下午3:08:13
 * @desc : 在线申测信息表
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class OnlineExamReq implements Serializable
{
    private static final long serialVersionUID = -182159564901262533L;

    private long reqId; // 申请id
    private int positionId; //测评id，使用positionId和candidateId来进行申测去重
    private int candidateId; // 候选人Id
    private long testId; // candidate_test表的test_id
    private String passport; // 登录在线考试的口令
    private Timestamp createDate; // 创建日期
    private int status; // 生效状态: 1有效(默认)，0失效'

    public long getReqId()
    {
        return reqId;
    }

    public void setReqId(long reqId)
    {
        this.reqId = reqId;
    }

    public long getTestId()
    {
        return testId;
    }

    public void setTestId(long testId)
    {
        this.testId = testId;
    }

    public String getPassport()
    {
        return passport;
    }

    public void setPassport(String passport)
    {
        this.passport = passport;
    }

    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getPositionId()
    {
        return positionId;
    }

    public void setPositionId(int positionId)
    {
        this.positionId = positionId;
    }

    public int getCandidateId()
    {
        return candidateId;
    }

    public void setCandidateId(int candidateId)
    {
        this.candidateId = candidateId;
    }

}
