package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
/**
 * 试卷信息
 * @author panyl
 *
 */
public class PaperInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8660250331052597090L;
	private Integer paperId;
	private String paperName;
	private Long paperTime;  //考试时长
	private int questionNum; //考卷题目总数
	private Timestamp createDate;
	private String createDateDesc;
	private Integer prebuilt;//百一试卷  自定义试卷
	private Integer answerNumber;
	List<PaperQuestionTypeInfo>  typeInfos;
	
	public List<PaperQuestionTypeInfo> getTypeInfos() {
		return typeInfos;
	}
	public void setTypeInfos(List<PaperQuestionTypeInfo> typeInfos) {
		this.typeInfos = typeInfos;
	}
	public Integer getPrebuilt() {
		return prebuilt;
	}
	public void setPrebuilt(Integer prebuilt) {
		this.prebuilt = prebuilt;
	}
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
	public String getCreateDateDesc() {
		return createDateDesc;
	}
	public void setCreateDateDesc(String createDateDesc) {
		this.createDateDesc = createDateDesc;
	}
	public Integer getAnswerNumber() {
		return answerNumber;
	}
	public void setAnswerNumber(Integer answerNumber) {
		this.answerNumber = answerNumber;
	}
    public Long getPaperTime()
    {
        return paperTime;
    }
    public void setPaperTime(Long paperTime)
    {
        this.paperTime = paperTime;
    }
    public int getQuestionNum()
    {
        return questionNum;
    }
    public void setQuestionNum(int questionNum)
    {
        this.questionNum = questionNum;
    }
    
}
