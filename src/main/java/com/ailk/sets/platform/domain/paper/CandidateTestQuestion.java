package com.ailk.sets.platform.domain.paper;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * PaperInstanceQuestion entity. @author MyEclipse Persistence Tools
 */

public class CandidateTestQuestion implements java.io.Serializable {
	private static final long serialVersionUID = 5038364691076852966L;
	private CandidateTestQuestionId id;
	private Integer partSeq;
	private Integer questionSeq;
	private String relSkills;
	private Integer answerFlag;
	private Integer correctFlag;
	private Double getScore;
	private String videoFile;
	private Integer suggestTime;
	private Integer answerTime;
	private Timestamp beginTime;
	private Timestamp endTime;
	private Integer moti;

	// Constructors

	public Integer getMoti() {
		return moti;
	}

	public void setMoti(Integer moti) {
		this.moti = moti;
	}

	/** default constructor */
	public CandidateTestQuestion() {
		this.answerFlag = 0;
	}

	/** minimal constructor */
	public CandidateTestQuestion(CandidateTestQuestionId id, Integer partSeq,
			Integer answerFlag, Integer correctFlag, Double getScore,
			Integer answerTime) {
		this.id = id;
		this.partSeq = partSeq;
		this.answerFlag = answerFlag;
		this.correctFlag = correctFlag;
		this.getScore = getScore;
		this.answerTime = answerTime;
	}

	/** full constructor */
	public CandidateTestQuestion(CandidateTestQuestionId id, Integer partSeq,
			Integer questionSeq, String relSkills, Integer answerFlag,
			Integer correctFlag, Double getScore, String videoFile,
			Integer suggestTime, Integer answerTime, Timestamp beginTime,
			Timestamp endTime) {
		this.id = id;
		this.partSeq = partSeq;
		this.questionSeq = questionSeq;
		this.relSkills = relSkills;
		this.answerFlag = answerFlag;
		this.correctFlag = correctFlag;
		this.getScore = getScore;
		this.videoFile = videoFile;
		this.suggestTime = suggestTime;
		this.answerTime = answerTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	// Property accessors
	public List<String> getRelSkillsArray(){
		return  Arrays.asList(this.relSkills.split(","));
	}

	public CandidateTestQuestionId getId() {
		return this.id;
	}

	public void setId(CandidateTestQuestionId id) {
		this.id = id;
	}

	public Integer getPartSeq() {
		return this.partSeq;
	}

	public void setPartSeq(Integer partSeq) {
		this.partSeq = partSeq;
	}

	public Integer getQuestionSeq() {
		return this.questionSeq;
	}

	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String getRelSkills() {
		return this.relSkills;
	}

	public void setRelSkills(String relSkills) {
		this.relSkills = relSkills;
	}

	public Integer getAnswerFlag() {
		return this.answerFlag;
	}

	public void setAnswerFlag(Integer answerFlag) {
		this.answerFlag = answerFlag;
	}

	public Integer getCorrectFlag() {
		return this.correctFlag;
	}

	public void setCorrectFlag(Integer correctFlag) {
		this.correctFlag = correctFlag;
	}

	public Double getGetScore() {
		return this.getScore;
	}

	public void setGetScore(Double getScore) {
		this.getScore = getScore;
	}

	public String getVideoFile() {
		return this.videoFile;
	}

	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
	}

	public Integer getSuggestTime() {
		return this.suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}

	public Integer getAnswerTime() {
		return this.answerTime;
	}

	public void setAnswerTime(Integer answerTime) {
		this.answerTime = answerTime;
	}

	public Timestamp getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

}