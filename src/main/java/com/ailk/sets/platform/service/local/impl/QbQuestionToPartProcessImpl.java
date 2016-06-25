package com.ailk.sets.platform.service.local.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.service.local.IQbQuestionToPartProcess;

@Transactional(rollbackFor = Exception.class)
@Service
public class QbQuestionToPartProcessImpl implements IQbQuestionToPartProcess {

	private Logger logger = LoggerFactory.getLogger(QbQuestionToPartProcessImpl.class);
	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Override
	public Map<PaperPartSeqEnum, List<QbQuestion>> getPaperPartQuestions(
			Collection<Long> questionIds) {
		Map<PaperPartSeqEnum, List<QbQuestion>> quesToParts = new HashMap<PaperPartSeqEnum, List<QbQuestion>>();
		if (questionIds == null)
			return quesToParts;
		for (Long questionId : questionIds) {
			QbQuestion question = qbQuestionDao.getEntity(questionId);
			String type = question.getQuestionType();
			int category = question.getCategory();
			if (category == Constants.CATEGORY_SKILL) {// 技能  划分为选择题  编程题 以及问答题
				if (type.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE)
						|| type.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE)
						|| type.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE_PLUS)
						|| type.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE_PLUS)) {
					putQuestionIdToParts(question, quesToParts,
							PaperPartSeqEnum.OBJECT);
				}
				if (type.equals(Constants.QUESTION_TYPE_NAME_PROGRAM)
						|| type.equals(Constants.QUESTION_TYPE_NAME_TEXT)) {
					putQuestionIdToParts(question, quesToParts,
							PaperPartSeqEnum.SUBJECT);
				}
				if (type.equals(Constants.QUESTION_TYPE_NAME_ESSAY)) {
					putQuestionIdToParts(question, quesToParts,
							PaperPartSeqEnum.ESSAY);
				}
			} else if (category == Constants.CATEGORY_BUSI) {// 业务
				putQuestionIdToParts(question, quesToParts,
						PaperPartSeqEnum.EXTRA);
			} else if (category == Constants.CATEGORY_INTE) {// 智力
				putQuestionIdToParts(question, quesToParts,
						PaperPartSeqEnum.EXTRA);
			} else if (category == Constants.CATEGORY_INTER) {// 面试题
				if (type.equals(Constants.QUESTION_TYPE_NAME_GROUP)) {
					putQuestionIdToParts(question, quesToParts,
							PaperPartSeqEnum.INTEVEIW);
				}
			}
		}
		return quesToParts;
	}
	/**
	 * 获取校招试卷题目分类信息 
	 * @param questionIds
	 * @return
	 */
	public Map<PaperPartSeqEnum, List<QbQuestion>> getCampusPaperPartQuestions(Collection<Long> questionIds){

		Map<PaperPartSeqEnum, List<QbQuestion>> quesToParts = new HashMap<PaperPartSeqEnum, List<QbQuestion>>();
		if (questionIds == null)
			return quesToParts;
		for (Long questionId : questionIds) {
			QbQuestion question = qbQuestionDao.getEntity(questionId);
			String type = question.getQuestionType();
			int category = question.getCategory();
			if (category == Constants.CATEGORY_SKILL) {// 技能选择题  
				if (type.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE)
						|| type.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE)) {//校招没有单选文本以及多选文本
					putQuestionIdToParts(question, quesToParts,
							PaperPartSeqEnum.OBJECT);
				}else if (type.equals(Constants.QUESTION_TYPE_NAME_ESSAY)) {
					putQuestionIdToParts(question, quesToParts,
							PaperPartSeqEnum.ESSAY);
				}
				else{
					logger.warn("not found question type for campus of skill qid {} ", questionId);
				}
			} 
			else if (category == Constants.CATEGORY_INTE) {// 智力
				putQuestionIdToParts(question, quesToParts,
						PaperPartSeqEnum.EXTRA);
			} else if (category == Constants.CATEGORY_BUSI) {// 业务
				putQuestionIdToParts(question, quesToParts,
						PaperPartSeqEnum.EXTRA);
			}else {
				logger.warn("not found question type for campus  qId {} ", questionId);
			}
		}
		return quesToParts;
	
	}
	/**
	 * 
	 * @param question
	 * @param quesToParts
	 * @param seq
	 */
	private void putQuestionIdToParts(QbQuestion question,
			Map<PaperPartSeqEnum, List<QbQuestion>> quesToParts,
			PaperPartSeqEnum seq) {
		List<QbQuestion> questions = quesToParts.get(seq);
		if (questions == null) {
			questions = new ArrayList<QbQuestion>();
			quesToParts.put(seq, questions);
		}
		questions.add(question);
	}

}
