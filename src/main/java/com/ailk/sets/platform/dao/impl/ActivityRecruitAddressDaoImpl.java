package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IActivityRecruitAddressDao;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.model.condition.Interval;

/**
 * 校招活动地址信息Dao
 * 
 * @author : lipan
 * @create_time : 2014年7月1日 下午8:52:11
 * @desc :
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
@SuppressWarnings("unchecked")
@Repository
public class ActivityRecruitAddressDaoImpl extends
		BaseDaoImpl<ActivityRecruitAddress> implements
		IActivityRecruitAddressDao {

	/**
	 * 获得地址列表 使用城市、大学获得地址列表（前10条，根据）
	 * 
	 */
	@Override
	public List<ActivityRecruitAddress> getActivityAddressList(
			ActivityRecruitAddress address) {
		Session session = getSession();

		// sql
		String sql = "SELECT * FROM activity_recruit_address WHERE address_id IN (SELECT max(address_id) FROM "
				+ "activity_recruit_address WHERE city = :city AND college = :college GROUP BY address ) "
				+ "order by address_id desc ";
		return session.createSQLQuery(sql)
				.addEntity(ActivityRecruitAddress.class)
				.setString("city", address.getCity())
				.setString("college", address.getCollege()).list();
	}

	/**
	 * 删除地址信息
	 */
	@Override
	public void deleteActivityAddress(ActivityRecruitAddress address) {
		delete(address);
	}

	/**
	 * 保存地址信息
	 */
	@Override
	public void saveActivityAddress(ActivityRecruitAddress address) {
		getSession().saveOrUpdate(address);
	}

	/**
	 * 使用城市、学校、地址 查询地址信息 查询地址
	 */
	@Override
	public ActivityRecruitAddress getAddress(ActivityRecruitAddress address) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("city", address.getCity().trim()));
		criteria.add(Restrictions.eq("college", address.getCollege().trim()));
		criteria.add(Restrictions.eq("address", address.getAddress().trim()));
		return (ActivityRecruitAddress) criteria.uniqueResult();
	}

	
    /**
     *  测速App - 查询所有未结束的活动地址信息
     */
    @Override
    public List<ActivityRecruitAddress> getAddListByPositionId(Integer positionId)
    {
//        String hql = "from ActivityRecruitAddress where addressId in(select addressId from CompanyRecruitActivity where positionId=:positionId and testState!=2 )";
        String hql = "select * from Activity_Recruit_Address where find_in_set(address_Id, (select group_concat(address_id) from Company_Recruit_Activity where position_Id = :positionId and test_State!=2))";
        
        return getSession().createSQLQuery(hql).addEntity(ActivityRecruitAddress.class)
                .setInteger("positionId", positionId)
                .list();
    }

    @Override
    public List<String> getDetailAddList(ActivityRecruitAddress address)
    {
        String hql = "select address from ActivityRecruitAddress where city=:city and college=:college order by createDate desc";
        return getSession().createQuery(hql)
                .setString("city", address.getCity())
                .setString("college", address.getCollege())
                .list();
    }

	@Override
	public List<ActivityRecruitAddress> getAllAddressByPositionId(Integer positionId) {
		 String hql = "select * from Activity_Recruit_Address where find_in_set(address_Id, (select group_concat(address_id) from Company_Recruit_Activity where position_Id = :positionId))";
	     return getSession().createSQLQuery(hql).addEntity(ActivityRecruitAddress.class)
	                .setInteger("positionId", positionId)
	                .list();
	}

	@Override
	public List<ActivityRecruitAddress> getUnTestActivityRecruitAddresses() {
		String hql = " from Activity_Recruit_Address where totalNum is null ";
		return getSession().createQuery(hql).list();
	}

    @Override
    public List<ActivityRecruitAddress> getAddresseSignals(String keyword, Interval num)
    {
        if (null == num.getMin())
        {
            num.setMin("0");
        }
        if (null == num.getMax())
        {
            num.setMax("10000000");
        }
        String params = "";
        if (StringUtils.isNotBlank(keyword))
        {
            params =  "(city like '%"+keyword+"%' or college like '%"+keyword+"%' or address like '%"+keyword+"%')";
        }
        String hql = " from ActivityRecruitAddress where 1=1 and "+params
                + "and totalNum>=:min and totalNum<=:max";
        return getSession().createQuery(hql)
//                .setString("city", keyword)
//                .setString("college", keyword)
//                .setString("address", keyword)
                .setInteger("min", Integer.parseInt(num.getMin()))
                .setInteger("max", Integer.parseInt(num.getMax()))
                .list();
    }
	
}
