package com.ailk.sets.grade.service.intf;

public interface IJmxCacheService {

	public void evictConfigCodeName();

	public void evictConfigTemplate();

	public void evictQbDifficultyLevel();

	public void evictQbQuestion();

	public void evictQbQuestionDetail();

	public void evictQbQuestionSkill();

	public void evictQbSkill();

	public void evictConfigRegion();

	public void evictSql();

	public void evictQbBase();

	public void evictQbBaseFile();

	public void evictStatQuestion();

	public void evictStatQuestionTest();

	public void evictQbQuestionView();

	public void evictPositionLevel();

	public void evictQbSkillDegree();

	public void evictQbSkillSubjectView();

	public void evictQbQuestionViewForMoti();

	public void evictStatQuestionByseriesDegree();
	
	public void evictPositionSkillRecommend();
	
	public void evictIpInfo();
	
	public void evictPositionSeries();

}
