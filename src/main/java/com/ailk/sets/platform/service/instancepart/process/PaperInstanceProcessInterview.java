package com.ailk.sets.platform.service.instancepart.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
@Transactional(rollbackFor = Exception.class)
@Service
public class PaperInstanceProcessInterview extends PaperInstanceProcessAbstract implements IPaperInstanceProcess {
    private Logger logger = LoggerFactory.getLogger(PaperInstanceProcessInterview.class);
    
    @Override
	public void processPaperInstancePart(long testId,Paper paper,PaperPart paperPart,List<Long> paperQuestionIds){
		List<PaperQuestionToSkills> questions = new ArrayList<PaperQuestionToSkills>();
		List<PaperQuestion> paperQuestions = getPaperQuestions(paperPart);
		for (PaperQuestion paperQuestion : paperQuestions) {
			PaperQuestionToSkills pq = createQuestionFromQuestion(paper,paperQuestion, paperPart, questions);
				if(pq != null){
					if (!paperQuestionIds.contains(pq.getQuestionId())) {// 没有被加入
						questions.add(pq);
					} else {
						logger.warn("the paper has contained the interview quesiton {} for createPaperInstanceByPaper ",
								pq.getQuestionId());
					}
				}
		}
		savePaperInstanceQuestions(questions, testId, paperPart,
				paperPart.getSuggestTime());
	
	}
}
