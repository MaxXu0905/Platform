package com.ailk.sets.grade.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.excel.ExportExcel;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.intf.ExportErrorQbResponse;
import com.ailk.sets.grade.intf.ExportPositionResponse;
import com.ailk.sets.grade.intf.ExportReportResponse;
import com.ailk.sets.grade.intf.GetGroupResponse;
import com.ailk.sets.grade.intf.GetQuestionResponse;
import com.ailk.sets.grade.intf.GetSuggestTimeRequest;
import com.ailk.sets.grade.intf.ILoadService;
import com.ailk.sets.grade.intf.LoadConst;
import com.ailk.sets.grade.intf.LoadRequest;
import com.ailk.sets.grade.intf.LoadResponse;
import com.ailk.sets.grade.intf.LoadRow;
import com.ailk.sets.grade.intf.LoadTalksResponse;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.condition.Interval;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
public class LoadServiceTest {

	private static final Gson gson = new Gson();

	@Autowired
	private ILoadService loadService;

	@Autowired
	private ExportExcel exportExcel;

	@Test
	public void getSuggestTime() {
		GetSuggestTimeRequest request = new GetSuggestTimeRequest();
		request.setTitle("title");

		List<String> options = new ArrayList<String>();
		options.add("option1");
		options.add("option2");
		request.setOptions(options);

		System.out.println(gson.toJson(request));
		System.out.println(gson.toJson(loadService.getSuggestTime(request)));
	}

	@Test
	public void loadFile() throws Exception {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(new File(
					"/Users/xugq/Downloads/技能题库模版.xls"));
			out = new ByteArrayOutputStream();
			IOUtils.copy(in, out);

			LoadResponse response = loadService.loadFile(100000068, 100000019,
					0.8, true, out.toByteArray());
			System.out.println(gson.toJson(response));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}

			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void loadPaper() throws Exception {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(new File(
					"D:\\亚信2014秋季校招题目-A卷-百一.xls"));
			out = new ByteArrayOutputStream();
			IOUtils.copy(in, out);

			LoadResponse response = loadService.loadPaper(100000120, "ttttttt", true, 2,  out.toByteArray());
			System.out.println(gson.toJson(response));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}

			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void loadQuestion() throws Exception {
		List<LoadRow> rows = new ArrayList<LoadRow>();
		LoadRow row = new LoadRow();
		rows.add(row);

		row.setTitle("aaa条件语句开始的关键字是？");

		List<String> options = new ArrayList<String>();
		row.setOptions(options);

		options.add("ifaaa");
		options.add("whilebbb");
		options.add("docccc");

		row.setCorrectOptions("AB");
		row.setSuggestSeconds("30");
		row.setSkill("低难度");
		row.setLevel("低难度");
		row.setExplainReqired("是");
		row.setRefExplain("why");

		LoadRequest request = new LoadRequest();
		request.setSheetType(LoadConst.SHEET_TYPE_INTEL_CHOICE);
		request.setRows(rows);

		LoadResponse response = loadService.loadQuestions(100000114, 100000325,
				null, 0.8, true, request);

		System.out.println(gson.toJson(response));
	}

	@Test
	public void exportExcel() {
		int[] categories = new int[] { GradeConst.CATEGORY_TECHNOLOGY,
				GradeConst.CATEGORY_INTELLIGENCE,
				GradeConst.CATEGORY_INTERVIEW, GradeConst.CATEGORY_PAPER };
		for (int i = 0; i < categories.length; i++) {
			OutputStream out = null;
			try {
				byte[] bytes = exportExcel.exportExcel(100000068, 100000019,
						categories[i], false);

				out = new FileOutputStream(new File(
						"/Users/xugq/Downloads/export-" + categories[i]
								+ ".xls"));
				out.write(bytes);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	@Test
	public void exportErrorQb(){

		OutputStream out = null;
		try {
			ExportErrorQbResponse rs = loadService.exportErrorQb(100001216, 100000626);

			out = new FileOutputStream(new File("D:\\100000626" + ".xls"));
			out.write(rs.getData());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	
	}
	@Test
	public void exportPaper() {
		int paperId = 799;
		OutputStream out = null;
		try {
			byte[] bytes = exportExcel.exportPaper(paperId, false);

			out = new FileOutputStream(new File("/Users/xugq/Downloads/"
					+ paperId + ".xls"));
			out.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void isEmpty() {
		System.out.println(exportExcel.isEmpty(100000084));
	}

	@Test
	public void exportErrorExcel() {
		OutputStream out = null;
		try {
			byte[] bytes = exportExcel.exportErrorExcel(100000068, 100000019,
					false);

			out = new FileOutputStream(new File(
					"/Users/xugq/Downloads/export-error.xls"));
			out.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void getGroup() {
		GetGroupResponse response = exportExcel.getGroup(100001153l);

		System.out.println(gson.toJson(response));
	}

	@Test
	public void getQuestion() {
		GetQuestionResponse response = exportExcel.getQuestion(100002786l);

		System.out.println(gson.toJson(response));
	}

	@Test
	public void loadTalks() throws Exception {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(
					new File("/Users/xugq/Downloads/宣讲会模版.xls"));
			out = new ByteArrayOutputStream();
			IOUtils.copy(in, out);

			LoadTalksResponse response = loadService.loadTalks(out
					.toByteArray());
			System.out.println(gson.toJson(response));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}

			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void exportPosition() throws Exception
	{
		GetReportParam param = new GetReportParam(1635 ,"0" , new Page(20, 1) ,new Interval(
				null, null));
//		param.setInputKey("钻石版");
//		param.setCommitPaperFromDate("2014-08-13");
//		param.setCommitPaperToDate("2014-08-13");
//		param.setPositionIntent("");
		param.setEmployerId(100000120);
//		param.setOrderByPositionId(1619);
		ExportPositionResponse res = loadService.exportPosition(param);
		OutputStream out = new FileOutputStream(new File(
				"D:\\dddd.xls"));
		out.write(res.getData());
	}

	@Test
	public void exportReport() throws Exception{
		ExportReportResponse rs = loadService.exportReport(127086, null, "http://124.207.3.10:28029/EmplPorta");
	    System.out.println(rs);
	}
}
