package com.ailk.sets.grade.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ailk.sets.grade.cache.IQbQuestionService.EnumKey;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml" })
@TransactionConfiguration(defaultRollback = true)
public class QbQuestionTest {

	@Autowired
	private IQbQuestionService qbQuestionService;

	@Test
	public void checkCache() {
		System.out.println("First");
		qbQuestionService.get(10000000100001L);

		System.out.println("Second");
		qbQuestionService.get(10000000100001L);

		qbQuestionService.evict();

		System.out.println("Third");
		qbQuestionService.get(10000000100001L);
	}
	
	@Test
	public void checkCacheEnum() {
		System.out.println("First");
		qbQuestionService.get(0, "1", EnumKey.A);

		System.out.println("Second");
		qbQuestionService.get(1, "2", EnumKey.B);
		
		qbQuestionService.get(0, "1", EnumKey.A);
		qbQuestionService.get(1, "2", EnumKey.B);
	}

}
