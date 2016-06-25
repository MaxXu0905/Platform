package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 试卷信息  外部接口
 * @author panyl
 *
 */
public class PaperOutInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7714857065808160370L;
	private Integer paperId;
	private String paperName;
	private Timestamp createDate;
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
}
