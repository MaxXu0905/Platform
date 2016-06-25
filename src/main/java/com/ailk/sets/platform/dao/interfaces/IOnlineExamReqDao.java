/**
 * author :  lipan
 * filename :  IOnlineExamReqDao.java
 * create_time : 2014年10月20日 下午3:18:50
 */
package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.empl.domain.OnlineExamReq;

/**
 * @author : lipan
 * @create_time : 2014年10月20日 下午3:18:50
 * @desc : 在线申测信息dao
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public interface IOnlineExamReqDao extends IBaseDao<OnlineExamReq>
{
	 /**
	  * 根据口令获取在线申请
	  * @param passCode
	  * @return
	  */
	 public OnlineExamReq getOnlineExamReqByPassport(String passport);
	 
	 /**
	  * 根据positionId 和 testId获取在线申请
	  * @param passCode
	  * @return
	  */
	 public OnlineExamReq getOnlineExamReq(long candidateId,int positionId);
}
