package com.ailk.sets.platform.service.instancepart.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.impl.CandidateTestDaoImpl;
import com.ailk.sets.platform.dao.impl.PaperSkillDaoImpl;
import com.ailk.sets.platform.dao.impl.PositionDaoImpl;
import com.ailk.sets.platform.dao.impl.PositionLevelDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.domain.PaperSkill;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.PositionLevel;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.service.local.IQbQuestionService;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Transactional(rollbackFor = Exception.class)
@Service
public class PaperInstanceProcessObject extends PaperInstanceProcessAbstract
		implements IPaperInstanceProcess {
	private Logger logger = LoggerFactory
			.getLogger(PaperInstanceProcessObject.class);

	@Autowired
	private IQbQuestionService qbQuestionService;
	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;
	@Autowired
	private PaperSkillDaoImpl paperSkillDao;
	@Autowired
	private CandidateTestDaoImpl candidateTestDaoImpl;
	@Autowired
	private PositionDaoImpl positionDaoImpl;
	@Autowired
	private PositionLevelDaoImpl positionLevelDaoImpl;

	@Override
	public void processPaperInstancePart(long testId, Paper paper,PaperPart paperPart,
			List<Long> paperQuestionIds) {
		List<PaperQuestionToSkills> questions = new ArrayList<PaperQuestionToSkills>();
		List<PaperQuestion> paperQuestions = getPaperQuestions(paperPart);
		for (PaperQuestion paperQuestion : paperQuestions) {
			PaperQuestionToSkills pq = createQuestionFromQuestion(paper,
					paperQuestion, paperPart, questions);
			if (pq != null) {
				if (!paperQuestionIds.contains(pq.getQuestionId())) {// 没有被加入
					questions.add(pq);
					paperQuestionIds.add(pq.getQuestionId());
				} else {
					logger.warn(
							"the paper has contained the object quesiton {} for createPaperInstanceByPaper ",
							pq.getQuestionId());
				}
			}
		}
		if (questions.size() > 0) {
			long time1 = System.currentTimeMillis();
			List<PaperSkill> skills = paperSkillDao.getPaperSkills(paperPart
					.getId().getPaperId());
			List<String> skillIds = new ArrayList<String>();
			for (PaperSkill skill : skills) {
				skillIds.add(skill.getId().getSkillId());
			}
			CandidateTest test = candidateTestDaoImpl.getEntity(testId);
			Position position = positionDaoImpl.getPosition(test
					.getPositionId());
			int level = position.getLevel();
			int seriesId = position.getSeriesId();
			PositionLevel pl = positionLevelDaoImpl.getPositionLevel(seriesId,
					level);
			List<Integer> degrees = new ArrayList<Integer>();
			for (int degree = pl.getDegreeLow().intValue(); degree <= pl
					.getDegreeHigh().intValue(); degree++) {
				degrees.add(degree);
			}
			if (skillIds.size() == 0 || degrees.size() == 0) {
				logger.error("the skills or degrees is 0 for positionId {} ",
						test.getPositionId());
			} else {
				int motiSize = questions.size() / 10;
				List<PaperQuestionToSkills> motiQuestions = qbQuestionDao
						.getMotiObjectQuestions(skillIds, degrees);
				if (motiQuestions != null) {
					logger.debug("found {} moti questions ",
							motiQuestions.size());
					motiQuestions.removeAll(questions);
					if (motiQuestions.size() <= motiSize) {
						questions.addAll(motiQuestions);
						paperQuestionIds
								.addAll(PaperCreateUtils
										.getPaperQuestionIdsFromQuesitonToSkills(motiQuestions));
					} else {
						int index = 0;
						while (index < motiSize) {
							PaperQuestionToSkills question = PaperCreateUtils
									.randomQuestions(motiQuestions);
							questions.add(question);
							paperQuestionIds.add(question.getQuestionId());
							motiQuestions.remove(question);
							index++;
						}
					}

				}
			}
			logger.debug("moti waste times  {} for testId {} ",
					System.currentTimeMillis() - time1, testId);
		}

		savePaperInstanceQuestions(questions, testId,
				paperPart,
				paperPart.getSuggestTime());

	}

	@Override
	public PaperQuestionToSkills createQuestionFromQuestion(Paper paper,
			PaperQuestion paperQuestion, PaperPart paperPart,
			List<PaperQuestionToSkills> hasFoundQuestions) {
		PaperQuestionToSkills question = createQuestionFromSelf(paper,paperQuestion,
				paperPart, hasFoundQuestions);
		if (question != null) {
			logger.debug(
					"the question is self or QUESTION_DERIVE_FLAG_YES , direct return  id {}",
					question.getQuestionId());
			return question;
		} else {
			List<String> relSkillIds = new ArrayList<String>();
			String[] relSkills = paperQuestion.getRelSkills().split(",");
			for (String skillId : relSkills) {
				relSkillIds.add(skillId);
			}
			QbQuestion qbQuestion = qbQuestionDao.getEntity(paperQuestion
					.getId().getQuestionId());
			question = getQuestionByPaperQuestion(qbQuestion, relSkillIds,
					PaperPartSeqEnum.valueOf(paperPart.getId().getPartSeq()),
					hasFoundQuestions);
			if (question != null) {
				return question;
			}
		}
		logger.debug("not found any object question for id {} ", paperQuestion
				.getId().getQuestionId());
		return null;
	}

	private PaperQuestionToSkills getQuestionByPaperQuestion(
			QbQuestion qbQuestion, List<String> relSkillIds,
			PaperPartSeqEnum paperType,
			List<PaperQuestionToSkills> hasFoundQuestions) {

		List<String> tmp = qbQuestionSkillDao.getSkillIds(qbQuestion
				.getQuestionId());
		List<String> skillIds = new ArrayList<String>();//copy 如果直接操作tmp  会导致cache出现问题
		for(String skillId : tmp){
			skillIds.add(skillId);
		}
		int degree = qbQuestion.getDegree();
		skillIds.removeAll(relSkillIds);// 先删除掉找出原题的skills
		int subSize = skillIds.size();
		PaperQuestionToSkills thePaperQuestionToSkills = new PaperQuestionToSkills();
		thePaperQuestionToSkills.setQuestionId(qbQuestion.getQuestionId());
		thePaperQuestionToSkills.setSkillIds(relSkillIds);
		while (true) {
			List<List<String>> splitSkillIds1 = PaperCreateUtils.getSubSet(
					skillIds, subSize); // 删除原题后的skills子集依次减少(先取n个元素子集，再取n-1个元素的子集。。。)
			if (splitSkillIds1 == null) {
				// 如果按多余的skill没找到，则根据relskillid找
				// TODO 是否要递归去找，有可能原问题已经被加入到hasFoundQuestions
				List<PaperQuestionToSkills> queIds = qbQuestionService
						.getPaperQuestionToSkillsForPaper(degree, relSkillIds,
								paperType);
				queIds.remove(thePaperQuestionToSkills);
				queIds.removeAll(hasFoundQuestions); // 删除已经存在的题目
				logger.debug("found by relskillIds....");
				if (!CollectionUtils.isEmpty(queIds)) {// 还有没有选中的题目，则直接随机选取一个题目
					logger.debug(
							"not found for more skills but found {} questions by relSkillIds {}",
							queIds.size(),
							PaperCreateUtils.getSkillsInStr(relSkillIds));
					PaperQuestionToSkills questionToSkills = PaperCreateUtils
							.randomQuestions(queIds);
					return questionToSkills;
				} else {
					logger.warn(
							"begin to deep because of not found any question for relSkillIds {}  ,degree is {}",
							PaperCreateUtils.getSkillsInStr(relSkillIds),
							degree);
					return getQuestionByPaperQuestionDeep(degree,
							thePaperQuestionToSkills, relSkillIds, paperType,
							hasFoundQuestions);
				}
			}
			for (List<String> splitIds : splitSkillIds1) {
				logger.debug("found by splitIds....");
				splitIds.addAll(relSkillIds);// 加上找出原题skills
				List<PaperQuestionToSkills> queIds = qbQuestionService
						.getPaperQuestionToSkillsForPaper(degree, splitIds,
								paperType);
				queIds.remove(thePaperQuestionToSkills);
				queIds.removeAll(hasFoundQuestions); // 删除已经存在的题目
				if (!CollectionUtils.isEmpty(queIds)) {// 还有没有选中的题目，则直接随机选取一个题目
					logger.debug("found {} questions for {}", queIds.size(),
							PaperCreateUtils.getSkillsInStr(splitIds));
					PaperQuestionToSkills questionToSkills = PaperCreateUtils
							.randomQuestions(queIds);
					return questionToSkills;
				}
			}

			subSize--;
		}
	}

	private PaperQuestionToSkills getQuestionByPaperQuestionDeep(int degree,
			PaperQuestionToSkills thePaperQuestionToSkills,
			List<String> relSkillIds, PaperPartSeqEnum paperType,
			List<PaperQuestionToSkills> hasFoundQuestions) {
		int subSize = relSkillIds.size();
		while (true) {
			List<List<String>> splitSkillIds1 = PaperCreateUtils.getSubSet(
					relSkillIds, subSize); // 删除原题后的skills子集依次减少(先取n个元素子集，再取n-1个元素的子集。。。)
			if (splitSkillIds1 == null) {
				logger.warn("deep not found any question for {}, degee {}",
						PaperCreateUtils.getSkillsInStr(relSkillIds), degree);
				break;
			}
			for (List<String> splitIds : splitSkillIds1) {
				logger.debug("deep found by splitIds....");
				List<PaperQuestionToSkills> queIds = qbQuestionService
						.getPaperQuestionToSkillsForPaper(degree, splitIds,
								paperType);
				queIds.remove(thePaperQuestionToSkills);
				queIds.removeAll(hasFoundQuestions); // 删除已经存在的题目
				if (!CollectionUtils.isEmpty(queIds)) {// 还有没有选中的题目，则直接随机选取一个题目
					logger.debug("deep found {} questions for {}",
							queIds.size(),
							PaperCreateUtils.getSkillsInStr(splitIds));
					PaperQuestionToSkills questionToSkills = PaperCreateUtils
							.randomQuestions(queIds);
					return questionToSkills;
				}
			}
			subSize--;
		}
		logger.warn(
				"not found any for deep , so return itself  skills {}, degree {}",
				PaperCreateUtils.getSkillsInStr(relSkillIds), degree);
		return thePaperQuestionToSkills;// 如果没有找到，则返回自己
	}

}
