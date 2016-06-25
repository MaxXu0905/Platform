package com.ailk.sets.grade.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.sets.grade.service.IAliyunService;

@Service
public class WordServiceImpl implements IWordService {
	
	@Autowired
	private IAliyunService aliyunService;

	@Override
	public PaperWord execute(XWPFDocument doc) {
		IWord word = new WordImpl(aliyunService);

		return word.execute(doc);
	}

}
