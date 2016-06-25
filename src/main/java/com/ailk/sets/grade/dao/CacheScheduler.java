package com.ailk.sets.grade.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ailk.sets.grade.dao.intf.IStatQuestionTestDao;
import com.ailk.sets.platform.dao.interfaces.IStatQuestionDao;

@Service
public class CacheScheduler implements ICacheScheduler {

	private static final Logger logger = Logger.getLogger(CacheScheduler.class);

	@Autowired
	private IStatQuestionTestDao statQuestionTestDao;
	@Autowired
	private IStatQuestionDao statQuestionDao;
	@Value("${grade.schedulerEnabled}")
	private boolean schedulerEnabled;

	/**
	 * 清除缓存，每天1:00执行
	 */
	@Scheduled(cron="0 0 01 * * *")
	@Override
	public void evict() {
		if (!schedulerEnabled)
			return;

		statQuestionTestDao.evict();
		logger.info("清除 StatQuestionTest 表的缓存成功");
		statQuestionDao.evict();
		logger.info("清除 StatQuestion 表的缓存成功");
	}

}