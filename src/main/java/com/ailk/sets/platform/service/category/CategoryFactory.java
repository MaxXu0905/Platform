package com.ailk.sets.platform.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;

@Service
public class CategoryFactory {

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IGradeService gradeService;

	public Category getInstance(String category) {
		/*if ("1".equals(category)) {
			return new ExtrasCategory(positionService);
		} else*/ 
		if ("2".equals(category)) {
			return new BusinessCategory(qbQuestionDao, gradeService);
		} else if ("3".equals(category)) {
			return new IntelligenceCategory(qbQuestionDao, gradeService);
		} else if ("4".equals(category)) {
			return new InterviewCategory(qbQuestionDao);
		}
		return null;
	}
}
