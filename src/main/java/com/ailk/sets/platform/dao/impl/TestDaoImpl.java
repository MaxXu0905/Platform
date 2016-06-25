package com.ailk.sets.platform.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.domain.PositionLog;
@Repository
public class TestDaoImpl {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	public void save1(PositionLog log){
		sessionFactory.getCurrentSession().save(log);
	}
	
	/*@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory)   
    { 
        super.setSessionFactory(sessionFactory);   
    }  */
}
