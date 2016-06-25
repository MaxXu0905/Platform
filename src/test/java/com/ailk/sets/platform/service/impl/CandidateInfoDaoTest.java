package com.ailk.sets.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.dao.impl.CandidateInfoDaoImpl;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExtId;
import com.ailk.sets.platform.intf.cand.domain.InfoNeeded;
import com.ailk.sets.platform.intf.cand.service.ICandidateInfoService;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public class CandidateInfoDaoTest {

//	@Test
	public void testDao() throws PFDaoException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "/spring/beans.xml" });
		context.start();
		CandidateInfoDaoImpl config = (CandidateInfoDaoImpl) context.getBean("candidateInfoDaoImpl");
		List<PositionInfoExt> list = config.getCandConfigInfoExts(21,null);
		for (PositionInfoExt ce : list)
			System.out.println(ce.getId().getInfoId());
		System.out.println(list.get(0));
		System.out.println(list);
	}

	@Test
	public void testGetCandConfigInfoExts() throws PFServiceException
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/consumer.xml"});
        context.start();
        ICandidateInfoService candidateInfoService = (ICandidateInfoService)context.getBean("candidateInfoService");
        List<InfoNeeded> list = candidateInfoService.getCandConfigInfoExts(21);
        System.out.println(list.size());
	}
	
//	@Test
	public void testSaveCandidateInfo()
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spring/consumer.xml"});
        context.start();
        ICandidateInfoService candidateInfoService = (ICandidateInfoService)context.getBean("candidateInfoService");
        List<CandidateInfoExt> list = new ArrayList<CandidateInfoExt>();
        CandidateInfoExt cie = new CandidateInfoExt();
        CandidateInfoExtId id = new CandidateInfoExtId();
        id.setInfoId("EDUCATION");
        cie.setId(id);
        list.add(cie);
        cie = new CandidateInfoExt();
        id = new CandidateInfoExtId();
        id.setInfoId("PROFESSION");
        cie.setId(id);
        list.add(cie);
        cie = new CandidateInfoExt();
        id = new CandidateInfoExtId();
        id.setInfoId("WORK_INDUSTRY");
        cie.setId(id);
        list.add(cie);
//        candidateInfoService.saveCandidateInfo("毕希研", "bixy@asiainfo-linkage.com", list);
	}
}
