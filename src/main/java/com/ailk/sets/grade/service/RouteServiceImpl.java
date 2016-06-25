package com.ailk.sets.grade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IIpInfoDao;
import com.ailk.sets.grade.intf.IRouteService;

@Transactional(rollbackFor = Exception.class)
public class RouteServiceImpl implements IRouteService {

	@Autowired
	private IIpInfoDao ipInfoDao;

	@Override
	public String getNetName(String ip) {
		return ipInfoDao.getNetName(ip);
	}

}
