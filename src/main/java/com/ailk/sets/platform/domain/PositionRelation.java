package com.ailk.sets.platform.domain;

/**
 * PositionRelation entity. @author MyEclipse Persistence Tools
 */

public class PositionRelation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -277507680953739437L;
	private PositionRelationId id;
	private Integer relation;

	// Constructors

	/** default constructor */
	public PositionRelation() {
	}

	/** minimal constructor */
	public PositionRelation(PositionRelationId id) {
		this.id = id;
	}

	/** full constructor */
	public PositionRelation(PositionRelationId id, Integer relation) {
		this.id = id;
		this.relation = relation;
	}

	// Property accessors

	public PositionRelationId getId() {
		return this.id;
	}

	public void setId(PositionRelationId id) {
		this.id = id;
	}

	public Integer getRelation() {
		return this.relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

}