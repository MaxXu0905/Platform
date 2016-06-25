package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.EmployerAuthorization;

public interface IEmployerAuthDao extends IBaseDao<EmployerAuthorization>{

	public List<Integer> getGrantedId(int employerId);
	
	public int deleteAuth(int positionId);

    /**
     * 
     * @param positionId
     * @return
     */
	public List<EmployerAuthorization> getEmployerAuthorizations(int employerId,int positionId);
} 
