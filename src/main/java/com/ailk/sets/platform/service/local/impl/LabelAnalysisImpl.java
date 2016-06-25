package com.ailk.sets.platform.service.local.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.impl.LabelAnalysisDaoImpl;
import com.ailk.sets.platform.dao.impl.PaperSkillDaoImpl;
import com.ailk.sets.platform.dao.impl.PaperSkillUnknownDaoImpl;
import com.ailk.sets.platform.dao.impl.PositionSkillScopeViewDaoImpl;
import com.ailk.sets.platform.dao.impl.QbSkillDegreeDaoImpl;
import com.ailk.sets.platform.dao.impl.QbSkillSubjectViewDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.domain.DegreeToSkillLabels;
import com.ailk.sets.platform.domain.PaperSkill;
import com.ailk.sets.platform.domain.PaperSkillId;
import com.ailk.sets.platform.domain.PositionAnalysisResult;
import com.ailk.sets.platform.domain.skilllabel.PaperSkillUnknown;
import com.ailk.sets.platform.domain.skilllabel.PositionSkillScopeView;
import com.ailk.sets.platform.domain.skilllabel.QbSkillSentenceParse;
import com.ailk.sets.platform.domain.skilllabel.QbSkillSentenceSep;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillSubjectView;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.ILabelAnalysis;
import com.ailk.sets.platform.util.LabelSentenceAnalysisUtils;
import com.ailk.sets.platform.util.PaperCreateUtils;

/**
 * 
 * @author panyl
 * 
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class LabelAnalysisImpl implements ILabelAnalysis {
	@Autowired
	LabelAnalysisDaoImpl labelAnalysisDao;
	@Autowired
	private PaperSkillUnknownDaoImpl positionSkillUnknownDaoImpl;
	@Autowired
	private PaperSkillDaoImpl positionSkillDaoImpl;
	@Autowired
	private QbSkillSubjectViewDaoImpl qbSkillSubjectViewDaoImpl;
	@Autowired
	private QbSkillDegreeDaoImpl qbSkillDegreeDaoImpl;
	@Autowired
	private IPositionSeriesDao seriesDao;
	@Autowired
	private PositionSkillScopeViewDaoImpl positionSeriesSkillViewDaoImpl;
	private Logger logger = LoggerFactory.getLogger(LabelAnalysisImpl.class);


	public PositionAnalysisResult  analysisSentences(String seriesId,String sentence) {
		PositionAnalysisResult result = new PositionAnalysisResult();
		String[] lines = sentence.split("\r\n", -1);
		if(lines.length <= 1){
			lines = sentence.split("\n", -1);
			logger.debug("split with n , the size is {} for sentence {} ", lines.length,sentence);
		}
		List<QbSkillSentenceParse> sentenceParses = labelAnalysisDao.getQbSkillSentenceParses();
		logger.debug("sentenceParses size is {} ", sentenceParses.size());
		List<QbSkillSentenceSep> sentenceSeps = labelAnalysisDao.getQbSkillSentenceSeps();
		logger.debug("sentenceSeps size is {} ", sentenceSeps.size());
		List<QbSkillDegree> skillDegrees = getQbSkillDegrees();
		List<PositionSkillScopeView> skills =positionSeriesSkillViewDaoImpl.getPositionSeriesSkillView(seriesId);
		List<PaperSkillUnknown> unknowns = new ArrayList<PaperSkillUnknown>();
//		List<DegreeToSkillLabels> degreeToSkillLabelses  = new ArrayList<DegreeToSkillLabels>();
		Map<QbSkillDegree, DegreeToSkillLabels> degreeSets= new HashMap<QbSkillDegree, DegreeToSkillLabels>();
		for (String line : lines) {
			long time1 = System.currentTimeMillis();
			List<String> sentences = LabelSentenceAnalysisUtils.analysisSentence(line, sentenceParses, sentenceSeps,
					skillDegrees, unknowns);
			long time2 = System.currentTimeMillis();
			logger.debug("sentences size is {}, waste {} millisecond ", sentences.size(), (time2 - time1));
		    for (String sent : sentences) {
				logger.debug(" the sentence is {} .", sent);
//				list.addAll(getDegreeToSkillLabelsBySent(sent, skillDegrees, skills, unknowns,degreeSets));
				getDegreeToSkillLabelsBySent(sent, skillDegrees, skills, unknowns,sentenceSeps,degreeSets);
			}
		}
		
		List<DegreeToSkillLabels> l = new ArrayList<DegreeToSkillLabels>();
		org.apache.commons.collections.CollectionUtils.addAll(l,  degreeSets.values().iterator());
		result.setDegreeToSkillLableses(l);
		result.setUnknowns(unknowns);
		return result;
	}


	private void getDegreeToSkillLabelsBySent(String sent, List<QbSkillDegree> skillDegrees,
			List<PositionSkillScopeView> skills, List<PaperSkillUnknown> unknowns,List<QbSkillSentenceSep> sentenceSeps,Map<QbSkillDegree, DegreeToSkillLabels> degreeSets) {
//		List<DegreeToSkillLabels> list = new ArrayList<DegreeToSkillLabels>();
		boolean found = false;
		boolean foundDegree = false;
		for (QbSkillDegree skillDegree : skillDegrees) {
			if (found) {
				break;
			}
			int index = -1;
			index = StringUtils.indexOfIgnoreCase(sent, skillDegree.getDegreeName());
			if (index != -1) {
				DegreeToSkillLabels degreeToSkills = degreeSets.get(skillDegree);
				if(degreeToSkills == null){
					degreeToSkills = new DegreeToSkillLabels();
					degreeToSkills.setSkillLabels(new ArrayList<PositionSkillScopeView>());
				}
				foundDegree = true;
				degreeToSkills.setLabelDegree(skillDegree);
				degreeToSkills.setSelectedDegreeName(skillDegree.getDegreeName());
				degreeToSkills.setSkillLabelStr(sent);
				List<PositionSkillScopeView> matchedSkills = LabelSentenceAnalysisUtils.getSkillLabel(degreeToSkills, skills, unknowns,sentenceSeps);
				if (matchedSkills.size() > 0) {
					for(PositionSkillScopeView skill : matchedSkills){
						if(PaperCreateUtils.isNewSkill(degreeSets, skill)){
							degreeSets.put(skillDegree, degreeToSkills);//有技能才加入到map中
							degreeToSkills.getSkillLabels().add(skill);
						}
					}
//					degreeToSkills.getSkillLabels().addAll(matchedSkills);
					found = true;
					break;
				}
			}
			if (StringUtils.isNotEmpty(skillDegree.getDegreeAlias())) {
				String[] names = skillDegree.getDegreeAlias().split(",", -1);
				for (String name : names) {
					index = StringUtils.indexOfIgnoreCase(sent, name);
					if (index != -1) {
						DegreeToSkillLabels degreeToSkills = degreeSets.get(skillDegree);
						if(degreeToSkills == null){
							degreeToSkills = new DegreeToSkillLabels();
							degreeToSkills.setSkillLabels(new ArrayList<PositionSkillScopeView>());
						}
						foundDegree = true;
						degreeToSkills.setLabelDegree(skillDegree);
						degreeToSkills.setSelectedDegreeName(skillDegree.getDegreeName());
						degreeToSkills.setSkillLabelStr(sent);

						List<PositionSkillScopeView> matchedSkills = LabelSentenceAnalysisUtils.getSkillLabel(degreeToSkills, skills, unknowns,sentenceSeps);
						if (matchedSkills.size() > 0) {
							for(PositionSkillScopeView skill : matchedSkills){
								if(PaperCreateUtils.isNewSkill(degreeSets, skill)){
									degreeSets.put(skillDegree, degreeToSkills);
									degreeToSkills.getSkillLabels().add(skill);
								}
							}
//							degreeToSkills.getSkillLabels().addAll(matchedSkills);
							found = true;
							break;
						}
					}
				}
			}
		}
		if (!foundDegree) {
			PaperSkillUnknown unknown = new PaperSkillUnknown();
			unknown.setParseLeft(sent);
			unknown.setParseReason(PaperSkillUnknown.PARSE_REASON_NODEGREE);
//			unknown.setPositionId(positionId);
			unknowns.add(unknown);
		}
//		return list;
	}

	public List<QbSkillSubjectView> getQbSkills(String programLanguage) {
		List<QbSkillSubjectView> skills =  labelAnalysisDao.getQbSkills(programLanguage);
		List<String> neededSortsSkills = Arrays.asList(new String[]{"JSP","JS","Java"});
		List<QbSkillSubjectView> sortedSkills = new ArrayList<QbSkillSubjectView>();
		for (int index = 0; index < neededSortsSkills.size(); index++) {
			for (QbSkillSubjectView skill : skills) {
				if (skill.getSkillName().equalsIgnoreCase(
						neededSortsSkills.get(index))) {
					sortedSkills.add(skill);
					break;
				}
				if (StringUtils.isNotEmpty(skill.getSkillAlias())) {
					List<String> aliases = Arrays.asList(skill.getSkillAlias()
							.split(",", -1));
					if (aliases.contains(neededSortsSkills.get(index))) {
						sortedSkills.add(skill);
						break;
					}
				}
			}
		}
		skills.removeAll(sortedSkills);
		skills.addAll(sortedSkills);
		return skills;
	}

	public List<QbSkillDegree> getQbSkillDegrees() {
		return labelAnalysisDao.getQbSkillDegrees();
	}
	
	public PositionAnalysisResult getPositionAnalysisResult(Position p){
	  PositionAnalysisResult result = new PositionAnalysisResult();
	  List<PaperSkillUnknown> unknowns =	positionSkillUnknownDaoImpl.getPaperSkillUnknowns(p.getPositionId());
	  List<PaperSkill> positionSkills = positionSkillDaoImpl.getPaperSkills(p.getPaperId());
	  Map<Integer,DegreeToSkillLabels>  degreeToSkills = new HashMap<Integer,DegreeToSkillLabels>();
	  for(PaperSkill positionSkill : positionSkills){
		  PaperSkillId id = positionSkill.getId();
		  int degreeId = positionSkill.getDegreeId();
		  DegreeToSkillLabels degreeToSkillLabel= degreeToSkills.get(degreeId);
		  if(degreeToSkillLabel == null){
			  degreeToSkillLabel = new DegreeToSkillLabels();
			  degreeToSkills.put(degreeId, degreeToSkillLabel);
			  degreeToSkillLabel.setLabelDegree(qbSkillDegreeDaoImpl.getQbSkillDegree(degreeId));
			  degreeToSkillLabel.setSkillLabels(new ArrayList<PositionSkillScopeView>());
		  }
		  PositionSkillScopeView qbSkill = positionSeriesSkillViewDaoImpl.getPositionSeriesSkillView(p.getSeriesId()+"", positionSkill.getId().getSkillId());
		  degreeToSkillLabel.getSkillLabels().add(qbSkill);
	  }
	  List<DegreeToSkillLabels> degrees = new ArrayList<DegreeToSkillLabels>();
	  org.apache.commons.collections.CollectionUtils.addAll(degrees, degreeToSkills.values().iterator());
	  result.setDegreeToSkillLableses(degrees);
	  result.setUnknowns(unknowns);
	  return result;
	}

}
