package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.EmployerLostPwd;

public interface IEmployerLostPwd extends IBaseDao<EmployerLostPwd> {
	
	public void deleteAll(String emailAcct);

}
