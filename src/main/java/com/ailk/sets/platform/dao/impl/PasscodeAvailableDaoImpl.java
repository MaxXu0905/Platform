/**
 * author :  lipan
 * filename :  PasscodeAvailableDaoImpl.java
 * create_time : 2014年7月12日 上午10:52:57
 */
package com.ailk.sets.platform.dao.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPasscodeAvailableDao;
import com.ailk.sets.platform.intf.domain.PasscodeAvailable;

/**
 * @author : lipan
 * @create_time : 2014年7月12日 上午10:52:57
 * @desc : 校招微信测评passcode码表DaoImpl
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
@Repository
public class PasscodeAvailableDaoImpl  extends BaseDaoImpl<PasscodeAvailable> implements IPasscodeAvailableDao
{
    @Autowired
    @Qualifier("sessionFactory")
    protected SessionFactory sessionFactory;

    /**
     * 获取可用的passcode
     * 
     * @return
     */
    @Override
    public String getAvailablePassCode()
    {
        // 随机生成30位数字字母
        String ticket = RandomStringUtils.randomAlphanumeric(30);

        // 先更新一条记录为已用,设置标识
        String updateSql = "update passcode_available set status=1,ticket=:ticket where status=0 order by rand() limit 1;";

        // 查询刚才更新的记录
        String selectSql = "select passcode from passcode_available where ticket=:ticket";

        // 设置标志位已用
        sessionFactory.getCurrentSession().createSQLQuery(updateSql).setString("ticket", ticket)
                .executeUpdate();
        return (String) sessionFactory.getCurrentSession().createSQLQuery(selectSql)
                .setString("ticket", ticket).uniqueResult();
    }

    /**
     * 回收passcode
     * 
     * @param passcode
     */
    @Override
    public void recyclePassCode(String passcode)
    {
        // 更新标志
        String update = "update PasscodeAvailable set status=0,ticket='' where passcode=:passcode";

        // 更新标志
        sessionFactory.getCurrentSession().createQuery(update).setString("passcode", passcode)
                .executeUpdate();
    }

    /**
     * 获取测速可用的passcode
     * 
     * @return
     */
    @Override
    public String getAvailableCSPassCode()
    {
        // 随机生成30位数字字母
        String ticket = RandomStringUtils.randomAlphanumeric(30);

        // 先更新一条记录为已用,设置标识
        String updateSql = "update passcode_available set cs_status=1,cs_ticket=:cs_ticket where cs_status=0 order by rand() limit 1;";

        // 查询刚才更新的记录
        String selectSql = "select passcode from passcode_available where cs_ticket=:cs_ticket";

        // 设置标志位已用
        sessionFactory.getCurrentSession().createSQLQuery(updateSql).setString("cs_ticket", ticket)
                .executeUpdate();
        return (String) sessionFactory.getCurrentSession().createSQLQuery(selectSql)
                .setString("cs_ticket", ticket).uniqueResult();
    }

    /**
     * 回收测速passcode
     * 
     * @param passcode
     */
    @Override
    public void recycleCSPassCode(String passcode)
    {
        // 更新标志
        String update = "update PasscodeAvailable set cs_status=0,cs_ticket='' where passcode=:passcode";

        // 更新标志
        sessionFactory.getCurrentSession().createQuery(update).setString("passcode", passcode)
                .executeUpdate();
    }

	@Override
	public PasscodeAvailable getPasscodeAvailable(String passcode) {
		return (PasscodeAvailable) sessionFactory.getCurrentSession()
				.createQuery("from PasscodeAvailable where passcode = :passcode").setParameter("passcode", passcode)
				.uniqueResult();
	}
}
