package com.ailk.sets.grade.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IQbUploadErrorDao;
import com.ailk.sets.grade.intf.LoadConst;
import com.ailk.sets.grade.jdbc.QbUploadError;

@Repository
public class QbUploadErrorDaoImpl implements IQbUploadErrorDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<QbUploadError> getList(int qbId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM QbUploadError WHERE qbId = ?1 ORDER BY serialNo")
				.setInteger("1", qbId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QbUploadError> getFormatErrorList(int qbId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM QbUploadError WHERE qbId = ?1 AND errorType = "
								+ LoadConst.TYPE_FORMAT + " ORDER BY serialNo")
				.setInteger("1", qbId).list();
	}

	@Override
	public QbUploadError get(int serialNo) {
		Session session = sessionFactory.getCurrentSession();

		return (QbUploadError) session.get(QbUploadError.class, serialNo);
	}

	@Override
	public void save(QbUploadError qbUploadError) {
		Session session = sessionFactory.getCurrentSession();

		session.save(qbUploadError);
	}

	@Override
	public void update(QbUploadError qbUploadError) {
		Session session = sessionFactory.getCurrentSession();

		session.update(qbUploadError);
	}

	@Override
	public void saveOrUpdate(QbUploadError qbUploadError) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(qbUploadError);
	}

	@Override
	public void delete(QbUploadError qbUploadError) {
		Session session = sessionFactory.getCurrentSession();

		session.delete(qbUploadError);
	}

	@Override
	public void deleteByQbId(int qbId) {
		Session session = sessionFactory.getCurrentSession();

		session.createQuery("DELETE FROM QbUploadError WHERE qbId = ?1")
				.setInteger("1", qbId).executeUpdate();
	}

	@Override
	public boolean isEmpty(int qbId) {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery("FROM QbUploadError WHERE qbId = ?1")
				.setInteger("1", qbId).setMaxResults(1).list().isEmpty();
	}

}
