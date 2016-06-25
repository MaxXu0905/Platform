package com.ailk.sets.platform.service.instancepart.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.impl.QbQuestionDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;

/**
 * 
 * @author panyl
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public  class PaperInstanceProcessSchool extends PaperInstanceProcessAbstract implements IPaperInstanceProcess {
	private Logger logger = LoggerFactory.getLogger(PaperInstanceProcessSchool.class);
	@Autowired
	protected  QbQuestionDaoImpl qbQuestionDaoImpl;
	@Autowired
	protected IConfigDao configDao;
	@Override
	public void processPaperInstancePart(long testId,Paper paper,PaperPart paperPart,List<Long> paperQuestionIds){
		List<PaperQuestionToSkills> questions = new ArrayList<PaperQuestionToSkills>();
		List<PaperQuestion> paperQuestions = getPaperQuestions(paperPart);
		for (PaperQuestion paperQuestion : paperQuestions) {
			PaperQuestionToSkills question = new PaperQuestionToSkills();
			question.setQuestionId(paperQuestion.getId().getQuestionId());
			question.setSkillIds(paperQuestion.getRelSkillsArray());
			question.setQuestionSeq(paperQuestion.getQuestionSeq());
			if(!questions.contains(question)){
				questions.add(question);
				paperQuestionIds.add(question.getQuestionId());
			}
		}
		savePaperInstanceQuestions(questions, testId, paperPart,
				paperPart.getSuggestTime());
	}
}
