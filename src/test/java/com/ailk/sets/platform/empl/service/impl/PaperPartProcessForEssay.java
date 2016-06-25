package com.ailk.sets.platform.empl.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IPaperPartDao;
import com.ailk.sets.platform.dao.interfaces.IPaperQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperPartId;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaperPartProcessForEssay {
	private Logger logger = LoggerFactory.getLogger(PaperPartProcessForEssay.class);
	@Autowired
	private IPaperQuestionDao paperQuestionDao;
	@Autowired
	private IQbQuestionDao questionDao;
	@Autowired
	private IPaperPartDao paperPartDao;
	/**
	 * 将试卷的附加题部分变为问答题和智力题
	 * @param paperId
	 */
	public void processPaperForEssay(int paperId) {
		List<PaperQuestion> essayQuestions = paperQuestionDao.getPaperQuestionsByPaperIdAndSeq(paperId, PaperPartSeqEnum.ESSAY.getValue());
        if(essayQuestions.size() > 0 ){
        	throw new RuntimeException("has found essay quesiton ");
        }
		List<PaperQuestion> paperQuestions = paperQuestionDao.getPaperQuestionsByPaperIdAndSeq(paperId, PaperPartSeqEnum.EXTRA.getValue());
		PaperPart essayPart = null;
		PaperPart extraPart = null;
       
		if(paperQuestions.size() == 0){
			throw new RuntimeException("not found any for extra ");
		}
		logger.debug("begin to process essay , paperId {} , the old extra size is {} ", paperId, paperQuestions.size());
		int essaySeq = 0;
		int extraSeq = 0;
		for(PaperQuestion paperQuestion : paperQuestions){
			long questionId = paperQuestion.getId().getQuestionId();
			QbQuestion qbQuestion = questionDao.getEntity(questionId);
			if(qbQuestion.getQuestionType().equals(Constants.QUESTION_TYPE_NAME_ESSAY)){
				logger.debug("the question is essay , questionId {} , desc {} ", qbQuestion.getQuestionId(), qbQuestion.getQuestionDesc());
				paperQuestion.setPartSeq(PaperPartSeqEnum.ESSAY.getValue());
				paperQuestion.setQuestionSeq(essaySeq++);
				paperQuestionDao.saveOrUpdate(paperQuestion);
				if(essayPart == null){
					essayPart = new PaperPart();
					essayPart.setId(new PaperPartId(paperId, PaperPartSeqEnum.ESSAY.getValue()));
					essayPart.setPartDesc("问答题");
					essayPart.setQuestionNum(1);
					essayPart.setSuggestTime(qbQuestion.getSuggestTime());
					essayPart.setPartPoints(qbQuestion.getPoint());
				}else{
					essayPart.setQuestionNum(essayPart.getQuestionNum() + 1);
					essayPart.setSuggestTime(essayPart.getSuggestTime() + qbQuestion.getSuggestTime());
					essayPart.setPartPoints(essayPart.getPartPoints() + qbQuestion.getPoint());
				}
				
			}else{
				paperQuestion.setQuestionSeq(extraSeq++);
				paperQuestionDao.saveOrUpdate(paperQuestion);
				if(extraPart == null){
					extraPart = new PaperPart();
					extraPart.setId(new PaperPartId(paperId, PaperPartSeqEnum.EXTRA.getValue()));
					extraPart.setPartDesc("智力题");
					extraPart.setQuestionNum(1);
					extraPart.setSuggestTime(qbQuestion.getSuggestTime());
					extraPart.setPartPoints(qbQuestion.getPoint());
				}else{
					extraPart.setQuestionNum(extraPart.getQuestionNum() + 1);
					extraPart.setSuggestTime(extraPart.getSuggestTime()+qbQuestion.getSuggestTime());
					extraPart.setPartPoints(extraPart.getPartPoints() + qbQuestion.getPoint());
				}
			}
		}
		
		if(essayPart != null){//表示有问答题
			logger.debug("save essayPart for paperId {}, size {}  ", paperId, essayPart.getQuestionNum());
		    paperPartDao.saveOrUpdate(essayPart);
		    if(extraPart != null){//除去问答题外还有题
		    	logger.debug("save extraPart for paperId {}, size {}  ", paperId, extraPart.getQuestionNum());
		    	paperPartDao.saveOrUpdate(extraPart);
		    }else{//全是问答题
		    	logger.debug("delete extraPart for paperId {}, essayPart size {}  ", paperId,essayPart.getQuestionNum());
		    	 paperPartDao.delete(new PaperPartId(paperId, PaperPartSeqEnum.EXTRA.getValue()));
		    }
		}
		
	}
}
