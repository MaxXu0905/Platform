package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "qb_question_detail")
public class QbQuestionDetail implements Serializable, Cloneable {

	@Id
	@Column(name = "question_id", nullable = false)
	private long questionId;

	@Column(name = "encrypted", nullable = false)
	private boolean encrypted;

	@Column(name = "content", nullable = false)
	private String content;

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
