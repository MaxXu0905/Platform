package com.ailk.sets.platform.domain.paper;

/**
 * PaperQbase entity. @author MyEclipse Persistence Tools
 */

public class PaperQbase implements java.io.Serializable {

	private static final long serialVersionUID = -912207793084781110L;
	private PaperQbaseId id;
	private Integer versionId;
	private Integer percent;

	// Constructors

	/** default constructor */
	public PaperQbase() {
	}

	/** minimal constructor */
	public PaperQbase(PaperQbaseId id, Integer versionId) {
		this.id = id;
		this.versionId = versionId;
	}

	/** full constructor */
	public PaperQbase(PaperQbaseId id, Integer versionId, Integer percent) {
		this.id = id;
		this.versionId = versionId;
		this.percent = percent;
	}

	// Property accessors

	public PaperQbaseId getId() {
		return this.id;
	}

	public void setId(PaperQbaseId id) {
		this.id = id;
	}

	public Integer getVersionId() {
		return this.versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public Integer getPercent() {
		return this.percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

}