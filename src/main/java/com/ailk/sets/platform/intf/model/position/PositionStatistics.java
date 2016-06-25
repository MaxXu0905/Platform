package com.ailk.sets.platform.intf.model.position;

import java.io.Serializable;

import com.ailk.sets.platform.intf.common.PFResponse;

/**
 * 职位相关的统计信息
 * 
 * @author 毕希研
 * 
 */
public class PositionStatistics extends PFResponse implements Serializable {

	private static final long serialVersionUID = 6218650273241734114L;
	private Long testId;
	private Long invitationFailNum; // 失败邀请数
	private Long reportNum; // 未读报告数
	private Long invitatedNum; // 邀请总数
	private Long chosenNum; // 已通过数
	private Long todoNum; // 未处理数
	private Long eliminationNum;// 已淘汰数
	private int reportStatus; // 0-待定; 1-通过; 2-淘汰;3-复试
	
	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Long getInvitationFailNum() {
		return invitationFailNum;
	}

	public void setInvitationFailNum(Long invitationFailNum) {
		this.invitationFailNum = invitationFailNum;
	}

	public Long getReportNum() {
		return reportNum;
	}

	public void setReportNum(Long reportNum) {
		this.reportNum = reportNum;
	}

	public Long getInvitatedNum() {
		return invitatedNum;
	}

	public void setInvitatedNum(Long invitatedNum) {
		this.invitatedNum = invitatedNum;
	}

	public Long getChosenNum() {
		return chosenNum;
	}

	public void setChosenNum(Long chosenNum) {
		this.chosenNum = chosenNum;
	}

	public Long getTodoNum() {
		return todoNum;
	}

	public void setTodoNum(Long todoNum) {
		this.todoNum = todoNum;
	}

	public Long getEliminationNum() {
		return eliminationNum;
	}

	public void setEliminationNum(Long eliminationNum) {
		this.eliminationNum = eliminationNum;
	}

    public int getReportStatus()
    {
        return reportStatus;
    }

    public void setReportStatus(int reportStatus)
    {
        this.reportStatus = reportStatus;
    }

}
