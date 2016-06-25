package com.ailk.sets.platform.empl.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.platform.intf.empl.service.IQbBase;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.param.GetQbBasesParam;
import com.ailk.sets.platform.intf.model.param.GetQbGroupsParam;
import com.ailk.sets.platform.intf.model.param.GetQbQuestionsParam;
import com.ailk.sets.platform.intf.model.param.SearchCondition;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.ailk.sets.platform.intf.model.qb.QbBaseInfo;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/localbean.xml","/spring/beans.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
public class QbBaseImplTest {

	private static final Gson gson = new Gson();

	private static final int CREATE_BY = 100000057;

	@Autowired
	private IQbBase qbBaseService;

	@Test
	public void createQbBase() throws Exception {
		QbBase qbBase = new QbBase();
		qbBase.setQbName("qbName");
		qbBase.setQbDesc("qbDesc");
		qbBase.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
		qbBase.setCreateBy(CREATE_BY);

		System.out.println(gson.toJson(qbBase));
		System.out.println(gson.toJson(qbBaseService.createQbBase(qbBase)));
	}

	@Test
	public void createQbBaseByDefault() throws Exception {
		QbBase qbBase = new QbBase();
		qbBase.setQbName("qbName");
		qbBase.setQbDesc("qbDesc");
		qbBase.setCreateBy(CREATE_BY);

		System.out.println(gson.toJson(qbBase));
		System.out.println(gson.toJson(qbBaseService.createQbBase(qbBase)));
	}

	@Test
	public void getQbBases() throws Exception {
		GetQbBasesParam param = new GetQbBasesParam();
		param.setEmployerId(100000068);
		param.setPage(new Page(20, 1));
		long time1 = System.currentTimeMillis();
		List<QbBaseInfo> list = qbBaseService.getQbBases(param);
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1);
		System.out.println(list);
	}

	@Test
	public void getQbGroups() throws Exception {
		GetQbGroupsParam param = new GetQbGroupsParam();
		param.setQbId(100000084);
		param.setPage(new Page(10, 1));
		
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setQuestionDesc("zj");
		searchCondition.setModifyDateAsc(false);
		searchCondition.setAnswerNumAsc(false);
		searchCondition.setSuggestTimeAsc(false);
		searchCondition.setNegNumAsc(false);
		param.setSearchCondition(searchCondition);

		System.out.println(gson.toJson(param));
		System.out.println(gson.toJson(qbBaseService.getQbGroups(100000068, param)));
	}
	
	@Test
	public void getQbQuestions() throws Exception {
		GetQbQuestionsParam param = new GetQbQuestionsParam();
		param.setQbId(100000019);
		param.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
		param.setPage(new Page(10, 1));
		
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setChoice(true);
		searchCondition.setQuestionDesc("zj");
		searchCondition.setModifyDateAsc(false);
		searchCondition.setAvgScoreAsc(false);
		searchCondition.setAnswerNumAsc(false);
		searchCondition.setSuggestTimeAsc(false);
		searchCondition.setNegNumAsc(false);
		param.setSearchCondition(searchCondition);

		System.out.println(gson.toJson(param));
		System.out.println(gson.toJson(qbBaseService.getQbQuestions(100000068, param)));
		System.out.println(111);
	}

	
	@Test
	public void getQbBasesCount() throws Exception {
		GetQbBasesParam param = new GetQbBasesParam();
		param.setEmployerId(100000068);
		long time1 = System.currentTimeMillis();
		int a = qbBaseService.getQbBasesCount(param).getCount();
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1);
		System.out.println(a);
	}
	
	@Test
	public void getQbQuestionsCount() throws Exception {
		GetQbQuestionsParam param = new GetQbQuestionsParam();
		param.setQbId(100000019);
		param.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
		
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setChoice(true);
		searchCondition.setQuestionDesc("zj");
		/*searchCondition.setModifyDateAsc(false);
		searchCondition.setAvgScoreAsc(false);
		searchCondition.setAnswerNumAsc(false);
		searchCondition.setSuggestTimeAsc(false);
		searchCondition.setNegNumAsc(false);*/
		param.setSearchCondition(searchCondition);

		System.out.println(gson.toJson(param));
		System.out.println(gson.toJson(qbBaseService.getQbQuestionsCount(100000068, param)));
		System.out.println("-==============");
	}
}
