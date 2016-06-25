package com.ailk.sets.platform.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.domain.DegreeToSkillLabels;
import com.ailk.sets.platform.domain.skilllabel.PositionSkillScopeView;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;

/**
 * 程度技能解析标签工具类
 * @author panyl
 *
 */
public class DegreeSkillLabelUtils {
	private static Logger logger = LoggerFactory.getLogger(DegreeSkillLabelUtils.class);
	public static void findDegreeSkillLabel(String line, List<QbSkillDegree> skillDegreses, List<PositionSkillScopeView> skillLabels,List<DegreeToSkillLabels> list) {
		if (StringUtils.isNotEmpty(line)) {
			DegreeToSkillLabels degreeToSkill;
			QbSkillDegree firstLabelDegree = null; //第一个degree
			String firstDegreeSkillDesc = "";
			String firstSelectedDegreeName = "";
			int firstDegreeIndex = -1;//第一个degree词索引
			int secondDegreeIndex = -1;//去除第一个degree次后剩余字符串的第一个degree词的索引
			Map result = null;
			for (QbSkillDegree labelDegree : skillDegreses) {
				result = getFirstDegree(line, labelDegree);
				if (result.size() == 0) {
					continue;
				}
				int index = (Integer) result.get("INDEX");
				if (index != -1) {
					if (firstDegreeIndex == -1 || firstDegreeIndex > index) {//找出最小的index
						firstDegreeIndex = index;
						firstLabelDegree = labelDegree;
						firstSelectedDegreeName = result.get("SELECTEDDEGREENAME").toString();
					}
				}
			}
			if (firstDegreeIndex != -1) {
				String secondSelectedDegreeName = "";
				String skillLabelStr = line.substring(firstDegreeIndex + firstSelectedDegreeName.length());// 后移
				for (QbSkillDegree labelDegree2 : skillDegreses) {
					Map result2 = getFirstDegree(skillLabelStr, labelDegree2);//
					if (result2.size() == 0) {
						continue;
					}
					int index = (Integer) result2.get("INDEX");
					if (index != -1) {
						if (secondDegreeIndex == -1 || secondDegreeIndex > index) {
							secondDegreeIndex = index;
							secondSelectedDegreeName =  result2.get("SELECTEDDEGREENAME").toString();
						}
					}
				}
				if (secondDegreeIndex != -1) {
					firstDegreeSkillDesc = line.substring(firstDegreeIndex, firstDegreeIndex
							+ firstSelectedDegreeName.length() + secondDegreeIndex);
					degreeToSkill = new DegreeToSkillLabels();
					degreeToSkill.setLabelDegree(firstLabelDegree);
					degreeToSkill.setSkillLabelStr(firstDegreeSkillDesc);
					degreeToSkill.setSelectedDegreeName(firstSelectedDegreeName);
					logger.debug("the secondSelectedDegreeName is {}", secondSelectedDegreeName);
				} else {
					firstDegreeSkillDesc = line.substring(firstDegreeIndex);
					degreeToSkill = new DegreeToSkillLabels();
					degreeToSkill.setLabelDegree(firstLabelDegree);
					degreeToSkill.setSkillLabelStr(firstDegreeSkillDesc);
					degreeToSkill.setSelectedDegreeName(firstSelectedDegreeName);
				}
				
				logger.debug("the degreeName is {}, the skillLabel desc is {} ",firstSelectedDegreeName,firstDegreeSkillDesc);
			
				if (degreeToSkill != null) {
					List<PositionSkillScopeView> usedSkillLabels = new ArrayList<PositionSkillScopeView>();
					findSkillLabel(degreeToSkill,skillLabels,usedSkillLabels);
					degreeToSkill.setSkillLabels(usedSkillLabels);
					list.add(degreeToSkill);
					if (secondDegreeIndex != -1) {// 还有下一个degree词则递归遍历
                        String leftLine = line.substring(firstDegreeIndex + firstSelectedDegreeName.length() + secondDegreeIndex);
						logger.debug("the left desc str is {} ", leftLine);
						findDegreeSkillLabel(line.substring(firstDegreeIndex + firstSelectedDegreeName.length()
								+ secondDegreeIndex), skillDegreses, skillLabels,list);
					}

				}
			}
		}
	}
	
	private static Map getFirstDegree(String line, QbSkillDegree labelDegree) {
		Map result = new HashMap();
		int minIndex = -1;
		minIndex = StringUtils.indexOfIgnoreCase(line, labelDegree.getDegreeName());
		if (minIndex != -1) {
			result.put("INDEX", minIndex);
			result.put("SELECTEDDEGREENAME", labelDegree.getDegreeName());
		}
		if (StringUtils.isNotEmpty(labelDegree.getDegreeAlias())) {
			String[] names = labelDegree.getDegreeAlias().split(",", -1);
			for (String name : names) {
				int index = StringUtils.indexOfIgnoreCase(line, name);
				if (index == -1) {
					continue;
				}
				if (minIndex == -1 || minIndex > index) {
					minIndex = index;
					result.put("INDEX", minIndex);
					result.put("SELECTEDDEGREENAME", name);
					return result;
				}
			}
		}
		return result;
	}

	private static  void findSkillLabel(DegreeToSkillLabels degreeToSkill, List<PositionSkillScopeView> skillLabels,List<PositionSkillScopeView> matchedSkillLabels) {
		String skillLabelStr = degreeToSkill.getSkillLabelStr();
		for (PositionSkillScopeView skillLabel : skillLabels) {
			Map result = findFirstSkillLabel(skillLabelStr,skillLabel);
			if(result.size() > 0){
				skillLabelStr = result.get("NEWSKILLLABELSTR").toString();
				matchedSkillLabels.add(skillLabel);
			}
			
		}
	}

	private static Map findFirstSkillLabel(String skillLabelStr, PositionSkillScopeView skillLabel) {
		Map result =  new HashMap();
		int index = -1;
		index = StringUtils.indexOfIgnoreCase(skillLabelStr, skillLabel.getSkillName());
		if (index == -1) {
			String skillAlias = skillLabel.getSkillAlias();
			if (StringUtils.isNotEmpty(skillAlias)) {
				String[] aliases = skillAlias.split(",", -1);
				for (String alias : aliases) {
					index = StringUtils.indexOfIgnoreCase(skillLabelStr, alias);
					if (index != -1) {
						skillLabelStr = StringUtils.replace(skillLabelStr.toLowerCase(), alias.toLowerCase(), "", -1);
						result.put("INDEX", index);
						result.put("NEWSKILLLABELSTR", skillLabelStr);
                        return result;                        
					}
				}
			}
		}else{
			skillLabelStr = StringUtils.replace(skillLabelStr.toLowerCase(), skillLabel.getSkillName().toLowerCase(), "", -1);
			result.put("INDEX", index);
			result.put("NEWSKILLLABELSTR", skillLabelStr);
		}
		return result;
	}
}
