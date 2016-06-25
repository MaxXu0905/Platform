package com.ailk.sets.platform.domain.skilllabel;

/**
 * PositionSkillUnknown entity. @author MyEclipse Persistence Tools
 */

public class PaperSkillUnknown implements java.io.Serializable {
	
	public final static int PARSE_REASON_NODEGREE = 1; //没有程度词
	public final static int PARSE_REASON_NOSKILL = 2;//没有技能词
	public final static int PARSE_REASON_UNKNOWN = 3;//未知
	

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5813384691873975798L;
	private Integer leftId;
	private Integer positionId;
	private String parseLeft;
	private Integer parseReason;

	// Constructors

	/** default constructor */
	public PaperSkillUnknown() {
	}

	/** minimal constructor */
	public PaperSkillUnknown(Integer leftId, Integer positionId) {
		this.leftId = leftId;
		this.positionId = positionId;
	}

	/** full constructor */
	public PaperSkillUnknown(Integer leftId, Integer positionId, String parseLeft, Integer parseReason) {
		this.leftId = leftId;
		this.positionId = positionId;
		this.parseLeft = parseLeft;
		this.parseReason = parseReason;
	}

	// Property accessors

	public Integer getLeftId() {
		return this.leftId;
	}

	public void setLeftId(Integer leftId) {
		this.leftId = leftId;
	}

	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getParseLeft() {
		return this.parseLeft;
	}

	public void setParseLeft(String parseLeft) {
		this.parseLeft = parseLeft;
	}

	public Integer getParseReason() {
		return this.parseReason;
	}

	public void setParseReason(Integer parseReason) {
		this.parseReason = parseReason;
	}

}