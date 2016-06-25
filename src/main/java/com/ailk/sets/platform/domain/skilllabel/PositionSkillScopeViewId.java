package com.ailk.sets.platform.domain.skilllabel;

import java.io.Serializable;

public class PositionSkillScopeViewId implements Serializable {

	private static final long serialVersionUID = -4108200868655847822L;
	private String skillId;
	private String seriesId;
	public String getSkillId() {
		return skillId;
	}
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	public String getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}
	@Override
	public String toString() {
		return "PositionSkillScopeViewId [skillId=" + skillId + ", seriesId=" + seriesId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((seriesId == null) ? 0 : seriesId.hashCode());
		result = prime * result + ((skillId == null) ? 0 : skillId.hashCode());
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
		PositionSkillScopeViewId other = (PositionSkillScopeViewId) obj;
		if (seriesId == null) {
			if (other.seriesId != null)
				return false;
		} else if (!seriesId.equals(other.seriesId))
			return false;
		if (skillId == null) {
			if (other.skillId != null)
				return false;
		} else if (!skillId.equals(other.skillId))
			return false;
		return true;
	}
	public PositionSkillScopeViewId(){
		
	}
	public PositionSkillScopeViewId(String skillId, String seriesId) {
		super();
		this.skillId = skillId;
		this.seriesId = seriesId;
	}
	
	


}
