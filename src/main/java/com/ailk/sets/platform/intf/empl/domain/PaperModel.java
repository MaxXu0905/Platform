package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class PaperModel implements Serializable {

	private static final long serialVersionUID = 3905392343087078069L;
	private PaperObjectModelInfo objects;
	private PaperSubjectModelInfo subjects;
	private PaperInteModelInfo intellige;
	private PaperVideoModelInfo videos;
	private PaperEssayModelInfo essays;
	

	private String name; // 职位名称或者试卷名称
	private Integer totalTime;
	private Integer totalNum;
	private Timestamp modifyDate; // 职位或者试卷的修改时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public PaperEssayModelInfo getEssays() {
		return essays;
	}

	public void setEssays(PaperEssayModelInfo essays) {
		this.essays = essays;
	}
	
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}


	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public PaperObjectModelInfo getObjects() {
		return objects;
	}

	public void setObjects(PaperObjectModelInfo objects) {
		this.objects = objects;
	}

	public PaperSubjectModelInfo getSubjects() {
		return subjects;
	}

	public void setSubjects(PaperSubjectModelInfo subjects) {
		this.subjects = subjects;
	}

	public PaperInteModelInfo getIntellige() {
		return intellige;
	}

	public void setIntellige(PaperInteModelInfo intellige) {
		this.intellige = intellige;
	}

	public PaperVideoModelInfo getVideos() {
		return videos;
	}

	public void setVideos(PaperVideoModelInfo videos) {
		this.videos = videos;
	}

}
