package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IQbDifficultyLevelDao;
import com.ailk.sets.platform.domain.QbDifficultyLevel;
import com.ailk.sets.platform.domain.QbDifficultyLevelId;

@Repository
public class QbDifficultyLevelDaoImpl extends BaseDaoImpl<QbDifficultyLevel>
		implements IQbDifficultyLevelDao {

	@Cacheable(value = "qbDifficultyLevelId")
	public QbDifficultyLevel getDifficultyLevel(String levelCode,
			int positionLevel) {
		Session session = sessionFactory.getCurrentSession();
		return (QbDifficultyLevel) session.get(QbDifficultyLevel.class,
				new QbDifficultyLevelId(levelCode, positionLevel));
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "qbDifficultyLevel")
	public DifficultyLevel[] getDifficultyLevel(int positionLevel) {
		Session session = sessionFactory.getCurrentSession();

		List<QbDifficultyLevel> qbDifficultyLevels = session
				.createQuery(
						"From QbDifficultyLevel WHERE id.positionLevel=?1 ORDER BY difficultyLow")
				.setInteger("1", positionLevel).list();

		DifficultyLevel[] difficultyLevels = new DifficultyLevel[qbDifficultyLevels
				.size()];
		for (int i = 0; i < difficultyLevels.length; i++) {
			difficultyLevels[i] = new DifficultyLevel();
		}

		for (QbDifficultyLevel qbDifficultyLevel : qbDifficultyLevels) {
			int levelCodeInt;
			String levelCode = qbDifficultyLevel.getId().getLevelCode();
			if (levelCode.equals(LEVEL_CODE_LOW))
				levelCodeInt = IDX_LOW;
			else if (levelCode.equals(LEVEL_CODE_MEDIUM))
				levelCodeInt = IDX_MEDIUM;
			else if (levelCode.equals(LEVEL_CODE_HIGH))
				levelCodeInt = IDX_HIGH;
			else
				continue;

			DifficultyLevel difficultyLevel = difficultyLevels[levelCodeInt];
			difficultyLevel.setLow(qbDifficultyLevel.getDifficultyLow());
			difficultyLevel.setHigh(qbDifficultyLevel.getDifficultyHigh());
		}

		return difficultyLevels;
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbDifficultyLevelId", allEntries = true),
			@CacheEvict(value = "qbDifficultyLevel", allEntries = true) })
	public void evict() {
	}

}