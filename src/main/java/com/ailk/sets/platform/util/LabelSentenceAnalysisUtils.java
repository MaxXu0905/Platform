package com.ailk.sets.platform.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.domain.DegreeToSkillLabels;
import com.ailk.sets.platform.domain.skilllabel.PaperSkillUnknown;
import com.ailk.sets.platform.domain.skilllabel.PositionSkillScopeView;
import com.ailk.sets.platform.domain.skilllabel.QbSkillSentenceParse;
import com.ailk.sets.platform.domain.skilllabel.QbSkillSentenceSep;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;

public class LabelSentenceAnalysisUtils {
	private static Logger logger = LoggerFactory.getLogger(DegreeSkillLabelUtils.class);
    /**
     * 
     * @param lineDesc  要解析的语句
     * @param sentenceParses  分句规则
     * @param sentenceSeps  分隔符
     * @param skillDegrees 程度词
     * @param positionId  职位id
     * @param unknowns   未解析结果集（返回）
     * @return
     */
	public static List<String> analysisSentence(String lineDesc, List<QbSkillSentenceParse> sentenceParses,
			List<QbSkillSentenceSep> sentenceSeps,List<QbSkillDegree> skillDegrees,List<PaperSkillUnknown> unknowns) {
		List<String> sents = new ArrayList<String>();
		int firstSenIndex;
		String firstStart;
		int sendSenIndex;
		String sendStart;
		Map<String,Object> result = getFirstSent(lineDesc, sentenceParses, sentenceSeps, skillDegrees);
		while(true){
			if(result.size() > 0){
				firstSenIndex = (Integer)result.get("INDEX");
				if(firstSenIndex == -1){
					firstSenIndex = 0;
				}
				firstStart = (String)result.get("SELECTEDDEGREENAME");
				logger.debug("firstSentIndex is {}, firstStart is {} for " + lineDesc,firstSenIndex,firstStart);
				String sub = lineDesc.substring(firstSenIndex + firstStart.length());
				Map<String,Object> result2 = getFirstSent(sub,sentenceParses, sentenceSeps, skillDegrees);
				if( result2.size() > 0 ){
					sendSenIndex = (Integer)result2.get("INDEX") ;
					sendStart = (String)result2.get("SELECTEDDEGREENAME");
					logger.debug("sendSenIndex is {}, sendStart is {} for  " + sub,sendSenIndex,sendStart);
					logger.debug("the sub string add to list is {} ", lineDesc.substring(firstSenIndex, firstSenIndex + firstStart.length() + sendSenIndex ));
					sents.add(lineDesc.substring(firstSenIndex, firstSenIndex + firstStart.length() + sendSenIndex));
					
					lineDesc = lineDesc.substring(firstSenIndex + firstStart.length());
					result = result2;
					
				}else{
					logger.debug(" sub string is {} ",lineDesc.substring(firstSenIndex));
					sents.add(lineDesc.substring(firstSenIndex));
					break;
				}
			}else{
				if(StringUtils.isNotBlank(lineDesc)){
					PaperSkillUnknown  unknown = new PaperSkillUnknown();
					unknown.setParseLeft(lineDesc);
					unknown.setParseReason(PaperSkillUnknown.PARSE_REASON_UNKNOWN);
//					unknown.setPositionId(positionId);
					unknowns.add(unknown);
				}
				break;
			}
		}
		return sents;
	}

	private static Map<String, Object> getFirstSent(String lineDesc, List<QbSkillSentenceParse> sentenceParses,
			List<QbSkillSentenceSep> sentenceSeps, List<QbSkillDegree> skillDegrees) {
		Map<String, Object> result = new HashMap<String, Object>();
		int minIndex = -1;
		for (QbSkillSentenceParse parse : sentenceParses) {
			int wordType = parse.getWordType();
			String beginWord = parse.getBeginWord();
			if (wordType == 0) {
				for (QbSkillSentenceSep sep : sentenceSeps) {
					if (StringUtils.isNotBlank(beginWord)) {
						if (StringUtils.indexOfIgnoreCase(lineDesc, beginWord) == 0) {// 第0个是开始语句，直接return
							result.put("INDEX", minIndex);
							result.put("SELECTEDDEGREENAME", beginWord);
							return result;
						}
						String key = sep.getSeparator() + beginWord;
						int index = StringUtils.indexOfIgnoreCase(lineDesc, key);
						if (index != -1) {
							if (minIndex == -1 || minIndex > index) {
								minIndex = index;
								result.put("INDEX", minIndex);
								result.put("SELECTEDDEGREENAME", key);
							}
						}
					}
				}
			} else {
				for (QbSkillSentenceSep sep : sentenceSeps) {
					if (StringUtils.isNotBlank(beginWord)) {
						for (QbSkillDegree degree : skillDegrees) {
							if (StringUtils.indexOfIgnoreCase(lineDesc, degree.getDegreeName()) == 0) {
								result.put("INDEX", 0);
								result.put("SELECTEDDEGREENAME", degree.getDegreeName());
								return result;
							}
							String key = sep.getSeparator() + degree.getDegreeName();
							int index = StringUtils.indexOfIgnoreCase(lineDesc, key);
							if (index != -1) {
								if (minIndex == -1 || minIndex > index) {
									minIndex = index;
									result.put("INDEX", minIndex);
									result.put("SELECTEDDEGREENAME", key);
								}
							}
							if (StringUtils.isNotEmpty(degree.getDegreeAlias())) {
								String[] names = degree.getDegreeAlias().split(",", -1);
								for (String name : names) {
									if (StringUtils.indexOfIgnoreCase(lineDesc, name) == 0) {
										result.put("INDEX", minIndex);
										result.put("SELECTEDDEGREENAME", name);
									}
									name = sep.getSeparator() + name;
									index = StringUtils.indexOfIgnoreCase(lineDesc, name);
									if (index == -1) {
										continue;
									}
									if (minIndex == -1 || minIndex > index) {
										minIndex = index;
										result.put("INDEX", minIndex);
										result.put("SELECTEDDEGREENAME", name);
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	public static List<PositionSkillScopeView> getSkillLabel(DegreeToSkillLabels degreeToSkill, List<PositionSkillScopeView> skillLabels,List<PaperSkillUnknown> unknowns,List<QbSkillSentenceSep> sentenceSeps) {
		List<PositionSkillScopeView> matchedSkillLabels = new ArrayList<PositionSkillScopeView>();
		String skillLabelStr = degreeToSkill.getSkillLabelStr();
		for (PositionSkillScopeView skillLabel : skillLabels) {
			Map<String, Object> result = getFirstSkillLabel(skillLabelStr, skillLabel,sentenceSeps);
			if (result.size() > 0) {
				skillLabelStr = result.get("NEWSKILLLABELSTR").toString();
				matchedSkillLabels.add(skillLabel);
			}
		}
		//TODO 如何判断剩下的是否是有意义的词语
		if(StringUtils.isNoneBlank(skillLabelStr)){
			logger.debug("the not unknown skill str is {}", skillLabelStr);
			PaperSkillUnknown  unknown = new PaperSkillUnknown();
			unknown.setParseLeft(skillLabelStr);
			unknown.setParseReason(PaperSkillUnknown.PARSE_REASON_NOSKILL);
//			unknown.setPositionId(positionId);
			unknowns.add(unknown);
		}
		return matchedSkillLabels;
	}

	private static Map<String, Object> getFirstSkillLabel(String skillLabelStr, PositionSkillScopeView skillLabel,List<QbSkillSentenceSep> sentenceSeps) {
		skillLabelStr +=";";//加上一个分隔符，防止最后没有分隔符
		Map<String, Object> result = new HashMap<String, Object>();
		int index = -1;
		boolean matched = false;
		String skillName = skillLabel.getSkillName();
		  String regEx=skillName + PaperCreateUtils.getRegexBySep(sentenceSeps);  
	         Pattern p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
	         Matcher m=p.matcher(skillLabelStr);
	         matched=m.find();
		if (!matched) {
			String skillAlias = skillLabel.getSkillAlias();
			if (StringUtils.isNotEmpty(skillAlias)) {
				String[] aliases = skillAlias.split(",", -1);
				for (String alias : aliases) {
					  regEx=alias.trim() + PaperCreateUtils.getRegexBySep(sentenceSeps);  
			          p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
			          m=p.matcher(skillLabelStr);
			          matched=m.find();
//					index = StringUtils.indexOfIgnoreCase(skillLabelStr, alias.trim());
					if (matched) {
						skillLabelStr = StringUtils.replace(skillLabelStr.toLowerCase(), alias.trim().toLowerCase(), "", -1);
						result.put("INDEX", index);
						result.put("NEWSKILLLABELSTR", skillLabelStr);
						return result;
					}
				}
			}
		} else {
			skillLabelStr = StringUtils.replace(skillLabelStr.toLowerCase(), skillLabel.getSkillName().toLowerCase(),
					"", -1);
			result.put("INDEX", index);
			result.put("NEWSKILLLABELSTR", skillLabelStr);
		}
		return result;
	}
}
