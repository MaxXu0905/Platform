package com.ailk.sets.grade.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.CommitFile;
import com.ailk.sets.grade.intf.GetCandidateInfoResponse;
import com.ailk.sets.grade.intf.GetQInfoResponse;
import com.ailk.sets.grade.intf.GetQidsResponse;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.grade.intf.RunTestResponse;
import com.ailk.sets.grade.intf.report.GetComparedReportsResponse;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.intf.report.GetReportSummaryResponse;
import com.ailk.sets.grade.intf.report.Interview.InterviewItem;
import com.ailk.sets.grade.intf.report.InterviewInfo;
import com.ailk.sets.grade.intf.report.OverallItem;
import com.ailk.sets.grade.service.intf.IInterviewTemplateService;
import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.msg.SimpleMsg;
import com.ailk.sets.platform.intf.model.wx.msg.Text;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional(rollbackFor = Exception.class)
public class GradeServiceTest {

	private static final long TEST_ID = 107040125000357L;

	@Autowired
	private IGradeService gradeService;

	@Autowired
	private IInterviewTemplateService interviewTemplateService;

	@Autowired
	private IQbQuestionDao qbQuestionDao;
	
	@Autowired
	private ICandidateTestDao candidateTestDao;
	
    @Autowired
    private IConfigSysParamDao configSysParamDao;
    
    @Autowired
    private IPositionDao positionDao;
    
    @Autowired
    private ICandidateDao candidateDao;
    
	private static final Gson gson = new Gson();

	@Test
	public void checkEnv() {
		BaseResponse response = gradeService.chechEnv(TEST_ID);

		System.out.println("errorCode=" + response.getErrorCode());
		System.out.println("errorDesc=" + response.getErrorDesc());
	}

	@Test
	public void genExam() throws Exception {
		long[] qidsArray = { 107041012080001L, 107041012080002L,
				107041012080004L, 107041012080006L, 107041012080007L,
				107041012080009L, 107041012080010L, 107041012080011L,
				107041012080012L, 107041012080014L, 107041012080015L,
				107041012080016L, 107041012080017L, 107041012080018L,
				107041012080019L, 107041012080020L, 107041012080021L,
				107041012080022L, 107041012080023L, 107041012080024L,
				107041012080025L, 107041013080001L, 107041013080004L,
				107041013080005L, 107041013080006L, 107041013080007L,
				107041013080009L, 107041013080010L, 107041013080011L,
				107041013080012L, 107041013080013L, 107041013080014L,
				107041014080001L, 107041014080002L, 107041014080003L,
				107041014080004L, 107041014080005L, 107041014080006L,
				107041019080001L, 107041019080002L, 107041019080003L,
				107041019080004L, 107041019080005L, 107041019080006L,
				107041019080008L, 107041019080010L, 107041019080012L,
				107041019080013L, 107041019080014L, 107041019080015L,
				107041019080016L, 107041019080017L, 107041019080018L,
				107041019080020L, 107041019080021L, 107041019080022L };
		List<Long> qids = new ArrayList<Long>();
		for (long qid : qidsArray) {
			qids.add(qid);
		}

		BaseResponse response = gradeService.genExam(TEST_ID, qids);

		System.out.println("errorCode=" + response.getErrorCode());
		System.out.println("errorDesc=" + response.getErrorDesc());
	}

	@Test
	public void genExamForDerived() throws Exception {
		List<QbQuestion> qbQuestions = qbQuestionDao.getList();

		for (QbQuestion qbQuestion : qbQuestions) {
			if (qbQuestion.getQuestionDesc().contains("inputA")) {
				System.out.println(qbQuestion.getQuestionId());

				List<Long> qids = new ArrayList<Long>();
				qids.add(qbQuestion.getQuestionId());

				BaseResponse response = gradeService.genExam(TEST_ID, qids);

				System.out.println("errorCode=" + response.getErrorCode());
				System.out.println("errorDesc=" + response.getErrorDesc());
			}
		}

	}

	@Test
	public void getQids() throws Exception {
		GetQidsResponse response = gradeService.getQids(TEST_ID);

		System.out.println(gson.toJson(response));
	}

	@Test
	public void getTestQInfo() throws Exception {
		long[] qids = { 107040000000096L, 107040009080001L };

		for (long qid : qids) {
			GetQInfoResponse response = gradeService.getQInfo(TEST_ID, 100000000006974L);

			System.out.println(gson.toJson(response));
		}
	}

	@Test
	public void getQInfo() throws Exception {
		long[] qids = { 107049009000003L };

		for (long qid : qids) {
			GetQInfoResponse response = gradeService.getQInfo(qid);

			System.out.println(gson.toJson(response));
		}
	}

	@Test
	public void commitFiles() throws Exception {
		List<CommitFile> items = new ArrayList<CommitFile>();

		CommitFile item = new CommitFile();
		item.setFilename("com/ailk/TBase1.java");
		item.setContent("XXX");
		items.add(item);

		item = new CommitFile();
		item.setFilename("com/ailk/TBase2.java");
		item.setContent("XXX");
		items.add(item);

		BaseResponse response = gradeService.commitFiles(TEST_ID,
				107040130000144L, items);

		System.out.println("errorCode=" + response.getErrorCode());
		System.out.println("errorDesc=" + response.getErrorDesc());
	}

	@Test
	public void commitChoice() throws Exception {
		BaseResponse response = gradeService.commitChoice(TEST_ID,
				107040130000144L, "ABC", null);

		System.out.println("errorCode=" + response.getErrorCode());
		System.out.println("errorDesc=" + response.getErrorDesc());
	}

	@Test
	public void runTest() throws Exception {
		long[] qids = { 107040009080001L };

		long timestamp = System.currentTimeMillis();
		for (long qid : qids) {
			List<CommitFile> items = new ArrayList<CommitFile>();
			CommitFile file = new CommitFile();
			file.setFilename("/com/ailk/StringUtils.java");
			file.setContent("package com.ailk;\n\npublic class StringUtils {\n\n\t/**\n\t * 把参数原样输出到控制台（标准输出)\n\t * \u003cp\u003e\n\t * 请把以下代码复制到指定位置\n\t * \u003cp\u003e\n\t * System.out.println(str);\n\t * \n\t * @param str\n\t *            待输出的字符串\n\t */\n\tpublic static void println(String str) throws Exception {\n\t\t// 请在此添加代码\n\t\t// BEGIN\n\t\tSystem.out.println(str);\n\t\t// END\n\t}\n\n}\n");
			items.add(file);
			RunTestResponse response = gradeService.runTest(TEST_ID, qid,
					items, 0);

			System.out.println("errorCode=" + response.getErrorCode());
			System.out.println("errorDesc=" + response.getErrorDesc());
			System.out.println("exitValue=" + response.getExitValue());
			System.out.println("out=" + response.getOut());
			System.out.println("err=" + response.getErr());
		}
		System.out.println("millis=" + (System.currentTimeMillis() - timestamp));
	}

	@Test
	public void runTestSample() throws Exception {
		long[] qids = { 107041009080001L };

		long timestamp = System.currentTimeMillis();
		for (long qid : qids) {
			RunTestResponse response = gradeService.runTest(2231, qid, null,
					0);

			System.out.println("errorCode=" + response.getErrorCode());
			System.out.println("errorDesc=" + response.getErrorDesc());
			System.out.println("exitValue=" + response.getExitValue());
			System.out.println("out=" + response.getOut());
			System.out.println("err=" + response.getErr());
		}
		System.out.println(System.currentTimeMillis() - timestamp);
	}

	@Test
	public void runTestByArg() throws Exception {
		RunTestResponse response = gradeService.runTest(TEST_ID,
				107040009080001L, null, "你好");

		System.out.println("errorCode=" + response.getErrorCode());
		System.out.println("errorDesc=" + response.getErrorDesc());
		System.out.println("exitValue=" + response.getExitValue());
		System.out.println("out=" + response.getOut());
		System.out.println("err=" + response.getErr());
	}

	@Test
	public void getReport() throws Exception {
		GetReportResponse response = gradeService.getReport(126635);

		Gson gson = new Gson();
		System.out.println(gson.toJson(response));
	}

	@Test
	public void getReportSummary() throws Exception {
	    
	    int testId = 9543;
		GetReportSummaryResponse response = gradeService.getReportSummary(testId);
		Gson gson = new Gson();
		System.out.println(gson.toJson(response));

		CandidateTest candidateTest = candidateTestDao.getEntity(testId);
        Position position = positionDao.getEntity(candidateTest.getPositionId());
		
        Candidate candidate = candidateDao.getEntity(candidateTest
                .getCandidateId());
        if (StringUtils.isNotEmpty(candidate.getOpenId())) {
            WXCommunicator wxCommunicator = new WXCommunicator(
                    HttpClientUtil.getHttpClient());
            String token = wxCommunicator
                    .getAccessToken(
                            configSysParamDao
                                    .getConfigParamValue(ConfigSysParam.EMPLOYERAPPID),
                            configSysParamDao
                                    .getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
            String content = configSysParamDao
                    .getConfigParamValue(ConfigSysParam.EMPLOYERGETSCORE);

            GetReportSummaryResponse grsr = gradeService
                    .getReportSummary(testId);
            StringBuilder scoreDetail = new StringBuilder();
            for (OverallItem item : grsr.getItems())
            {
                scoreDetail.append(item.getName() + ": ")
                           .append(item.getScore() == null ? "未打" : item.getScore())
                           .append("分\n");
            }
            content = MessageFormat.format(
                    content,
                    candidate.getCandidateName(),
                    position.getPositionName(), 
                    1000,
                    scoreDetail.toString());
            // 封装消息对象
            SimpleMsg msg = new SimpleMsg(candidate.getOpenId(), SimpleMsg.MSG_TYPE_TEXT ,new Text(content));
            Gson gs = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
            wxCommunicator.sendMessage(gs.toJson(msg), token);
        }
	}

	@Test
	public void getComparedReport() throws Exception {
		List<Long> testIds = new ArrayList<Long>();
		testIds.add(1505L);
		testIds.add(1505L);
		testIds.add(1505L);

		GetComparedReportsResponse response = gradeService
				.getComparedReports(testIds);

		Gson gson = new Gson();
		System.out.println(gson.toJson(response));
	}

	@Test
	public void getInterviewInfo() {
		try {
			InterviewInfo interviewInfo = interviewTemplateService.load(1,
					GradeConst.TEST_TYPE_COMMUNITY, GradeConst.INTERVIEW_ID);

			Gson gson = new Gson();
			System.out.println(gson.toJson(interviewInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveInterview() throws Exception {
		List<InterviewItem> items = new ArrayList<InterviewItem>();
		InterviewItem item = new InterviewItem();
		item.setGroupId("groupId1");
		item.setInfoId("infoId1");
		item.setValue("value1");
		item.setRealValue("realValue1");
		items.add(item);

		item.setGroupId("groupId2");
		item.setInfoId("infoId2");
		item.setValue("value2");
		item.setRealValue("realValue2");
		items.add(item);

		BaseResponse response = gradeService.saveInterview(63, items);

		Gson gson = new Gson();
		System.out.println(gson.toJson(response));
	}

	@Test
	public void getCandidateInfo() throws Exception {
		GetCandidateInfoResponse response = gradeService.getCandidateInfo(27,
				100000032, -1);

		Gson gson = new Gson();
		System.out.println(gson.toJson(response));
	}

}
