package com.ailk.sets.platform.intf.model.qb;



/**
 * QbSkill entity. @author MyEclipse Persistence Tools
 */

public class QbSkill implements java.io.Serializable {

	private static final long serialVersionUID = 8097702656739983602L;
	private String skillId;
	private String skillName;
	private String skillAlias;
	private String parentId;
	private Integer level;
	private Integer qbId;
	
	private Integer questionNum;//需要百一出此技能的题目个数
 
	private Integer prebuilt;//是否百一技能  1百一技能 0自定义技能  试卷预览时使用
	
	public Integer getPrebuilt() {
		return prebuilt;
	}
	public void setPrebuilt(Integer prebuilt) {
		this.prebuilt = prebuilt;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public Integer getQbId() {
		return qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public String getSkillId() {
		return this.skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillAlias() {
		return this.skillAlias;
	}

	public void setSkillAlias(String skillAlias) {
		this.skillAlias = skillAlias;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() { 
		return "QbSkill [skillId=" + skillId + ", skillName=" + skillName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		QbSkill other = (QbSkill) obj;
		if (skillId == null) {
			if (other.skillId != null)
				return false;
		} else if (!skillId.equals(other.skillId))
			return false;
		return true;
	}


}