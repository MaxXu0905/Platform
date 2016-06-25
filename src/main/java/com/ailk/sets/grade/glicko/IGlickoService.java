package com.ailk.sets.grade.glicko;

import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.GetQInfoResponse;

public interface IGlickoService {

	/**
	 * 对有一定答题次数的问题进行打分
	 */
	public void ratingQuestion();
	
	public void loadQuestionPlayers();

	public GetQInfoResponse getQInfo(RatingSession ratingSession)
			throws Exception;
	
	public RatingSession buildRatingSession(int candidateId, int qbId);
	
	public BaseResponse commit(RatingSession ratingSession, String answer);

}
