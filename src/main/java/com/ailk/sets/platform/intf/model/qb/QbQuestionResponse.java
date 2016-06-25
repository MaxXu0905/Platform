package com.ailk.sets.platform.intf.model.qb;

import com.ailk.sets.platform.intf.common.PFResponse;

public class QbQuestionResponse extends PFResponse {

	private static final long serialVersionUID = -5654680662644466089L;
	private Long questionId;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
