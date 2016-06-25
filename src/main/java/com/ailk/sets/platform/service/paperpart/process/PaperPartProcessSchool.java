package com.ailk.sets.platform.service.paperpart.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.DegreeToSkills;
import com.ailk.sets.platform.intf.domain.PositionLevel;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.PaperSet;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

/**
 * 校招处理器
 * 
 * @author panyl
 * 
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class PaperPartProcessSchool extends PaperPartProcessAbstract implements
		IPaperPartProcess {

	private Logger logger = LoggerFactory
			.getLogger(PaperPartProcessSchool.class);

	@Override
	public void processPaperParts(PaperSet paperSet,
			List<QbQuestion> selfQbQuestions) {
		Paper paper = paperSet.getPaper();
		List<DegreeToSkills> degreeToSkills = paper.getDegreeToSkills();
		PositionLevel pl = positionLevelDaoImpl.getPositionLevel(
				paper.getSeriesId(), paper.getLevel());
		List<PaperQuestionToSkills> questions = findQuestionsByDegreeToSkills(
				pl, degreeToSkills);
		int totalTime = 0;
		for (PaperQuestionToSkills q : questions) {
			totalTime += qbQuestionDao.getEntity(q.getQuestionId())
					.getSuggestTime();
		}
		if (selfQbQuestions != null) {
			for (QbQuestion qb : selfQbQuestions) {
				totalTime += qb.getSuggestTime();
				PaperQuestionToSkills p = new PaperQuestionToSkills();
				p.setQuestionId(qb.getQuestionId());
				questions.add(p);
			}
		}
		qbQuestionService.savePaperQuestions(questions, paper,
				PaperPartSeqEnum.OBJECT, totalTime);
		
		// 试答题  校招也要有试答，否则用邀请方式答校招题会出问题，在微信端答题时生成实例时过滤掉试答即可
 		if (questions.size() > 0) {
			QbQuestion qbQuestion = qbQuestionDao
					.getEntity(Constants.TEST_OBJECT_QUESTION_ID);
			if (qbQuestion == null) {
				logger.error("not found the question for test of id {} ",
						Constants.TEST_OBJECT_QUESTION_ID);
			} else {
				List<PaperQuestionToSkills> ques = new ArrayList<PaperQuestionToSkills>();
				PaperQuestionToSkills tmp = new PaperQuestionToSkills();
				tmp.setQuestionId(qbQuestion.getQuestionId());
				ques.add(tmp);
				processTestPaperPart(ques, paper, PaperPartSeqEnum.TEST_OBJECT);
			}
		}
	}

}
