package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.StatQuestion;

public interface IStatQuestionDao extends IBaseDao<StatQuestion> {

	public void evict();

}
