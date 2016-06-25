package com.ailk.sets.platform.empl.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.domain.DegreeToSkillLabels;
import com.ailk.sets.platform.domain.PositionAnalysisResult;
import com.ailk.sets.platform.domain.skilllabel.PositionSkillScopeView;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillSubjectView;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.ILabelAnalysis;

public class LabelAnalysisImplTest {
	static ApplicationContext context;
	static{
		PropertyConfigurator.configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/provider.xml","/spring/consumer.xml","/spring/beans.xml" });
	}

	@Test
	public void getSkillLabels() throws Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/beans.xml", });
		ILabelAnalysis labelAnalysis = (ILabelAnalysis) context.getBean("labelAnalysisImpl");
		List<QbSkillSubjectView> labels = labelAnalysis.getQbSkills("java");
		System.out.println(labels.size());
		for(QbSkillSubjectView s : labels){
		    System.out.println(s);
		}
		 System.out.println("Press any key to exit.");
//		System.in.read();
	}

	@Test
	public void getSkillLabelDegrees() {
//		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/beans.xml", });
		ILabelAnalysis labelAnalysis = (ILabelAnalysis) context.getBean("labelAnalysisImpl");
		List<QbSkillDegree> labels = labelAnalysis.getQbSkillDegrees();
		System.out.println(labels.size());
	}
	
	
	@Test
	public void analysisSentences(){
		String positionDesc = "1.熟悉JAVA、数据库等技术；有良好的OOP编程思想；掌握J2EE平台的核心技术；" +
		"2.熟悉J2EE主流框架的使用，熟练使用Spring/Structs/Hibernate等主流框架。" ;


//		System.out.println(positionDesc);
		System.out.println(new Date());
		ILabelAnalysis labelAnalysis = (ILabelAnalysis) context.getBean("labelAnalysisImpl");
		PositionAnalysisResult result = labelAnalysis.analysisSentences("10010",positionDesc);
		List<DegreeToSkillLabels> degreeToSkillLabels = result.getDegreeToSkillLableses();
		
		for (DegreeToSkillLabels degreeToLabel : degreeToSkillLabels) {
			for (PositionSkillScopeView skillLabel : degreeToLabel.getSkillLabels()) {
				System.out.println(skillLabel.getSkillName() + "#####" + degreeToLabel.getSelectedDegreeName());
			}
		}
		System.out.println(new Date());
	}
	
	
	@Test
	public void analysisSentences2(){
		String positionDesc = "1、计算机/电子相关专业，大专以上学历，有2年以上Android平台开发经验；" +


	"2、熟练掌握JAVA语言,JSP，熟悉android应用开发；" +


	"3、熟悉Linux开发环境，对c和c++语言有一定的了解,有底层开发经验优先；" +
	"4、能独立解决问题，具备良好的沟通能力和团队合作精神；"+
	"5、熟悉全志A20 A31平台或智能家居系统等方面工作经验 "+
	"有智能家居系统工作经验优先。";

//		System.out.println(positionDesc);
		ILabelAnalysis labelAnalysis = (ILabelAnalysis) context.getBean("labelAnalysisImpl");
//		labelAnalysis.analysisSentences(1,positionDesc);
		
		
		long time1 = System.currentTimeMillis();
		/*positionDesc ="1、精通Java高级、Ext、Javascript、JSP等开发语言；、/r/n" +
    " 2、熟悉面向对象，能够使用Spring、Struts、iBatis、Hibernate等技术进行快速开发；/r/n" +
"3、熟悉XML、XSL、SOA技术；/r/n" +
"4、熟悉多线程、IBM WebSphere、JBoss、Tomcat等中间件产品的配置和开发；/r/n" +
"5、有网络编程使用经验，熟悉Oracle数据库，并熟悉其他一种中型数据库技术，如SQL Server、MySQL等，具有基于Oracle数据库的设计和开发经验；熟悉PL/SQL开发语言/r/n";
	*/	
		positionDesc ="有三年以上的项目开发经验,精通java，JSP，" +
			"了解多线程,面向对象，有shell，sql使用经验，" +
			"能独立解决ibatis问题，对struts，hibernate了解。掌握JQuery等WEB技术";
		labelAnalysis.analysisSentences("java",positionDesc);
		System.out.println("执行时间----" + (System.currentTimeMillis() - time1));
	}
	@Test
	public void testGetPositionAnalysisResult(){
		
		ILabelAnalysis labelAnalysis = (ILabelAnalysis) context.getBean("labelAnalysisImpl");
		Position p = new Position();
		p.setPositionId(1834);
//		p.setProgramLanguage("java");
		long time1 = System.currentTimeMillis();
		PositionAnalysisResult result = labelAnalysis.getPositionAnalysisResult(p);
		System.out.println("执行时间----" + (System.currentTimeMillis() - time1));
		System.out.println(result);
		 time1 = System.currentTimeMillis();
		 result = labelAnalysis.getPositionAnalysisResult(p);
		System.out.println("执行时间----" + (System.currentTimeMillis() - time1));
	}
	
	

}
