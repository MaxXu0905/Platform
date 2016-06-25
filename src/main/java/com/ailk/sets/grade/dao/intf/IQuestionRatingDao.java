package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.grade.jdbc.QuestionRating;
import com.ailk.sets.grade.jdbc.QuestionRatingPK;

public interface IQuestionRatingDao {

	public QuestionRating get(QuestionRatingPK questionRatingPK);
	
	public List<QuestionRating> getList();
	
	public List<QuestionRating> getList(int qbId);

	public void save(QuestionRating questionRating);

	public void update(QuestionRating questionRating);

	public void saveOrUpdate(QuestionRating questionRating);

}
