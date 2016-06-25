package com.ailk.sets.platform.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.domain.paper.CandidateTestPartId;

public class PaperCreateUtilsTest {
	@Test
	public void sortPaperInstancePartByPartSeq(){
		List<CandidateTestPart> parts = new ArrayList<CandidateTestPart>();
		
		CandidateTestPart part1 = new CandidateTestPart();
		CandidateTestPartId id1 = new CandidateTestPartId(1L, 21);
		part1.setId(id1);
		
		CandidateTestPart part2 = new CandidateTestPart();
		CandidateTestPartId id2 = new CandidateTestPartId(1L, 22);
		part2.setId(id2);
		
		CandidateTestPart part3 = new CandidateTestPart();
		CandidateTestPartId id3 = new CandidateTestPartId(1L, 1);
		part3.setId(id3);
		
		CandidateTestPart part4 = new CandidateTestPart();
		CandidateTestPartId id4 = new CandidateTestPartId(1L, 3);
		part4.setId(id4);
		
		CandidateTestPart part5 = new CandidateTestPart();
		CandidateTestPartId id5 = new CandidateTestPartId(1L, 2);
		part5.setId(id5);
		
		
		CandidateTestPart part6 = new CandidateTestPart();
		CandidateTestPartId id6 = new CandidateTestPartId(1L, 23);
		part6.setId(id6);
		
		parts.add(part1);
		parts.add(part2);
		parts.add(part3);
		parts.add(part4);
		parts.add(part5);
		parts.add(part6);
		PaperCreateUtils.sortPaperInstancePartByPartSeq(parts);
		for(CandidateTestPart p : parts){
			System.out.println(p.getId().getPartSeq());
		}
		
		
	}
	
}
