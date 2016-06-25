package com.ailk.sets.platform.service.local.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.impl.TestDaoImpl;
import com.ailk.sets.platform.domain.PositionLog;

@Transactional(rollbackFor=Exception.class)
@Service
public class TestServiceImpl{
	
   @Autowired
   TestDaoImpl daoImpl;
   
   public void save1(PositionLog l){
	      PositionLog log = new PositionLog();
	      log.setPositionId(-1);
	      log.setPositionState(-1);
	      log.setStateId(-1L);
	      log.setEmployerId(-1);
	      log.setLogTime(new Timestamp(System.currentTimeMillis()));
	      daoImpl.save1(log);
	      
	      PositionLog log2 = new PositionLog();
	      log2.setPositionId(-2);
	      log2.setPositionState(-1);
	      log2.setStateId(-1L);
	      log2.setEmployerId(-1);
	      log2.setLogTime(new Timestamp(System.currentTimeMillis()));
	      daoImpl.save1(log2);
	   throw new RuntimeException();

   }
}
