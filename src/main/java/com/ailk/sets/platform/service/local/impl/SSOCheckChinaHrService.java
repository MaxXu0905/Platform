package com.ailk.sets.platform.service.local.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.service.local.ISSOCheckService;
@Transactional(rollbackFor = Exception.class)
@Service
public class SSOCheckChinaHrService implements ISSOCheckService {

	@Override
	public EmployerRegistInfo login(String tocken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployerRegistInfo getEmployerInfoByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
