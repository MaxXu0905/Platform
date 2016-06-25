package com.ailk.sets.grade.main;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IAnalyseDao;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.jdbc.Analyse;
import com.ailk.sets.grade.jdbc.AnalysePK;
import com.sun.mail.util.LineInputStream;

@Transactional(rollbackFor = Exception.class)
@Service
public class AnalyseLoad {

	private static final Logger logger = Logger.getLogger(AnalyseLoad.class);

	@Autowired
	private IAnalyseDao analyseDao;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/spring/beans.xml", "/spring/localbean.xml" });
		context.start();

		AnalyseLoad instance = context.getBean(AnalyseLoad.class);

		try {
			File dir = new File("/Users/xugq/Downloads/logs");
			File[] files = dir.listFiles();

			for (File file : files) {
				System.out.println("加载：" + file.getAbsolutePath());

				Set<Analyse> analyses = new HashSet<Analyse>();
				int rows = 0;
				LineInputStream in = new LineInputStream(new FileInputStream(
						file));
				String line;

				while ((line = in.readLine()) != null) {
					int pos = line.indexOf("logger watcher");
					if (pos == -1)
						continue;

					String[] fields = line.substring(
							pos + "logger watcher".length()).split(",");

					Analyse analyse = new Analyse();
					AnalysePK analysePK = new AnalysePK();
					analysePK.setType(Integer.parseInt(fields[0]));
					analysePK.setUrl(fields[1]);
					analysePK.setSessionId(fields[2]);
					analysePK.setTimestamp(Long.parseLong(fields[3]));
					analyse.setAnalysePK(analysePK);

					analyses.add(analyse);
					rows++;

					if (analyses.size() == 100) {
						instance.loadLines(analyses);
						analyses.clear();
						System.out.println(rows);
					}
				}

				if (!analyses.isEmpty()) {
					instance.loadLines(analyses);
					System.out.println(rows);
				}
			}

			System.exit(0);
		} catch (Throwable e) {
			logger.error(TraceManager.getTrace(e));
			System.exit(1);
		}
	}

	public void loadLines(Set<Analyse> analyses) throws Exception {
		for (Analyse analyse : analyses) {
			analyseDao.saveOrUpdate(analyse);
		}
	}

}
