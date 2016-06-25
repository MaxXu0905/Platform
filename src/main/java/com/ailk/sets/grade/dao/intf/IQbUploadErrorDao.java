package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.grade.jdbc.QbUploadError;

public interface IQbUploadErrorDao {
	
	public List<QbUploadError> getList(int qbId);
	
	public List<QbUploadError> getFormatErrorList(int qbId);
	
	public QbUploadError get(int serialNo);

	public void save(QbUploadError qbUploadError);
	
	public void update(QbUploadError qbUploadError);
	
	public void saveOrUpdate(QbUploadError qbUploadError);
	
	public void delete(QbUploadError qbUploadError);
	
	public void deleteByQbId(int qbId);
	
	public boolean isEmpty(int qbId);
	
}
