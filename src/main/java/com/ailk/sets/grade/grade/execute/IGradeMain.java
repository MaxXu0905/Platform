package com.ailk.sets.grade.grade.execute;

import java.util.List;

public interface IGradeMain {
	
	public List<Long> getReadyList();

	public void gradeTest(long testId);

}
