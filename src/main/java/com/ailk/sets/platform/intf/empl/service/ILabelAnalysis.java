package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.platform.domain.PositionAnalysisResult;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillSubjectView;
import com.ailk.sets.platform.intf.empl.domain.Position;

/**
 * 标签解析接口
 * @author panyl
 *
 */
public interface ILabelAnalysis {
	public List<QbSkillSubjectView> getQbSkills(String programLanuage);
	public List<QbSkillDegree> getQbSkillDegrees();
	public PositionAnalysisResult  analysisSentences(String seriesId,String sentence);
	
	public PositionAnalysisResult getPositionAnalysisResult(Position p);
	
}
