package com.ailk.sets.platform.intf.model;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.domain.DegreeToSkills;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.model.qb.QbBaseModelInfo;
/**
 * 创建试卷初始化信息
 * @author panyl
 *
 */
public class PaperInitInfo implements Serializable {
	private static final long serialVersionUID = 3920742580175178361L;

	private List<QbBaseModelInfo> qbBaseInfos;
	private List<QbSkillDegree>  degreeInfos;
	private Integer objectAvgTime;//客观题平均答题时间
	private Integer subjectAvgTime;//主观题平均答题时间
	private Integer leastSubjectNum;//编程题最小题目数
	private Integer mostSubjectNum;//编程题最大题目数
	
	//根据每一个职位类别以及级别有一个初始化的掌握程度
	private  List<DegreeToSkills> initDegreeSkills;
	
	public List<DegreeToSkills> getInitDegreeSkills() {
		return initDegreeSkills;
	}
	public void setInitDegreeSkills(List<DegreeToSkills> initDegreeSkills) {
		this.initDegreeSkills = initDegreeSkills;
	}
	public Integer getLeastSubjectNum() {
		return leastSubjectNum;
	}
	public void setLeastSubjectNum(Integer leastSubjectNum) {
		this.leastSubjectNum = leastSubjectNum;
	}
	public Integer getMostSubjectNum() {
		return mostSubjectNum;
	}
	public void setMostSubjectNum(Integer mostSubjectNum) {
		this.mostSubjectNum = mostSubjectNum;
	}
	public Integer getObjectAvgTime() {
		return objectAvgTime;
	}
	public void setObjectAvgTime(Integer objectAvgTime) {
		this.objectAvgTime = objectAvgTime;
	}
	public Integer getSubjectAvgTime() {
		return subjectAvgTime;
	}
	public void setSubjectAvgTime(Integer subjectAvgTime) {
		this.subjectAvgTime = subjectAvgTime;
	}
	public List<QbBaseModelInfo> getQbBaseInfos() {
		return qbBaseInfos;
	}
	public void setQbBaseInfos(List<QbBaseModelInfo> qbBaseInfos) {
		this.qbBaseInfos = qbBaseInfos;
	}
	public List<QbSkillDegree> getDegreeInfos() {
		return degreeInfos;
	}
	public void setDegreeInfos(List<QbSkillDegree> degreeInfos) {
		this.degreeInfos = degreeInfos;
	}
	
}
