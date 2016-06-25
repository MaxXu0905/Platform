package com.ailk.sets.platform.service.local;

import java.util.List;

import com.ailk.sets.platform.domain.paper.CandidateTestPartId;
public interface IPaperHanderService {
	public List<CandidateTestPartId> getNeededHandInCandidateTestPart();

	public void processCandidateTestPart(CandidateTestPartId id);
	
	public List<Long>  getDataOutTest();
	
	public void processDateOutTest(long testId);
}
