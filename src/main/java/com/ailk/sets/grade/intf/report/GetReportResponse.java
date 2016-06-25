package com.ailk.sets.grade.intf.report;

import java.util.List;

import com.ailk.sets.grade.intf.BaseResponse;

@SuppressWarnings("serial")
public class GetReportResponse extends BaseResponse {

	private Integer testResult; // 试卷状态，在获取报告时实时获取
	private boolean sample; // 是否为样例报告
	private String title; // 报告标题
	private long reportTime; // 报告生成时间
	private long beginTime; // 考试开始时间
	private long endTime; // 考试结束时间
	private Summary summary; // 概述
	private List<OverallItem> overallItems; // 全面概述
	private Completion completion; // 完整度
	private Interview interview; // 面试评分
	private List<Part> parts; // 部分
	private List<Video> videos; // 视频
	private List<String> abnormalUrls; // 异常URLS

	public GetReportResponse() {
	}

	public GetReportResponse(Report report) {
		title = report.getTitle();
		reportTime = report.getReportTime();
		beginTime = report.getBeginTime();
		endTime = report.getEndTime();
		summary = report.getSummary();
		overallItems = report.getOverallItems();
		completion = report.getCompletion();
		interview = report.getInterview();
		parts = report.getParts();
		videos = report.getVideos();
		abnormalUrls = report.getAbnormalUrls();
	}

	public Integer getTestResult() {
		return testResult;
	}

	public void setTestResult(Integer testResult) {
		this.testResult = testResult;
	}

	public boolean isSample() {
		return sample;
	}

	public void setSample(boolean sample) {
		this.sample = sample;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<String> getAbnormalUrls() {
		return abnormalUrls;
	}

	public void setAbnormalUrls(List<String> abnormalUrls) {
		this.abnormalUrls = abnormalUrls;
	}

}
