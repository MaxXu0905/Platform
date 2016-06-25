package com.ailk.sets.grade.dao.intf;

import com.ailk.sets.grade.jdbc.StatQuestionTest;

public interface IStatQuestionTestDao {

	public StatQuestionTest getById(long questionId, int positionLevel);

	public void evict();

}
