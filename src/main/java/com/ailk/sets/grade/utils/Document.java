package com.ailk.sets.grade.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import com.ailk.sets.grade.grade.common.TraceManager;

/**
 * 把文档转成向量
 * @author xugq
 *
 */
public class Document {

	private static final Logger logger = Logger.getLogger(Document.class);

	private Map<String, Integer> freqMap;

	public Document(String content) {
		segment(content);
	}
	
	public Map<String, Integer> getFreqMap() {
		return freqMap;
	}

	private Map<String, Integer> segment(String content) {
		IKSegmentation ikSegmentation = new IKSegmentation(new StringReader(content), true);
		Lexeme lexeme = null;
		freqMap = new HashMap<String, Integer>();

		try {
			while ((lexeme = ikSegmentation.next()) != null) {
				Integer freq = freqMap.get(lexeme.getLexemeText());
				if (freq == null)
					freqMap.put(lexeme.getLexemeText(), 1);
				else
					freqMap.put(lexeme.getLexemeText(), ++freq);
			}
		} catch (IOException e) {
			logger.error(TraceManager.getTrace(e));
			return null;
		}

		return freqMap;
	}

}