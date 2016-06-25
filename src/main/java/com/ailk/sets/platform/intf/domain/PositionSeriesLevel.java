package com.ailk.sets.platform.intf.domain;



/**
 * 职位类别定制信息
 * @author panyl
 *
 */
public class PositionSeriesLevel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7771075039020762912L;
	private Integer levelId;  //同一个级别只有一个即可
	private String levelName;
	private Integer paperId;
	private int customed;//是否已经被定制过  1已经定制过  0为定制过
	public int getCustomed() {
		return customed;
	}
	public void setCustomed(int customed) {
		this.customed = customed;
	}
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
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((levelId == null) ? 0 : levelId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionSeriesLevel other = (PositionSeriesLevel) obj;
		if (levelId == null) {
			if (other.levelId != null)
				return false;
		} else if (!levelId.equals(other.levelId))
			return false;
		return true;
	}
	
	

}