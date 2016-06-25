package com.ailk.sets.platform.intf.domain.skilllabel;

import com.ailk.sets.platform.intf.domain.PositionLevel;

/**
 * QbSkillDegree entity. @author MyEclipse Persistence Tools
 */

public class QbSkillDegree implements java.io.Serializable {

	@Override
	public String toString() {
		return "QbSkillDegree [degreeAlias=" + degreeAlias + ", degreeName=" + degreeName + "]";
	}
	private static final long serialVersionUID = -2522526719365369805L;
	private Integer degreeId;
	private String degreeName;
	private String degreeAlias;
	private Double degreeRatio;
	private Integer degreeWeight;
	private Integer questionLeast;
	private Integer questionMost;

	// Constructors
    public int getLowDegree(PositionLevel pl){
    	
    	int plLow =  pl.getDegreeLow().intValue();
    	return plLow;
		
    }
    
    public int getHighDegree(PositionLevel pl){
    	int plLow =  pl.getDegreeLow().intValue();
		int plHigh = pl.getDegreeHigh().intValue();
    	double degree = plLow + (plHigh - plLow) * degreeRatio;
		int degreeHigh = (int) Math.round(degree);//四舍五入
		return degreeHigh;
    }
	public Integer getQuestionLeast() {
		return questionLeast;
	}

	public void setQuestionLeast(Integer questionLeast) {
		this.questionLeast = questionLeast;
	}

	public Integer getQuestionMost() {
		return questionMost;
	}

	public void setQuestionMost(Integer questionMost) {
		this.questionMost = questionMost;
	}

	/** default constructor */
	public QbSkillDegree() {
	}

	/** minimal constructor */
	public QbSkillDegree(Integer degreeId) {
		this.degreeId = degreeId;
	}


	// Property accessors

	public Integer getDegreeId() {
		return this.degreeId;
	}

	public void setDegreeId(Integer degreeId) {
		this.degreeId = degreeId;
	}

	public String getDegreeName() {
		return this.degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getDegreeAlias() {
		return this.degreeAlias;
	}

	public void setDegreeAlias(String degreeAlias) {
		this.degreeAlias = degreeAlias;
	}

	public Double getDegreeRatio() {
		return this.degreeRatio;
	}

	public void setDegreeRatio(Double degreeRatio) {
		this.degreeRatio = degreeRatio;
	}

	public Integer getDegreeWeight() {
		return this.degreeWeight;
	}

	public void setDegreeWeight(Integer degreeWeight) {
		this.degreeWeight = degreeWeight;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((degreeId == null) ? 0 : degreeId.hashCode());
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
		QbSkillDegree other = (QbSkillDegree) obj;
		if (degreeId == null) {
			if (other.degreeId != null)
				return false;
		} else if (!degreeId.equals(other.degreeId))
			return false;
		return true;
	}

}