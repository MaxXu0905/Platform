package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.Collection;

import com.ailk.sets.platform.intf.model.question.Extras;
import com.ailk.sets.platform.intf.model.question.GenQuestion;
import com.ailk.sets.platform.intf.model.question.InterviewGroup;

/**
 * 保存职位提交信息的模型
 * 
 * @author 毕希研
 * 
 */
public class PositionSet implements Serializable {
	private static final long serialVersionUID = -6390269715143803817L;
	private Position position; // 职位相关信息
	private Collection<Extras> extras; // 该职位的附加题列表
	private Collection<GenQuestion> business; //
	private Collection<GenQuestion> intelligence;
	private InterviewGroup interviewGroup;
	
   private Collection<ConfigInfoExtEx>  positionConfigInfo;//修改职位时用于回显的职位信息
	
	public Collection<ConfigInfoExtEx> getPositionConfigInfo() {
		return positionConfigInfo;
	}

	public void setPositionConfigInfo(Collection<ConfigInfoExtEx> positionConfigInfo) {
		this.positionConfigInfo = positionConfigInfo;
	}

	public InterviewGroup getInterviewGroup() {
		return interviewGroup;
	}

	public void setInterviewGroup(InterviewGroup interviewGroup) {
		this.interviewGroup = interviewGroup;
	}

	public Collection<Extras> getExtras() {
		return extras;
	}

	public void setExtras(Collection<Extras> extras) {
		this.extras = extras;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Collection<GenQuestion> getBusiness() {
		return business;
	}

	public void setBusiness(Collection<GenQuestion> business) {
		this.business = business;
	}

	public Collection<GenQuestion> getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(Collection<GenQuestion> intelligence) {
		this.intelligence = intelligence;
	}

}
