package com.ailk.sets.platform.intf.model.position;

import java.io.Serializable;
/**
 * 以组形式展现测评信息  试卷统计信息
 * @author panyl
 *
 */
public class PaperCountInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3801043963387485820L;
	private Integer paperId;
    private Integer positionId;
    private Long reportNum;
    private String  paperName;
    private Integer relation;// 1必考  2选考
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public Long getReportNum() {
		return reportNum;
	}
	public void setReportNum(Long reportNum) {
		this.reportNum = reportNum;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
    
    
}
