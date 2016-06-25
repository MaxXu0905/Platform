package com.ailk.sets.platform.service.category;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.domain.EmployerPosHistory;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Question;
import com.ailk.sets.platform.intf.model.param.RandomQuestionParam;
import com.ailk.sets.platform.intf.model.question.Interview;
import com.ailk.sets.platform.intf.model.question.InterviewGroup;

public class InterviewCategory implements Category {

	private IQbQuestionDao qbQuestionDao;

	public InterviewCategory(IQbQuestionDao qbQuestionDao) {
		this.qbQuestionDao = qbQuestionDao;
	}

	@Override
	public String getHistoryIdSuffix(Position pos) {
		return null;
	}

	@Override
	public List<Question> getHistory(List<EmployerPosHistory> list) throws PFServiceException {
		List<Question> resultList = new ArrayList<Question>();
		try {
			if (!CollectionUtils.isEmpty(list)) {
				for (EmployerPosHistory eph : list) {
					InterviewGroup interviewGroup = new InterviewGroup();
					interviewGroup.setQuestionId(Long.parseLong(eph.getId().getHistoryId()));
					List<QbQuestion> questions = qbQuestionDao.getQbQuestionsByGroup(Long.parseLong(eph.getId().getHistoryId()));
					List<Interview> interviews = new ArrayList<Interview>();
					if (!CollectionUtils.isEmpty(questions)) {
						for (QbQuestion qq : questions) {
							Interview interview = new Interview();
							PropertyUtils.copyProperties(interview, qq);
							interviews.add(interview);
						}
					}
					interviewGroup.setQbQuestion(interviews);
					resultList.add(interviewGroup);
				}
			}
		} catch (Exception e) {
			throw new PFServiceException(e);
		}
		return resultList;
	}

	@Override
	public Question getRandom(RandomQuestionParam param) throws PFServiceException {
		QbQuestion question = qbQuestionDao.getRandomQuestion(param.getCategory());
		if (question != null ) {
			InterviewGroup interviewGroup = new InterviewGroup();
			interviewGroup.setQuestionId(question.getQuestionId());
			List<Interview> interviews = new ArrayList<Interview>();
			List<QbQuestion> questions = qbQuestionDao.getQbQuestionsByGroup(question.getQuestionId());
			try {
				if (!CollectionUtils.isEmpty(questions)) {
					for (QbQuestion qq : questions) {
						Interview interview = new Interview();
						PropertyUtils.copyProperties(interview, qq);
						interviews.add(interview);
					}
					interviewGroup.setQbQuestion(interviews);
				}
				return interviewGroup;
			} catch (Exception e) {
				throw new PFServiceException(e);
			}
		} else
			return null;
	}
}
