package com.ailk.sets.platform.intf.cand.domain;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Invitation entity. @author MyEclipse Persistence Tools
 */

public class Invitation implements java.io.Serializable {

	private static final long serialVersionUID = 1965902590477046504L;
	private Integer positionId;
	private Integer employerId;
	private Timestamp invitationDate;
	private Timestamp expDate;
	private Timestamp effDate;
	private String candidateName;
	private String candidateEmail;
	private String invitationUrl;
	private String passport;
	private Long testId;
	private Integer invitationState;
	private String invitationErrtxt;
	private Date stateDate;

	//add by lipan 2014年7月5日14:41:21 
//    private Integer employerId; //招聘者Id
//    private Integer positionId; //  测评Id
    private String testPositionName;   // 测评职位名称
    private String validDate; //测评失效时间yyyyMMdd
    private String beginDate;//开始考试时间
    private String selfContext;//自定义内容
    private Integer canWithOutCamera; // 是否允许考生在没有摄像头时进行考试 0:允许，1不允许
    
    private Integer channelType; //账户类型 1职酷 11英才(此字段在candidateTest中有保存)
	private String employerEmail;//体验发送邀请时传递的邮箱

	private String openId; // 微信openid，用于微信在线申测时进行绑定
	
	public String getSelfContext() {
		return selfContext;
	}

	public void setSelfContext(String selfContext) {
		this.selfContext = selfContext;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEffDate() {
		return effDate;
	}

	public void setEffDate(Timestamp effDate) {
		this.effDate = effDate;
	}

	public String getEmployerEmail() {
		return employerEmail;
	}

	public void setEmployerEmail(String employerEmail) {
		this.employerEmail = employerEmail;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}


	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Timestamp getInvitationDate() {
		return this.invitationDate;
	}

	public void setInvitationDate(Timestamp invitationDate) {
		this.invitationDate = invitationDate;
	}

	public Timestamp getExpDate() {
		return this.expDate;
	}

	public void setExpDate(Timestamp expDate) {
		this.expDate = expDate;
	}

	public String getCandidateName() {
		return this.candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return this.candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getInvitationUrl() {
		return this.invitationUrl;
	}

	public void setInvitationUrl(String invitationUrl) {
		this.invitationUrl = invitationUrl;
	}

	public String getPassport() {
		return this.passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Long getTestId() {
		return this.testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Integer getInvitationState() {
		return this.invitationState;
	}

	public void setInvitationState(Integer invitationState) {
		this.invitationState = invitationState;
	}

	public String getInvitationErrtxt() {
		return this.invitationErrtxt;
	}

	public void setInvitationErrtxt(String invitationErrtxt) {
		this.invitationErrtxt = invitationErrtxt;
	}

	public Date getStateDate() {
		return this.stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

    public String getTestPositionName()
    {
        return testPositionName;
    }

    public void setTestPositionName(String testPositionName)
    {
        this.testPositionName = testPositionName;
    }

    public String getValidDate()
    {
        return validDate;
    }

    public void setValidDate(String validDate)
    {
        this.validDate = validDate;
    }

    public Integer getCanWithOutCamera()
    {
        return canWithOutCamera;
    }

    public void setCanWithOutCamera(Integer canWithOutCamera)
    {
        this.canWithOutCamera = canWithOutCamera;
    }

	public String getOpenId()
    {
        return openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    @Override
	public String toString() {
		return "Invitation [positionId=" + positionId + ", candidateName=" + candidateName + ", candidateEmail="
				+ candidateEmail + ", testPositionName=" + testPositionName + ", validDate=" + validDate
				+ ", beginDate=" + beginDate + ", selfContext=" + selfContext + ", canWithOutCamera="
				+ canWithOutCamera + "]";
	}

}