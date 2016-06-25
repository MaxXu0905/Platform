package com.ailk.sets.grade;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.intf.ILoadService;
import com.ailk.sets.grade.intf.LoadWordResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional(rollbackFor = Exception.class)
public class WordGeneratorTest {

	@Autowired
	private ILoadService loadService;

	@Test
	public void loadPaperWord() {
		InputStream in;
		try {
			in = new FileInputStream("/Users/xugq/Downloads/2014年亚信联创校园招聘-A卷 - JAVA技能.docx");
			byte[] bytes = IOUtils.toByteArray(in);

			LoadWordResponse response = loadService.loadPaperWord(0, 1, bytes);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
