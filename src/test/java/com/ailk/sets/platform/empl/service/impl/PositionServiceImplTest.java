package com.ailk.sets.platform.empl.service.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionSet;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.service.local.IPositionService;

public class PositionServiceImplTest {
	static ApplicationContext context;
	static{
		 PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/consumer.xml","/spring/beans.xml" });
	}
	
	@Test
	public void testCreatePosition() throws PFServiceException{
		IPositionService positionService = (IPositionService)context.getBean("positionServiceImpl");
		String subjectCode = "java";
		String positionName = "开发工程师4446android";
		int levelId = 4;
		String positionDesc = "1.精通JAVA Core，精通J2EE框架技术，熟练使用Spring/Structs/Hibernate/iBatis/Apache系列等主流框架；"+
"2.精通Ajax，熟练使用Javascript/XML/CSS/XPATH等RIA技术，熟悉WebService/SOAP协议，熟悉中间件技术及各种中间件；"+
"3.熟悉数据库（Mysql/Oracle/SQL Server）技术，如日常查询统计、数据库管理、性能优化等，熟悉Linux常用命令；"+
"4.熟悉主流Web服务器，如Tomcat，Apache，Websphere或Weblogic等。";
//		positionDesc ="dddddddddd";
		long time1 = System.currentTimeMillis();
		PositionSet positionSet = new PositionSet();
		Position position = new Position();
//		position.setProgramLanguage(subjectCode);
		position.setRequiresDesc(positionDesc);
		position.setLevel(levelId);
		position.setPositionName(positionName);
		position.setEmployerId(100000060);
//		position.setPositionId(145);
		positionSet.setPosition(position);
		
		/*
		
		Collection<Extras> extras = new ArrayList<Extras>();
		
		Extras a = new Extras();
		a.setBrandNew(true);
		a.setProgramLang("java");
		a.setTime(600);
		a.setaDesc("test");
		a.setqDesc("test");
		extras.add(a);
		positionSet.setExtras(extras);*/
	/*	InterviewGroup grp = new InterviewGroup();
		grp.setBrandNew(false);
		grp.setQuestionId(118);
		Interview in = new Interview();
		in.setBrandNew(true);
		in.setQuestionDesc("dddd");
		
		Interview in2 = new Interview();
		in2.setBrandNew(true);
		in2.setQuestionDesc("aaa");
		Collection<Interview>  ins = new ArrayList<Interview>();
		ins.add(in);
		ins.add(in2);
		grp.setQbQuestion(ins);
		positionSet.setInterviewGroup(grp);*/
		
//		positionService.createPosition(positionSet);
		long time2 = System.currentTimeMillis();
		System.out.println("waste =====" + (time2- time1));
		/* positionSet = new PositionSet();
		 position = new Position();
		position.setProgramLanguage(subjectCode);
		position.setRequiresDesc(positionDesc);
		position.setLevel(levelId);
		position.setPositionName(positionName);
		position.setEmployerId(100000060);
//		position.setPositionId(145);
		positionSet.setPosition(position);
		positionService.createPosition(positionSet);
		long time3 = System.currentTimeMillis();
		System.out.println("waste =====" + (time3- time2));*/
	}
	
	@Test
	public void testGetExtrQuestionsByAnalyis(){
		/*IPositionService positionService = (IPositionService)context.getBean("positionServiceImpl");
		String subjectCode = "java";
		int levelId = 3;
		String positionDesc = "有三年以上的项目开发经验, 精通java，JSP，" +
		"了解多线程,面向对象，有shell，sql使用经验，" +
		"能独立解决ibatis问题，对struts，hibernate了解。掌握JQuery等WEB技术";
		long time1 = System.currentTimeMillis();
		
		PositionSet positionSet = new PositionSet();
		Position position = new Position();
//		position.setProgramLanguage(subjectCode);
		position.setRequiresDesc(positionDesc);
		position.setLevel(levelId);
		positionSet.setPosition(position);
		
		List<String> pqts = null;
		List<Extras> list = positionService.getExtrQuestionsByAnalyis(position, pqts);
		System.out.println("list size is " + list.size());
		long time2 = System.currentTimeMillis();
		System.out.println("waste " + (time2- time1));
	    list = positionService.getExtrQuestionsByAnalyis(position, pqts);
		System.out.println("list size is " + list.size());
		long time3 = System.currentTimeMillis();
		System.out.println("waste " + (time3- time2));*/
	}
	
	@Test
	public void testGetExtrQuestionsByExtraId(){
		IPositionService positionService = (IPositionService)context.getBean("positionServiceImpl");
//		Extras o  = positionService.getExtrQuestionsByExtraId(10l);
//		System.out.println("list size is " + o);
	}
	

	@Test
	public void test(){
		IPositionService positionService = (IPositionService)context.getBean("positionServiceImpl");
//		positionService.saveTest();
	}
}
