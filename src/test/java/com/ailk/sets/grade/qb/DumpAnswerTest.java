package com.ailk.sets.grade.qb;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateExamQuestionDao;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.jdbc.CandidateExamQuestion;
import com.ailk.sets.grade.service.intf.AnswerWrapper;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
public class DumpAnswerTest {

	@Autowired
	private ICandidateExamQuestionDao candidateExamQuestionDao;
	
	private static final Gson gson = new Gson();

	@Test
	public void run() {
		List<CandidateExamQuestion> candidateExamQuestions = candidateExamQuestionDao
				.getList(121511);
		for (CandidateExamQuestion candidateExamQuestion : candidateExamQuestions) {
			switch (candidateExamQuestion.getQuestionType()) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
			case GradeConst.QUESTION_TYPE_M_CHOICE:
				break;
			default:
				continue;
			}

			String content = candidateExamQuestion.getContent();
			QuestionContent questionContent = gson.fromJson(content,
					QuestionContent.class);
			QuestionConf questionConf = questionContent.getQuestionConf();
			System.out.println(questionConf.getTitle());
			System.out.println("题库：" + questionConf.getQbName());
			System.out.println("标准答案：" + questionConf.getOptAnswers());

			String answer = candidateExamQuestion.getAnswer();
			if (answer != null) {
				AnswerWrapper answerWrapper = gson.fromJson(answer,
						AnswerWrapper.class);
				answerWrapper.getOptAnswer();
				System.out.println("实际答案：" + answerWrapper.getOptAnswer());
			}

			char ch = 'A';
			for (String option : questionConf.getOptions()) {
				System.out.println(ch + "." + option);
				ch++;
			}
			
			System.out.println();
		}
	}

}
