package com.ailk.sets.platform.service.paperpart.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperPartId;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.PaperSet;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
/**
 * 附加题处理器
 * @author panyl
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class PaperPartProcessExtra extends PaperPartProcessAbstract implements IPaperPartProcess {

	private Logger logger = LoggerFactory.getLogger(PaperPartProcessExtra.class);
	@Override
	public void processPaperParts(PaperSet paperSet, List<QbQuestion> selfQbQuestions) {
		if(CollectionUtils.isEmpty(selfQbQuestions))
			return;
		Paper paper = paperSet.getPaper();
        List<PaperQuestionToSkills> questions =	new ArrayList<PaperQuestionToSkills>();
        int totalTime = 0;
        totalTime += getSelfQuestionsTime(selfQbQuestions);
        questions.addAll(getSelfPaperQuestions(selfQbQuestions));
        qbQuestionService.savePaperQuestions(questions, paper, PaperPartSeqEnum.EXTRA, totalTime);
        
        PaperPart paperPart = paperPartDao.getEntity(new PaperPartId(paper.getPaperId(), PaperPartSeqEnum.TEST_OBJECT.getValue()));
        
         //问答题增加选择题 试答题
      	if (paperPart == null && questions.size() > 0 && needCreateTestQuestion(paper)) {
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
