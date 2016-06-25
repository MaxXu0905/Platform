package com.ailk.sets.platform.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.domain.QbQuestionSkill;

@Repository
public class QbQuestionSkillDaoImpl extends BaseDaoImpl<QbQuestionSkill>
		implements IQbQuestionSkillDao {

	@Override
	public List<QbQuestionSkill> getQbQuestionSkills(List<Long> qids) {
		List<QbQuestionSkill> result = new ArrayList<QbQuestionSkill>();

		for (long qid : qids) {
			List<QbQuestionSkill> qbQuestionSkills = getSkills(qid);
			if (qbQuestionSkills != null)
				result.addAll(qbQuestionSkills);
		}

		return result;
	}

	@Override
	public String[] getQbQuestionSkillIds(List<Long> qids) {
		Set<String> result = new HashSet<String>();

		for (long qid : qids) {
			List<QbQuestionSkill> qbQuestionSkills = getSkills(qid);
			if (qbQuestionSkills == null)
				continue;

			for (QbQuestionSkill qbQuestionSkill : qbQuestionSkills) {
				result.add(qbQuestionSkill.getId().getSkillId());
			}
		}

		return result.toArray(new String[result.size()]);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbQuestionSkill", key = "#qbQuestionSkill.id.questionId"),
			@CacheEvict(value = "qbQuestionSkillId", key = "#qbQuestionSkill.id.questionId") })
	public void save(QbQuestionSkill qbQuestionSkill) {
		super.save(qbQuestionSkill);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbQuestionSkill", key = "#qbQuestionSkill.id.questionId"),
			@CacheEvict(value = "qbQuestionSkillId", key = "#qbQuestionSkill.id.questionId") })
	public void update(QbQuestionSkill qbQuestionSkill) {
		super.update(qbQuestionSkill);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbQuestionSkill", key = "#qbQuestionSkill.id.questionId"),
			@CacheEvict(value = "qbQuestionSkillId", key = "#qbQuestionSkill.id.questionId") })
	public void saveOrUpdate(QbQuestionSkill qbQuestionSkill) {
		super.saveOrUpdate(qbQuestionSkill);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "qbQuestionSkill")
	public List<QbQuestionSkill> getSkills(long qid) {
		List<QbQuestionSkill> result = new ArrayList<QbQuestionSkill>();
		Session session = sessionFactory.getCurrentSession();

		List<QbQuestionSkill> qbQuestionSkills = session
				.createQuery("From QbQuestionSkill WHERE id.questionId = ?1")
				.setLong("1", qid).list();
		for (QbQuestionSkill qbQuestionSkill : qbQuestionSkills) {
			result.add(qbQuestionSkill);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "qbQuestionSkillId")
	public List<String> getSkillIds(long qid) {
		List<String> result = new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();

		List<QbQuestionSkill> qbQuestionSkills = session
				.createQuery("From QbQuestionSkill WHERE id.questionId = ?1")
				.setLong("1", qid).list();
		for (QbQuestionSkill qbQuestionSkill : qbQuestionSkills) {
			result.add(qbQuestionSkill.getId().getSkillId());
		}

		return result;
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "qbQuestionSkill"),
			@CacheEvict(value = "qbQuestionSkillId") })
	public void deleteByQuestionId(long questionId) {
		Session session = sessionFactory.getCurrentSession();

		session.createQuery(
				"DELETE FROM QbQuestionSkill WHERE id.questionId = ?1")
				.setLong("1", questionId).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getQuestionIds(String skillId) {
		Session session = sessionFactory.getCurrentSession();

		List<QbQuestionSkill> qbQuestionSkills = session
				.createQuery("FROM QbQuestionSkill WHERE id.skillId = ?1")
				.setString("1", skillId).list();
		List<Long> result = new ArrayList<Long>();
		for (QbQuestionSkill qbQuestionSkill : qbQuestionSkills) {
			result.add(qbQuestionSkill.getId().getQuestionId());
		}
		
		return result;
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbQuestionSkill", allEntries = true),
			@CacheEvict(value = "qbQuestionSkillId", allEntries = true) })
	public void evict() {
	}
}
