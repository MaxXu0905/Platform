package com.ailk.sets.platform.empl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.PositionOutInfo;
import com.ailk.sets.platform.intf.domain.PositionQuickInfo;
import com.ailk.sets.platform.intf.domain.PositionSeriesCustom;
import com.ailk.sets.platform.intf.empl.domain.EmployerAuthorizationIntf;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionInfoConfig;
import com.ailk.sets.platform.intf.empl.domain.PositionInitInfo;
import com.ailk.sets.platform.intf.empl.domain.PositionSet;
import com.ailk.sets.platform.intf.empl.service.IPosition;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.position.PosResponse;
import com.ailk.sets.platform.intf.model.position.PositionInfo;

public class PositionImplConsumerTest {
	static IPosition per;
	    static {
	    	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
			  context.start();
			per  = (IPosition)context.getBean("position"); 
	    }
	    @Test
	    public void testGetPositionInitInfo() throws Exception{
		      PositionInitInfo info = per.getPositionInitInfo(100000035);
		      System.out.println(info); 
	    }
	    
	    
	    @Test
	    public void testGetPositionInfo() throws Exception{
		      PositionSet info = per.getPositionInfo(1310);
		      System.out.println(info);
	    }
	    
	    @Test
	    public void testCreatePosition() throws Exception{
		      Position p = new Position();
//		      p.setPositionId(1310);
		      p.setPaperId(1314);
		      p.setEmployerId(1);
		      p.setSeriesId(10010);
		      p.setLevel(1);
		      p.setTestType(1);
		      p.setPositionName("test1111234");
		      p.setSeriesName("自定义技能1");
		      
		      List<PositionInfoConfig> configInfos = new ArrayList<PositionInfoConfig>();
		      PositionInfoConfig config = new PositionInfoConfig();
		      config.setInfoId("SEX");
//		      config.setSeq(1);
		      configInfos.add(config);
		      
		      
		      config = new PositionInfoConfig();
		      config.setInfoId("WORK_YEARS");
//		      config.setSeq(2);
		      configInfos.add(config);
		      
		      
		      p.setConfigInfo(configInfos);
		      
		      List<EmployerAuthorizationIntf> auths = new ArrayList<EmployerAuthorizationIntf>();
		      EmployerAuthorizationIntf auth1 = new EmployerAuthorizationIntf();
		      auth1.setEmailGranted("panyl@asiainfo.com");
		      auth1.setEmployerId(1);
		      auths.add(auth1);
		      
		      EmployerAuthorizationIntf auth2 = new EmployerAuthorizationIntf();
		      auth2.setEmailGranted("panyl@asiainfo-linkage.com");
		      auth2.setEmployerId(1);
		      auths.add(auth2);
		      
		      p.setEmployerAuths(auths);
		      PosResponse res =   per.createPosition(p);
		      System.out.println(res.getPositionId());
		      
		      
	    }
	    @Test
	    public void getPositionSeriesCustom() throws Exception{
             List<PositionSeriesCustom> res = per.getPositionSeriesCustom(100000069);
             for(PositionSeriesCustom c : res){
            	  System.out.println("======== seriesId="+ c.getSeriesId());
             }
           
	    }
	    @Test
	    public void createPositionByCustom() throws Exception{
	    	//1111188  1332
		      List<Integer> paperIds = new ArrayList<Integer>();
		      paperIds.add(1111188);
		      paperIds.add(1332);
		      
		      PositionQuickInfo quickInfo = new PositionQuickInfo();
		      quickInfo.setPaperIds(paperIds);
             PFResponse res = per.createPositionByCustom(100000069, quickInfo);
             System.out.println(res);
	    }
	    
	    @Test
	    public void getPositionOutInfos() throws Exception{
	    	//1111188  1332
             List<PositionOutInfo> pos = per.getPositionOutInfos(100000110);
             System.out.println(pos.size());
	    }
	    
	    @Test
	    public void getPositionInfoOfSample() throws Exception{
	    	
            List<PositionInfo> pos = per.getPositionInfoOfSample();
            System.out.println(pos.size());
	    }
	    
	    @Test
	    public void getPositionTestTypeInfo() throws Exception{
	    	
            List pos = per.getPositionTestTypeInfo(3);
            System.out.println("" + pos.size() + pos);
	    }
	    
	    @Test
	    public void getPositionByTestType() throws Exception{
	    	Page page = new Page();
            List pos = per.getPositionByTestType(100000067,1,page);
            System.out.println(pos.size());
            
            pos = per.getPositionByTestType(2,2,page);
            System.out.println(pos.size());
	    }
	    
}
