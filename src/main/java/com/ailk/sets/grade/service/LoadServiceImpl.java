package com.ailk.sets.grade.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICampusTestPlanDao;
import com.ailk.sets.grade.dao.intf.ICandidateReportDao;
import com.ailk.sets.grade.dao.intf.IQbUploadErrorDao;
import com.ailk.sets.grade.excel.intf.IConvertExcel;
import com.ailk.sets.grade.excel.intf.IExportExcel;
import com.ailk.sets.grade.excel.intf.ITalkExcel;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.ExportErrorQbResponse;
import com.ailk.sets.grade.intf.ExportPositionResponse;
import com.ailk.sets.grade.intf.ExportQbResponse;
import com.ailk.sets.grade.intf.ExportReportResponse;
import com.ailk.sets.grade.intf.GetGroupResponse;
import com.ailk.sets.grade.intf.GetQuestionResponse;
import com.ailk.sets.grade.intf.GetSuggestTimeRequest;
import com.ailk.sets.grade.intf.GetSuggestTimeResponse;
import com.ailk.sets.grade.intf.GetTestPlanRequest;
import com.ailk.sets.grade.intf.GetTestPlanResponse;
import com.ailk.sets.grade.intf.HasErrorQbResponse;
import com.ailk.sets.grade.intf.ILoadService;
import com.ailk.sets.grade.intf.LoadRequest;
import com.ailk.sets.grade.intf.LoadResponse;
import com.ailk.sets.grade.intf.LoadTalksResponse;
import com.ailk.sets.grade.intf.LoadWordResponse;
import com.ailk.sets.grade.intf.LoadWordResponse.PartInfo;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.jdbc.CampusTestPlan;
import com.ailk.sets.grade.jdbc.QbUploadError;
import com.ailk.sets.grade.word.IWordGenerator;
import com.ailk.sets.grade.word.IWordService;
import com.ailk.sets.grade.word.PaperWord;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.service.IQbBase;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.google.gson.Gson;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.PngImage;

@Transactional(rollbackFor = Exception.class)
@Service
public class LoadServiceImpl implements ILoadService {

	@Autowired
	private IConvertExcel convertExcel;

	@Autowired
	private IExportExcel exportExcel;

	@Autowired
	private IQbBaseDao qbBaseDao;

	@Autowired
	private IQbUploadErrorDao qbUploadErrorDao;

	@Autowired
	private ITalkExcel talkExcel;

	@Autowired
	private ICampusTestPlanDao campusTestPlanDao;

	@Autowired
	private ICandidateTestDao candidateTestDao;

	@Autowired
	private ICandidateReportDao candidateReportDao;

	@Autowired
	private IWordService wordService;

	@Autowired
	private IWordGenerator wordGenerator;
	@Autowired
	private IQbBase qbBaseImpl;

	@Value("${phantomjs.root}")
	private String phantomjsRoot;

	private String PHANTOMJS_ROOT;

	private static final Logger logger = Logger
			.getLogger(LoadServiceImpl.class);
	private static final Gson gson = new Gson();
	private static final int MARGIN = 20;

	@PostConstruct
	public void init() {
		PHANTOMJS_ROOT = System.getenv("PHANTOMJS_ROOT");
	}

	@Override
	public GetSuggestTimeResponse getSuggestTime(GetSuggestTimeRequest request) {
		GetSuggestTimeResponse response = new GetSuggestTimeResponse();

		response.setSuggestTime(convertExcel.getSuggestTime(request.getTitle(),
				request.getOptions()));
		return response;
	}

	@Override
	public LoadResponse loadFile(int createBy, int qbId,
			double similarityLimit, boolean checkTime, byte[] data)
			throws Exception {
		ByteArrayInputStream in = null;

		try {
			in = new ByteArrayInputStream(data);

			return convertExcel.loadFile(in, null, createBy, qbId,
					similarityLimit, checkTime, GradeConst.TEST_TYPE_COMMUNITY,
					false);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public LoadResponse loadPaper(int createBy, String qbName,
			boolean checkTime, int testType, byte[] data) throws Exception {
		ByteArrayInputStream in = null;

		try {
			in = new ByteArrayInputStream(data);

			// 创建题库
			QbBase qbBase = new QbBase();
			qbBase.setQbId(qbBaseDao.getUIDFromBase(Constants.QB_ID).intValue());
			qbBase.setQbName(qbName);
			qbBase.setQbDesc(null);
			qbBase.setCategory(GradeConst.CATEGORY_PAPER);
			qbBase.setCreateBy(createBy);
			qbBase.setCreateDate(new Timestamp(System.currentTimeMillis()));
			qbBase.setModifyDate(qbBase.getCreateDate());
			qbBase.setPrebuilt(0);
			qbBaseDao.save(qbBase);

			LoadResponse response = convertExcel.loadFile(in, null, createBy,
					qbBase.getQbId(), 0.0, checkTime, testType, false);
			response.setQbId(qbBase.getQbId());
			response.setTypeInfos(qbBaseImpl
					.getPaperQuestionTypeInfoByQbId(qbBase.getQbId()));
			return response;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public LoadWordResponse loadPaperWord(int createBy, int testType,
			byte[] data) throws Exception {
		ByteArrayInputStream in = null;

		try {
			in = new ByteArrayInputStream(data);
			LoadWordResponse response = new LoadWordResponse();
			XWPFDocument doc = new XWPFDocument(in);
			PaperWord paperWord = wordService.execute(doc);

			if (paperWord.getErrors() > 0) {
				File file = File.createTempFile("XWPF", ".docx");
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(file);
					doc.write(out);
					response.setFilename(file.getAbsolutePath());
				} finally {
					if (out != null)
						out.close();
				}
			} else {
				response.setQbId(wordGenerator.generate(paperWord, createBy));
			}

			response.setPaperName(paperWord.getPaperName());
			response.setErrors(paperWord.getErrors());

			if (paperWord.getParts() != null) {
				List<PartInfo> partInfos = new ArrayList<LoadWordResponse.PartInfo>();
				for (PaperWord.Part part : paperWord.getParts()) {
					PartInfo partInfo = new PartInfo();
					partInfo.setPartName(part.getPartName());
					partInfo.setQuestions(part.getQuestions());
					partInfos.add(partInfo);
				}
				response.setPartInfos(partInfos);
			}

			return response;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public byte[] downloadPaperWord(String filename) throws Exception {
		InputStream in = null;
		try {
			in = new FileInputStream(filename);
			return IOUtils.toByteArray(in);
		} catch (Exception e) {
			return null;
		} finally {
			if (in != null)
				in.close();
		}
	}

	@Override
	public LoadResponse loadQuestions(int createBy, int qbId,
			Set<Long> skipQids, double similarityLimit, boolean checkTime,
			LoadRequest request) throws Exception {
		logger.debug("loadQuestions  createBy " + createBy + ", request is "
				+ request.toString());
		return convertExcel.loadQuestions(createBy, qbId, skipQids,
				similarityLimit, checkTime, request);
	}

	@Override
	public ExportQbResponse exportQb(int createBy, int qbId) throws Exception {
		ExportQbResponse response = new ExportQbResponse();

		QbBase qbBase = qbBaseDao.getEntity(qbId);
		if (qbBase == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到自定义题库，qbId=" + qbId);
			return response;
		}

		if (qbBase.getPrebuilt() == 1) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("预定义题库不允许导出，qbId=" + qbId);
			return response;
		}

		if (qbBase.getCreateBy() != createBy) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("本题库不属于创建者，createBy= " + createBy + ", qbId="
					+ qbId);
			return response;
		}

		byte[] data = exportExcel.exportExcel(createBy, qbId,
				qbBase.getCategory(), false);
		response.setQbName(qbBase.getQbName());
		response.setData(data);
		return response;
	}

	@Override
	public HasErrorQbResponse hasErrorQb(int qbId) throws Exception {
		HasErrorQbResponse response = new HasErrorQbResponse();

		response.setEmpty(exportExcel.isEmpty(qbId));
		return response;
	}

	@Override
	public BaseResponse deleteErrorQuestion(int serialNo) throws Exception {
		logger.debug("deleteErrorQuestion by serialNo " + serialNo);
		BaseResponse response = new BaseResponse();

		QbUploadError qbUploadError = new QbUploadError();
		qbUploadError.setSerialNo(serialNo);
		qbUploadErrorDao.delete(qbUploadError);
		return response;
	}

	@Override
	public LoadResponse getErrorQb(int createBy, int qbId) throws Exception {
		return exportExcel.getErrorQb(createBy, qbId);
	}

	@Override
	public ExportErrorQbResponse exportErrorQb(int createBy, int qbId)
			throws Exception {
		ExportErrorQbResponse response = new ExportErrorQbResponse();

		QbBase qbBase = qbBaseDao.getEntity(qbId);
		if (qbBase == null) {
			response.setErrorCode(BaseResponse.ENOENT);
			response.setErrorDesc("找不到自定义题库，qbId=" + qbId);
			return response;
		}

		if (qbBase.getPrebuilt() == 1) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("预定义题库不允许导出，qbId=" + qbId);
			return response;
		}

		if (qbBase.getCreateBy() != createBy) {
			response.setErrorCode(BaseResponse.EPERM);
			response.setErrorDesc("本题库不属于创建者，createBy= " + createBy + ", qbId="
					+ qbId);
			return response;
		}

		byte[] data = exportExcel.exportErrorExcel(createBy, qbId, false);
		response.setQbName(qbBase.getQbName());
		response.setData(data);
		return response;
	}

	@Override
	public GetQuestionResponse getQuestion(long questionId) {
		return exportExcel.getQuestion(questionId);
	}

	@Override
	public GetGroupResponse getGroup(long groupId) {
		return exportExcel.getGroup(groupId);
	}

	@Override
	public LoadTalksResponse loadTalks(byte[] data) throws Exception {
		ByteArrayInputStream in = null;

		try {
			in = new ByteArrayInputStream(data);

			return talkExcel.loadTalks(in, false);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public GetTestPlanResponse getTestPlan(GetTestPlanRequest request) {
		logger.info("deviceId=" + request.getDeviceId() + ", longitude="
				+ request.getLongitude() + ", latitude="
				+ request.getLatitude() + ", stats=" + request.getStats());

		GetTestPlanResponse response = new GetTestPlanResponse();

		try {
			CampusTestPlan campusTestPlan = campusTestPlanDao.getEntity(request
					.getDeviceId());
			if (campusTestPlan == null) {
				campusTestPlan = new CampusTestPlan();
				campusTestPlan.setDeviceId(request.getDeviceId());
				campusTestPlan.setBaseUrl(null);
				campusTestPlan.setPrefix("00");
				campusTestPlan.setExecuteDate(new Timestamp(System
						.currentTimeMillis()));
				campusTestPlan.setCheckInterval(0);
				campusTestPlan.setParallels(0);
				campusTestPlan.setWaitTime(0);
				campusTestPlan.setWeixinId(null);
				campusTestPlan.setWifiEnabled(false);
				campusTestPlan.setLongitude(0.0);
				campusTestPlan.setLatitude(0.0);
				campusTestPlan.setDistance(0.0);
				campusTestPlan.setState(0);
				campusTestPlan.setStats(request.getStats());
				campusTestPlan.setCreateDate(new Timestamp(System
						.currentTimeMillis()));
				campusTestPlan.setActiveDate(campusTestPlan.getCreateDate());
				campusTestPlan.setNetworkType(request.getNetworkType());
				campusTestPlan.setPhoneType(request.getPhoneType());
				campusTestPlan.setSubscriberId(request.getSubscriberId());
				campusTestPlan.setModel(request.getModel());
				campusTestPlan.setRelease(request.getRelease());
				campusTestPlan
						.setSysMobileEnabled(request.isSysMobileEnabled());
				campusTestPlan.setSysWifiEnabled(request.isSysWifiEnabled());
				campusTestPlan.setSysLongitude(request.getLongitude());
				campusTestPlan.setSysLatitude(request.getLatitude());
				campusTestPlan.setIpAddress(request.getIpAddress());
				campusTestPlan.setJobNumber(request.getJobNumber());
				campusTestPlanDao.save(campusTestPlan);
			} else {
				if (request.getStats() != null)
					campusTestPlan.setStats(request.getStats());
				campusTestPlan.setActiveDate(new Timestamp(System
						.currentTimeMillis()));
				campusTestPlan.setNetworkType(request.getNetworkType());
				campusTestPlan.setPhoneType(request.getPhoneType());
				campusTestPlan.setSubscriberId(request.getSubscriberId());
				campusTestPlan.setModel(request.getModel());
				campusTestPlan.setRelease(request.getRelease());
				campusTestPlan
						.setSysMobileEnabled(request.isSysMobileEnabled());
				campusTestPlan.setSysWifiEnabled(request.isSysWifiEnabled());
				campusTestPlan.setSysLongitude(request.getLongitude());
				campusTestPlan.setSysLatitude(request.getLatitude());
				campusTestPlan.setIpAddress(request.getIpAddress());
				campusTestPlan.setJobNumber(request.getJobNumber());
				campusTestPlanDao.update(campusTestPlan);
			}

			if (campusTestPlan.getState() == 0) {
				response.setErrorCode(BaseResponse.ENOENT);
				return response;
			}

			response.setBaseUrl(campusTestPlan.getBaseUrl());
			response.setPrefix(campusTestPlan.getPrefix());
			response.setDelay(campusTestPlan.getExecuteDate().getTime()
					- System.currentTimeMillis());
			response.setCheckInterval(campusTestPlan.getCheckInterval());
			if (response.getDelay() > response.getCheckInterval() + 60000)
				response.setDelay(0);
			response.setParallels(campusTestPlan.getParallels());
			response.setWaitTime(campusTestPlan.getWaitTime());
			response.setWeixinId(campusTestPlan.getWeixinId());
			response.setWifiEnabled(campusTestPlan.isWifiEnabled());
			response.setLongitude(campusTestPlan.getLongitude());
			response.setLatitude(campusTestPlan.getLatitude());
			response.setDistance(campusTestPlan.getDistance());
			return response;
		} catch (Exception e) {
			logger.error(TraceManager.getTrace(e));
			response.setErrorCode(BaseResponse.ESYSTEM);
			return response;
		}
	}

	@Override
	public ExportReportResponse exportReport(long testId, String passport,
			String baseUrl) {
		ExportReportResponse response = new ExportReportResponse();
		File pngFile = null;

		try {
			if (PHANTOMJS_ROOT == null) {
				response.setErrorCode(BaseResponse.ESYSTEM);
				response.setErrorDesc("系统环境不具备，testId=" + testId);
				return response;
			}

			CandidateTest candidateTest = candidateTestDao.getEntity(testId);
			if (candidateTest == null) {
				response.setErrorCode(BaseResponse.ENOENT);
				response.setErrorDesc("找不到测试报告，testId=" + testId);
				return response;
			}

			CandidateReport candidateReport = candidateReportDao.get(testId);
			if (candidateReport == null) {
				response.setErrorCode(BaseResponse.ENOENT);
				response.setErrorDesc("找不到测试报告，testId=" + testId);
				return response;
			}

			if (passport != null
					&& !passport.equals(candidateReport.getReportPassport())) {
				response.setErrorCode(BaseResponse.EPERM);
				response.setErrorDesc("通行证不正确，testId=" + testId);
				return response;
			}

			GetReportResponse report = gson.fromJson(
					candidateReport.getContent(), GetReportResponse.class);
			response.setTitle(report.getTitle());

			pngFile = File.createTempFile("report", ".png");

			String urlPrefix;
			if (phantomjsRoot != null && !phantomjsRoot.isEmpty()) {
				int index = baseUrl.indexOf("://");
				if (index == -1)
					index = baseUrl.indexOf("/");
				else
					index = baseUrl.indexOf("/", index + 3);

				if (index == -1)
					urlPrefix = phantomjsRoot;
				else
					urlPrefix = phantomjsRoot + baseUrl.substring(index);
			} else {
				urlPrefix = baseUrl;
			}
            logger.debug("urlPrefix is " + urlPrefix);
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(
					PHANTOMJS_ROOT + "/bin/phantomjs",
					PHANTOMJS_ROOT + "/examples/rasterize.js",
					urlPrefix + "/sets/page/goReportForPdf/"
							+ candidateTest.getPositionId() + "/" + testId
							+ "/" + candidateReport.getReportPassport(),
					pngFile.getAbsolutePath());

			Process process = builder.start();
			try {
				process.waitFor();
			} finally {
				process.destroy();
			}

			if (process.exitValue() != 0) {
				response.setErrorCode(BaseResponse.ESYSTEM);
				response.setErrorDesc("系统运行环境异常，testId=" + testId);
				return response;
			}

			Document doc = new Document(PageSize.A4, MARGIN, MARGIN, MARGIN,
					MARGIN);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			PdfWriter.getInstance(doc, bout);
			doc.open();
			doc.addAuthor("101test.com");
			doc.addTitle(report.getTitle());

			BufferedImage bufferedImage = ImageIO.read(new FileInputStream(
					pngFile));
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			float percent = (PageSize.A4.getWidth() - MARGIN * 2) / width * 100;
			int pageHeigth = (int) ((PageSize.A4.getHeight() - MARGIN * 2)
					/ percent * 100);

			for (int y = 0; y < height; y += pageHeigth) {
				ByteArrayOutputStream subimageOut = new ByteArrayOutputStream();
				ImageIO.write(
						bufferedImage.getSubimage(0, y, width,
								Math.min(pageHeigth, height - y)), "png",
						subimageOut);

				Image image = PngImage.getImage(new ByteArrayInputStream(
						subimageOut.toByteArray()));
				image.setAlignment(Image.MIDDLE);
				image.scalePercent(percent);

				doc.newPage();
				doc.add(image);
			}

			doc.close();

			response.setData(bout.toByteArray());
			return response;
		} catch (Exception e) {
			logger.error(TraceManager.getTrace(e));
			response.setErrorCode(BaseResponse.ESYSTEM);
			return response;
		} finally {
			if (pngFile != null)
				pngFile.delete();
		}
	}

	@Override
	public ExportPositionResponse exportPosition(GetReportParam param) {
		try {
			return exportExcel.exportPosition(param);
		} catch (Exception e) {
			logger.error(TraceManager.getTrace(e));
			ExportPositionResponse response = new ExportPositionResponse();
			response.setErrorCode(BaseResponse.ESYSTEM);
			return response;
		}
	}

}
