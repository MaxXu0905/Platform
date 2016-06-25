package com.ailk.sets.platform.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IConfigCodeNameDao;
import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.domain.EmployerPosHistory;
import com.ailk.sets.platform.domain.PositionLog;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.PositionOutInfo;
import com.ailk.sets.platform.intf.domain.PositionSeries;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeNameId;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionTestTypeInfo;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.util.PFUtils;

@Repository
public class PositionDaoImpl extends BaseDaoImpl<Position> implements
		IPositionDao {
	@Autowired
	private IConfigCodeNameDao configCodeNameDao;

    @Autowired
	private IPositionSeriesDao positionSeriesDao;
	public Position getPosition(int positionId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Position.class);
		criteria.add(Restrictions.eq("positionId", positionId));
		return (Position) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<EmployerPosHistory> getHistory(int employerId, String category,
			String historyId, Page page) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EmployerPosHistory.class);
		criteria.add(Restrictions.eq("id.employerId", employerId));
		criteria.add(Restrictions.eq("category", category));
		if (StringUtils.isNotEmpty(historyId))
			criteria.add(Restrictions.like("id.historyId", historyId,
					MatchMode.END));
		criteria.addOrder(Order.desc("frequency"));
		criteria.addOrder(Order.desc("lastUse"));
		criteria.setFirstResult((page.getRequestPage() - 1)
				* page.getPageSize());
		criteria.setMaxResults(page.getPageSize());
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<PositionLog> getPositionLog(int employerId,List<Integer> positionGrantedIds, Page page) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String ids = PFUtils.getIdString(positionGrantedIds);
		String hql = "select * from (select * from position_log where employer_id = " + employerId ;
		if(positionGrantedIds.size() > 0){	
			hql += " or position_id in ("
					+ ids
					+ ")";
		}
		hql+=" order by log_time desc) p group by position_id order by log_time desc";
		SQLQuery query = session.createSQLQuery(hql);
		query.setFirstResult((page.getRequestPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		query.addEntity(PositionLog.class);
		return (List<PositionLog>) query.list();
	}

	public boolean isPositionNew(int employerId, int positionId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(PositionLog.class);
		criteria.add(Restrictions.eq("employerId", employerId));
		criteria.add(Restrictions.eq("positionId", positionId));
		criteria.add(Restrictions.eq("positionState", 0));
		return (criteria.uniqueResult() != null);
	}

	@SuppressWarnings("unchecked")
	public List<PositionLog> getPositionLog(int positionId, int employerId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "from PositionLog where employerId = " + employerId
				+ " and positionId = " + positionId
				+ " and positionState != 0 order by logTime desc limit 5";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public Position getPositionByInvitation(long testId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "from Position where positionId in (select positionId from Invitation where testId = "
				+ testId + ")";
		Query query = session.createQuery(hql);
		return (Position) query.uniqueResult();
	}

	@Override
	public void delPositionLog(int positionId, long stateId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("delete PositionLog where positionId="
						+ positionId + " and stateId=" + stateId);
		query.executeUpdate();
	}

	public void delPositionLogByState(int positionId, int state) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("delete PositionLog where positionId="
						+ positionId + " and positionState=" + state);
		query.executeUpdate();
	}

	public void delPositionLog(int positionId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("delete PositionLog where positionId="
						+ positionId);
		query.executeUpdate();
	}

	@Override
	public void updatePositionState(int positionId, int state) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("update Position set positionState = " + state
						+ " where positionId=" + positionId);
		query.executeUpdate();
	}

	@Override
	public void delQuesHistory(int employerId, Long qId, String category) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder(
				"delete from EmployerPosHistory where id.employerId = "
						+ employerId);
		if ("1".equals(category))
			hql.append(" and id.historyId like '" + qId + "_%'");
		else
			hql.append(" and id.historyId = '" + qId + "'");
		Query query = session.createQuery(hql.toString());
		query.executeUpdate();
	}

	@Override
	public void savePositionLog(PositionLog positionLog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(positionLog);
	}

	/**
	 * 获得entry的Map集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getEntryMap() {
		// 用公司微信号时
		// List<String> keyList=
		// getSession().createQuery("select entry from Position where weixinCompany = 1 and positionState = 0")
		List<String> keyList = getSession()
				.createQuery(
						"select entry from Position where positionState = 0")
				.setCacheable(false).list();// 禁用缓存
		Map<String, String> keyMap = new HashMap<String, String>();
		for (String key : keyList) {
			if (!StringUtils.isBlank(key)) {
				keyMap.put(key, null);
			}
		}
		return keyMap;
	}

	/**
	 * 获得passport的Map集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getPassportMap() {
		// 用百一微信号获得通行证时
		List<String> keyList = getSession()
				.createQuery(
						"select passport from Position where positionState=0")
				.setCacheable(false).list();// 禁用缓存
		Map<String, String> keyMap = new HashMap<String, String>();
		for (String key : keyList) {
			if (!StringUtils.isBlank(key)) {
				keyMap.put(key, null);
			}
		}
		return keyMap;
	}

	/**
	 * 使用entry查询职位信息
	 * 
	 * @param passport
	 * @return
	 */
	@Override
	public Position getPositionByEntry(String entry) {
		return (Position) getSession()
				.createQuery(
						"from Position where entry=:entry and positionState=0")
				.setString("entry", entry).uniqueResult();
	}

	/**
	 * 使用passport查询职位信息
	 * 
	 * @param passport
	 * @return
	 */
	@Override
	public String getPositionByPassport(String passport) {
		return (String) getSession()
				.createQuery(
						"select entry from Position where passport=:passport and positionState=0")
				.setString("passport", passport).uniqueResult();
	}
	/**
	 * 使用passport查询职位信息
	 * 
	 * @param passport
	 * @return
	 */
	@Override
	public Position getPositionObjByPassport(String passport) {
		return  (Position)getSession()
				.createQuery(
						" from Position where passport=:passport ")
				.setString("passport", passport).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> getCustomedPaperPositions(int employer) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from Position where employerId =:employerId " +
				" and paperId in " +
				"(select paperId from Paper where  prebuilt = :prebuilt ) ");
		q.setParameter("employerId", employer);
		q.setParameter("prebuilt", Constants.PREBUILT_SYS);
		return q.list();
	}

	@Override
	public List<PositionOutInfo> getPositionOutInfos(int employerId) {
		List<PositionOutInfo> outInfos = new ArrayList<PositionOutInfo>();
		Session session = sessionFactory.getCurrentSession();
	    String sysHql = "from Position where preBuilt = 1 and " +
	    		"positionId not in (select createFrom from Position where employerId =:employerId and createFrom is not null)";//没有被copy的默认测评
	    String selfHql = "from Position where employerId = " + employerId + " order by publishDate desc";//自己创建的测评
	    Query query = session.createQuery(selfHql);
	    List<Position>  positions = query.list();
       
	    query = session.createQuery(sysHql);
	    query.setInteger("employerId", employerId);
	    positions.addAll(query.list());
	    for(Position position : positions){//dao层已经做了缓存,可以重复访问
	    	PositionOutInfo outInfo = new PositionOutInfo();
	    	outInfo.setPositionId(position.getPositionId());
        	outInfo.setPositionName(position.getPositionName());
	    	PositionSeries series = positionSeriesDao.getEntity(position.getSeriesId());
	    	PositionSeries parent = null;
	    	if(series != null){
	    		parent = positionSeriesDao.getEntity(series.getParentId());
	    		outInfo.setSeriesId(position.getSeriesId());
	        	outInfo.setSeriesName(series.getSeriesName());
	        	if(parent != null){
	        		outInfo.setParentSeriesId(parent.getSeriesId());
		        	outInfo.setParentSeriesName(parent.getSeriesName());
	        	}
	        	
	    	}
        	outInfo.setLevel(position.getLevel());
        	ConfigCodeNameId configCodeNameId = new ConfigCodeNameId();
    		configCodeNameId.setCodeType(ConfigCodeType.POSITION_LEVEL);
    		configCodeNameId.setCodeId(position.getLevel() +"");
    		ConfigCodeName levelName = configCodeNameDao.get(configCodeNameId);
		    if(levelName != null)
        	outInfo.setLevelName(levelName.getCodeName());
		    
        	outInfos.add(outInfo);
        }
	    
		return outInfos;
	}

	@Override
	public Position getPositionByCreateFrom(int createFromPositionId, int employerId) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from Position where createFrom = "+ createFromPositionId + " and employerId = " + employerId);
		List<Position> list = q.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Position> getPositionsOfSample() {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from Position where sample = 1");
		return q.list();
	}

	@Override
	public List<PositionTestTypeInfo> getPositionTestTypeInfo(int employerId,List<Integer> positionGrantedIds) {
		String sql = "select test_type, count(*) , max(position_id) from position where group_flag != "+ Constants.GROUP_FLAG_CHILD  + " and  employer_id = " + employerId ;
		if (positionGrantedIds.size() > 0) {
			String ids = PFUtils.getIdString(positionGrantedIds);
			sql += " or position_id in (" + ids + ")";
		}
		sql += " group by test_type";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery(sql);
		q.setResultTransformer(new ResultTransformer() {
			
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				PositionTestTypeInfo info = new PositionTestTypeInfo();
				info.setTestType((Integer) tuple[0]);
				info.setPositionNum(((BigInteger) tuple[1]).intValue());
				info.setPositionId(((Integer) tuple[2]).intValue());
				return info;
			}
			
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
	
		return q.list();
	}

	@Override
	public List<PositionLog> getPositionLogByTestType(int employerId,List<Integer> positionGrantedIds, Page page, Integer testType) {
		Session session = null;
		session = sessionFactory.getCurrentSession();

		String hql = "select * from (select * from position_log where  employer_id = " + employerId;
		if (positionGrantedIds.size() > 0) {
			String ids = PFUtils.getIdString(positionGrantedIds);
			hql += " or position_id in (" + ids + ")";
		}
		hql += " order by log_time desc) p group by position_id  having position_id in "
				+ "( select position_id from position where test_type = " + testType + " ) order by log_time desc";
		SQLQuery query = session.createSQLQuery(hql);
		query.setFirstResult((page.getRequestPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		query.addEntity(PositionLog.class);
		return (List<PositionLog>) query.list();
	}

	@Override
	public List<Position> getPosition(int employerId, List<Integer> positionGrantedIds, Page page, Integer testType) {

		Session session = null;
		session = sessionFactory.getCurrentSession();
		String ids = PFUtils.getIdString(positionGrantedIds);
		String hql = "select * from position where group_flag != "+ Constants.GROUP_FLAG_CHILD;
		if(testType != null){
			hql += " and test_type = " + testType;
		}
			hql 	+=" and (employer_id = " + employerId ;
		if(positionGrantedIds.size() > 0){	
			hql += " or position_id in ("
					+ ids
					+ ")";
		}
		hql+=") order by modify_date desc";
		SQLQuery query = session.createSQLQuery(hql);
		query.setFirstResult((page.getRequestPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		query.addEntity(Position.class);
		return (List<Position>) query.list();
	
	}
	
}
