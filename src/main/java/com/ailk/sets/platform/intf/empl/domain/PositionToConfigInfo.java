package com.ailk.sets.platform.intf.empl.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class PositionToConfigInfo {
	public TreeMap<Double, Integer> rankMap = new TreeMap<Double, Integer>();
	public TreeMap<Double, Integer> intelRankMap = new TreeMap<Double, Integer>();
	public TreeMap<Double, Integer> interviewRankMap = new TreeMap<Double, Integer>();

	public  boolean  gottonInterviewMeta = false; // 是否获取了面试的元数据
	public  List<String> extInfos = new ArrayList<String>(); // 用户扩展信息，按照职位扩展信息中的顺序排序
	public   List<String> interviewResults = new ArrayList<String>(); // 面试结果，按照面试信息中的顺序排序
	public  List<String> interviewScores = new ArrayList<String>(); // 技能问答题分项，按照面试信息中的顺序排序
	public  Set<String> choiceSet = new TreeSet<String>(); // 选择题技能，按照字母顺序排序
	public  Set<String> programSet = new TreeSet<String>(); // 编程题分项，按照字母顺序排序
	public  Set<String> techEssaySet = new TreeSet<String>(); // 选择题技能，按照字母顺序排序
	public  Map<String, String> interviewMap = new HashMap<String, String>();  // 面试项信息映射（infoId
																				// =>
																				// infoName）
	
	
	
	public boolean containsIntel = false; // 是否有智力题
	public boolean containsInterviewResult = false; // 是否有面试结果
	public boolean containsInterviewScore = false; // 是否有面试得分
	public boolean containsExtInfo = false; // 是否有扩展信息
}
