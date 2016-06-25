package com.ailk.sets.grade.intf;

import com.ailk.sets.grade.intf.report.Interview;


public class GetInterviewResponse extends BaseResponse {

	private static final long serialVersionUID = -4600572175769557588L;

	private Interview interview; // 面试信息

	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}

}
