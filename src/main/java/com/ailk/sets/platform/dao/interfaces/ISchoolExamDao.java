package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.SchoolPaperSkillCount;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;

public interface ISchoolExamDao {
//	public List<SchoolPaperSkillCount> getSchoolPaperSkillCounts(Invitation invitation);
//	 public List<SchoolPaperSkillCount> getSchoolPaperSkillCounts(List<Long> questionIds,int skillId);
	 
	 public List<Invitation> getInvitations(CompanyRecruitActivity acti,Candidate candidate,Integer positionId);
}
