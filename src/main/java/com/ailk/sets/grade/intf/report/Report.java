package com.ailk.sets.grade.intf.report;

import java.util.List;
import java.util.Map;

import com.ailk.sets.grade.intf.BaseResponse;

@SuppressWarnings("serial")
public class Report extends BaseResponse {

	private String title; // 报告标题
	private int level; // 等级
	private long reportTime; // 报告生成时间
	private long beginTime; // 考试开始时间
	private long endTime; // 考试结束时间
	private Summary summary; // 概述
	private List<OverallItem> overallItems; // 全面概述
	private Completion completion; // 完整度
	private List<Video> videos; // 视频
	private Interview interview; // 面试信息
	private List<Part> parts; // 部分
	private List<String> abnormalUrls; // 异常URLS
	private Map<Long, Integer> reportIndexMap; // 题归属的报告索引

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getReportTime() {
		return reportTime;
	}

	public void setReportTime(long reportTime) {
		this.reportTime = reportTime;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public List<OverallItem> getOverallItems() {
		return overallItems;
	}

	public void setOverallItems(List<OverallItem> overallItems) {
		this.overallItems = overallItems;
	}

	public Completion getCompletion() {
		return completion;
	}

	public void setCompletion(Completion completion) {
		this.completion = completion;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	public Map<Long, Integer> getReportIndexMap() {
		return reportIndexMap;
	}

	public void setReportIndexMap(Map<Long, Integer> reportIndexMap) {
		this.reportIndexMap = reportIndexMap;
	}

	public List<String> getAbnormalUrls() {
		return abnormalUrls;
	}

	public void setAbnormalUrls(List<String> abnormalUrls) {
		this.abnormalUrls = abnormalUrls;
	}

}
