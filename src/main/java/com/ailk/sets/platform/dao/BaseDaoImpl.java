package com.ailk.sets.platform.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ailk.sets.platform.intf.model.Page;

/**
 * 基础dao实现类
 * 
 * @author 毕希研
 * 
 * @param <T>
 */
public class BaseDaoImpl<T> implements IBaseDao<T> {

	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@SuppressWarnings("unchecked")
	public T getEntity(Map<String,Object> params){
	    Session session = getSession();
	    Criteria criteria = session.createCriteria(entityClass);
	    for (String paramName : params.keySet())
        {
	        criteria.add(Restrictions.eq(paramName, params.get(paramName)));
        }
	    return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T getEntity(Object key, String keyName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(entityClass);
		criteria.add(Restrictions.eq(keyName, key));
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T getEntity(Serializable objId) {
		Session session = getSession();
		return (T) session.get(entityClass, objId);
	}

	@SuppressWarnings("unchecked")
	public T getEntityBySql(String sql) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(entityClass);
		return (T) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T getEntityByHql(String hql) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		return (T) query.uniqueResult();
	}

	public void delete(T t) {
		Session session = getSession();
		session.delete(t);
	}

	@Override
	public void delete(Serializable objId) {
		Session session = getSession();
		session.delete(this.getEntity(objId));
	}

	public void save(T t) {
		Session session = getSession();
		session.save(t);
	}

	public void update(T t) {
		Session session = getSession();
		session.update(t);
	}

	public void saveOrUpdate(T t) {
		Session session = getSession();
		session.saveOrUpdate(t);
	}

	public int excuteUpdate(String hql) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(entityClass);
		return (List<T>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getListBySql(String sql) {
		Session session = getSession();
		return (List<T>) session.createSQLQuery(sql).addEntity(entityClass)
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(Object key, String keyName) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq(keyName, key));
		return (List<T>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(Map<String,Object> params) {
	    Criteria criteria = getCriteria();
	    for (String paramName : params.keySet())
        {
	        criteria.add(Restrictions.eq(paramName, params.get(paramName)));
        }
	    return (List<T>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(Object key, String keyName, Page page) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq(keyName, key));
		if (page.getPageSize() != 0) {
			criteria.setFirstResult((page.getRequestPage() - 1)
					* page.getPageSize());
			criteria.setFetchSize(page.getPageSize());
		}
		return (List<T>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(String hql) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getListBySql(String sql, Page page) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(entityClass);
		if (page.getPageSize() != 0) {
			query.setFirstResult((page.getRequestPage() - 1)
					* page.getPageSize());
			query.setFetchSize(page.getPageSize());
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(String hql, Page page) {
		Session session = getSession();
		Query query = session.createSQLQuery(hql);
		if (page.getPageSize() != 0) {
			query.setFirstResult((page.getRequestPage() - 1)
					* page.getPageSize());
			query.setFetchSize(page.getPageSize());
		}
		return query.list();
	}

	public Long getCount(Object key, String keyName) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq(keyName, key));
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	public BigInteger getUIDFromBase(String type) {
		Session session = getSession();
		String hql = "select uf_uid(\"" + type + "\")";
		SQLQuery query = session.createSQLQuery(hql);
		return (BigInteger) query.uniqueResult();
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Criteria getCriteria() {
		return getSession().createCriteria(entityClass);
	}

}
