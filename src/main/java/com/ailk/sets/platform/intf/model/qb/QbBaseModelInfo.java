package com.ailk.sets.platform.intf.model.qb;

import java.io.Serializable;
import java.util.List;

public class QbBaseModelInfo  implements Serializable{
	private static final long serialVersionUID = -4287241882481717535L;

	private Integer qbId;
	private String qbName;
	private Integer prebuilt;
	private List<QbSkillModelInfo> skills;
	
	public Integer getPrebuilt() {
		return prebuilt;
	}
	public void setPrebuilt(Integer prebuilt) {
		this.prebuilt = prebuilt;
	}
	public List<QbSkillModelInfo> getSkills() {
		return skills;
	}
	public void setSkills(List<QbSkillModelInfo> skills) {
		this.skills = skills;
	}
	public Integer getQbId() {
		return qbId;
	}
	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}
	public String getQbName() {
		return qbName;
	}
	public void setQbName(String qbName) {
		this.qbName = qbName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qbId == null) ? 0 : qbId.hashCode());
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
		QbBaseModelInfo other = (QbBaseModelInfo) obj;
		if (qbId == null) {
			if (other.qbId != null)
				return false;
		} else if (!qbId.equals(other.qbId))
			return false;
		return true;
	}
	
	
}
