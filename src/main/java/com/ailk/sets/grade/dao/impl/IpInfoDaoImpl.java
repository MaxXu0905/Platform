package com.ailk.sets.grade.dao.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IIpInfoDao;
import com.ailk.sets.grade.jdbc.IpInfo;
import com.ailk.sets.platform.dao.BaseDaoImpl;

@Repository
public class IpInfoDaoImpl extends BaseDaoImpl<IpInfo> implements IIpInfoDao {

	@Override
	@Cacheable(value = "ipInfo")
	public String getNetName(String ip) {
		int index = ip.lastIndexOf(".");
		if (index == -1)
			return "";

		String key = ip.substring(0, index);
		IpInfo ipInfo = getEntity(key);
		if (ipInfo == null)
			return "";

		return ipInfo.getNetName();
	}

	@Override
	@CacheEvict(value = "ipInfo", allEntries = true)
	public void evict() {
	}

}
