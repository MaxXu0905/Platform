package com.ailk.sets.platform.empl.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.domain.DegreeToSkills;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.empl.domain.PaperInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperSet;
import com.ailk.sets.platform.intf.empl.service.IPaper;
import com.ailk.sets.platform.intf.model.PaperInitInfo;
import com.ailk.sets.platform.intf.model.PaperResponse;
import com.ailk.sets.platform.intf.model.qb.QbBaseModelInfo;
import com.ailk.sets.platform.intf.model.qb.QbSkill;

public class PaperImplTest {
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
	}
	
	@Test
	public void getPaperInfo() throws Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/beans.xml", });
		IPaper paper = (IPaper) context.getBean("paperImpl");
		List<PaperInfo> papers = paper.getPaperInfo(10010, 1, 1,2);
		System.out.println(papers);
//		System.in.read();
	}
	
	@Test
	public void getQbBases() throws Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/beans.xml", });
		IPaper paper = (IPaper) context.getBean("paperImpl");
		List<QbBaseModelInfo> papers = paper.getQbBases(100000023,10010,1);
		System.out.println(papers);
//		System.in.read();
	}
	
	@Test
	public void getPaperInitInfo() throws Exception{
		IPaper paper = (IPaper) context.getBean("paperImpl");
		 PaperInitInfo info = paper.getPaperInitInfo(1,99,1);
		System.out.println(info);
	}
	
	@Test
	public void analysisSkills() throws Exception{
		IPaper ser = (IPaper) context.getBean("paperImpl");
		Paper paper = new Paper();
		paper.setSeriesId(10010);
		paper.setSkillDesc("精通java,jsp;熟悉javascript");
	    List<DegreeToSkills> info = ser.analysisSkills(paper);
		System.out.println(info);
	}
	@Test
    public void createPaper() throws Exception{
		PaperSet paperSet = new PaperSet();
		Paper paper = new Paper();
		paper.setSeriesId(10010);
		paper.setLevel(1);
		paper.setCreateBy(1);
		paper.setTestType(1);
		paper.setPrebuilt(0);
		paper.setSysSubjectNum(4);
		paper.setSkillDesc("精通java,jsp;熟悉javascript,222");
		
		List<DegreeToSkills> degreeToSkills = new ArrayList<DegreeToSkills>();
		
		DegreeToSkills degs = new DegreeToSkills();
		
		QbSkillDegree degree = new QbSkillDegree(1);
		degree.setDegreeRatio(1.0);
		degs.setLabelDegree(degree);
		List<QbSkill> skills = new ArrayList<QbSkill>();
		QbSkill qbSkill = new QbSkill();
		qbSkill.setSkillId("1100000");
		qbSkill.setQuestionNum(8);
		skills.add(qbSkill);
		
		QbSkill qbSkill2 = new QbSkill();
		qbSkill2.setSkillId("110005001");
		qbSkill2.setQuestionNum(3);
		skills.add(qbSkill2);
		
		degs.setSkills(skills);
		
		degreeToSkills.add(degs);
		////////////////////
		
		DegreeToSkills degs2 = new DegreeToSkills();
		QbSkillDegree degree2 = new QbSkillDegree(2);
		degree2.setDegreeRatio(0.8);
		degs2.setLabelDegree(degree2);
		List<QbSkill> skills2 = new ArrayList<QbSkill>();
		QbSkill qbSkill21 = new QbSkill();
		qbSkill21.setSkillId("1100040");
		qbSkill21.setQuestionNum(2);
		skills2.add(qbSkill21);
		degs2.setSkills(skills2);
		
		degreeToSkills.add(degs2);
		
		paperSet.setPaper(paper);
		paper.setDegreeToSkills(degreeToSkills);
		Collection<Long> selfs = new ArrayList<Long>();
		//选择
		selfs.add(100000337l);
		selfs.add(100000338l);
		selfs.add(100000339l);
		//编程
		selfs.add(100000384l);
		//附加智力题
		selfs.add(107049009090001l);
		//面试题组
		selfs.add(100000346l);
		//技术问答题
		selfs.add(100000341l);
		paperSet.setQuestionIds(selfs);
		
		IPaper ser = (IPaper) context.getBean("paperImpl");
		long time1 = System.currentTimeMillis();
		PaperResponse res = ser.createPaper(paperSet);
		System.out.println("===================== waste1 " + (System.currentTimeMillis() - time1) );
//		 time1 = System.currentTimeMillis();
//		 res = ser.createPaper(paperSet);
		System.out.println("===================== waste2 " + (System.currentTimeMillis() - time1) );
		System.out.println(res);
		
	}
	
	@Test
    public void createCampusPaper() throws Exception{
		PaperSet paperSet = new PaperSet();
		Paper paper = new Paper();
		paper.setSeriesId(99);
		paper.setLevel(1);
		paper.setCreateBy(1);
		paper.setTestType(2);
		paper.setPrebuilt(0);
		paper.setSkillDesc("ffffdddddddd飞飞飞2222精通java,jsp;熟悉javascript,222");
		
		List<DegreeToSkills> degreeToSkills = new ArrayList<DegreeToSkills>();
		
		DegreeToSkills degs = new DegreeToSkills();
		
		QbSkillDegree degree = new QbSkillDegree(1);
		degree.setDegreeRatio(1.0);
		degs.setLabelDegree(degree);
		List<QbSkill> skills = new ArrayList<QbSkill>();
		QbSkill qbSkill = new QbSkill();
		qbSkill.setSkillId("1100000");
		qbSkill.setQuestionNum(8);
		skills.add(qbSkill);
		
		QbSkill qbSkill2 = new QbSkill();
		qbSkill2.setSkillId("110005001");
		qbSkill2.setQuestionNum(3);
		skills.add(qbSkill2);
		
		degs.setSkills(skills);
		
		degreeToSkills.add(degs);
		////////////////////
		
		DegreeToSkills degs2 = new DegreeToSkills();
		QbSkillDegree degree2 = new QbSkillDegree(2);
		degree2.setDegreeRatio(0.8);
		degs2.setLabelDegree(degree2);
		List<QbSkill> skills2 = new ArrayList<QbSkill>();
		QbSkill qbSkill21 = new QbSkill();
		qbSkill21.setSkillId("1100040");
		qbSkill21.setQuestionNum(2);
		skills2.add(qbSkill21);
		degs2.setSkills(skills2);
		
		degreeToSkills.add(degs2);
		
		paperSet.setPaper(paper);
		paper.setDegreeToSkills(degreeToSkills);
		Collection<Long> selfs = new ArrayList<Long>();
		//选择
		selfs.add(100000337l);
		selfs.add(100000338l);
		selfs.add(100000339l);
		//编程
//		selfs.add(100000384l);
		//附加智力题
		selfs.add(100000623L);//单选文本
		selfs.add(100000642L);//单选文本
		selfs.add(100004968L);//单选
		selfs.add(100005039L);//单选
		
		
		//面试题组
//		selfs.add(100000346l);
		paperSet.setQuestionIds(selfs);
		//技术问答题
	    selfs.add(100000341l);
	    selfs.add(100000367L);
	    selfs.add(100000401L);
		IPaper ser = (IPaper) context.getBean("paperImpl");
		long time1 = System.currentTimeMillis();
		PaperResponse res = ser.createCampusPaper(paperSet);
		System.out.println("===================== waste1 " + (System.currentTimeMillis() - time1) );
//		 time1 = System.currentTimeMillis();
//		 res = ser.createPaper(paperSet);
		System.out.println("===================== waste2 " + (System.currentTimeMillis() - time1) );
		System.out.println(res);
		
	}
	
	
	@Test
    public void createPaperByQbId() throws Exception{
		Paper paper = new Paper();
		paper.setSeriesId(10010);
		paper.setLevel(1);
		paper.setCreateBy(100000068);
		paper.setTestType(1);
		paper.setPrebuilt(0);
		paper.setSkillDesc("精通java111,jsp;熟悉javascript,222");
		
		
		
		
		IPaper ser = (IPaper) context.getBean("paperImpl");
		long time1 = System.currentTimeMillis();
		PaperResponse res = ser.createPaperByQbId(100000163,paper);
		System.out.println("===================== waste1 " + (System.currentTimeMillis() - time1) );
//		 time1 = System.currentTimeMillis();
//		 res = ser.createPaper(paperSet);
		System.out.println("===================== waste2 " + (System.currentTimeMillis() - time1) );
		System.out.println(res);
		
	}
	
	@Test
    public void createCampusPaperByQbId() throws Exception{
		Paper paper = new Paper();
		paper.setSeriesId(99);
		paper.setLevel(1);
		paper.setCreateBy(100000068);
		paper.setTestType(2);
		paper.setPrebuilt(0);
		paper.setSkillDesc("精通java222,jsp;熟悉javascript,222");
		
		IPaper ser = (IPaper) context.getBean("paperImpl");
		long time1 = System.currentTimeMillis();
		PaperResponse res = ser.createCampusPaperByQbId(100000163,paper);
		System.out.println("===================== waste1 " + (System.currentTimeMillis() - time1) );
//		 time1 = System.currentTimeMillis();
//		 res = ser.createPaper(paperSet);
		System.out.println("===================== waste2 " + (System.currentTimeMillis() - time1) );
		System.out.println(res);
		
	}
	
}
