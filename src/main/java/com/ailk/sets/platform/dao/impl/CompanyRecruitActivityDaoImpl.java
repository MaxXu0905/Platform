package com.ailk.sets.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IActivityRecruitAddressDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyRecruitActivityDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.util.DateUtils;
import com.alibaba.dubbo.common.utils.StringUtils;

@SuppressWarnings("unchecked")
@Repository
public class CompanyRecruitActivityDaoImpl extends
		BaseDaoImpl<CompanyRecruitActivity> implements
		ICompanyRecruitActivityDao {

	private Logger logger = LoggerFactory
			.getLogger(CompanyRecruitActivityDaoImpl.class);

	@Autowired
	private IActivityRecruitAddressDao activityRecruitAddressDaoImpl;
	@Autowired
	private IPositionDao positionDao;

	@Override
	public CompanyRecruitActivity getRecruitActivity(int postionId,
			String passcode) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("positionId", postionId));
		criteria.add(Restrictions.eq("passcode", passcode));
		return (CompanyRecruitActivity) criteria.uniqueResult();
	}

	/**
	 * 获得职位对应的活动列表
	 */
	@Override
	public List<Integer> getActivityIds(Integer positionId) {
		Query query = getSession()
				.createQuery(
						"select activityId from CompanyRecruitActivity where positionId=:positionId");
		query.setInteger("positionId", positionId);
		return query.list();
	}

	/**
	 * 获得职位对应的活动列表
	 */
	@Override
	public List<CompanyRecruitActivity> getActivityList(Integer positionId) {
		Query query = getSession()
				.createSQLQuery(
						"select * from company_recruit_activity where position_id=:positionId order by "+
				        "str_to_date(activity_date,'%Y-%m-%d'),str_to_date(begin_time,'%H:%i'),str_to_date(end_time,'%H:%i')")
				        .addEntity(CompanyRecruitActivity.class);
		query.setInteger("positionId", positionId);
		return query.list();
	}

	/**
	 * 使用活动Id删除活动(只删除考试状态为未开启的活动)
	 * 
	 * @param activityId
	 */
	@Override
	public void deleteCompanyRecruitActivity(Integer activityId) {
		CompanyRecruitActivity activity = new CompanyRecruitActivity();
		activity.setActivityId(activityId);
		activity.setTestState(CompanyRecruitActivity.TEST_STATE_NORMAL); // 只删除状态为0的活动
		getSession().delete(activity);
	}

	/**
	 * 使用活动id查询活动信息
	 * 
	 * @param activityId
	 */
	@Override
	public CompanyRecruitActivity getActivityById(Integer activityId) {
		Query query = getSession().createQuery(
				"from CompanyRecruitActivity where activityId=:activityId");
		query.setInteger("activityId", activityId);
		return (CompanyRecruitActivity) query.uniqueResult();
	}

	@Override
	public List<CompanyRecruitActivity> getCompanyRecruitActivity(int employerId, Integer positionId) {
		List<CompanyRecruitActivity> list;
		Session session = getSession();
		String whereCondition = " where positionId in (select positionId from Position where employerId =:employerId ) ";
		if(positionId != null){
			whereCondition = " where positionId = :positionId ";
		}
		String hql = "from CompanyRecruitActivity " + whereCondition + " order by activityDate,beginTime,endTime";
		Query query = session.createQuery(hql);
		if(positionId != null){
			query.setInteger("positionId", positionId);
		}else{
			query.setInteger("employerId", employerId);
		}
		list = query.list();
		if(list.size() > 0 ){
			  // 系统当前时间
	        long currentMillis = DateUtils.getCurrentMillis();
	        // 活动时间与当前时间的offtime
	        long offMillis = 0; 
	        // 开始时间距离当前时间最近的活动的索引  
	        int index = 0;
	        int i = 0;
			for (CompanyRecruitActivity act : list) {
				  String addressIds = act.getAddressId();
				  String[] addressIdsArray = addressIds.split(",");
				  List<String> addresses = new ArrayList<String>();
				  for(String addressid : addressIdsArray){
						ActivityRecruitAddress address = activityRecruitAddressDaoImpl
								.getEntity(Integer.valueOf(addressid));
					  addresses.add(address.getAddress());
					try {
						act.copyPropertiesFromAddress(address);
					} catch (Exception e) {
						logger.error("copy properties error ", e);
					}
				  }
				  act.setAddresses(addresses);
				/*ActivityRecruitAddress address = activityRecruitAddressDaoImpl
						.getEntity(act.getAddressId());*/
				
				Position position = positionDao.getEntity(act.getPositionId());
				if(StringUtils.isNotEmpty(position.getPassport())){ //position的口令，如果是用百一微信公众号 用于hr活动列表显示，
					act.setPassport(position.getPassport());
				}
				
				   // 默认值
				act.setIsCurrent(Constants.NEGATIVE);
	            try
	            {
	                // 距离当前时间的毫秒数..
	                long tempMillis = Math.abs(currentMillis - DateUtils.getDateMillis(act.getActivityDate() +" "+act.getBeginTime(), DateUtils.DATE_FORMAT_3));
	                if (i == 0)
	                {
	                    offMillis = tempMillis;
	                }else
	                {
	                    if(tempMillis < offMillis)
	                    {
	                        offMillis = tempMillis;
	                        index = i;
	                    }
	                }
	            } catch (Exception e)
	            {
	                logger.error("error compare date ", e);
	            }
	            i++;
			}
			// 距离当前时间最近的宣讲会
            list.get(index).setIsCurrent(Constants.POSITIVE);
		}
		
		return list;
	}
	
	@Override
	public List<CompanyRecruitActivity> getActiveActivityListByEntry(String positionEntry) {
		Session session = getSession();
		Position position = positionDao.getPositionByEntry(positionEntry);
		String hql ="";
		if(position.getGroupFlag() == Constants.GROUP_FLAG_NORMAL || position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
			hql = "from CompanyRecruitActivity where testState = 1 and " +
					"positionId =  (select positionId from Position where entry=:positionEntry)  ";
		}else{
			hql = "from CompanyRecruitActivity where testState = 1 and " +
					"positionId in  (select id.positionGroupId from PositionRelation where id.positionId in (select positionId from Position where entry=:positionEntry))  ";
		}
		
		hql += " order by activityDate desc,  beginTime desc ";
		Query query = session.createQuery(hql);
		query.setParameter("positionEntry", positionEntry);
		return query.list();
	}

	@Override
	public List<CompanyRecruitActivity> getCompanyRecruitActivitiesByAddressId(Integer addressId) {
		Session session = getSession();
		String hql = "from CompanyRecruitActivity " +
				" where addressId = :addressId or addressId like :leftLike or addressId like :rightLike  or addressId like :middleLike";
		Query query = session.createQuery(hql);
		query.setParameter("addressId" , addressId+"");
		query.setParameter("leftLike" ,  addressId +",%");
		query.setParameter("rightLike" , "%," + addressId);
		query.setParameter("middleLike" , "%," + addressId +",%");
		return query.list();
	}
}
