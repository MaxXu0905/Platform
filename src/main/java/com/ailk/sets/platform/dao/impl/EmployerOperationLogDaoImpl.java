package com.ailk.sets.platform.dao.impl;

import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IEmployerOperationLogDao;
import com.ailk.sets.platform.intf.empl.domain.EmployerOperationLog;
@Repository
public class EmployerOperationLogDaoImpl extends BaseDaoImpl<EmployerOperationLog> implements IEmployerOperationLogDao {}
