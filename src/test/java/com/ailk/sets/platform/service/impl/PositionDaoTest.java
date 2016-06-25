package com.ailk.sets.platform.service.impl;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.intf.model.Page;

public class PositionDaoTest {
	@Test
	public void getExtrasHistoryTest() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/beans.xml" });
		context.start();
		IPositionDao positionDao = (IPositionDao) context.getBean("positionDaoImpl");
		Page page = new Page();
		page.setPageSize(3);
		page.setRequestPage(2);
//		List<ExtrasHistory> list =  positionDao.getExtrasHistory(0, "java", 4, page);
//		for(ExtrasHistory eh : list)
//			System.out.println(eh.getId().getQuestionId());
	}

}
