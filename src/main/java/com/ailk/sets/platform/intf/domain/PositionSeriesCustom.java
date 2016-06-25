package com.ailk.sets.platform.intf.domain;

import java.util.Date;
import java.util.List;


/**
 * 职位类别定制信息
 * @author panyl
 *
 */
public class PositionSeriesCustom implements java.io.Serializable {

	private static final long serialVersionUID = 7771075039020762912L;
	private Integer seriesId;
	private String seriesName;
	List<PositionSeriesLevel>  levelPapers;
	
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
	public List<PositionSeriesLevel> getLevelPapers() {
		return levelPapers;
	}
	public void setLevelPapers(List<PositionSeriesLevel> levelPapers) {
		this.levelPapers = levelPapers;
	}
}