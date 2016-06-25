package com.ailk.sets.platform.intf.domain;

import java.util.Date;
import java.util.List;

/**
 * PositionSeries entity. @author MyEclipse Persistence Tools
 */

public class PositionSeries implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7771075039020762912L;
	private Integer seriesId;
	private String seriesName;
	private Integer parentId;
	private Integer seriesType;
	private String positionLanguage;
	private Integer createBy;
	private Date createDate;
	private Integer prebuilt;



	private List<PositionSeries> children;
	private Integer sysPaperNumber;//百一题库试卷个数
	private Integer sysQuestionNumber;//百一题目个数
	
	public Integer getSysQuestionNumber() {
		return sysQuestionNumber;
	}

	public void setSysQuestionNumber(Integer sysQuestionNumber) {
		this.sysQuestionNumber = sysQuestionNumber;
	}

	public List<PositionSeries> getChildren() {
		return children;
	}

	public void setChildren(List<PositionSeries> children) {
		this.children = children;
	}

	public Integer getSysPaperNumber() {
		return sysPaperNumber;
	}

	public void setSysPaperNumber(Integer sysPaperNumber) {
		this.sysPaperNumber = sysPaperNumber;
	}
	
	
	public Integer getPrebuilt() {
		return prebuilt;
	}

	public void setPrebuilt(Integer prebuilt) {
		this.prebuilt = prebuilt;
	}
	// Constructors

	/** default constructor */
	public PositionSeries() {
	}

	/** minimal constructor */
	public PositionSeries(String seriesName, Integer parentId, Integer seriesType) {
		this.seriesName = seriesName;
		this.parentId = parentId;
		this.seriesType = seriesType;
	}

	/** full constructor */
	public PositionSeries(String seriesName, Integer parentId, Integer seriesType, String positionLanguage, Integer createBy, Date createDate) {
		this.seriesName = seriesName;
		this.parentId = parentId;
		this.seriesType = seriesType;
		this.positionLanguage = positionLanguage;
		this.createBy = createBy;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeriesName() {
		return this.seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSeriesType() {
		return this.seriesType;
	}

	public void setSeriesType(Integer seriesType) {
		this.seriesType = seriesType;
	}

	public String getPositionLanguage() {
		return this.positionLanguage;
	}

	public void setPositionLanguage(String positionLanguage) {
		this.positionLanguage = positionLanguage;
	}

	public Integer getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}