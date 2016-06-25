package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.PaperSkill;

public interface IPaperSkillDao  extends IBaseDao<PaperSkill>{

	public List<PaperSkill> getPaperSkills(int paperId);

	public List<PaperSkill> getList(int paperId);

}
