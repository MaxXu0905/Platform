package com.ailk.sets.platform.intf.empl.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionGroupRow implements Comparable<PositionGroupRow> {

	public static final int STATE_PASS = 0; // 推荐
	public static final int STATE_PENDING = 1; // 待定
	public static final int STATE_REJECT = 2; // 淘汰

	private String name; // 姓名
	private String email; // 邮箱
	private String phone; // 手机号
	private Map<String, String> extInfoMap; // 用户扩展信息

	private Map<Integer,PositionGrpChildRow> positionToRows;
	/*private int stateForSort; // 排序用筛选状态
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
	private String duration; // 答卷时长
	private String beginTime; // 开始时间
	private String endTime; // 交卷时间
*/
	public PositionGroupRow() {
	/*	interviewResultMap = new HashMap<String, String>();
		choiceMap = new HashMap<String, Double>();
		programMap = new HashMap<String, Double>();
		techEssayMap = new HashMap<String, Double>();
		interviewScoreMap = new HashMap<String, Double>();*/
		extInfoMap = new HashMap<String, String>();
		positionToRows = new HashMap<Integer, PositionGrpChildRow>();
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


	public Map<String, String> getExtInfoMap() {
		return extInfoMap;
	}

	public void setExtInfoMap(Map<String, String> extInfoMap) {
		this.extInfoMap = extInfoMap;
	}


	public Map<Integer, PositionGrpChildRow> getPositionToRows() {
		return positionToRows;
	}

	public void setPositionToRows(Map<Integer, PositionGrpChildRow> positionToRows) {
		this.positionToRows = positionToRows;
	}

	@Override
	public int compareTo(PositionGroupRow o) {
		// TODO Auto-generated method stub
		return 0;
	}



}
