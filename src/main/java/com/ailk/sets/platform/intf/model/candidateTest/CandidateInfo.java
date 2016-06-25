package com.ailk.sets.platform.intf.model.candidateTest;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;

public class CandidateInfo extends PFResponse {

	private static final long serialVersionUID = -1794260454994100084L;
	private CandidateTest candidateTest;
	private String candidateName;
	private String candidateDesc;

	public String getCandidateDesc() {
		return candidateDesc;
	}

	public void setCandidateDesc(String candidateDesc) {
		this.candidateDesc = candidateDesc;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public CandidateTest getCandidateTest() {
		return candidateTest;
	}

	public void setCandidateTest(CandidateTest candidateTest) {
		this.candidateTest = candidateTest;
	}

}
