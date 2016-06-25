package com.ailk.sets.platform.service.category;

import java.util.List;

import com.ailk.sets.platform.domain.EmployerPosHistory;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Question;
import com.ailk.sets.platform.intf.model.param.RandomQuestionParam;

public interface Category {

	public String getHistoryIdSuffix(Position pos);

	public List<Question> getHistory(List<EmployerPosHistory> list) throws PFServiceException;

	public Question getRandom(RandomQuestionParam param) throws PFServiceException;

}
