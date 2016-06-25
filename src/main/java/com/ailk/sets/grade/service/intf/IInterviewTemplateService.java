package com.ailk.sets.grade.service.intf;

import com.ailk.sets.grade.intf.report.InterviewInfo;

public interface IInterviewTemplateService {

	public InterviewInfo load(int employerId, int testType, String templateId)
			throws Exception;

	public void save(int employerId, int testType, String templateId,
			InterviewInfo interviewInfo) throws Exception;

}
