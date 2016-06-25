package com.ailk.sets.grade.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ailk.sets.grade.jdbc.Analyse;
import com.ailk.sets.grade.jdbc.AnalyseResult;
import com.ailk.sets.grade.jdbc.AnalyseResultPK;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
public class AnalyseTest {

	@Autowired
	private IAnalyseService analyseService;

	public static class Key implements Comparable<Key> {
		private String url;
		private String sessionId;
		private long timestamp;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		@Override
		public int compareTo(Key o) {
			int result = url.compareTo(o.url);
			if (result != 0)
				return result;

			result = sessionId.compareTo(o.sessionId);
			if (result != 0)
				return result;

			if (timestamp < o.timestamp)
				return -1;
			else if (timestamp > o.timestamp)
				return 1;
			else
				return 0;
		}
	}

	@Test
	public void execute() {
		TreeSet<Key> beginSet = new TreeSet<Key>();
		TreeSet<Key> endSet = new TreeSet<Key>();
		Map<String, String> emailMap = new HashMap<String, String>();

		List<Analyse> analyses = analyseService.getList();
		for (Analyse analyse : analyses) {
			Key key = new Key();
			key.setUrl(analyse.getAnalysePK().getUrl());
			key.setSessionId(analyse.getAnalysePK().getSessionId());
			key.setTimestamp(analyse.getAnalysePK().getTimestamp());

			switch (analyse.getAnalysePK().getType()) {
			case 1:
				beginSet.add(key);
				break;
			case 2:
				endSet.add(key);
				break;
			case 3:
				emailMap.put(key.getSessionId(), key.getUrl());
				break;
			}
		}

		List<AnalyseResult> analyseResults = new ArrayList<AnalyseResult>();
		int rows = 0;

		for (Key beginKey : beginSet) {
			Key endKey = endSet.ceiling(beginKey);
			if (endKey == null)
				continue;

			if (!beginKey.getUrl().equals(endKey.getUrl())
					|| !beginKey.getSessionId().equals(endKey.getSessionId()))
				continue;

			AnalyseResultPK analyseResultPK = new AnalyseResultPK();
			analyseResultPK.setUrl(beginKey.getUrl());
			analyseResultPK.setSessionId(beginKey.getSessionId());
			analyseResultPK.setBeginTimestamp(beginKey.getTimestamp());

			AnalyseResult analyseResult = new AnalyseResult();
			analyseResult.setAnalyseResultPK(analyseResultPK);

			analyseResult.setEmail(emailMap.get(beginKey.getSessionId()));
			analyseResult.setEndTimestamp(endKey.getTimestamp());
			analyseResult.setMillis(endKey.getTimestamp()
					- beginKey.getTimestamp());

			analyseResults.add(analyseResult);
			rows++;

			if (analyseResults.size() == 100) {
				analyseService.saveOrUpdate(analyseResults);
				analyseResults.clear();
				System.out.println(rows);
			}
		}

		if (!analyseResults.isEmpty()) {
			analyseService.saveOrUpdate(analyseResults);
			System.out.println(rows);
		}
	}

}
