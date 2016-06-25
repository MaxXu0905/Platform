/**
 * author :  lipan
 * filename :  OnlineExamReqDaoImpl.java
 * create_time : 2014年10月20日 下午3:19:50
 */
package com.ailk.sets.platform.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IOnlineExamReqDao;
import com.ailk.sets.platform.intf.empl.domain.OnlineExamReq;

/**
 * @author : lipan
 * @create_time : 2014年10月20日 下午3:19:50
 * @desc : 在线申测信息
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
@Repository
public class OnlineExamReqDaoImpl extends BaseDaoImpl<OnlineExamReq> implements IOnlineExamReqDao
{

  @Override
  public OnlineExamReq getOnlineExamReqByPassport(String passport){
	
	  Session session = getSession();
	  Query q = session.createQuery("from OnlineExamReq where passport = :passport and status = 1");
	  q.setString("passport", passport);
	  return (OnlineExamReq)q.uniqueResult();
  }

    @Override
    public OnlineExamReq getOnlineExamReq(long candidateId, int positionId)
    {
        Session session = getSession();
        Query q = session.createQuery("from OnlineExamReq where positionId = :positionId and candidateId= :candidateId and status = 1");
        q.setLong("candidateId", candidateId);
        q.setInteger("positionId", positionId);
        return (OnlineExamReq)q.uniqueResult();
    }
}
