package com.ailk.sets.platform.service.instancepart.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateTestPartDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IPaperQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.domain.paper.CandidateTestPartId;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestionId;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperInstancePartStateEnum;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.util.PaperCreateUtils;

/**
 * 
 * @author panyl
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public  class PaperInstanceProcessAbstract implements IPaperInstanceProcess {
	private Logger logger = LoggerFactory.getLogger(PaperInstanceProcessAbstract.class);
	@Autowired
	protected  IQbQuestionDao qbQuestionDao;
	@Autowired
	protected IConfigDao configDao;
	@Autowired
	protected ICandidateTestQuestionDao candidateTestQuestionDao;
	@Autowired
	protected ICandidateTestPartDao candidateTestPartDao;
	@Autowired
	protected IPaperQuestionDao paperQuestionDao;
	@Override
	public List<PaperQuestion> getPaperQuestions(PaperPart paperPart){
		int partSeq = paperPart.getId().getPartSeq();
	    int paperId = paperPart.getId().getPaperId();
		List<PaperQuestion> paperQuestions = paperQuestionDao.getPaperQuestionsByPaperIdAndSeq(paperId, partSeq);
		return paperQuestions;
	}
	@Override
	public void processPaperInstancePart(long testId,Paper paper,PaperPart paperPart,List<Long> paperQuestionIds){
		List<PaperQuestionToSkills> questions = new ArrayList<PaperQuestionToSkills>();
		List<PaperQuestion> paperQuestions = getPaperQuestions(paperPart);
		for (PaperQuestion paperQuestion : paperQuestions) {
			PaperQuestionToSkills pq = createQuestionFromQuestion(paper,paperQuestion, paperPart, questions);
				if(pq != null){
					if (!paperQuestionIds.contains(pq.getQuestionId())) {// 没有被加入
						questions.add(pq);
						paperQuestionIds.add(pq.getQuestionId());
					} else {
						logger.warn("the paper has contained the quesiton {} for createPaperInstanceByPaper ",
								pq.getQuestionId());
					}
				}
		}
		savePaperInstanceQuestions(questions, testId, paperPart,
				paperPart.getSuggestTime());
	
	}
     @Override
    public PaperQuestionToSkills createQuestionFromSelf(Paper paper,PaperQuestion paperQuestion, PaperPart paperPart, List<PaperQuestionToSkills> hasFoundQuestions) {
    		QbQuestion qbQuestion = qbQuestionDao.getEntity(paperQuestion.getId().getQuestionId());
    		logger.debug("paperQuestion.getId().getQuestionId() is {} ", paperQuestion.getId().getQuestionId());
    		String questionType = qbQuestion.getQuestionType();
    		int prebuilt = qbQuestion.getPrebuilt();
		if (paper.getTestType() == Constants.TEST_TYPE_SCHOOL || Constants.PREBUILT_SELF == prebuilt
				|| Constants.QUESTION_TYPE_NAME_INTERVIEW.equals(questionType)
				|| Constants.QUESTION_DERIVE_FLAG_YES == qbQuestion.getDeriveFlag()
				|| qbQuestion.getIsSample() == Constants.QUESTION_IS_SAMPLE
				|| paperPart.getId().getPartSeq() == PaperPartSeqEnum.EXTRA.getValue()
				|| paperPart.getId().getPartSeq() == PaperPartSeqEnum.ESSAY.getValue()) {// 附加题或者面试题  试答题，变型题,prebuilt=0直接返回
    			PaperQuestionToSkills question = new PaperQuestionToSkills();
    			question.setQuestionId(paperQuestion.getId().getQuestionId());
    			question.setSkillIds(paperQuestion.getRelSkillsArray());
    			question.setQuestionSeq(paperQuestion.getQuestionSeq());
    			if (!hasFoundQuestions.contains(question)) {
    				return question;
    			}
    		}
    	return null;
    }
     
     @Override
    public PaperQuestionToSkills createQuestionFromQuestion(Paper paper,PaperQuestion paperQuestion, PaperPart paperPart, List<PaperQuestionToSkills> hasFoundQuestions) {
    	 PaperQuestionToSkills self = createQuestionFromSelf(paper,paperQuestion, paperPart, hasFoundQuestions);
         return self;
     }
     
     
     protected void savePaperInstanceQuestions(List<PaperQuestionToSkills> questions, long testId,
 			PaperPart paperPart, int minutes) {
    	 if(questions.size() == 0){
    		 logger.warn("not any questions for testId {}, partSeq {} ", testId,paperPart.getId().getPartSeq());
    		 return ;
    	 }
    	 PaperPartSeqEnum seq = PaperPartSeqEnum.valueOf(paperPart.getId().getPartSeq());
 		List<Long> ids = new ArrayList<Long>();
 		for (PaperQuestionToSkills question : questions) {
 			logger.debug("question is  {}", question.getQuestionId());
 			ids.add(question.getQuestionId());
 			CandidateTestQuestion candidateTestQuestion = new CandidateTestQuestion();
 			CandidateTestQuestionId paperQuestionId = new CandidateTestQuestionId(testId, question.getQuestionId());
 			candidateTestQuestion.setId(paperQuestionId);
 			candidateTestQuestion.setPartSeq(seq.getValue());
 			candidateTestQuestion.setQuestionSeq(question.getQuestionSeq());
 			candidateTestQuestion.setMoti(question.getMoti());
 			candidateTestQuestion.setRelSkills(PaperCreateUtils.getSkillsInStrNoBracket(question.getSkillIds()));
 			candidateTestQuestion.setAnswerTime(0);
 			QbQuestion qbQuestion = qbQuestionDao.getEntity(question.getQuestionId());
 			candidateTestQuestion.setSuggestTime(qbQuestion.getSuggestTime());
 			candidateTestQuestionDao.saveOrUpdate(candidateTestQuestion);
 		}
 		CandidateTestPart candidateTestPart = new CandidateTestPart();
 		CandidateTestPartId id = new CandidateTestPartId(testId, seq.getValue());
 		candidateTestPart.setId(id);
 		candidateTestPart.setQuestionNum(questions.size());
 		ConfigCodeName  config = configDao.getConfigCode(Constants.CONFIG_PAPER_PART,seq.getValue()+"");
 		candidateTestPart.setPartDesc(config.getCodeName()); 
 		candidateTestPart.setQuestionType(config.getCodeName());
 		candidateTestPart.setPartPoints(qbQuestionDao.getSumPointsForQuestion(ids));
 		candidateTestPart.setSuggestTime(minutes);
 		candidateTestPart.setPartState(PaperInstancePartStateEnum.NOTANSWER.getValue());
 		candidateTestPart.setTimerType(paperPart.getTimerType());
 		candidateTestPartDao.saveOrUpdate(candidateTestPart);

 	}
}
