package com.ailk.sets.grade.qb;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;
import com.ailk.sets.grade.utils.QuestionUtils;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionSkillDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
public class DumpBankTest {

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IQbQuestionDetailDao qbQuestionDetailDao;

	@Autowired
	private IQbQuestionSkillDao qbQuestionSkillDao;

	@Autowired
	private IQbSkillDao qbSkillDao;

	private static final Gson gson = new Gson();

	@Test
	public void run() throws IOException {
//		String[] qbData = { "lvys@syan.com.cn", "100000356",
//				"BOOR-HR-2014-JAVA中级程序员面试-笔试题目", "gejianqing@boornet.com",
//				"100000363", "Java高级测评", "gejianqing@boornet.com", "100000364",
//				"java", "gejianqing@boornet.com", "100000365", "Java中级测评",
//				"an_chunjing@topsec.com.cn", "100000667", "j",
//				"fangw@njhhsoft.com", "100000753", "java",
//				"fangw@njhhsoft.com", "100000758", "java",
//				"kongrongbingling@live.cn", "100000803", "android",
//				"xiaoning@hmammon.com", "100000834", "安卓初级",
//				"yangqing@sinosoft.com.cn", "100000881", "java题库",
//				"lif@591woo.com", "100001102", "android",
//				"ryan@beijingonline.com.cn", "100001105", "2015年春季校招测评",
//				"ryan@beijingonline.com.cn", "100001106", "2015年春季校招测评",
//				"liyipeng@hn.chinamobile.com", "100001212", "营业员培训考试",
//				"liyipeng@hn.chinamobile.com", "100001213", "2015年春季校招测评",
//				"liyipeng@hn.chinamobile.com", "100001217",
//				"衡阳移动2015年两节促销培训考试试卷 （渠道经理篇）", "liyipeng@hn.chinamobile.com",
//				"100001218", "衡阳移动2015年两节促销培训考试试卷 （渠道经理篇）",
//				"echo.yuan@winfo.com.cn", "100001223", "2015年春季校招测评",
//				"echo.yuan@winfo.com.cn", "100001224", "2015年春季校招测评",
//				"echo.yuan@winfo.com.cn", "100001225", "2015年春季校招测评",
//				"echo.yuan@winfo.com.cn", "100001226", "a",
//				"zhangxuan@tarena.com.cn", "100001229", "java",
//				"zhangxuan@tarena.com.cn", "100001230", "UI",
//				"zhaoxd@tarena.com.cn", "100001233", "we",
//				"zhangxuan@tarena.com.cn", "100001236", "ui" };
		String[] qbData = { "zhangsy5@asiainfo.com", "100001041", "鹏博士文员类题目",
				"zhangsy5@asiainfo.com", "100001042", "张少毅的题库",
				"yujf@btte.net", "100001118", "于金凤的题库"
		};

		for (int i = 0; i < qbData.length; i += 3) {
			String email = qbData[i];
			int qbId = Integer.parseInt(qbData[i + 1]);
			String qbName = qbData[i + 2];

			InputStream in = new FileInputStream(
					"/Users/xugq/Downloads/bank.xls");
			Workbook workBook = new HSSFWorkbook(in);

			Sheet sheetChoice = workBook.getSheetAt(0);
			Sheet sheetProgram = workBook.getSheetAt(1);
			Sheet sheetEssay = workBook.getSheetAt(2);
			Sheet sheetVideo = workBook.getSheetAt(3);
			int choiceRows = 1;
			int programRows = 1;
			int essayRows = 1;
			int videoRows = 1;
			Set<String> titleSet = new HashSet<String>();

			List<QbQuestion> qbQuestions = qbQuestionDao
					.getList("FROM QbQuestion WHERE qbId = " + qbId
							+ " ORDER BY questionId");
			for (QbQuestion qbQuestion : qbQuestions) {
				if (titleSet.contains(qbQuestion.getQuestionDesc()))
					continue;
				titleSet.add(qbQuestion.getQuestionDesc());

				QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao
						.get(qbQuestion.getQuestionId());
				QuestionConf questionConf = null;
				Map<String, String> data = null;
				if (qbQuestionDetail != null) {
					QuestionContent questionContent = gson.fromJson(
							qbQuestionDetail.getContent(),
							QuestionContent.class);
					questionConf = questionContent.getQuestionConf();
					data = questionContent.getData();
				}

				String skillName = null;
				List<String> skillIds = qbQuestionSkillDao
						.getSkillIds(qbQuestion.getQuestionId());
				if (!CollectionUtils.isEmpty(skillIds)) {
					QbSkill qbSkill = qbSkillDao.getEntity(skillIds.get(0));
					if (qbSkill != null)
						skillName = qbSkill.getSkillName();
				}

				int questionType = GradeConst.toQuestionTypeInt(qbQuestion
						.getQuestionType());
				switch (questionType) {
				case GradeConst.QUESTION_TYPE_S_CHOICE:
				case GradeConst.QUESTION_TYPE_M_CHOICE:
				case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
				case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS: {
					Row row = sheetChoice.createRow(choiceRows++);
					switch (questionType) {
					case GradeConst.QUESTION_TYPE_S_CHOICE:
						createCell(row, 0, "单选");
						break;
					case GradeConst.QUESTION_TYPE_M_CHOICE:
						createCell(row, 0, "多选");
						break;
					case GradeConst.QUESTION_TYPE_S_CHOICE_PLUS:
						createCell(row, 0, "单选问答");
						break;
					case GradeConst.QUESTION_TYPE_M_CHOICE_PLUS:
						createCell(row, 0, "多选问答");
						break;
					}
					createCell(row, 1, questionConf.getTitle());
					List<String> options = questionConf.getOptions();
					for (int j = 0; j < options.size(); j++)
						createCell(row, j + 2, questionConf.getOptions().get(j));
					createCell(row, 7,
							QuestionUtils.getAnswers(questionConf.getAnswer()));
					createCell(row, 8, questionConf.getOptDesc());
					createCell(row, 9, skillName);
					if (qbQuestion.getDegree() <= 2)
						createCell(row, 10, "低难度");
					else if (qbQuestion.getDegree() <= 4)
						createCell(row, 10, "中难度");
					else
						createCell(row, 10, "高难度");
					break;
				}
				case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM: {
					Row row = sheetProgram.createRow(programRows++);
					createCell(row, 0, questionConf.getTitle());
					createCell(row, 1, data.get("/reference/com/ailk/代码"));
					createCell(row, 2, questionConf.getMode());
					createCell(row, 3, skillName);
					if (qbQuestion.getDegree() <= 2)
						createCell(row, 4, "低难度");
					else if (qbQuestion.getDegree() <= 4)
						createCell(row, 4, "中难度");
					else
						createCell(row, 4, "高难度");
					break;
				}
				case GradeConst.QUESTION_TYPE_ESSAY: {
					Row row = sheetEssay.createRow(essayRows++);
					createCell(row, 0, questionConf.getTitle());
					createCell(row, 1, data.get("/reference/com/ailk/代码"));
					createCell(row, 2, skillName);
					if (qbQuestion.getDegree() <= 2)
						createCell(row, 3, "低难度");
					else if (qbQuestion.getDegree() <= 4)
						createCell(row, 3, "中难度");
					else
						createCell(row, 3, "高难度");
					break;
				}
				case GradeConst.QUESTION_TYPE_VIDEO: {
					Row row = sheetVideo.createRow(videoRows++);
					createCell(row, 0, qbQuestion.getQuestionDesc());
					createCell(row, 2, skillName);
					if (qbQuestion.getDegree() <= 2)
						createCell(row, 2, "低难度");
					else if (qbQuestion.getDegree() <= 4)
						createCell(row, 2, "中难度");
					else
						createCell(row, 2, "高难度");
					break;
				}
				default:
					break;
				}
			}

			if (choiceRows > 1 || programRows > 1 || essayRows > 1
					|| videoRows > 1) {
				OutputStream out = new FileOutputStream(
						"/Users/xugq/Downloads/bank/" + email + "-" + qbName
								+ "-" + qbId + ".xls");
				workBook.write(out);
			}
		}
	}

	private void createCell(Row row, int column, String value) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
	}

}
