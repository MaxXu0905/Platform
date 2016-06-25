package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "candidate_exam_question")
public class CandidateExamQuestion implements Serializable, Cloneable {

	@EmbeddedId
	private CandidateExamQuestionPK candidateExamQuestionPK;

	@Column(name = "question_type", nullable = false)
	private int questionType;
	
	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "sample", nullable = false)
	private boolean sample;

	@Column(name = "answer", nullable = false)
	private String answer;

	@Column(name = "serial_no", nullable = false)
	private int serialNo;
	
	public CandidateExamQuestionPK getCandidateExamQuestionPK() {
		return candidateExamQuestionPK;
	}

	public void setCandidateExamQuestionPK(
			CandidateExamQuestionPK candidateExamQuestionPK) {
		this.candidateExamQuestionPK = candidateExamQuestionPK;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSample() {
		return sample;
	}

	public void setSample(boolean sample) {
		this.sample = sample;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

}
