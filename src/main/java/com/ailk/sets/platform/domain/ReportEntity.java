package com.ailk.sets.platform.domain;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.grade.intf.report.OverallItem;

/**
 * 报告邮件实体类
 * 
 * @author 毕希研
 * 
 */
public class ReportEntity implements Serializable {
	private static final long serialVersionUID = 6591070515911525127L;

	private String hrName;

	private String employerName;

	private String candidateName;
	private String candidateEmail;

	private String seriesName;
	private String level;

	private Double score;
	private String portrait;

	private String reportUrl;
	private String path = "report";
	private List<OverallItem> items;

	private Integer hasPortrait ; //是否有头像- - ；1-有; 0-你猜;
	private String testPositionName; // 自定义的职位名称
	
	public List<OverallItem> getItems() {
		return items;
	}

	public void setItems(List<OverallItem> items) {
		this.items = items;
	}

	public String getPosition() {
		return seriesName + level + "工程师";// positionName;
	}

	public String getSubject() {
		return candidateName + "【" + candidateEmail + "】已通过" + getTestPositionName() + "测评，请处理";
	}

	public String getRecommendSubject() {
		return hrName + "推荐" + candidateName + "【" + candidateEmail + "】参加" + getTestPositionName() + "面试，请处理";
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public void setHrName(String hrName) {
		this.hrName = hrName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getPath() {
		return path;
	}


    public Integer getHasPortrait()
    {
        return hasPortrait;
    }

    public void setHasPortrait(Integer hasPortrait)
    {
        this.hasPortrait = hasPortrait;
    }

    public String getTestPositionName()
    {
        return testPositionName;
    }

    public void setTestPositionName(String testPositionName)
    {
        this.testPositionName = testPositionName;
    }

    public String getSeriesName()
    {
        return seriesName;
    }

    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
	
}
