package com.ailk.sets.platform.service.paperpart.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.PaperSet;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
/**
 * 面试题处理器
 * @author panyl
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class PaperPartProcessInterview extends PaperPartProcessAbstract implements IPaperPartProcess {

	private final Logger logger = LoggerFactory.getLogger(PaperPartProcessInterview.class);
	
	@Override
	public void processPaperParts(PaperSet paperSet, List<QbQuestion> selfQbQuestions) {
		if(CollectionUtils.isEmpty(selfQbQuestions))
			return;
		Paper paper = paperSet.getPaper();
		List<PaperQuestionToSkills> questions =	new ArrayList<PaperQuestionToSkills>();
		int totalTime = 0;
		for(QbQuestion group : selfQbQuestions){
			String subAsks = group.getSubAsks();
			logger.debug("subAsks is {} ", subAsks);
			if (StringUtils.isNotEmpty(subAsks)) {
				List<String> qbIds = Arrays.asList(subAsks.split(","));
				for (int i = 0; i < qbIds.size(); i++) {
					QbQuestion qbQuestion = qbQuestionDao.getEntity(Long.valueOf(qbIds.get(i)));
					PaperQuestionToSkills q = new PaperQuestionToSkills();
					q.setQuestionId(qbQuestion.getQuestionId());
					q.setQuestionSeq(i);// 面试题需要保留顺序
					questions.add(q);
					totalTime += qbQuestion.getSuggestTime();
				}
			}
		}
        
        qbQuestionService.savePaperQuestions(questions, paper, PaperPartSeqEnum.INTEVEIW, totalTime);
        if(questions.size() > 0 && needCreateTestQuestion(paper)){
        	QbQuestion test = qbQuestionDao.getTestInterviewQuestion();
        	if(test == null){
        		logger.error("not found test interview question  ");
        	}else{
        		List<PaperQuestionToSkills> ques = new ArrayList<PaperQuestionToSkills>();
        		PaperQuestionToSkills tmp = new PaperQuestionToSkills();
        		tmp.setQuestionId(test.getQuestionId());
        		ques.add(tmp);
        		processTestPaperPart(ques, paper, PaperPartSeqEnum.TEST_INTERVIEW);
        	}
        }
	}
	
	
}
