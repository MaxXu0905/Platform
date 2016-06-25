package com.ailk.sets.grade.grade.execute;

import java.util.List;

import com.ailk.sets.grade.jdbc.CandidateExamQuestion;
import com.ailk.sets.grade.jdbc.CandidateTestSubject;
import com.ailk.sets.grade.qb.IExecutor;

public interface IGradeExecutorService {

	public List<CandidateTestSubject> execute(
			CandidateExamQuestion candidateExamQuestion) throws Exception;

	public ResultHolder test(int stage, int mode, String testId, long qid,
			String arg) throws Exception;

	public ResultHolder testSample(int stage, int mode, long testId, long qid,
			int sampleId) throws Exception;

	public ResultHolder run(IExecutor executor, String qRoot, int stage,
			int mode, List<String> args, boolean grading) throws Exception;

}
