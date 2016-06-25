package com.ailk.sets.platform.intf.domain.skilllabel;


/**
 * QbSkill entity. @author MyEclipse Persistence Tools
 */

public class QbSkillSubjectView implements java.io.Serializable {
	private static final long serialVersionUID = -6432590177822160892L;
//	private String skillId;
	private QbSkillSubjectViewId id;

	private String skillName;
	private String skillAlias;
	private String parentId;
	private Integer level;
	private String subjectCode;
	private String subjectName;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public QbSkillSubjectViewId getId() {
		return id;
	}

	public void setId(QbSkillSubjectViewId id) {
		this.id = id;
	}
	
	// Constructors

	/** default constructor */
	public QbSkillSubjectView() {
	}


	/** minimal constructor */
	public QbSkillSubjectView(QbSkillSubjectViewId id) {
		this.id = id;
	}



	public QbSkillSubjectView(QbSkillSubjectViewId id, String skillName, String skillAlias,
			String parentId, Integer level, String subjectCode,
			String subjectName) {
		super();
		this.id = id;
		this.skillName = skillName;
		this.skillAlias = skillAlias;
		this.parentId = parentId;
		this.level = level;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
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

	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Override
	public String toString() {
		return "QbSkill [id=" + id + ", skillName=" + skillName
				+ ", skillAlias=" + skillAlias + ", parentId=" + parentId
				+ ", level=" + level + ", subjectCode=" + subjectCode
				+ ", subjectName=" + subjectName + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		QbSkillSubjectView other = (QbSkillSubjectView) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}