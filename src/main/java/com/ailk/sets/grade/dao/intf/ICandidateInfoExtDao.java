package com.ailk.sets.grade.dao.intf;

import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;

public interface ICandidateInfoExtDao {

	public Map<String, String> getMap(int candidateId);

	
	public Map<Integer, Map<String, CandidateInfoExt>> getCandidateInfoExts(List<Integer> candidateIds);
}
