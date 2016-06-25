package com.ailk.sets.platform.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.intf.model.Page;

/**
 * 基础dao接口
 * 
 * @author 毕希研
 * 
 * @param <T>
 */
public interface IBaseDao<T> {

	/**
	 * 从数据库中获取某种类型的uid
	 * 
	 * @param type
	 * @return
	 */
	public BigInteger getUIDFromBase(String type);

	/**
	 * 根据一个查询条件获取对象
	 * 
	 * @param key
	 * @param keyName
	 * @return
	 */
	public T getEntity(Object key, String keyName);
	
	/**
	 * 根据一组查询条件获取对象
	 * 
	 * @param key
	 * @param keyName
	 * @return
	 */
	public T getEntity(Map<String,Object> params);

	/**
	 * 根据主键获取对象
	 * 
	 * @param objId
	 * @return
	 */
	public T getEntity(Serializable objId);

	/**
	 * 执行sql获取对象
	 * 
	 * @param sql
	 * @return
	 */
	public T getEntityBySql(String sql);

	/**
	 * 执行hql获取对象
	 * 
	 * @param hql
	 * @return
	 */
	public T getEntityByHql(String hql);

	/**
	 * 获取所有数据对象
	 * 
	 * @return
	 */
	public List<T> getAll();

	/**
	 * 根据一组查询条件获取对象集合
	 * 
	 * @param key
	 * @param keyName
	 * @return
	 */
	public List<T> getList(Map<String,Object> params);
	
	/**
	 * 根据一个查询条件获取对象集合
	 * 
	 * @param key
	 * @param keyName
	 * @return
	 */
	public List<T> getList(Object key, String keyName);

	/**
	 * 根据一个查询条件获取对象集合，带分页
	 * 
	 * @param key
	 * @param keyName
	 * @param page
	 * @return
	 */
	public List<T> getList(Object key, String keyName, Page page);

	/**
	 * 根据sql查询对象集合
	 * 
	 * @param sql
	 * @return
	 */
	public List<T> getListBySql(String sql);

	/**
	 * 获取分页的集合数据
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	public List<T> getListBySql(String sql, Page page);

	/**
	 * 根据hql查询对象集合
	 * 
	 * @param hql
	 * @return
	 */
	public List<T> getList(String hql);

	/**
	 * 获取分页的集合数据
	 * 
	 * @param hql
	 * @param page
	 * @return
	 */
	public List<T> getList(String hql, Page page);

	/**
	 * 删除某个对象
	 * 
	 * @param t
	 */
	public void delete(T t);

	/**
	 * 删除某个对象
	 * 
	 * @param objId
	 */
	public void delete(Serializable objId);

	/**
	 * 执行更新
	 * 
	 * @param hql
	 */
	public int excuteUpdate(String hql);
	
	/**
	 * 保存某个对象
	 * 
	 * @param t
	 */
	public void save(T t);
	
	/**
	 * 更新某个对象
	 * 
	 * @param t
	 */
	public void update(T t);

	/**
	 * 保存或者更新某个对象
	 * 
	 * @param t
	 */
	public void saveOrUpdate(T t);

	/**
	 * 获取数量
	 * 
	 * @param key
	 * @param keyName
	 * @return
	 */
	public Long getCount(Object key, String keyName);
}
