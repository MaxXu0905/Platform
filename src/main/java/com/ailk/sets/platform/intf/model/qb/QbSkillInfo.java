package com.ailk.sets.platform.intf.model.qb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QbSkillInfo implements Serializable {

	private static final long serialVersionUID = 8939329463503820138L;
	private String skillId;
	private String skillName;
	private Map<Integer, Integer> numMapping;
	private Integer createBy;
	private Integer qbId;

	public QbSkillInfo() {
		this.numMapping = new HashMap<Integer, Integer>();
	}

	public Integer getQbId() {
		return qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Map<Integer, Integer> getNumMapping() {
		return numMapping;
	}

	public void setNumMapping(Map<Integer, Integer> numMapping) {
		this.numMapping = numMapping;
	}

}
