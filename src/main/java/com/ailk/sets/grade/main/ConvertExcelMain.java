package com.ailk.sets.grade.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.grade.excel.ConvertExcel;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.intf.LoadResponse;

public class ConvertExcelMain {

	private static final Logger logger = Logger
			.getLogger(ConvertExcelMain.class);

	private static String globalFilePrefix = null;

	public static void main(String[] args) {
		if (args.length > 0)
			globalFilePrefix = args[0];

		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "/spring/beans.xml", "/spring/localbean.xml" });
			context.start();

			ConvertExcel instance = context.getBean(ConvertExcel.class);

			List<String> paths = getPaths("/Users/xugq/Documents/QuestionBank");

			for (String path : paths) {
				logger.info("系统开始转换文件：" + path);

				File file = new File(path);
				String[] fields = file.getName().split("\\-");
				if (fields.length == 0 || fields[0].length() != 10) {
					logger.error("文件名没有以-分隔，或长度不正确，" + file.getName());
					return;
				}

				InputStream in = null;
				try {
					long fileBeginId = Long.parseLong(fields[0])
							* GradeConst.MAX_FILE_ROWS;

					in = new FileInputStream(file);
					LoadResponse response = instance.loadFile(in, fileBeginId,
							GradeConst.CREATE_BY_SYS, null, 0, false,
							GradeConst.TEST_TYPE_COMMUNITY, true);
					if (response.getErrorCode() != 0)
						logger.error(response.getErrorDesc());
				} catch (NumberFormatException e) {
					logger.error("文件名前10位不是数字，" + file.getName(), e);
					continue;
				} catch (Throwable e) {
					logger.error(TraceManager.getTrace(e));
					continue;
				} finally {
					try {
						in.close();
					} catch (Exception e) {
					}
				}

				logger.info("系统结束转换文件：" + path);
			}

			logger.info("系统转换文件完成");
			System.exit(0);
		} catch (Throwable e) {
			logger.error(TraceManager.getTrace(e));
			System.exit(1);
		}
	}

	private static List<String> getPaths(String dir) {
		File dirFile = new File(dir);
		List<String> paths = new ArrayList<String>();

		for (File file : dirFile.listFiles()) {
			if (file.getName().startsWith(".")
					|| file.getName().startsWith("~$"))
				continue;

			if (globalFilePrefix != null
					&& !file.getName().startsWith(globalFilePrefix))
				continue;

			paths.add(file.getAbsolutePath());
		}

		return paths;
	}

}
