package com.ailk.sets.grade.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.grade.common.DebugConfig;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.grade.execute.IGradeExecutorService;
import com.ailk.sets.grade.grade.execute.ResultHolder;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;
import com.ailk.sets.grade.utils.Env;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "//spring/beans.xml", "/spring/consumer.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
public class SanityCheckTest {

	private static final int TEST_ID = 0;
	private static final Gson gson = new Gson();

	@Autowired
	private IGradeService gradeService;

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IQbQuestionDetailDao qbQuestionDetailDao;

	@Autowired
	private IGradeExecutorService gradeExecutor;

	@Value("${grade.user.reference.permission}")
	private String referencePermission;

	@Value("${grade.user.candidate.permission}")
	private String candidatePermission;

	@Test
	public void sanityCheck() throws Exception {
		String examRoot = Env.getExamRoot();

		for (String languange : new String[] { "java", "php", "javascript",
				"c", "c++" }) {
			System.out.println("开始检查：" + languange);
			List<QbQuestion> qbQuestions = qbQuestionDao.getListByLanguage(
					"subject", languange);
			List<Long> qids = new ArrayList<Long>();
			for (QbQuestion qbQuestion : qbQuestions) {
				if (qbQuestion.getState() != 1)
					continue;

				qids.add(qbQuestion.getQuestionId());
			}

			BaseResponse response = gradeService.genExam(TEST_ID, qids);
			if (response.getErrorCode() != 0) {
				System.out.println("errorCode=" + response.getErrorCode());
				System.out.println("errorDesc=" + response.getErrorDesc());
				return;
			}

			int modeInt = GradeConst.toModeInt(languange);
			if (modeInt == GradeConst.MODE_UNKNOWN) {
				System.out.println("错误：不认识的语言 " + languange);
				return;
			}

			try {
				for (long qid : qids) {
					QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao
							.get(qid);
					if (qbQuestionDetail == null) {
						System.out.println("题库错误：找不到试题详情，qid=" + qid);
						continue;
					}

					String qRoot = examRoot + File.separator + TEST_ID
							+ File.separator + qid;

					QuestionContent questionContent = gson.fromJson(
							qbQuestionDetail.getContent(),
							QuestionContent.class);
					QuestionUtils.save(qRoot, questionContent, null,
							referencePermission, candidatePermission);

					try {
						int stage;
						if (modeInt == GradeConst.MODE_JAVA)
							stage = GradeConst.STAGE_REFERENCE;
						else
							stage = GradeConst.STAGE_CANDIDATE;

						ResultHolder result = gradeExecutor.testSample(stage,
								modeInt, TEST_ID, qid, 0);
						if (result.getExitValue() != 0) {
							if (stage == GradeConst.STAGE_REFERENCE)
								System.out.println("参考答案测试错误：qid=" + qid);
							else
								System.out.println("候选答案测试错误：qid=" + qid);
							System.out.println(result.getOut());
							if (result.getErr() != null)
								System.out.println(result.getErr());
						}
					} finally {
						if (!DebugConfig.isKeepTempFiles())
							QuestionUtils.delete(qRoot);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("结束检查：" + languange);
		}
	}

}
