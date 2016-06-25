package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;

public class PositionOutInfo implements Serializable {
	private static final long serialVersionUID = -2622302882865805596L;
	private Integer positionId; // 职位ID
	private String positionName; // 职位名称
	private  Integer seriesId; //职位类别
	private String seriesName; //职位类别名
	private Integer parentSeriesId; //上级职位类别
	private String parentSeriesName; //上级职位类别名
	private Integer level; //级别
	private String levelName; //级别名称
	
	
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Integer getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public Integer getParentSeriesId() {
		return parentSeriesId;
	}
	public void setParentSeriesId(Integer parentSeriesId) {
		this.parentSeriesId = parentSeriesId;
	}
	public String getParentSeriesName() {
		return parentSeriesName;
	}
	public void setParentSeriesName(String parentSeriesName) {
		this.parentSeriesName = parentSeriesName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	

}
