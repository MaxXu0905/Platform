package com.ailk.sets.platform.domain;

/**
 * PositionSkillId entity. @author MyEclipse Persistence Tools
 */

public class PaperSkillId implements java.io.Serializable {

	private static final long serialVersionUID = -5375836396993657089L;
	private Integer paperId;
	private String skillId;

	// Constructors

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paperId == null) ? 0 : paperId.hashCode());
		result = prime * result + ((skillId == null) ? 0 : skillId.hashCode());
		return result;
	}
	public PaperSkillId(Integer paperId, String skillId) {
		super();
		this.paperId = paperId;
		this.skillId = skillId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaperSkillId other = (PaperSkillId) obj;
		if (paperId == null) {
			if (other.paperId != null)
				return false;
		} else if (!paperId.equals(other.paperId))
			return false;
		if (skillId == null) {
			if (other.skillId != null)
				return false;
		} else if (!skillId.equals(other.skillId))
			return false;
		return true;
	}

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	/** default constructor */
	public PaperSkillId() {
	}


}