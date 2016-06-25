package com.ailk.sets.platform.service.instancepart.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Transactional(rollbackFor = Exception.class)
@Service
public class PaperInstanceProcessSubject extends PaperInstanceProcessAbstract
		implements IPaperInstanceProcess {

	private final Logger logger = LoggerFactory
			.getLogger(PaperInstanceProcessSubject.class);

	@Override
	public PaperQuestionToSkills createQuestionFromQuestion(Paper paper,
			PaperQuestion paperQuestion, PaperPart paperPart,
			List<PaperQuestionToSkills> hasFoundQuestions) {
		PaperQuestionToSkills question = createQuestionFromSelf(paper,paperQuestion,
				paperPart, hasFoundQuestions);
		if (question != null) {
			logger.debug(
					"subject the question is self or QUESTION_DERIVE_FLAG_YES , direct return  id {}",
					question.getQuestionId());
			return question;
		} else {
			QbQuestion qb = qbQuestionDao.getEntity(paperQuestion.getId()
					.getQuestionId());
			List<PaperQuestionToSkills> paperQuestionToSkills = qbQuestionDao
					.getProgramQuestionsByLanguangeAndDegree(
							qb.getProgramLanguage(), qb.getDegree());
			question = PaperCreateUtils.randomQuestions(paperQuestionToSkills);
			if (question != null) {
				question.setQuestionSeq(paperQuestion.getQuestionSeq());
				return question;
			}
		}
		logger.debug("not found any subject question for id {} ", paperQuestion
				.getId().getQuestionId());
		return null;
	}

}
