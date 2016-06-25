package com.ailk.sets.grade.dao.intf;

import com.ailk.sets.grade.jdbc.IpInfo;
import com.ailk.sets.platform.dao.IBaseDao;

public interface IIpInfoDao extends IBaseDao<IpInfo> {
	
	public String getNetName(String ip);
	
	public void evict();

}
