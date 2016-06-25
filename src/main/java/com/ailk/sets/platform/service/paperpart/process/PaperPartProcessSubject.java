package com.ailk.sets.platform.service.paperpart.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.PositionLevel;
import com.ailk.sets.platform.intf.domain.PositionSeries;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.PaperSet;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.util.PaperCreateUtils;
import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * 编程题处理器
 * 
 * @author panyl
 * 
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class PaperPartProcessSubject extends PaperPartProcessAbstract implements
		IPaperPartProcess {

	private Logger logger = LoggerFactory
			.getLogger(PaperPartProcessSubject.class);

	@Override
	public void processPaperParts(PaperSet paperSet,
			List<QbQuestion> selfQbQuestions) {
		Paper paper = paperSet.getPaper();
		List<PaperQuestionToSkills> questions = findQuestionsBySeries(paper);
		int totalTime = 0;
		for (PaperQuestionToSkills q : questions) {
			totalTime += qbQuestionDao.getEntity(q.getQuestionId())
					.getSuggestTime();
		}
		logger.debug("found {} questions for sys subject  ", questions.size());
		totalTime += getSelfQuestionsTime(selfQbQuestions);
		questions.addAll(getSelfPaperQuestions(selfQbQuestions));
		for (int seq = 0; seq < questions.size(); seq++) {
			questions.get(seq).setQuestionSeq(seq);
		}
		qbQuestionService.savePaperQuestions(questions, paper,
				PaperPartSeqEnum.SUBJECT, totalTime);
		// 试答题
		if (questions.size() > 0 && needCreateTestQuestion(paper)) {
			PositionSeries ps = positionSeriesDao
					.getEntity(paper.getSeriesId());
			QbQuestion qbQuestion = null;
			if (StringUtils.isNotEmpty(ps.getPositionLanguage()))
				qbQuestion = qbQuestionDao.getTestSubjectQuestion(ps.getPositionLanguage());
			if (qbQuestion == null) {
				logger.warn("not found test subject question for language {}  ",
						ps.getPositionLanguage());
				qbQuestion = qbQuestionDao.getEntity(Constants.TEST_SUBJECT_QUESTION_ID);//找默认题目
			} 
			List<PaperQuestionToSkills> ques = new ArrayList<PaperQuestionToSkills>();
			PaperQuestionToSkills tmp = new PaperQuestionToSkills();
			tmp.setQuestionId(qbQuestion.getQuestionId());
			ques.add(tmp);
			processTestPaperPart(ques, paper, PaperPartSeqEnum.TEST_SUBJECT);
		}
	}

	/**
	 * @param qbSubjectToDegreeSkillses
	 * @param minutes
	 * @param paperType
	 * @return
	 */
	public List<PaperQuestionToSkills> findQuestionsBySeries(Paper paper) {
		List<PaperQuestionToSkills> hasFoundQuestionIds = new ArrayList<PaperQuestionToSkills>();
		Integer sysSubNum = paper.getSysSubjectNum();
		if (sysSubNum == null || sysSubNum <= 0) {
			logger.debug("do not need find sys subject ");
			return hasFoundQuestionIds;
		}
		PositionLevel pl = positionLevelDaoImpl.getPositionLevel(
				paper.getSeriesId(), paper.getLevel());
		int degreeLow = pl.getDegreeLow().intValue();
		int degreeHigh = pl.getDegreeHigh().intValue();

		int middle = degreeLow + (degreeHigh - degreeLow) / 2;
		int questionNum = 0;
		boolean needBreak = false;

		PositionSeries ps = positionSeriesDao.getEntity(paper.getSeriesId());
		while (true) {
			int oldSize = hasFoundQuestionIds.size();
			// 从中间往低难度找
			for (int deg = middle; deg >= degreeLow; deg--) {
				List<PaperQuestionToSkills> questions = qbQuestionDao
						.getProgramQuestionsByLanguangeAndDegree(
								ps.getPositionLanguage(), deg);
				if (!CollectionUtils.isEmpty(questions)) {
					questions.removeAll(hasFoundQuestionIds);
					if (questions.size() > 0) {
						hasFoundQuestionIds.add(PaperCreateUtils
								.randomQuestions(questions));
						questionNum++;
						if (questionNum >= sysSubNum) {
							logger.debug(
									"found {} questions for seriesId  {} , need break ",
									questionNum, paper.getSeriesId());
							needBreak = true;
							break;
						}
					}
				}
			}
			if (needBreak) {
				break;
			}
			// 往高难度找
			for (int deg = middle + 1; deg <= degreeHigh; deg++) {
				List<PaperQuestionToSkills> questions = qbQuestionDao
						.getProgramQuestionsByLanguangeAndDegree(
								ps.getPositionLanguage(), deg);
				if (!CollectionUtils.isEmpty(questions)) {
					questions.removeAll(hasFoundQuestionIds);
					if (questions.size() > 0) {
						hasFoundQuestionIds.add(PaperCreateUtils
								.randomQuestions(questions));
						questionNum++;
						if (questionNum >= sysSubNum) {
							logger.debug(
									"found {} questions for seriesId  {} , need break ",
									questionNum, paper.getSeriesId());
							needBreak = true;
							break;
						}
					}
				}
			}
			if (needBreak) {
				break;
			}
			if (oldSize == hasFoundQuestionIds.size()) {
				break;
			}
		}
		// 编程题按难度排序
		Collections.sort(hasFoundQuestionIds,
				new Comparator<PaperQuestionToSkills>() {
					@Override
					public int compare(PaperQuestionToSkills o1,
							PaperQuestionToSkills o2) {
						return o1.getDegree().compareTo(o2.getDegree());
					}
				});
		return hasFoundQuestionIds;
	}

}
