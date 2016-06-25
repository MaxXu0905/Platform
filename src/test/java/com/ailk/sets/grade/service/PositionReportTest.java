package com.ailk.sets.grade.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ailk.sets.grade.intf.ExportPositionResponse;
import com.ailk.sets.grade.intf.ILoadService;
import com.ailk.sets.platform.intf.empl.service.IReport;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.candidateReport.CandReportAndInfo;
import com.ailk.sets.platform.intf.model.condition.Interval;
import com.ailk.sets.platform.intf.model.param.GetReportParam;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
@TransactionConfiguration(defaultRollback = false)
public class PositionReportTest {

	@Autowired
	private ILoadService loadService;

	@Test
	public void exportPosition() {
	    
	    GetReportParam param = new GetReportParam(1332 ,"0" , new Page(20, 1) ,new Interval(
                "0", "100"));
//      param.setInputKey("钻石版");
//        param.setCommitPaperFromDate("2014-08-13");
//        param.setCommitPaperToDate("2014-08-13");
//      param.setPositionIntent("");
        param.setEmployerId(100000069);
        
		ExportPositionResponse response = loadService.exportPosition(param);
		System.out.println("Title=" + response.getTitle());
	
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("/Users/Pan/position.xls"));
			out.write(response.getData());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
