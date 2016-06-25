package com.ailk.sets.platform.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.intf.model.qb.QbSkill;

@Repository
public class QbSkillDaoImpl extends BaseDaoImpl<QbSkill> implements IQbSkillDao {

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;

	@Override
	public List<QbSkill> getQbSkills(List<Long> qids) {
		String[] skillIds = qbQuestionSkillDao.getQbQuestionSkillIds(qids);
		List<QbSkill> qbSkills = new ArrayList<QbSkill>();

		for (String skillId : skillIds) {
			QbSkill qbSkill = getEntity(skillId);
			if (qbSkill != null)
				qbSkills.add(qbSkill);
		}

		return qbSkills;
	}

	@Override
	public List<QbSkill> getQbSkills(long qid) {
		List<String> skillIds = qbQuestionSkillDao.getSkillIds(qid);
		List<QbSkill> qbSkills = new ArrayList<QbSkill>();

		for (String skillId : skillIds) {
			QbSkill qbSkill = getEntity(skillId);
			if (qbSkill != null)
				qbSkills.add(qbSkill);
		}

		return qbSkills;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "qbSkillByQbId")
	public List<QbSkill> getQbSkillsOfLowLevel(int qbId) {
		Session session = getSession();

		return session
				.createQuery("FROM QbSkill WHERE qbId = ?1 AND level <= 1")
				.setInteger("1", qbId).list();
	}

	@Override
	@Cacheable(value = "qbSkill")
	public QbSkill getEntity(Serializable skillId) {
		return super.getEntity(skillId);
	}

	@Override
	@CachePut(value = "qbSkillByName", key = "#skillName + '-' + #parentId + '-' + #qbId")
	public QbSkill getByName(String skillName, String parentId, int qbId) {
		Session session = sessionFactory.getCurrentSession();

		List<?> list = session
				.createQuery(
						"FROM QbSkill WHERE skillName = ?1 AND parentId = ?2 AND qbId = ?3")
				.setString("1", skillName).setString("2", parentId)
				.setInteger("3", qbId).list();

		if (list == null || list.isEmpty())
			return null;

		return (QbSkill) list.get(0);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbSkill", key = "#qbSkill.skillId"),
			@CacheEvict(value = "qbSkillByName", key = "#qbSkill.skillName + '-' + #qbSkill.parentId + '-' + #qbSkill.qbId"),
			@CacheEvict(value = "qbSkillByQbId", key = "#qbSkill.qbId") })
	public void save(QbSkill qbSkill) {
		super.save(qbSkill);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbSkill", key = "#qbSkill.skillId"),
			@CacheEvict(value = "qbSkillByName", key = "#qbSkill.skillName + '-' + #qbSkill.parentId + '-' + #qbSkill.qbId"),
			@CacheEvict(value = "qbSkillByQbId", key = "#qbSkill.qbId") })
	public void update(QbSkill qbSkill) {
		super.update(qbSkill);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbSkill", key = "#qbSkill.skillId"),
			@CacheEvict(value = "qbSkillByName", key = "#qbSkill.skillName + '-' + #qbSkill.parentId + '-' + #qbSkill.qbId"),
			@CacheEvict(value = "qbSkillByQbId", key = "#qbSkill.qbId") })
	public void saveOrUpdate(QbSkill qbSkill) {
		super.saveOrUpdate(qbSkill);
	}

	@Override
	public String getNextSkillId() {
		Session session = sessionFactory.getCurrentSession();

		List<?> list = session.createSQLQuery(
				"SELECT uf_uid('SK_ID') FROM dual").list();
		return ((BigInteger) list.get(0)).toString();
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "qbSkill", allEntries = true),
			@CacheEvict(value = "qbSkillByName", allEntries = true),
			@CacheEvict(value = "qbSkillByQbId", allEntries = true) })
	public void evict() {
	}

}
