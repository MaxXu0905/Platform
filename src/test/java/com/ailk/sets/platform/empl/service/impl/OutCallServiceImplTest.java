package com.ailk.sets.platform.empl.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ailk.sets.platform.intf.common.OutResponse;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.OutReportInfo;
import com.ailk.sets.platform.intf.empl.service.IOutCallService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/localbean.xml","/spring/beans.xml" })
public class OutCallServiceImplTest {
	@Autowired
	private IOutCallService callService;

	public void updateMrReportStatus(){
		CandidateTest test = new CandidateTest();
		test.setTestId(123L);
		test.setChannelType(1);
		OutResponse pf = callService.updateMrReportStatus(test, 1);
	    System.out.println(pf);
	}
	@Test
	public void giveMrReportData(){
		OutResponse pf = callService.giveMrReportData(11373);
	    System.out.println(pf);
	}
	
	
}
