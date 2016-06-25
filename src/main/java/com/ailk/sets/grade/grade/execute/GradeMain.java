package com.ailk.sets.grade.grade.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateExamQuestionDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestSubjectDao;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.jdbc.CandidateExamQuestion;
import com.ailk.sets.grade.jdbc.CandidateTestSubject;
import com.ailk.sets.grade.jdbc.CandidateTestSubjectPK;
import com.ailk.sets.grade.service.intf.IReportSaveService;
import com.ailk.sets.grade.utils.MathUtils;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

@Service
public class GradeMain implements IGradeMain {

	private static final Logger logger = Logger.getLogger(GradeMain.class);

	private static final int TEST_STATE_SUCCESS = 0x20;
	private static final int TEST_STATE_FAIL = 0x40;

	@Autowired
	private ICandidateTestDao candidateTestDao;

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;

	@Autowired
	private ICandidateExamQuestionDao candidateExamQuestionDao;

	@Autowired
	private ICandidateTestSubjectDao candidateTestSubjectDao;

	@Autowired
	private IGradeExecutorService gradeExecutorService;

	@Autowired
	private IReportSaveService reportService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Long> getReadyList() {
		List<CandidateTest> candidateTests = candidateTestDao.getReadyList();
		List<Long> readyList = new ArrayList<Long>();

		for (CandidateTest candidateTest : candidateTests) {
			readyList.add(candidateTest.getTestId());
		}

		return readyList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void gradeTest(long testId) {
		try {
			CandidateTest candidateTest = candidateTestDao.getEntity(testId);
			if (candidateTest == null)
				return;

			if (candidateTest.getTestState() != 2
					&& candidateTest.getTestState() != 3)
				return;

			// 判卷
			gradeTestInternal(candidateTest);

			if (!reportService.saveReport(candidateTest)) {
				// 更新状态
				candidateTest.setTestState(candidateTest.getTestState()
						| TEST_STATE_FAIL);
			} else {
				// 更新状态
				candidateTest.setTestState(candidateTest.getTestState()
						| TEST_STATE_SUCCESS);
			}

			candidateTestDao.update(candidateTest);
		} catch (Exception e) {
			logger.error(TraceManager.getTrace(e));
		}
	}

	/**
	 * 对特定试卷进行判卷
	 * 
	 * @param candidateTest
	 *            测试对象
	 * @throws Exception
	 */
	private void gradeTestInternal(CandidateTest candidateTest)
			throws Exception {
		// 加载试题列表，只取选择题和编程题进行判卷
		long testId = candidateTest.getTestId();

		List<CandidateExamQuestion> candidateExamQuestions = candidateExamQuestionDao
				.getList(testId);
		if (candidateExamQuestions == null)
			return;

		List<CandidateTestQuestion> candidateTestQuestions = candidateTestQuestionDao
				.getList(candidateTest.getTestId());
		if (candidateTestQuestions == null)
			return;

		Map<Long, CandidateTestQuestion> candidateTestQuestionMap = new HashMap<Long, CandidateTestQuestion>();
		for (CandidateTestQuestion candidateTestQuestion : candidateTestQuestions) {
			candidateTestQuestionMap.put(candidateTestQuestion.getId()
					.getQuestionId(), candidateTestQuestion);
		}

		for (CandidateExamQuestion candidateExamQuestion : candidateExamQuestions) {
			// 不需要处理例子
			if (candidateExamQuestion.isSample())
				continue;

			long qid = candidateExamQuestion.getCandidateExamQuestionPK()
					.getQuestionId();

			// 获取试卷的类型和分值
			QbQuestion qbQuestion = qbQuestionDao.getEntity(qid);
			if (qbQuestion == null) {
				logger.error("qb_question表中找不到记录，question_id=" + qid);
				continue;
			}

			// 获取答题问题
			CandidateTestQuestion candidateTestQuestion = candidateTestQuestionMap
					.get(qid);
			if (candidateTestQuestion == null) {
				logger.error("candidate_test_question表中找不到试题，test_id=" + testId
						+ "，question_id=" + qid);
				continue;
			}

			int score = 0;

			// 判卷，只有已经答题了，才需要判卷
			if (candidateTestQuestion.getAnswerFlag() == 1) {
				try {
					switch (candidateExamQuestion.getQuestionType()) {
					case GradeConst.QUESTION_TYPE_S_CHOICE:
					case GradeConst.QUESTION_TYPE_M_CHOICE:
					case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
					case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS: {
						List<CandidateTestSubject> candidateTestSubjects = gradeExecutorService
								.execute(candidateExamQuestion);

						for (CandidateTestSubject candidateTestSubject : candidateTestSubjects) {
							score += candidateTestSubject.getScore();
						}
						break;
					}
					case GradeConst.QUESTION_TYPE_PROGRAM: {
						List<CandidateTestSubject> candidateTestSubjects = gradeExecutorService
								.execute(candidateExamQuestion);

						for (CandidateTestSubject candidateTestSubject : candidateTestSubjects) {
							CandidateTestSubjectPK candidateTestSubjectPK = candidateTestSubject
									.getCandidateTestSubjectPK();
							candidateTestSubjectPK.setTestId(candidateTest
									.getTestId());
							candidateTestSubjectPK.setQuestionId(qid);
							candidateTestSubjectDao
									.saveOrUpdate(candidateTestSubject);

							score += candidateTestSubject.getScore();
						}
						break;
					}
					default:
						continue;
					}
				} catch (Exception e) {
					logger.error(TraceManager.getTrace(e));
				}
			}

			// 对分值标准化
			double standardScore = MathUtils.round(
					(double) score * qbQuestion.getPoint()
							/ GradeConst.GRADE_INTERNAL_SCORE, 1);

			if (score == GradeConst.GRADE_INTERNAL_SCORE)
				candidateTestQuestion.setCorrectFlag(1);
			else
				candidateTestQuestion.setCorrectFlag(0);

			candidateTestQuestion.setGetScore(standardScore);
			candidateTestQuestionDao.update(candidateTestQuestion);
		}
	}

}