package com.ailk.sets.platform.dao.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.alibaba.dubbo.common.utils.StringUtils;

@Repository
public class QbBaseDaoImpl extends BaseDaoImpl<QbBase> implements IQbBaseDao {

	public List<QbBase> search(int createBy, String qbName, Page page) {
		List<QbBase> qbBases = getSelfQbBases(createBy);
		List<QbBase> matchedQbBases = new ArrayList<QbBase>();

		for (QbBase qbBase : qbBases) {
			if (StringUtils.isNotEmpty(qbName))
				if (!qbBase.getQbName().toUpperCase().contains(qbName.toUpperCase()))
					continue;

			matchedQbBases.add(qbBase);
		}

		Collections.sort(matchedQbBases, new Comparator<QbBase>() {

			@Override
			public int compare(QbBase o1, QbBase o2) {
				return o2.getModifyDate().compareTo(o1.getModifyDate());
			}
		});

		int fromIndex = page.getFirstRow();
		if (fromIndex >= matchedQbBases.size())
			return new ArrayList<QbBase>();

		int toIndex = fromIndex + page.getPageSize();
		if (toIndex > matchedQbBases.size())
			toIndex = matchedQbBases.size();

		return matchedQbBases.subList(fromIndex, toIndex);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "selfQbBaseByCreateBy", key = "#createBy")
	public List<QbBase> getSelfQbBases(int createBy) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("FROM QbBase WHERE createBy = :createBy and category != :category");
		query.setInteger("createBy", createBy);
		query.setInteger("category", Constants.CATEGORY_PAPER);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "qbBaseByCreateBy", key = "#createBy")
	public List<QbBase> getQbBases(int createBy) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("FROM QbBase WHERE category = :category and (createBy = :createBy or prebuilt = :prebuilt) order by prebuilt desc,qbName asc");
		query.setInteger("createBy", createBy);
		query.setInteger("category", Constants.CATEGORY_SKILL);
		query.setInteger("prebuilt", Constants.PREBUILT_SYS);
		return query.list();
	}

	@Override
	public void initInterviewAndIntellQbBases(int employerId) {
		Timestamp time = new Timestamp(System.currentTimeMillis());

		try {
			QbBase qbBase = new QbBase();
			qbBase.setCategory(Constants.CATEGORY_INTE);
			qbBase.setCreateBy(employerId);
			qbBase.setCreateDate(time);
			qbBase.setModifyDate(time);
			qbBase.setPrebuilt(Constants.PREBUILT_SELF);
			qbBase.setQbName("智力题库");
			qbBase.setQbId(getUIDFromBase(Constants.QB_ID).intValue());
			save(qbBase);
		} catch (Exception e) {
		}

		try {
			QbBase qbBase = new QbBase();
			qbBase.setCategory(Constants.CATEGORY_INTER);
			qbBase.setCreateBy(employerId);
			qbBase.setCreateDate(time);
			qbBase.setModifyDate(time);
			qbBase.setPrebuilt(Constants.PREBUILT_SELF);
			qbBase.setQbName("面试题库");
			qbBase.setQbId(getUIDFromBase(Constants.QB_ID).intValue());
			save(qbBase);
		} catch (Exception e) {
		}
	}

	@Override
	@Cacheable(value = "qbBaseByName", key = "#qbName.toLowerCase() + '-' + #createBy")
	public QbBase getPaperQbBase(String qbName, int createBy) {
		Session session = sessionFactory.getCurrentSession();

		return (QbBase) session
				.createQuery(
						"FROM QbBase WHERE qbName = ?1 AND createBy = ?2 AND category = "
								+ GradeConst.CATEGORY_PAPER)
				.setString("1", qbName).setInteger("2", createBy)
				.uniqueResult();
	}
	
	@Override
	public Integer getQbBasesCount(int createBy, String qbName) {
		Session session = sessionFactory.getCurrentSession();
		if(qbName ==null){
			qbName = "";
		}
		Query query = session
				.createQuery("select count(*) FROM QbBase WHERE createBy = :createBy and qbName like :qbName and category != :category");
		query.setInteger("createBy", createBy);
		query.setString("qbName", "%"+qbName+"%");
		query.setInteger("category", Constants.CATEGORY_PAPER);
		return ((Long)query.uniqueResult()).intValue();
	}

	
	

	@Override
	@Cacheable(value = "qbBaseById")
	public QbBase getEntity(Serializable qbId) {
		return super.getEntity(qbId);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbBaseByName", key = "#qbBase.qbName.toLowerCase() + '-' + #qbBase.createBy"),
			@CacheEvict(value = "qbBaseById", key = "#qbBase.qbId"),
			@CacheEvict(value = "qbBaseByCreateBy", key = "#qbBase.createBy"),
			@CacheEvict(value = "selfQbBaseByCreateBy", key = "#qbBase.createBy") })
	public void save(QbBase qbBase) {
		super.save(qbBase);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbBaseByName", key = "#qbBase.qbName.toLowerCase() + '-' + #qbBase.createBy"),
			@CacheEvict(value = "qbBaseById", key = "#qbBase.qbId"),
			@CacheEvict(value = "qbBaseByCreateBy", key = "#qbBase.createBy"),
			@CacheEvict(value = "selfQbBaseByCreateBy", key = "#qbBase.createBy") })
	public void update(QbBase qbBase) {
		super.update(qbBase);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "qbBaseByName", key = "#qbBase.qbName.toLowerCase() + '-' + #qbBase.createBy"),
			@CacheEvict(value = "qbBaseById", key = "#qbBase.qbId"),
			@CacheEvict(value = "qbBaseByCreateBy", key = "#qbBase.createBy"),
			@CacheEvict(value = "selfQbBaseByCreateBy", key = "#qbBase.createBy") })
	public void saveOrUpdate(QbBase qbBase) {
		super.saveOrUpdate(qbBase);
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "qbBaseByName", allEntries = true),
			@CacheEvict(value = "qbBaseById", allEntries = true),
			@CacheEvict(value = "qbBaseByCreateBy", allEntries = true),
			@CacheEvict(value = "selfQbBaseByCreateBy", allEntries = true) })
	public void evict() {
	}

}
