package com.ailk.sets.platform.service.local;

import com.ailk.sets.platform.intf.domain.PositionLevel;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public interface IPaperService {
	public void  createPaperInstance(int positionId, long testId, String inviteCode) throws PFServiceException;
//	public PosResponse createPaper(String paperSubCode, List<DegreeToSkillLabels> degreeToSkillses, PositionSet positionSet) throws PFServiceException;
//	public void analysisPaper(PositionPaperAnalysisResult result,String paperSubCode, List<DegreeToSkillLabels> degreeToSkillses, PositionSet positionSet) throws PFServiceException;
	public PositionLevel getPositionLevelByCache(int seriesId,int levelId);
//	public List<PaperQuestionToSkills> getExtrQuestionsByAnalyis(QbSubjectToDegreeSkills paperCodeSbToDegreeSkills,int max);

}
