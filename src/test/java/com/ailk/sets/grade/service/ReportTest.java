package com.ailk.sets.grade.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ailk.sets.grade.grade.IReportTestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml","/spring/localbean.xml" })
@TransactionConfiguration(defaultRollback = false)
public class ReportTest {

	@Autowired
	private IReportTestService reportTestService;

	@Test
	public void saveReport() {
		try {
			reportTestService.saveReport(126643);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		reportTestService.save();

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
