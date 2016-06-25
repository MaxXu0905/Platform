package com.ailk.sets.platform.service.local.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.GetQInfoResponse;
import com.ailk.sets.grade.intf.GetQInfoResponse.Matrix;
import com.ailk.sets.grade.intf.GetQInfoResponse.MatrixItem;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.question.Extras;
import com.ailk.sets.platform.service.local.IPositionService;

@Transactional(rollbackFor = Exception.class)
@Service
public class PositionServiceImpl implements IPositionService {
	
	private Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

	
	@Autowired
	private IQbQuestionDao qbQuestionDao;
	
	@Autowired
	private IGradeService gradeService;

	/*
	 * @Override public PosResponse createPosition(PositionSet positionSet)
	 * throws PFServiceException { PosResponse pr = new PosResponse(); Position
	 * position = positionSet.getPosition(); try { // String subjectCode =
	 * position.getProgramLanguage(); String positionLanuage =
	 * seriesDao.getEntity(position.getSeriesId()).getPositionLanguage();
	 * QbSubject qbSubject = qbSubjectDaoImpl.getQbSubject(positionLanuage); if
	 * (qbSubject == null) {
	 * logger.error("not found qbSubjcet for language {} ", positionLanuage);
	 * throw new PFServiceException("not found qbSubjcet for language " +
	 * positionLanuage); } String subjectCode = qbSubject.getSubjectCode();
	 * String positionDesc = position.getRequiresDesc(); // QbSubject qbSubject
	 * = qbNoramlDaoImpl.getNormalObject(subjectCode, // QbSubject.class);
	 * position.setPositionState(PositionState.VALID.getValue());
	 * position.setTestType(1); //
	 * position.setProgramLanguage(qbSubject.getProgramLanguage());
	 * PositionAnalysisResult result =
	 * labelAnalysis.analysisSentences(positionLanuage, positionDesc);
	 * List<DegreeToSkillLabels> degreeToSkillLabels = null; if
	 * (CollectionUtils.isNotEmpty(position.getDegreeToSkillLabels())) {
	 * logger.debug("use skills create position from paper ");
	 * degreeToSkillLabels = position.getDegreeToSkillLabels(); } else {
	 * degreeToSkillLabels = result.getDegreeToSkillLableses(); logger.error(
	 * "use desc create position from desc , need skills to create , please check, desc is {} "
	 * , position.getRequiresDesc()); }
	 * 
	 * if (degreeToSkillLabels.size() == 0) {
	 * logger.error("position desc analysis error for {} ", positionDesc);
	 * pr.setCode(FuncBaseResponse.FAILED);
	 * pr.setMessage("position desc analysis error for " + positionDesc); return
	 * pr; } qbNoramlDaoImpl.saveOrUpdate(position); if
	 * (position.getPositionId() != null) { deletePositinInfo(position,
	 * false);// 更新 不需要删除职位信息 } List<PaperSkillUnknown> unknowns =
	 * result.getUnknowns(); long time3 = System.currentTimeMillis();
	 * List<String> allSkills = new ArrayList<String>(); for
	 * (DegreeToSkillLabels degreeToLabel : degreeToSkillLabels) { for
	 * (PositionSkillScopeView skillLabel : degreeToLabel.getSkillLabels()) {
	 * if (allSkills.contains(skillLabel.getId().getSkillId())) {
	 * logger.warn("the skill is contained {} ", skillLabel.getSkillName());
	 * continue; } allSkills.add(skillLabel.getId().getSkillId());
	 * logger.debug(skillLabel.getSkillName() + "#####" +
	 * degreeToLabel.getSelectedDegreeName()); PaperSkill pk = new PaperSkill();
	 * PaperSkillId pkId = new PaperSkillId(position.getPositionId(),
	 * skillLabel.getId().getSkillId()); pk.setId(pkId);
	 * pk.setDegreeId(degreeToLabel.getLabelDegree().getDegreeId());
	 * pk.setSkillSeq(1); qbNoramlDaoImpl.saveOrUpdate(pk); } } for
	 * (PaperSkillUnknown unknown : unknowns) {
	 * unknown.setPositionId(position.getPositionId());
	 * qbNoramlDaoImpl.saveOrUpdate(unknown); } long time4 =
	 * System.currentTimeMillis();
	 * logger.debug("save positionSkill waste {} millisecond  ", (time4 -
	 * time3));
	 * 
	 * logger.debug("degreeToSkillLabels size is {} ",
	 * degreeToSkillLabels.size());
	 * 
	 * pr = paperService.createPaper(subjectCode, degreeToSkillLabels,
	 * positionSet); if (!pr.getCode().equals(FuncBaseResponse.SUCCESS)) {
	 * return pr; } if (position.getEmployerId() != null) { PositionLog log =
	 * new PositionLog(); log.setEmployerId(position.getEmployerId());
	 * log.setLogTime(new Timestamp(System.currentTimeMillis()));
	 * log.setPositionState(0); log.setStateId(position.getPositionId());
	 * log.setPositionId(position.getPositionId());
	 * qbNoramlDaoImpl.saveOrUpdate(log); }
	 * 
	 * pr.setCode(FuncBaseResponse.SUCCESS);
	 * pr.setPositionId(position.getPositionId()); return pr; } catch (Exception
	 * e) { logger.error("error to create position or paper", e); throw new
	 * PFServiceException(e); }
	 * 
	 * }
	 */

	/*
	 * public PositionPaperAnalysisResult analysisPosition(PositionSet
	 * positionSet) throws PFServiceException { PositionPaperAnalysisResult pr =
	 * new PositionPaperAnalysisResult(); Position position =
	 * positionSet.getPosition(); try { String positionLanguage =
	 * seriesDao.getEntity(position.getSeriesId()).getPositionLanguage();
	 * QbSubject qbSubject = qbSubjectDaoImpl.getQbSubject(positionLanguage); if
	 * (qbSubject == null) {
	 * logger.error("not found qbSubjcet for language {} ", positionLanguage);
	 * throw new PFServiceException("not found qbSubjcet for language " +
	 * positionLanguage); } String subjectCode = qbSubject.getSubjectCode();
	 * String positionDesc = position.getRequiresDesc();
	 * position.setPositionState(PositionState.VALID.getValue());
	 * position.setTestType(1); List<DegreeToSkillLabels> degreeToSkillLabels =
	 * null;//position.getDegreeToSkillLabels(); if (degreeToSkillLabels != null
	 * && degreeToSkillLabels.size() > 0) {
	 * logger.debug("use skills analysis position from paper "); } else {
	 * logger.debug("use desc create position from desc ");
	 * PositionAnalysisResult result =
	 * labelAnalysis.analysisSentences(positionLanguage, positionDesc);
	 * degreeToSkillLabels = result.getDegreeToSkillLableses(); }
	 * 
	 * if (degreeToSkillLabels.size() == 0) {
	 * logger.error("position desc analysis error for {} ", positionDesc);
	 * pr.setCode(FuncBaseResponse.FAILED);
	 * pr.setMessage("position desc analysis error for " + positionDesc); return
	 * pr; } // 按程度降序排列 Collections.sort(degreeToSkillLabels, new
	 * Comparator<DegreeToSkillLabels>() {
	 * 
	 * @Override public int compare(DegreeToSkillLabels o1, DegreeToSkillLabels
	 * o2) { return
	 * o2.getLabelDegree().getDegreeWeight().compareTo(o1.getLabelDegree
	 * ().getDegreeWeight()); } }); //
	 * position.setDegreeToSkillLabels(degreeToSkillLabels);
	 * position.setRequiresDesc
	 * (PaperCreateUtils.getPositionDescFromDegreeSkills(degreeToSkillLabels));
	 * long time3 = System.currentTimeMillis(); List<String> allSkills = new
	 * ArrayList<String>(); for (DegreeToSkillLabels degreeToLabel :
	 * degreeToSkillLabels) { for (PositionSkillScopeView skillLabel :
	 * degreeToLabel.getSkillLabels()) { if
	 * (allSkills.contains(skillLabel.getId().getSkillId())) {
	 * logger.warn("the skill is contained {} ", skillLabel.getSkillName());
	 * continue; } allSkills.add(skillLabel.getId().getSkillId());
	 * logger.debug(skillLabel.getSkillName() + "#####" +
	 * degreeToLabel.getSelectedDegreeName()); } }
	 * pr.setPositionSkills(allSkills); long time4 = System.currentTimeMillis();
	 * logger.debug("save positionSkill waste {} millisecond  ", (time4 -
	 * time3));
	 * 
	 * logger.debug("degreeToSkillLabels size is {} ",
	 * degreeToSkillLabels.size()); paperService.analysisPaper(pr, subjectCode,
	 * degreeToSkillLabels, positionSet); if
	 * (!pr.getCode().equals(FuncBaseResponse.SUCCESS)) { return pr; }
	 * pr.setCode(FuncBaseResponse.SUCCESS);
	 * pr.setPositionId(position.getPositionId()); return pr; } catch (Exception
	 * e) { logger.error("error to create position or paper", e); throw new
	 * PFServiceException(e); } }
	 */

	/*
	 * public List<Extras> getExtrQuestionsByAnalyis(Position position,
	 * List<String> skillIds) { String positionDesc =
	 * position.getRequiresDesc(); int levelId = position.getLevel(); // String
	 * subjectCode = position.getProgramLanguage(); String positionLanguage =
	 * seriesDao.getEntity(position.getSeriesId()).getPositionLanguage();
	 * QbSubject qbSubject = qbSubjectDaoImpl.getQbSubject(positionLanguage); if
	 * (qbSubject == null) {
	 * logger.error("not found qbSubjcet for language {} ", positionLanguage);
	 * throw null; }
	 * 
	 * List<PositionSeries> ps = qbNoramlDaoImpl.getNoramlListByHql(
	 * "from PositionSeries where positionLanguage = '" +
	 * position.getProgramLanguage() + "'", PositionSeries.class); if (ps ==
	 * null || ps.size() == 0) {
	 * logger.error("not found PositionSeries for language {} ",
	 * position.getProgramLanguage()); return null; }
	 * 
	 * String subjectCode = qbSubject.getSubjectCode(); int seriesId =
	 * position.getSeriesId(); PositionAnalysisResult result =
	 * labelAnalysis.analysisSentences(positionLanguage, positionDesc);
	 * List<DegreeToSkillLabels> degreeToSkillLabels =
	 * result.getDegreeToSkillLableses(); for (DegreeToSkillLabels degreeToLabel
	 * : degreeToSkillLabels) { for (PositionSkillScopeView skillLabel :
	 * degreeToLabel.getSkillLabels()) { logger.debug(skillLabel.getSkillName()
	 * + "#####" + degreeToLabel.getSelectedDegreeName()); } }
	 * 
	 * PositionLevel pl = paperService.getPositionLevelByCache(seriesId,
	 * levelId); Map<String, QbSubjectToDegreeSkills> qbSubjectToDegreeSkillses
	 * = PaperCreateUtils.parseSubjectToDegreeSkills(subjectCode,
	 * degreeToSkillLabels, pl); if (qbSubjectToDegreeSkillses == null ||
	 * qbSubjectToDegreeSkillses.size() == 0) { return null; }
	 * QbSubjectToDegreeSkills paperCodeSbToDegreeSkills =
	 * qbSubjectToDegreeSkillses.get(subjectCode);// 本考试科目对应技能 if
	 * (paperCodeSbToDegreeSkills == null) {
	 * logger.error("not found any skill for the subject {}", subjectCode);
	 * return null; } List<PaperQuestionToSkills> list =
	 * paperService.getExtrQuestionsByAnalyis(paperCodeSbToDegreeSkills, 1);
	 * List<Extras> extras = new ArrayList<Extras>(); for (PaperQuestionToSkills
	 * q : list) { Extras p = new Extras(); GetQInfoResponse response =
	 * gradeService.getQInfo(q.getQuestionId()); if (response == null) {
	 * logger.error("error to get extra for questionId {}", q.getQuestionId());
	 * return extras; } p.setQuestionId(q.getQuestionId());
	 * p.setSkillIds(q.getSkillIds()); p.setqDesc(response.getTitle()); Matrix m
	 * = response.getMatrix(); if (m != null && m.getItems() != null &&
	 * m.getItems().size() > 0) { MatrixItem item = m.getItems().get(0);
	 * p.setProgramLang(item.getMode()); p.setaDesc(item.getTemplate()); }
	 * QbQuestion question =
	 * qbQuestionDaoImpl.getQbQuestionById(q.getQuestionId()); if (question ==
	 * null) { logger.error("error to get question for questionId {}",
	 * q.getQuestionId()); continue; // return extras; } int time =
	 * qbQuestionService.getQuestionSuggestTime(question); p.setTime(time);
	 * p.setQuestionType(question.getQuestionType()); extras.add(p); } return
	 * extras;
	 * 
	 * }
	 */
	/**
	 * 获取一道附加题
	 * 
	 * @param position
	 * @param pqts
	 * @return
	 */
	public Extras getExtrQuestionsByExtraId(Long questionId) {
		try {
			Extras s = new Extras();
			QbQuestion question = qbQuestionDao.getEntity(questionId);
			GetQInfoResponse response = gradeService.getQInfo(questionId);
			if (response == null || response.getErrorCode() != BaseResponse.SUCCESS) {
				logger.error("error for call get question service for id {}",
						questionId);
				return null;
			}
			Matrix m = response.getMatrix();
			if (m != null && m.getItems() != null && m.getItems().size() > 0) {
				MatrixItem item = m.getItems().get(0);
				s.setProgramLang(item.getMode());
				s.setaDesc(item.getTemplate());
			}
			s.setQuestionId(questionId);
			s.setqDesc(response.getTitle());
			s.setQuestionType(question.getQuestionType());
			s.setTime(question.getSuggestTime());
			return s;
		} catch (Exception e) {
			return null;
		}
	}

}
