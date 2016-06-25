package com.ailk.sets.platform.domain;

import java.io.Serializable;

public class PassEmailEntity implements Serializable {

	private static final long serialVersionUID = 1105637203182841141L;

	private String seriesName;
	private String level;
	private String companyName;
	private String candidateName;
	private String path = "passemail";
	private String testPositionName;

	public String getSubject() {
		return "您暂时不符合"+companyName+testPositionName+"的要求";
	}

	public String getPosition() {
		return seriesName + level + "工程师";// positionName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getPath() {
		return path;
	}

    public String getSeriesName()
    {
        return seriesName;
    }

    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public String getTestPositionName()
    {
        return testPositionName;
    }

    public void setTestPositionName(String testPositionName)
    {
        this.testPositionName = testPositionName;
    }

}
