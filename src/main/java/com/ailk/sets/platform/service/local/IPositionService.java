package com.ailk.sets.platform.service.local;

import com.ailk.sets.platform.intf.model.question.Extras;

public interface IPositionService {
//	public PosResponse createPosition(PositionSet positionSet) throws PFServiceException;
//	
//	public PositionPaperAnalysisResult analysisPosition(PositionSet positionSet) throws PFServiceException;

//	public List<Extras> getExtrQuestionsByAnalyis(Position position, List<String> skillIds);
	
	/**
	 * 获取一道附加题
	 * 
	 * @param position
	 * @param pqts
	 * @return
	 */
	public Extras getExtrQuestionsByExtraId(Long questionId);
	
	
	
//	public void saveTest();

}
