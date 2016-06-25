package com.ailk.sets.grade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.sets.grade.dao.intf.IConfigCodeNameDao;
import com.ailk.sets.grade.dao.intf.IConfigRegionDao;
import com.ailk.sets.grade.dao.intf.IConfigTemplateDao;
import com.ailk.sets.grade.dao.intf.IIpInfoDao;
import com.ailk.sets.grade.dao.intf.IQbBaseFileDao;
import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.dao.intf.IStatQuestionTestDao;
import com.ailk.sets.grade.service.intf.IJmxCacheService;
import com.ailk.sets.platform.dao.impl.PositionLevelDaoImpl;
import com.ailk.sets.platform.dao.impl.PositionSkillRecommendDaoImpl;
import com.ailk.sets.platform.dao.impl.QbQuestionViewDaoImpl;
import com.ailk.sets.platform.dao.impl.QbSkillDegreeDaoImpl;
import com.ailk.sets.platform.dao.impl.QbSkillSubjectViewDaoImpl;
import com.ailk.sets.platform.dao.impl.StatQuestionByseriesDegreeDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.dao.interfaces.IQbDifficultyLevelDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.dao.interfaces.IStatQuestionDao;

@Service
public class JmxCacheService implements IJmxCacheService {

	@Autowired
	private IConfigCodeNameDao configCodeNameDao;

	@Autowired
	private IConfigTemplateDao configTemplateDao;

	@Autowired
	private IQbDifficultyLevelDao qbDifficultyLevelDao;

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IQbQuestionDetailDao qbQuestionDetailDao;

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;

	@Autowired
	private IQbSkillDao qbSkillDao;

	@Autowired
	private IConfigRegionDao configRegionDao;

	@Autowired
	private ICandidateDao candidateDao;

	@Autowired
	private IQbBaseDao qbBaseDao;

	@Autowired
	private IStatQuestionTestDao statQuestionTestDao;
	
	@Autowired
	private IQbBaseFileDao qbBaseFileDao;
	
	@Autowired
	private IStatQuestionDao statQuestionDao;

	@Autowired
	private QbQuestionViewDaoImpl qbQuestionViewDaoImpl;

	@Autowired
	private PositionLevelDaoImpl positionLevelDaoImpl;

	@Autowired
	private QbSkillDegreeDaoImpl qbSkillDegreeDaoImpl;

	@Autowired
	private QbSkillSubjectViewDaoImpl qbSkillSubjectViewDaoImpl;

	@Autowired
	private StatQuestionByseriesDegreeDaoImpl statQuestionByseriesDegreeDaoImpl;
	
	@Autowired
	private PositionSkillRecommendDaoImpl positionSkillRecommendDaoImpl;
	
	@Autowired
	private IIpInfoDao ipInfoDao;
	@Autowired
	private IPositionSeriesDao positionSeriesDao;
	
	@Override
	public void evictConfigCodeName() {
		configCodeNameDao.evict();
	}

	@Override
	public void evictConfigTemplate() {
		configTemplateDao.evict();
	}

	@Override
	public void evictQbDifficultyLevel() {
		qbDifficultyLevelDao.evict();
	}

	@Override
	public void evictQbQuestion() {
		qbQuestionDao.evict();
	}

	@Override
	public void evictQbQuestionDetail() {
		qbQuestionDetailDao.evict();
	}

	@Override
	public void evictQbQuestionSkill() {
		qbQuestionSkillDao.evict();
	}

	@Override
	public void evictQbSkill() {
		qbSkillDao.evict();
	}

	@Override
	public void evictConfigRegion() {
		configRegionDao.evict();
	}

	@Override
	public void evictSql() {
		candidateDao.evict();
	}

	@Override
	public void evictQbBase() {
		qbBaseDao.evict();
	}
	
	@Override
	public void evictQbBaseFile() {
		qbBaseFileDao.evict();
	}
	
	@Override
	public void evictStatQuestion() {
		statQuestionDao.evict();
	}

	@Override
	public void evictStatQuestionTest() {
		statQuestionTestDao.evict();
	}

	@Override
	public void evictQbQuestionView() {
		qbQuestionViewDaoImpl.evict();
	}

	@Override
	public void evictPositionLevel() {
		positionLevelDaoImpl.evict();
	}

	@Override
	public void evictQbSkillDegree() {
		qbSkillDegreeDaoImpl.evict();
	}

	@Override
	public void evictQbSkillSubjectView() {
		qbSkillSubjectViewDaoImpl.evict();
	}

	@Override
	public void evictQbQuestionViewForMoti() {
		qbQuestionViewDaoImpl.evictMoti();
	}

	@Override
	public void evictStatQuestionByseriesDegree() {
		statQuestionByseriesDegreeDaoImpl.evict();
	}
	
	@Override
	public void evictPositionSkillRecommend() {
		positionSkillRecommendDaoImpl.evict();
	}
	
	@Override
	public void evictIpInfo() {
		ipInfoDao.evict();
	}
	@Override
	public void evictPositionSeries() {
		positionSeriesDao.evict();
	}
}
