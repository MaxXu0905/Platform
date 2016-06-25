package com.ailk.sets.platform.service.paperpart.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.impl.PositionLevelDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPaperPartDao;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.DegreeToSkills;
import com.ailk.sets.platform.intf.domain.PositionLevel;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.ailk.sets.platform.service.local.IQbQuestionService;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Transactional(rollbackFor = Exception.class)
@Service
public abstract class PaperPartProcessAbstract implements IPaperPartProcess {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected IQbQuestionService qbQuestionService;

	@Autowired
	protected PositionLevelDaoImpl positionLevelDaoImpl;

	@Autowired
	protected IQbQuestionDao qbQuestionDao;

	@Autowired
	protected IPositionSeriesDao positionSeriesDao;
	
	@Autowired
	protected IPaperPartDao paperPartDao;

	@Override
	public void processTestPaperPart(List<PaperQuestionToSkills> questions,
			Paper paper, PaperPartSeqEnum partSeq) {
		qbQuestionService.savePaperQuestions(questions, paper, partSeq,
				Constants.TEST_PART_TIME);
	}

	/**
	 * 获取自定义题目时长
	 * 
	 * @param selfQbQuestions
	 * @return
	 */
	@Override
	public int getSelfQuestionsTime(List<QbQuestion> selfQbQuestions) {
		int time = 0;
		if (selfQbQuestions == null) {
			return time;
		}
		for (QbQuestion qb : selfQbQuestions) {
			time += qb.getSuggestTime();
		}
		return time;
	}

	@Override
	public List<PaperQuestionToSkills> getSelfPaperQuestions(
			List<QbQuestion> selfQbQuestions) {
		List<PaperQuestionToSkills> questions = new ArrayList<PaperQuestionToSkills>();
		if (selfQbQuestions == null) {
			return questions;
		}
		for (QbQuestion qb : selfQbQuestions) {
			PaperQuestionToSkills p = new PaperQuestionToSkills();
			p.setQuestionId(qb.getQuestionId());
			questions.add(p);
		}
		return questions;
	}

	@Override
	public boolean needCreateTestQuestion(Paper paper) {
		return paper.getTestType() == Constants.TEST_TYPE_CLUB;
	}
	
	/**
	 * @param qbSubjectToDegreeSkillses
	 * @param minutes
	 * @param paperType
	 * @return
	 */
	protected List<PaperQuestionToSkills> findQuestionsByDegreeToSkills(
			PositionLevel pl, List<DegreeToSkills> degreeToSkills) {
		List<PaperQuestionToSkills> hasFoundQuestionIds = new ArrayList<PaperQuestionToSkills>();
		if (degreeToSkills == null) {
			logger.debug("not any hasFoundQuestionIds .... ");
			return hasFoundQuestionIds;
		}
		for (DegreeToSkills degreeToSkill : degreeToSkills) {
			QbSkillDegree qbSkillDegree = degreeToSkill.getLabelDegree();
			List<QbSkill> skills = degreeToSkill.getSkills();
			for (QbSkill skill : skills) {
				int skillQuestionNum = skill.getQuestionNum();
				int degreeLow = qbSkillDegree.getLowDegree(pl);
				int degreeHigh = qbSkillDegree.getHighDegree(pl);
				List<String> skillIds = new ArrayList<String>();
				skillIds.add(skill.getSkillId());
				int questionNum = 0;
				boolean needBreak = false;
				while (true) {
					int oldSize = hasFoundQuestionIds.size();
					for (int deg = degreeLow; deg <= degreeHigh; deg++) {
						List<PaperQuestionToSkills> questions = qbQuestionService
								.getPaperQuestionToSkillsForPaper(deg,
										skillIds, PaperPartSeqEnum.OBJECT);
						questions.removeAll(hasFoundQuestionIds);
						if (questions.size() > 0) {
							hasFoundQuestionIds.add(PaperCreateUtils
									.randomQuestions(questions));
							questionNum++;
							if (questionNum >= skillQuestionNum) {
								logger.debug(
										"found {} questions for skill {} , need break ",
										questionNum, skill.getSkillId());
								needBreak = true;
								break;
							}
						}
					}
					if (needBreak) {
						break;
					}
					if (oldSize == hasFoundQuestionIds.size()) {
						logger.debug(
								"not have many more questions ,found {}  questions for skill {} , need break ",
								questionNum, skill.getSkillId());
						break;
					}
				}
			}
		}
		return hasFoundQuestionIds;
	}
}
