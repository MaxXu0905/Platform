package com.ailk.sets.platform.intf.domain.paper;

import java.sql.Timestamp;
import java.util.List;

import com.ailk.sets.platform.intf.domain.DegreeToSkills;

/**
 * Paper entity. @author MyEclipse Persistence Tools
 */

public class Paper implements java.io.Serializable {
	private static final long serialVersionUID = 8038026006873600943L;
	private Integer paperId;
	private String paperName;
	private String paperDesc;
	private Integer hasTrial;
	private Integer createBy;
	private Timestamp createDate;
	private Timestamp modifyDate; //修改时间
	private String skillDesc;
	private Integer prebuilt;
	private Integer seriesId;
	private Integer level;
	private Integer testType;
	
    private Integer  sysSubjectNum;//需要系统出的编程题数量
	private List<DegreeToSkills> degreeToSkills;//掌握技能程度结果
	
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Integer getSysSubjectNum() {
		return sysSubjectNum;
	}
	public void setSysSubjectNum(Integer sysSubjectNum) {
		this.sysSubjectNum = sysSubjectNum;
	}
	public List<DegreeToSkills> getDegreeToSkills() {
		return degreeToSkills;
	}
	public void setDegreeToSkills(List<DegreeToSkills> degreeToSkills) {
		this.degreeToSkills = degreeToSkills;
	}
	public Integer getTestType() {
		return testType;
	}
	public void setTestType(Integer testType) {
		this.testType = testType;
	}
	// Constructors
	public String getSkillDesc() {
		return skillDesc;
	}
	public void setSkillDesc(String skillDesc) {
		this.skillDesc = skillDesc;
	}
	/** default constructor */
	public Paper() {
	}
	
	/** minimal constructor */
	public Paper(Integer paperId) {
		this.paperId = paperId;
	}

	public Integer getPrebuilt() {
		return prebuilt;
	}

	public void setPrebuilt(Integer prebuilt) {
		this.prebuilt = prebuilt;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


	// Property accessors

	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public String getPaperName() {
		return this.paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getPaperDesc() {
		return this.paperDesc;
	}

	public void setPaperDesc(String paperDesc) {
		this.paperDesc = paperDesc;
	}

	public Integer getHasTrial() {
		return this.hasTrial;
	}

	public void setHasTrial(Integer hasTrial) {
		this.hasTrial = hasTrial;
	}


	public Integer getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "Paper [paperName=" + paperName + ", paperDesc=" + paperDesc + ", skillDesc=" + skillDesc
				+ ", seriesId=" + seriesId + ", level=" + level + ", testType=" + testType + ", sysSubjectNum="
				+ sysSubjectNum + ", degreeToSkills=" + degreeToSkills + "]";
	}
	
	

}