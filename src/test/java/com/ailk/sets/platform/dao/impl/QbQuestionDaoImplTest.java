package com.ailk.sets.platform.dao.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

public class QbQuestionDaoImplTest {
	static ApplicationContext context;
	static {
		PropertyConfigurator.configure(QbQuestionDaoImplTest.class
				.getResource("/log4j.properties"));
		context = new ClassPathXmlApplicationContext(new String[] {
				"/spring/provider.xml", "/spring/consumer.xml",
				"/spring/beans.xml" });
	}

	@Test
	public void testGetSeq() {
	/*	QbNormalDaoImpl dao = (QbNormalDaoImpl) context
				.getBean("qbNoramlDaoImpl");
		System.out.println(dao.getNextSequence());*/
	}

	@Test
	public void testGetNormal() {
		QbNormalDaoImpl dao = (QbNormalDaoImpl) context
				.getBean("qbNoramlDaoImpl");
		// List<CandidateTestPart> list =
		// dao.getNoramlListBySqlWithJvmDate(//延迟5分钟提交 300秒
		// "select * from candidate_test_part where DATE_ADD(begin_time,INTERVAL suggest_time+300 SECOND) < ? and part_seq in (4,1) and part_state =  "
		// + PaperInstancePartStateEnum.ANSWERING.getValue(),
		// CandidateTestPart.class, Arrays.asList(new Date()));
		// System.out.println(list.size());

		/*String testObjectSql = "select * from qb_question_view where is_sample = 1 and subject_code = '100' and question_type  in('s_choice','m_choice') limit 1";
		List<QbQuestionView> objectQuestions = dao.getNoramlListBySql(
				testObjectSql, QbQuestionView.class);
		System.out.println(objectQuestions.size());*/
		// ExtrasHistoryId id = new ExtrasHistoryId(101l, 3, "100", 2);
		// ExtrasHistory extrasHistory = dao.getNormalObject(id,
		// ExtrasHistory.class);
		// System.out.println(extrasHistory.getFrequency());
	}

	@Test
	public void getQbQuestionById() throws Exception {
		IQbQuestionDao dao = (IQbQuestionDao) context
				.getBean(IQbQuestionDao.class);
		QbQuestion question = dao.getEntity(1l);
		System.out.println(question);
		QbQuestion qb = new QbQuestion(1l);
		qb.setQuestionType("aaa");
		qb.setQbId(1);

		dao.saveOrUpdate(qb);
		question = dao.getEntity(1l);
		System.out.println(question);

	}

}
