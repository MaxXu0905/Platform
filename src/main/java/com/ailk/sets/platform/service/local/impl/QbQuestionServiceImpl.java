package com.ailk.sets.platform.service.local.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.platform.dao.impl.QbQuestionDaoImpl;
import com.ailk.sets.platform.dao.impl.QbQuestionViewDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IPaperPartDao;
import com.ailk.sets.platform.dao.interfaces.IPaperQuestionDao;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperPartId;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.domain.paper.PaperQuestionId;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.service.instancepart.process.IPaperInstanceProcess;
import com.ailk.sets.platform.service.instancepart.process.PaperInstanceProcessFactory;
import com.ailk.sets.platform.service.local.IQbQuestionService;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Transactional(rollbackFor = Exception.class)
@Service
public class QbQuestionServiceImpl implements IQbQuestionService {
	private Logger logger = LoggerFactory.getLogger(QbQuestionServiceImpl.class);

	@Autowired
	private QbQuestionDaoImpl qbQuestionDaoImpl;
	@Autowired
	private  QbQuestionViewDaoImpl qbQuestionViewDaoImpl;
	@Autowired
	private IGradeService gradeService;
	@Autowired
    private	IPaperQuestionDao paperQuestionDaoImpl;
	@Autowired
	private PaperInstanceProcessFactory instanceProcessFactory;
	@Autowired
	private IConfigDao configDao;
	@Autowired
	private IPaperPartDao paperPartDao;

	public void savePaperQuestions(List<PaperQuestionToSkills> questions, Paper paper, PaperPartSeqEnum seq,
			int partMinutes) {
		if (questions.size() <= 0) {
			logger.error("part size is 0,direct return , the seq is {}", seq.getValue());
			return;
		}
		List<Long> ids = new ArrayList<Long>();
		for (PaperQuestionToSkills question : questions) {
			PaperQuestion paperQuestion = new PaperQuestion();
			PaperQuestionId paperQuestionId = new PaperQuestionId(paper.getPaperId(), question.getQuestionId());
			paperQuestion.setId(paperQuestionId);
			paperQuestion.setPartSeq(seq.getValue());
			paperQuestion.setRelSkills(PaperCreateUtils.getSkillsInStrNoBracket(question.getSkillIds()));
			paperQuestion.setQuestionSeq(question.getQuestionSeq());
			paperQuestionDaoImpl.saveOrUpdate(paperQuestion);
			ids.add(question.getQuestionId());
		}

		PaperPart paperPart = new PaperPart();
		PaperPartId id = new PaperPartId(paper.getPaperId(), seq.getValue());
		paperPart.setId(id);
		paperPart.setQuestionNum(questions.size());
		ConfigCodeName  config = configDao.getConfigCode(Constants.CONFIG_PAPER_PART,seq.getValue()+"");
		paperPart.setPartDesc(config.getCodeName()); 
		paperPart.setSuggestTime(partMinutes);
		paperPart.setPartPoints(qbQuestionDaoImpl.getSumPointsForQuestion(ids));
		paperPartDao.saveOrUpdate(paperPart);

	}

	/**
	 * 根据试卷模板生成试卷
	 * 
	 * @param paper
	 * @throws PFServiceException
	 */
	public void createPaperInstanceByPaper(Paper paper, long testId, String inviteCode) throws PFServiceException {
		List<Long> paperQuestionIds = new ArrayList<Long>();
		List<PaperPart> paperParts = paperPartDao.getPaperPartsByPaperId(paper.getPaperId());
		for (PaperPart paperPart : paperParts) {
			 IPaperInstanceProcess process = instanceProcessFactory.getPaperInstanceProcess(PaperPartSeqEnum.valueOf(paperPart.getId().getPartSeq())); 
		     process.processPaperInstancePart(testId,paper, paperPart, paperQuestionIds);
		}
		callCreatePaperService(testId, inviteCode, paperQuestionIds);
		// return paperInstance.getPaperInstId();
	}


	public BaseResponse callCreatePaperService(long inviteId, String inviteCode, List<Long> questionIds)
			throws PFServiceException {
		logger.debug("begin to call create paper service, the question size is {}, ids is {}  for testId " + inviteId,
				questionIds.size(), PaperCreateUtils.getLongInStr(questionIds));
		try {
			long time1 = System.currentTimeMillis();
			BaseResponse response = gradeService.genExam(inviteId, questionIds);
			if (response.getErrorCode() != BaseResponse.SUCCESS) {
				logger.debug("responseCode is {}, desc is {}", response.getErrorCode(), response.getErrorDesc());
				throw new PFServiceException("create message error code is " + response.getErrorCode()
						+ ", error message is " + response.getErrorDesc());
			}
			logger.debug("end to call create paper service for testId {} used time {} ", inviteId,
					System.currentTimeMillis() - time1);
			return response;
		} catch (Exception e) {
			throw new PFServiceException(e);
		}
	}

	public List<PaperQuestionToSkills> getPaperQuestionToSkillsForPaper(int degree, List<String> skillIds,
			PaperPartSeqEnum paperType) {
		List<PaperQuestionToSkills> list  = new ArrayList<PaperQuestionToSkills>();
		List<Long> qIds = new ArrayList<Long>();
		for(String skillId : skillIds){
			List<Long> tmp = qbQuestionViewDaoImpl.getPaperQuestionsByDegreeAndSkill(degree, skillId, paperType);
			logger.debug("before============== degree={},skillId={} , size is " + tmp.size(),degree,skillId);
			if(tmp.size() == 0 )
				return list;//有一个技能没找到题,则直接返回
			if(qIds.size() == 0 ){
				qIds.addAll(tmp);
			}else{
				qIds.retainAll(tmp);
			}
		}
		for(Long qId : qIds){
			PaperQuestionToSkills questionToSkills = new PaperQuestionToSkills();
			questionToSkills.setQuestionId(qId);
			questionToSkills.setSkillIds(skillIds);
			questionToSkills.setMoti(0);
			list.add(questionToSkills);
		}
		logger.debug("the qb size is {} for skillIds {} , degree  " + degree, list.size(), PaperCreateUtils.getSkillsInStr(skillIds));
		return list;
	}
}
