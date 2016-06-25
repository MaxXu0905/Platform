package com.ailk.sets.platform.intf.model.qb;

import java.io.Serializable;
import java.sql.Timestamp;

public class QbBaseInfo implements Serializable {
	private static final long serialVersionUID = 437466314275292559L;
	private Integer qbId;
	private String qbName;
	private Timestamp createDate;
	private Timestamp modifyDate;
	private String createDateDesc;
	private String modifyDateDesc;
	private Long choiceNum;
	private Long subjectNum;
	private Long essayNum;
	private Long totalNum;
	private Integer category;

	public String getQbName() {
		return qbName;
	}

	public void setQbName(String qbName) {
		this.qbName = qbName;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateDateDesc() {
		return createDateDesc;
	}

	public void setCreateDateDesc(String createDateDesc) {
		this.createDateDesc = createDateDesc;
	}

	public Long getChoiceNum() {
		return choiceNum;
	}

	public void setChoiceNum(Long choiceNum) {
		this.choiceNum = choiceNum;
	}

	public Long getSubjectNum() {
		return subjectNum;
	}

	public void setSubjectNum(Long subjectNum) {
		this.subjectNum = subjectNum;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyDateDesc() {
		return modifyDateDesc;
	}

	public void setModifyDateDesc(String modifyDateDesc) {
		this.modifyDateDesc = modifyDateDesc;
	}

	public Long getEssayNum() {
		return essayNum;
	}

	public void setEssayNum(Long essayNum) {
		this.essayNum = essayNum;
	}

	public Integer getQbId() {
		return qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

}
