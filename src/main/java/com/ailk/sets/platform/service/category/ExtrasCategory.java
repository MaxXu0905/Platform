package com.ailk.sets.platform.service.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.domain.EmployerPosHistory;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.model.Question;
import com.ailk.sets.platform.intf.model.param.RandomQuestionParam;
import com.ailk.sets.platform.service.local.IPositionService;

public class ExtrasCategory implements Category {

	private IPositionService positionService;
	@Autowired
	private IPositionSeriesDao seriesDao;

	public ExtrasCategory(IPositionService positionService) {
		this.positionService = positionService;
	}

	@Override
	public String getHistoryIdSuffix(Position pos) {
		return "_" + seriesDao.getEntity(pos.getSeriesId()).getPositionLanguage() + "_" + pos.getLevel();
	}

	@Override
	public List<Question> getHistory(List<EmployerPosHistory> list) {
		if (CollectionUtils.isEmpty(list))
			return null;
		else {
			List<Question> result = new ArrayList<Question>();
			for (EmployerPosHistory eh : list) {
				String historyId = eh.getId().getHistoryId();
				historyId = historyId.substring(0, historyId.indexOf("_"));
				result.add(positionService.getExtrQuestionsByExtraId(Long.parseLong(historyId)));
			}
			return result;
		}
	}

	@Override
	public Question getRandom(RandomQuestionParam param) {
		/*List<Extras> list = positionService.getExtrQuestionsByAnalyis(param.getPosition(), param.getSkillIds());
		if (list != null && list.size() > 0) {
			return PaperCreateUtils.randomQuestions(list);
		}*/
		return null;
	}

}
