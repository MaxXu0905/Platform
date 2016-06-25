package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.paper.PaperPart;

public interface IPaperPartDao extends IBaseDao<PaperPart> {
  public List<PaperPart> getPaperPartsByPaperId(int paperId); 
}
