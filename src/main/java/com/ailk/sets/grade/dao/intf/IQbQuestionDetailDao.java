package com.ailk.sets.grade.dao.intf;

import com.ailk.sets.grade.jdbc.QbQuestionDetail;

public interface IQbQuestionDetailDao {

	public QbQuestionDetail get(long questionId);
	
	public QbQuestionDetail getWithoutCache(long questionId);

	public QbQuestionDetail save(QbQuestionDetail qbQuestionDetail);

	public QbQuestionDetail update(QbQuestionDetail qbQuestionDetail);

	public QbQuestionDetail saveOrUpdate(QbQuestionDetail qbQuestionDetail);

	public void evict();

}
