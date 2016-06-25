package com.ailk.sets.platform.intf.model.invatition;

import java.io.Serializable;
import java.util.Date;

/**
 * 邀请失败的信息
 * 
 * @author 毕希研
 * 
 */
public class InvitationInfo implements Serializable {
	private static final long serialVersionUID = 1193808639766480924L;
	private Long testId;
	private String candidateName;
	private String candidateEmail;
	private Date invitationDate;
	private String invitationDateDesc;
	private String invitationErrtxt;

	//一下几个属性失败邀请重新发送时有需要
    private String testPositionName;   // 测评职位名称
    private String validDate; //测评失效时间
    private String beginDate;//开始考试时间
    private Integer canWithOutCamera; // 是否允许考生在没有摄像头时进行考试 0:允许，1不允许
    private Long  validTimeLeft; //失败邀请失效时间还剩余多少
    
    
	public Long getValidTimeLeft() {
		return validTimeLeft;
	}

	public void setValidTimeLeft(Long validTimeLeft) {
		this.validTimeLeft = validTimeLeft;
	}

	public String getTestPositionName() {
		return testPositionName;
	}

	public void setTestPositionName(String testPositionName) {
		this.testPositionName = testPositionName;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public Integer getCanWithOutCamera() {
		return canWithOutCamera;
	}

	public void setCanWithOutCamera(Integer canWithOutCamera) {
		this.canWithOutCamera = canWithOutCamera;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public Date getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(Date invitationDate) {
		this.invitationDate = invitationDate;
	}

	public String getInvitationErrtxt() {
		return invitationErrtxt;
	}

	public void setInvitationErrtxt(String invitationErrtxt) {
		this.invitationErrtxt = invitationErrtxt;
	}

	public String getInvitationDateDesc() {
		return invitationDateDesc;
	}

	public void setInvitationDateDesc(String invitationDateDesc) {
		this.invitationDateDesc = invitationDateDesc;
	}

}
