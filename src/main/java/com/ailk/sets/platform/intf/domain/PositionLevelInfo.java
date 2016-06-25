package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;

public class PositionLevelInfo implements Serializable {
	private static final long serialVersionUID = 319697404277156836L;
	private Integer levelId;
	 private String levelName;
	 
	 public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public PositionLevelInfo(Integer levelId, String levelName) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
	}
	
	public PositionLevelInfo(){
		
	}
	
}
