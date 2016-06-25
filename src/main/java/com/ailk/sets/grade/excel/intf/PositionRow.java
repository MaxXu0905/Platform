package com.ailk.sets.grade.excel.intf;

import java.util.HashMap;
import java.util.Map;

public class PositionRow implements Comparable<PositionRow> {

	public static final int STATE_PASS = 0; // 推荐
	public static final int STATE_PENDING = 1; // 待定
	public static final int STATE_REJECT = 2; // 淘汰

	private String name; // 姓名
	private String email; // 邮箱
	private String phone; // 手机号
	private int stateForSort; // 排序用筛选状态
	private String state; // 筛选状态
	private Map<String, String> interviewResultMap; // 面试结果
	private double score; // 测评得分
	private int rank; // 测评得分排名
	private Map<String, Double> choiceMap; // 选择题分项
	private Map<String, Double> programMap; // 编程题分项
	private Map<String, Double> techEssayMap; // 技能问答题分项
	private Double intelScore; // 智力得分
	private Integer intelRank; // 智力得分排名
	private Double intelChoiceScore; // 智力选择题
	private Double intelEssayScore; // 智力问答题
	private Double interviewScore; // 面试得分
	private Integer interviewRank; // 面试得分排名
	private Map<String, Double> interviewScoreMap; // 面试分项
	private Map<String, String> extInfoMap; // 用户扩展信息
	private String duration; // 答卷时长
	private String beginTime; // 开始时间
	private String endTime; // 交卷时间

	public PositionRow() {
		interviewResultMap = new HashMap<String, String>();
		choiceMap = new HashMap<String, Double>();
		programMap = new HashMap<String, Double>();
		techEssayMap = new HashMap<String, Double>();
		interviewScoreMap = new HashMap<String, Double>();
		extInfoMap = new HashMap<String, String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStateForSort() {
		return stateForSort;
	}

	public void setStateForSort(int stateForSort) {
		this.stateForSort = stateForSort;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Map<String, String> getInterviewResultMap() {
		return interviewResultMap;
	}

	public void setInterviewResultMap(Map<String, String> interviewResultMap) {
		this.interviewResultMap = interviewResultMap;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Map<String, Double> getChoiceMap() {
		return choiceMap;
	}

	public void setChoiceMap(Map<String, Double> choiceMap) {
		this.choiceMap = choiceMap;
	}

	public Map<String, Double> getProgramMap() {
		return programMap;
	}

	public void setProgramMap(Map<String, Double> programMap) {
		this.programMap = programMap;
	}

	public Map<String, Double> getTechEssayMap() {
		return techEssayMap;
	}

	public void setTechEssayMap(Map<String, Double> techEssayMap) {
		this.techEssayMap = techEssayMap;
	}

	public Double getIntelScore() {
		return intelScore;
	}

	public void setIntelScore(Double intelScore) {
		this.intelScore = intelScore;
	}

	public Integer getIntelRank() {
		return intelRank;
	}

	public void setIntelRank(Integer intelRank) {
		this.intelRank = intelRank;
	}

	public Double getIntelChoiceScore() {
		return intelChoiceScore;
	}

	public void setIntelChoiceScore(Double intelChoiceScore) {
		this.intelChoiceScore = intelChoiceScore;
	}

	public Double getIntelEssayScore() {
		return intelEssayScore;
	}

	public void setIntelEssayScore(Double intelEssayScore) {
		this.intelEssayScore = intelEssayScore;
	}

	public Double getInterviewScore() {
		return interviewScore;
	}

	public void setInterviewScore(Double interviewScore) {
		this.interviewScore = interviewScore;
	}

	public Integer getInterviewRank() {
		return interviewRank;
	}

	public void setInterviewRank(Integer interviewRank) {
		this.interviewRank = interviewRank;
	}

	public Map<String, Double> getInterviewScoreMap() {
		return interviewScoreMap;
	}

	public void setInterviewScoreMap(Map<String, Double> interviewScoreMap) {
		this.interviewScoreMap = interviewScoreMap;
	}

	public Map<String, String> getExtInfoMap() {
		return extInfoMap;
	}

	public void setExtInfoMap(Map<String, String> extInfoMap) {
		this.extInfoMap = extInfoMap;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public int compareTo(PositionRow o) {
		if (stateForSort < o.stateForSort)
			return -1;
		else if (stateForSort > o.stateForSort)
			return 1;

		if (score < o.score)
			return 1;
		else if (score > o.score)
			return -1;

		if (o.interviewScore == null) {
			if (interviewScore == null)
				return 0;
			else
				return -1;
		} else if (interviewScore == null) {
			return 1;
		} else if (interviewScore < o.interviewScore) {
			return 1;
		} else {
			return -1;
		}
	}

}
