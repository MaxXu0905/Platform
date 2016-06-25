package com.ailk.sets.platform.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QbNormalDaoImpl {
	private Logger logger = LoggerFactory.getLogger(QbNormalDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	/*public <T> List<T> getNoramlListBySql(String sql, Class<T> cls) {
		Session session = null;
			session = sessionFactory.getCurrentSession();
			return (List<T>) session.createSQLQuery(sql).addEntity(cls).list();
	}*/
	
	public <T> List<T> getNoramlListBySqlWithJvmDate(String sql, Class<T> cls,List<Date> dates) {
		Session session = null;
			session = sessionFactory.getCurrentSession();
			SQLQuery q = session.createSQLQuery(sql);
			for(int i = 0; i< dates.size() ; i++){
				q.setTimestamp(0, dates.get(i));
			}
			return (List<T>) q.addEntity(cls).list();
	}
	

	
	/*public <T> List<T> getNoramlListByHql(String hql, Class<T> cls) {
		 Session session = null;
			session = sessionFactory.getCurrentSession();
			return (List<T>) session.createQuery(hql).list();
	}*/
	/*public <T> T getNormalObject(Serializable objId, Class<T> cls) {
		Session session = null;
			session = sessionFactory.getCurrentSession();
			return (T) session.get(cls, objId);
	}*/

	/*public void saveOrUpdate(Object o) {
		Session session = null;
			session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(o);
//			session.flush();
	}*/

	/*public long getNextSequence() {
		Session session = null;
			session = sessionFactory.getCurrentSession();
			List list = session.createSQLQuery("select uf_getQid() from dual").list();
			return ((BigInteger) list.get(0)).longValue();
	}*/


	
	public long getCountNumberBySql(String sql){
		Session session = null;
			session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(sql);
			Long result = (Long) query.uniqueResult();
			return result == null ? 0 : result;
	}
}
