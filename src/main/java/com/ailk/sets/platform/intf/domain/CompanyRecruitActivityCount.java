package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;

public class CompanyRecruitActivityCount implements Serializable {
	private static final long serialVersionUID = -5503821101760465360L;
	private Long totalJoinNum;//总参加人数
	private Long totalAnsweringNum;//正在答题人数
	private Long totalFinishedNum;//完成答卷人数
	public Long getTotalJoinNum() {
		return totalJoinNum;
	}
	public void setTotalJoinNum(Long totalJoinNum) {
		this.totalJoinNum = totalJoinNum;
	}
	public Long getTotalAnsweringNum() {
		return totalAnsweringNum;
	}
	public void setTotalAnsweringNum(Long totalAnsweringNum) {
		this.totalAnsweringNum = totalAnsweringNum;
	}
	public Long getTotalFinishedNum() {
		return totalFinishedNum;
	}
	public void setTotalFinishedNum(Long totalFinishedNum) {
		this.totalFinishedNum = totalFinishedNum;
	}

}
