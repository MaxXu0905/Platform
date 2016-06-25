package com.ailk.sets.platform.intf.model.invatition;

import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;

public class SchoolInvitationInfo extends PFResponse {

	private static final long serialVersionUID = -1794260454994100084L;
	private CandidateTest candidateTest;
	private Invitation invitation;
	private CompanyRecruitActivity activity;

	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

	public CompanyRecruitActivity getActivity() {
		return activity;
	}

	public void setActivity(CompanyRecruitActivity activity) {
		this.activity = activity;
	}

	public CandidateTest getCandidateTest() {
		return candidateTest;
	}

	public void setCandidateTest(CandidateTest candidateTest) {
		this.candidateTest = candidateTest;
	}

}
