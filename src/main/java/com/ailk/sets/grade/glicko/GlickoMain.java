package com.ailk.sets.grade.glicko;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GlickoMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/spring/beans.xml" });
		context.start();
		
		IGlickoService glickoService = context.getBean(IGlickoService.class);
		glickoService.ratingQuestion();
	}

}
