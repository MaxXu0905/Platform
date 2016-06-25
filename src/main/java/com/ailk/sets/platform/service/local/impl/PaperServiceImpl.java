package com.ailk.sets.platform.service.local.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.impl.PositionLevelDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.intf.domain.PositionLevel;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.service.local.IPaperService;
import com.ailk.sets.platform.service.local.IQbQuestionService;

@Transactional(rollbackFor = Exception.class)
@Service
public class PaperServiceImpl implements IPaperService {

	@Autowired
	IQbQuestionService qbQuestionService;


	@Autowired
	private PositionLevelDaoImpl positionLevelDaoImpl;
	@Autowired
	private IPositionDao positionDao;
	@Autowired
	private IPaperDao paperDao;

	private Logger logger = LoggerFactory.getLogger(PaperServiceImpl.class);

	/**
	 * 根据职位id生成试卷实例
	 * 
	 * @throws PFServiceException
	 */
	public void createPaperInstance(int positionId, long testId,
			String inviteCode) throws PFServiceException {
		long time1 = System.currentTimeMillis();
		Position position = positionDao.getEntity(positionId);
		int paperId = position.getPaperId();
		Paper paper = paperDao.getEntity(paperId) ;
		qbQuestionService.createPaperInstanceByPaper(paper, testId, inviteCode);
		logger.debug("create paper used {} for testId {} ",
				System.currentTimeMillis() - time1, testId);
	}

	/*
	 * public PosResponse createPaper(String paperSubCode,
	 * List<DegreeToSkillLabels> degreeToSkillses, PositionSet positionSet)
	 * throws PFServiceException { ConfigSysParameters sys = null; sys =
	 * configSysParamDao.getConfigSysParameters(Constants.PAPER_TOTAL_MINUTES);
	 * int PAPER_TOTAL_MINUTES = Integer.valueOf(sys.getParamValue()); Position
	 * position = positionSet.getPosition(); PosResponse response = new
	 * PosResponse(); response.setCode(FuncBaseResponse.SUCCESS); Paper paper =
	 * savePaper(position);
	 * 
	 * List<PositionSeries> ps = qbNoramlDaoImpl.getNoramlListByHql(
	 * "from PositionSeries where positionLanguage = '" +
	 * seriesDao.getEntity(position.getSeriesId()).getPositionLanguage() + "'",
	 * PositionSeries.class); if (ps == null || ps.size() == 0) {
	 * logger.error("not found PositionSeries for language {} ",
	 * seriesDao.getEntity(position.getSeriesId()).getPositionLanguage()); throw
	 * new PFServiceException("not found PositionSeries for language  " +
	 * seriesDao.getEntity(position.getSeriesId()).getPositionLanguage()); } int
	 * seriesId = position.getSeriesId();
	 * 
	 * PositionLevel pl = getPositionLevelByCache(seriesId,
	 * position.getLevel());
	 * 
	 * position.setPaperId(paper.getPaperId());
	 * position.setSeriesId(pl.getId().getSeriesId());
	 * position.setPublishDate(new Timestamp(System.currentTimeMillis()));
	 * 
	 * Map<String, QbSubjectToDegreeSkills> qbSubjectToDegreeSkillses =
	 * PaperCreateUtils.parseSubjectToDegreeSkills(paperSubCode,
	 * degreeToSkillses, pl); if (qbSubjectToDegreeSkillses.get(paperSubCode) ==
	 * null) { response.setCode(FuncBaseResponse.FAILED);
	 * response.setMessage("not found any skill for the subject"); return
	 * response; } logger.info("qbSubjectToDegreeSkillses info is {} ",
	 * qbSubjectToDegreeSkillses.toString()); // 根据科目分出时间比例(本科目以及非本科目) QbSubject
	 * qbSubject = qbNoramlDaoImpl.getNormalObject(paperSubCode,
	 * QbSubject.class); Map<String, QbSubjectToDegreeSkills> paperSub = new
	 * HashMap<String, QbSubjectToDegreeSkills>(); paperSub.put(paperSubCode,
	 * qbSubjectToDegreeSkillses.get(paperSubCode));
	 * qbSubjectToDegreeSkillses.remove(paperSubCode);
	 * 
	 * // 客观题 List<PaperQuestionToSkills> objectQuestions = new
	 * ArrayList<PaperQuestionToSkills>(); // 主观题 List<PaperQuestionToSkills>
	 * subjectQuestions = new ArrayList<PaperQuestionToSkills>(); // 附加题
	 * QuestionProcessResult extras =
	 * questionProcessService.getExtraPartQuestions(positionSet); int totalTime
	 * = PAPER_TOTAL_MINUTES; int sumObjectMinutes = 0; int sumSubjectMinutes =
	 * 0; // while(true){
	 * 
	 * int oldTime = totalTime; int objectMinutes = (int) (totalTime *
	 * (pl.getSubQuestionRatio() * 0.01)); int paperCodeObjectMinutes =
	 * objectMinutes;
	 * 
	 * int subjectMinutes = totalTime - objectMinutes; int
	 * paperCodeSubjectMinutes = subjectMinutes;
	 * 
	 * int otherTimeLeft = 0;
	 * 
	 * int otherObjectLeft = 0; int otherSubjectLeft = 0; boolean hasOtherCode =
	 * false; if (qbSubjectToDegreeSkillses.size() > 0) { paperCodeObjectMinutes
	 * = (int) (objectMinutes * (qbSubject.getObjectRatio() * 0.1));//
	 * 本科目客观题所占时间 int otherObjectMinutes = objectMinutes -
	 * paperCodeObjectMinutes; // 其他科目客观题所占时间
	 * 
	 * paperCodeSubjectMinutes = (int) (subjectMinutes *
	 * (qbSubject.getSubjectRatio() * 0.1));// 本科目主观题所占时间 int
	 * otherSubjectMinutes = subjectMinutes - paperCodeSubjectMinutes; //
	 * 其他科目主观题所占时间
	 * 
	 * hasOtherCode = true; otherObjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills
	 * (qbSubjectToDegreeSkillses, otherObjectMinutes, PaperPartSeqEnum.OBJECT,
	 * objectQuestions, position.getLevel());// 其他科目客观题 sumObjectMinutes +=
	 * otherObjectLeft > 0 ? otherObjectMinutes - otherObjectLeft :
	 * otherObjectMinutes; otherSubjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills
	 * (qbSubjectToDegreeSkillses, otherSubjectMinutes,
	 * PaperPartSeqEnum.SUBJECT, subjectQuestions, position.getLevel());//
	 * 其他科目主观题 sumSubjectMinutes += otherSubjectLeft > 0 ? otherSubjectMinutes -
	 * otherSubjectLeft : otherSubjectMinutes; otherTimeLeft = (otherObjectLeft
	 * > 0 ? otherObjectLeft : 0) + (otherSubjectLeft > 0 ? otherSubjectLeft :
	 * 0);
	 * logger.debug("otherObject size is {}, otherSubjectSize is {} for paperId "
	 * + paper.getPaperId(), objectQuestions.size(), subjectQuestions.size()); }
	 * int paperObjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills(paperSub,
	 * paperCodeObjectMinutes, PaperPartSeqEnum.OBJECT, objectQuestions,
	 * position.getLevel());// 本科目客观题
	 * 
	 * int paperSubjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills(paperSub,
	 * paperCodeSubjectMinutes, PaperPartSeqEnum.SUBJECT, subjectQuestions,
	 * position.getLevel());// 本科目主观题
	 * 
	 * sumObjectMinutes += paperObjectLeft > 0 ? paperCodeObjectMinutes -
	 * paperObjectLeft : paperCodeObjectMinutes; sumSubjectMinutes +=
	 * paperSubjectLeft > 0 ? paperCodeSubjectMinutes - paperSubjectLeft :
	 * paperCodeSubjectMinutes;
	 * 
	 * if (otherObjectLeft > 0 && paperObjectLeft <= 0) {// 其他科目客观题时间用来出本科目客观题
	 * logger.debug(
	 * "papercode create object from other object , the otherObjectLeft is {}  for paperId {}"
	 * , otherObjectLeft, paper.getPaperId()); int old = otherObjectLeft;
	 * otherObjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills(paperSub,
	 * otherObjectLeft, PaperPartSeqEnum.OBJECT,
	 * objectQuestions,position.getLevel());// 本科目主观题 sumObjectMinutes +=
	 * (otherObjectLeft > 0 ? old - otherObjectLeft : old); } else if
	 * (hasOtherCode && paperObjectLeft > 0 && otherObjectLeft <= 0) {//
	 * 本科目客观题时间用来出其他科目客观题 logger.debug(
	 * "othercode create object  from paperobject , the paperObjectLeft is {}  for paperId {}"
	 * , paperObjectLeft, paper.getPaperId()); int old = paperObjectLeft;
	 * paperObjectLeft = qbQuestionService.findQuestionsByQbSubjectDegreeSkills(
	 * qbSubjectToDegreeSkillses, paperObjectLeft, PaperPartSeqEnum.OBJECT,
	 * objectQuestions, position.getLevel());// 本科目客观题 sumObjectMinutes +=
	 * (paperObjectLeft > 0 ? old - paperObjectLeft : old); }
	 * 
	 * if (otherSubjectLeft > 0 && paperSubjectLeft <= 0) {// 其他科目主观题用于生成本科目主观题
	 * logger.debug(
	 * "papercode create subject from other subject , the otherSubjectLeft is {}  for paperId {}"
	 * , otherSubjectLeft, paper.getPaperId()); int old = otherSubjectLeft;
	 * otherSubjectLeft = otherSubjectLeft + paperSubjectLeft;// 减掉欠的时间
	 * otherSubjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills(paperSub,
	 * otherSubjectLeft, PaperPartSeqEnum.SUBJECT,
	 * subjectQuestions,position.getLevel());// 本科科目主观题 sumSubjectMinutes +=
	 * (otherSubjectLeft > 0 ? old - otherSubjectLeft : old); } else if
	 * (hasOtherCode && paperSubjectLeft > 0 && otherSubjectLeft <= 0) {//
	 * 本科目主观题用于生成其他科目主观题 logger.debug(
	 * "othercode create subject  from papersubject , the paperSubjectLeft is {}  for paperId {}"
	 * , paperSubjectLeft, paper.getPaperId()); int old = paperSubjectLeft;
	 * paperSubjectLeft = paperSubjectLeft + otherSubjectLeft; paperSubjectLeft
	 * = qbQuestionService.findQuestionsByQbSubjectDegreeSkills(
	 * qbSubjectToDegreeSkillses, paperSubjectLeft, PaperPartSeqEnum.SUBJECT,
	 * subjectQuestions, position.getLevel());// 其他科目客观题 sumSubjectMinutes +=
	 * (paperSubjectLeft > 0 ? old - paperSubjectLeft : old); }
	 * 
	 * int objectTimeLeft = (paperObjectLeft > 0 ? paperObjectLeft : 0) +
	 * (otherObjectLeft > 0 ? otherObjectLeft : 0); int subjectTimeLeft =
	 * (paperSubjectLeft > 0 ? paperSubjectLeft : 0) + (otherSubjectLeft > 0 ?
	 * otherSubjectLeft : 0);
	 * logger.debug("sumSubjectMinutes is {}, sumObjectMinutes is {} ",
	 * sumSubjectMinutes, sumObjectMinutes);
	 * logger.debug("subjectTimeLeft is {}, objectTimeLeft is {} ",
	 * subjectTimeLeft, objectTimeLeft);
	 * 
	 * if(objectQuestions.size() > 0){
	 * qbQuestionService.savePaperQuestions(objectQuestions, paper,
	 * PaperPartSeqEnum.OBJECT, sumObjectMinutes); // 保存客观题
	 * processTestQuestion(paper, paperSubCode,PaperPartSeqEnum.OBJECT,
	 * PaperPartSeqEnum.TEST_OBJECT); } if(subjectQuestions.size() > 0 ){
	 * qbQuestionService.savePaperQuestions(subjectQuestions, paper,
	 * PaperPartSeqEnum.SUBJECT, sumSubjectMinutes);// 保存主观
	 * processTestQuestion(paper, paperSubCode,PaperPartSeqEnum.SUBJECT,
	 * PaperPartSeqEnum.TEST_SUBJECT); }
	 * 
	 * qbQuestionService.savePaperQuestions(extras.getQuestions(), paper,
	 * PaperPartSeqEnum.EXTRA, extras.getSumSuggestTime());
	 * logger.debug("objectQuestions size is {}, info is {}, sumObjectTime is "
	 * + sumObjectMinutes, objectQuestions.size(),
	 * PaperCreateUtils.getPaperQuestionIdsFromQuesitonToSkills
	 * (objectQuestions));
	 * logger.debug("subjectQuestions size is {}, info is {}, sumSubjectTime is "
	 * + sumSubjectMinutes, subjectQuestions.size(),
	 * PaperCreateUtils.getPaperQuestionIdsFromQuesitonToSkills
	 * (subjectQuestions));
	 * logger.debug("extraQuestions size is {}, info is {}, sumExtraMinutes is "
	 * + extras.getSumSuggestTime(), extras.getQuestions().size(),
	 * PaperCreateUtils
	 * .getPaperQuestionIdsFromQuesitonToSkills(extras.getQuestions()));
	 * 
	 * QuestionProcessResult interviews = questionProcessService
	 * .getInterviewQuestions(positionSet); if (interviews != null &&
	 * interviews.getQuestions().size() > 0) {
	 * qbQuestionService.savePaperQuestions(interviews.getQuestions(), paper,
	 * PaperPartSeqEnum.INTEVEIW, interviews.getSumSuggestTime());// 保存主观
	 * processTestQuestion(paper, paperSubCode, PaperPartSeqEnum.INTEVEIW,
	 * PaperPartSeqEnum.TEST_INTERVIEW); }
	 * 
	 * qbNoramlDaoImpl.saveOrUpdate(position);// 更新职位信息 return response; }
	 * 
	 * public void analysisPaper(PositionPaperAnalysisResult result,String
	 * paperSubCode, List<DegreeToSkillLabels> degreeToSkillses, PositionSet
	 * positionSet) throws PFServiceException{ ConfigSysParameters sys = null;
	 * sys =
	 * configSysParamDao.getConfigSysParameters(Constants.PAPER_TOTAL_MINUTES);
	 * int PAPER_TOTAL_MINUTES = Integer.valueOf(sys.getParamValue()); Position
	 * position = positionSet.getPosition();
	 * result.setCode(FuncBaseResponse.SUCCESS);
	 * 
	 * List<PositionSeries> ps = qbNoramlDaoImpl.getNoramlListByHql(
	 * "from PositionSeries where positionLanguage = '" +
	 * position.getProgramLanguage() + "'", PositionSeries.class); if (ps ==
	 * null || ps.size() == 0) {
	 * logger.error("not found PositionSeries for language {} ",
	 * position.getProgramLanguage()); throw new
	 * PFServiceException("not found PositionSeries for language  " +
	 * position.getProgramLanguage()); } int seriesId = position.getSeriesId();
	 * 
	 * PositionLevel pl = getPositionLevelByCache(seriesId,
	 * position.getLevel());
	 * 
	 * // position.setPaperId(paper.getPaperId());
	 * position.setSeriesId(pl.getId().getSeriesId());
	 * position.setPublishDate(new Timestamp(System.currentTimeMillis()));
	 * 
	 * Map<String, QbSubjectToDegreeSkills> qbSubjectToDegreeSkillses =
	 * PaperCreateUtils.parseSubjectToDegreeSkills(paperSubCode,
	 * degreeToSkillses, pl); if (qbSubjectToDegreeSkillses.get(paperSubCode) ==
	 * null) { result.setCode(FuncBaseResponse.FAILED);
	 * result.setMessage("not found any skill for the subject"); return; }
	 * logger.info("qbSubjectToDegreeSkillses info is {} ",
	 * qbSubjectToDegreeSkillses.toString()); // 根据科目分出时间比例(本科目以及非本科目) QbSubject
	 * qbSubject = qbSubjectDaoImpl.getQbSubjectBySubjectCode(paperSubCode);
	 * Map<String, QbSubjectToDegreeSkills> paperSub = new HashMap<String,
	 * QbSubjectToDegreeSkills>(); paperSub.put(paperSubCode,
	 * qbSubjectToDegreeSkillses.get(paperSubCode));
	 * qbSubjectToDegreeSkillses.remove(paperSubCode);
	 * 
	 * // 客观题 List<PaperQuestionToSkills> objectQuestions = new
	 * ArrayList<PaperQuestionToSkills>(); // 主观题 List<PaperQuestionToSkills>
	 * subjectQuestions = new ArrayList<PaperQuestionToSkills>(); int totalTime
	 * = PAPER_TOTAL_MINUTES; int sumObjectMinutes = 0; int sumSubjectMinutes =
	 * 0; // while(true){
	 * 
	 * int oldTime = totalTime; int objectMinutes = (int) (totalTime *
	 * (pl.getSubQuestionRatio() * 0.01)); int paperCodeObjectMinutes =
	 * objectMinutes;
	 * 
	 * int subjectMinutes = totalTime - objectMinutes; int
	 * paperCodeSubjectMinutes = subjectMinutes;
	 * 
	 * int otherTimeLeft = 0;
	 * 
	 * int otherObjectLeft = 0; int otherSubjectLeft = 0; boolean hasOtherCode =
	 * false; if (qbSubjectToDegreeSkillses.size() > 0) { paperCodeObjectMinutes
	 * = (int) (objectMinutes * (qbSubject.getObjectRatio() * 0.1));//
	 * 本科目客观题所占时间 int otherObjectMinutes = objectMinutes -
	 * paperCodeObjectMinutes; // 其他科目客观题所占时间
	 * 
	 * paperCodeSubjectMinutes = (int) (subjectMinutes *
	 * (qbSubject.getSubjectRatio() * 0.1));// 本科目主观题所占时间 int
	 * otherSubjectMinutes = subjectMinutes - paperCodeSubjectMinutes; //
	 * 其他科目主观题所占时间
	 * 
	 * hasOtherCode = true; otherObjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills
	 * (qbSubjectToDegreeSkillses, otherObjectMinutes, PaperPartSeqEnum.OBJECT,
	 * objectQuestions, position.getLevel());// 其他科目客观题 sumObjectMinutes +=
	 * otherObjectLeft > 0 ? otherObjectMinutes - otherObjectLeft :
	 * otherObjectMinutes; otherSubjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills
	 * (qbSubjectToDegreeSkillses, otherSubjectMinutes,
	 * PaperPartSeqEnum.SUBJECT, subjectQuestions, position.getLevel());//
	 * 其他科目主观题 sumSubjectMinutes += otherSubjectLeft > 0 ? otherSubjectMinutes -
	 * otherSubjectLeft : otherSubjectMinutes; otherTimeLeft = (otherObjectLeft
	 * > 0 ? otherObjectLeft : 0) + (otherSubjectLeft > 0 ? otherSubjectLeft :
	 * 0); logger.debug("otherObject size is {}, otherSubjectSize is {}  ",
	 * objectQuestions.size(), subjectQuestions.size()); } int paperObjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills(paperSub,
	 * paperCodeObjectMinutes, PaperPartSeqEnum.OBJECT, objectQuestions,
	 * position.getLevel());// 本科目客观题
	 * 
	 * int paperSubjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills(paperSub,
	 * paperCodeSubjectMinutes, PaperPartSeqEnum.SUBJECT, subjectQuestions,
	 * position.getLevel());// 本科目主观题
	 * 
	 * sumObjectMinutes += paperObjectLeft > 0 ? paperCodeObjectMinutes -
	 * paperObjectLeft : paperCodeObjectMinutes; sumSubjectMinutes +=
	 * paperSubjectLeft > 0 ? paperCodeSubjectMinutes - paperSubjectLeft :
	 * paperCodeSubjectMinutes;
	 * 
	 * if (otherObjectLeft > 0 && paperObjectLeft <= 0) {// 其他科目客观题时间用来出本科目客观题
	 * logger.debug(
	 * "papercode create object from other object , the otherObjectLeft is {} ",
	 * otherObjectLeft); int old = otherObjectLeft; otherObjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills(paperSub,
	 * otherObjectLeft, PaperPartSeqEnum.OBJECT, objectQuestions,
	 * position.getLevel());// 本科目主观题 sumObjectMinutes += (otherObjectLeft > 0 ?
	 * old - otherObjectLeft : old); } else if (hasOtherCode && paperObjectLeft
	 * > 0 && otherObjectLeft <= 0) {// 本科目客观题时间用来出其他科目客观题 logger.debug(
	 * "othercode create object  from paperobject , the paperObjectLeft is {}  "
	 * , paperObjectLeft); int old = paperObjectLeft; paperObjectLeft =
	 * qbQuestionService
	 * .findQuestionsByQbSubjectDegreeSkills(qbSubjectToDegreeSkillses,
	 * paperObjectLeft, PaperPartSeqEnum.OBJECT, objectQuestions,
	 * position.getLevel());// 本科目客观题 sumObjectMinutes += (paperObjectLeft > 0 ?
	 * old - paperObjectLeft : old); }
	 * 
	 * if (otherSubjectLeft > 0 && paperSubjectLeft <= 0) {// 其他科目主观题用于生成本科目主观题
	 * logger.debug(
	 * "papercode create subject from other subject , the otherSubjectLeft is {} "
	 * , otherSubjectLeft); int old = otherSubjectLeft; otherSubjectLeft =
	 * otherSubjectLeft + paperSubjectLeft;// 减掉欠的时间 otherSubjectLeft =
	 * qbQuestionService.findQuestionsByQbSubjectDegreeSkills(paperSub,
	 * otherSubjectLeft, PaperPartSeqEnum.SUBJECT, subjectQuestions,
	 * position.getLevel());// 本科科目主观题 sumSubjectMinutes += (otherSubjectLeft >
	 * 0 ? old - otherSubjectLeft : old); } else if (hasOtherCode &&
	 * paperSubjectLeft > 0 && otherSubjectLeft <= 0) {// 本科目主观题用于生成其他科目主观题
	 * logger.debug(
	 * "othercode create subject  from papersubject , the paperSubjectLeft is {} "
	 * , paperSubjectLeft); int old = paperSubjectLeft; paperSubjectLeft =
	 * paperSubjectLeft + otherSubjectLeft; paperSubjectLeft =
	 * qbQuestionService.
	 * findQuestionsByQbSubjectDegreeSkills(qbSubjectToDegreeSkillses,
	 * paperSubjectLeft, PaperPartSeqEnum.SUBJECT, subjectQuestions,
	 * position.getLevel());// 其他科目客观题 sumSubjectMinutes += (paperSubjectLeft >
	 * 0 ? old - paperSubjectLeft : old); }
	 * 
	 * int objectTimeLeft = (paperObjectLeft > 0 ? paperObjectLeft : 0) +
	 * (otherObjectLeft > 0 ? otherObjectLeft : 0); int subjectTimeLeft =
	 * (paperSubjectLeft > 0 ? paperSubjectLeft : 0) + (otherSubjectLeft > 0 ?
	 * otherSubjectLeft : 0);
	 * logger.debug("sumSubjectMinutes is {}, sumObjectMinutes is {} ",
	 * sumSubjectMinutes, sumObjectMinutes);
	 * logger.debug("subjectTimeLeft is {}, objectTimeLeft is {} ",
	 * subjectTimeLeft, objectTimeLeft); List<PaperQuestionToSkills> questions =
	 * new ArrayList<PaperQuestionToSkills>();
	 * questions.addAll(objectQuestions);
	 * questions.addAll(subjectQuestions);//应要求试卷模型 去掉编程题
	 * result.setQuestions(questions); List<PaperPart> paperParts = new
	 * ArrayList<PaperPart>(); if(objectQuestions.size() > 0){
	 * paperParts.add(getPaperPart
	 * (objectQuestions,PaperPartSeqEnum.OBJECT,sumObjectMinutes)); }
	 * if(subjectQuestions.size() > 0){
	 * paperParts.add(getPaperPart(subjectQuestions
	 * ,PaperPartSeqEnum.SUBJECT,sumSubjectMinutes)); }
	 * result.setPaperParts(paperParts);
	 * logger.debug("objectQuestions size is {}, info is {}, sumObjectTime is "
	 * + sumObjectMinutes, objectQuestions.size(),
	 * PaperCreateUtils.getPaperQuestionIdsFromQuesitonToSkills
	 * (objectQuestions));
	 * logger.debug("subjectQuestions size is {}, info is {}, sumSubjectTime is "
	 * + sumSubjectMinutes, subjectQuestions.size(),
	 * PaperCreateUtils.getPaperQuestionIdsFromQuesitonToSkills
	 * (subjectQuestions)); }
	 */

	/*
	 * private PaperPart getPaperPart(List<PaperQuestionToSkills>
	 * questions,PaperPartSeqEnum seq, int time){ PaperPart paperPart = new
	 * PaperPart(); PaperPartId id = new PaperPartId(null, seq.getValue());
	 * paperPart.setId(id); paperPart.setQuestionNum(questions.size());
	 * paperPart.setPartDesc(seq.getDesc()); paperPart.setSuggestTime(time);
	 * return paperPart; //
	 * paperPart.setPartPoints(qbQuestionDaoImpl.getSumPointsForQuestion(ids));
	 * } private Paper savePaper(Position position) { Paper paper = new Paper();
	 * paper.setCreateDate(new Timestamp(System.currentTimeMillis()));
	 * paper.setCreateBy(position.getEmployerId());
	 * qbNoramlDaoImpl.saveOrUpdate(paper); return paper; }
	 */

	@Override
	public PositionLevel getPositionLevelByCache(int seriesId, int levelId) {
		return positionLevelDaoImpl.getPositionLevel(seriesId, levelId);
	}

	/*
	 * @Override public List<PaperQuestionToSkills>
	 * getExtrQuestionsByAnalyis(QbSubjectToDegreeSkills
	 * paperCodeSbToDegreeSkills, int max) {
	 * 
	 * List<PaperQuestionToSkills> getedQuestionIds = new
	 * ArrayList<PaperQuestionToSkills>(); for (Integer degree :
	 * paperCodeSbToDegreeSkills.getDegreeSortedAsc()) {
	 * DegreeSkillSearchCondition condition = new DegreeSkillSearchCondition();
	 * condition.setDegree(degree);
	 * condition.setPaperType(PaperPartSeqEnum.EXTRA);
	 * condition.setHasFoundQuestions(getedQuestionIds);
	 * List<PositionSkillScopeView> skills =
	 * paperCodeSbToDegreeSkills.getNumberToSkills().get(degree); if
	 * (!CollectionUtils.isEmpty(skills)) { for (PositionSkillScopeView skill :
	 * skills) {// 先找单标签的 List<String> tempSkillIds = new ArrayList<String>();
	 * tempSkillIds.add(skill.getId().getSkillId());
	 * condition.setSkillIds(tempSkillIds); List<PaperQuestionToSkills> queIds =
	 * qbQuestionService.findQbQuestionBySearchCondition(condition); //
	 * 根据单个单个技能寻找 queIds.removeAll(getedQuestionIds); if
	 * (!CollectionUtils.isEmpty(queIds)) {// 还有没有选中的题目，则直接随机选取一个题目
	 * logger.debug("found " + queIds.size() +
	 * " questions for {},degreeSize is {}", skill.getId().getSkillId(),
	 * degree); getedQuestionIds.addAll(queIds); if (getedQuestionIds.size() >=
	 * max) { break; } } } } }
	 * 
	 * return getedQuestionIds; }
	 */

	/**
	 * 
	 */
	/*
	 * private void processTestQuestion(Paper paper, String
	 * paperSubCode,PaperPartSeqEnum quesType,PaperPartSeqEnum partSeq) {
	 * List<PaperQuestionToSkills> questions = new
	 * ArrayList<PaperQuestionToSkills>(); if(quesType ==
	 * PaperPartSeqEnum.INTEVEIW){ String testSql =
	 * "select * from qb_question where is_sample = 1 and question_type  in" +
	 * PaperCreateUtils.getQuestionTypesInStr(quesType) + " limit 1";
	 * logger.debug("the interviewtestSql is {}", testSql); List<QbQuestion>
	 * testQuestions = qbNoramlDaoImpl.getNoramlListBySql(testSql,
	 * QbQuestion.class); logger.debug("the size is {} for sql {} ",
	 * testQuestions.size(),testSql); for(QbQuestion q : testQuestions){
	 * PaperQuestionToSkills pQ = new PaperQuestionToSkills();
	 * pQ.setQuestionId(q.getQuestionId()); questions.add(pQ); } }else{ String
	 * testSql =
	 * "select * from qb_question_view where is_sample = 1 and subject_code = '"
	 * + paperSubCode + "' and question_type  in" +
	 * PaperCreateUtils.getQuestionTypesInStr(quesType) + " limit 1";
	 * logger.debug("the testSql is {}", testSql); List<QbQuestionView>
	 * testQuestion = qbNoramlDaoImpl.getNoramlListBySql(testSql,
	 * QbQuestionView.class); questions =
	 * PaperCreateUtils.changeQbQuestionsToPaperQuestion(testQuestion); }
	 * qbQuestionService.savePaperQuestions(questions, paper, partSeq,
	 * Constants.TEST_PART_TIME); // 保存客观题 }
	 */

}
