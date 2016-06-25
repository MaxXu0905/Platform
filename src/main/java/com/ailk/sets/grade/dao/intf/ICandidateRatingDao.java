package com.ailk.sets.grade.dao.intf;

import com.ailk.sets.grade.jdbc.CandidateRating;
import com.ailk.sets.grade.jdbc.CandidateRatingPK;

public interface ICandidateRatingDao {

	public CandidateRating get(CandidateRatingPK candidateRatingPK);
	
	public void save(CandidateRating candidateRating);
	
	public void update(CandidateRating candidateRating);
	
	public void saveOrUpdate(CandidateRating candidateRating);

}
