package com.ailk.sets.grade.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

@Transactional(rollbackFor = Exception.class)
@Service
public class QbQuestionService implements IQbQuestionService {

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Override
	public QbQuestion get(long qid) {
		return qbQuestionDao.getEntity(qid);
	}

	@Override
	public void evict() {
		qbQuestionDao.evict();
	}

	@Override
	@Cacheable(value = "qbQuestion")
	public long get(int degree, String skillId, EnumKey key) {
		System.out.println("get(" + degree + ", " + skillId + ", " + key
				+ ") called");
		return 0;
	}

}
