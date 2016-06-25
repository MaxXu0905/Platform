package com.ailk.sets.platform.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * QbDifficultyLevel entity. @author MyEclipse Persistence Tools
 */

public class QbDifficultyLevel implements java.io.Serializable {
	@Override
	public String toString() {
		return "QbDifficultyLevel [difficultyHigh=" + difficultyHigh + ", difficultyLow=" + difficultyLow + ", id="
				+ id + ", levelName=" + levelName + "]";
	}

	private static final long serialVersionUID = -1903189143530047620L;
	private QbDifficultyLevelId id;
	private String levelName;
	private Integer difficultyLow;
	private Integer difficultyHigh;

	// Constructors

	/** default constructor */
	public QbDifficultyLevel() {
	}

	/** minimal constructor */
	public QbDifficultyLevel(QbDifficultyLevelId id) {
		this.id = id;
	}

	/** full constructor */
	public QbDifficultyLevel(QbDifficultyLevelId id, String levelName, Integer difficultyLow, Integer difficultyHigh) {
		this.id = id;
		this.levelName = levelName;
		this.difficultyLow = difficultyLow;
		this.difficultyHigh = difficultyHigh;
	}

	// Property accessors

	public QbDifficultyLevelId getId() {
		return this.id;
	}

	public void setId(QbDifficultyLevelId id) {
		this.id = id;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getDifficultyLow() {
		return this.difficultyLow;
	}

	public void setDifficultyLow(Integer difficultyLow) {
		this.difficultyLow = difficultyLow;
	}

	public Integer getDifficultyHigh() {
		return this.difficultyHigh;
	}

	public void setDifficultyHigh(Integer difficultyHigh) {
		this.difficultyHigh = difficultyHigh;
	}

	public List<Integer> getPositinLevels(){
		List<Integer> list = new ArrayList<Integer>();
		for(int i = difficultyLow; i<= difficultyHigh; i++){
			list.add(i);
		}
		return list;
	}
}