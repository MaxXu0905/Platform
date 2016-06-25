package com.ailk.sets.platform.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

//这就是一个文档向量,Document的实现
public class DocumentImpl implements Document {

	private String content;// 文档内容
	private IKSegmentation ikSegmentation;// 分词器
	private Logger logger = Logger.getLogger("DocumentIpmlLogger");
	private Map<String, Integer> dfreq;

	public DocumentImpl(String cont) {
		this.content = cont;
	}

	public Map<String, Integer> documentFreq() {
		if (dfreq == null || dfreq.isEmpty()) {
			dfreq = segment();
			return dfreq;
		}
		return dfreq;
	}

	public Map<String, Integer> segment() {
		if (this.content == null || content.isEmpty()) {
			logger.log(Level.WARNING, "document content can not be empty");
			return null;
		}

		if (ikSegmentation == null)
			ikSegmentation = new IKSegmentation(new StringReader(content), true);
		else
			ikSegmentation.reset(new StringReader(content));

		Lexeme lexeme = null;
		Map<String, Integer> mapfreq = new HashMap<String, Integer>();

		try {
			// System.out.println();
			while ((lexeme = ikSegmentation.next()) != null) {
				// String str = lexeme.getLexemeText();
				// System.out.print(str+"\t");
				if (!mapfreq.containsKey(lexeme.getLexemeText())) {
					mapfreq.put(lexeme.getLexemeText(), 1);
					continue;
				}

				int freq = mapfreq.get(lexeme.getLexemeText());
				mapfreq.put(lexeme.getLexemeText(), ++freq);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
			return null;
		}

		return mapfreq;
	}

	public String toString() {
		return content;
	}

}