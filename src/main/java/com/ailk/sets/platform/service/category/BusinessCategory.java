package com.ailk.sets.platform.service.category;

import java.util.ArrayList;
import java.util.List;

import com.ailk.sets.grade.intf.GetQInfoResponse;
import com.ailk.sets.grade.intf.GetQInfoResponse.Matrix;
import com.ailk.sets.grade.intf.GetQInfoResponse.MatrixItem;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.domain.EmployerPosHistory;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.Question;
import com.ailk.sets.platform.intf.model.param.RandomQuestionParam;
import com.ailk.sets.platform.intf.model.question.GenQuestion;

public class BusinessCategory implements Category {

	private IQbQuestionDao qbQuestionDao;
	private IGradeService gradeService;

	public BusinessCategory(IQbQuestionDao qbQuestionDao,
			IGradeService gradeService) {
		this.qbQuestionDao = qbQuestionDao;
		this.gradeService = gradeService;
	}

	@Override
	public String getHistoryIdSuffix(Position pos) {
		return null;
	}

	@Override
	public List<Question> getHistory(List<EmployerPosHistory> list) {
		List<Question> result = new ArrayList<Question>();
		for (EmployerPosHistory ep : list) {
			QbQuestion question = qbQuestionDao.getEntity(
					Long.parseLong(ep.getId().getHistoryId()), "questionId");
			if (question != null)
				result.add(fillData(question, question.getSuggestTime()));
		}
		return result;
	}

	@Override
	public Question getRandom(RandomQuestionParam param) {
		QbQuestion question = qbQuestionDao.getRandomQuestion(param
				.getCategory());
		if (question != null)
			return fillData(question, question.getSuggestTime());
		else
			return null;
	}

	private Question fillData(QbQuestion question, int suggestTime) {
		try {
			GetQInfoResponse response = gradeService.getQInfo(question
					.getQuestionId());
			GenQuestion genQuestion = new GenQuestion();
			genQuestion.setQuestionId(question.getQuestionId());
			genQuestion.setQuestionType(question.getQuestionType());
			genQuestion.setTime(suggestTime);
			genQuestion.setqDesc(response.getTitle());
			Matrix m = response.getMatrix();
			if (m != null && m.getItems() != null && m.getItems().size() > 0) {
				MatrixItem item = m.getItems().get(0);
				genQuestion.setaDesc(item.getTemplate());
			}
			return genQuestion;
		} catch (Exception e) {
			return null;
		}
	}
}
