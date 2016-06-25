/**
 * author :  lipan
 * filename :  PositionNew.java
 * create_time : 2014年6月26日 下午4:34:23
 */
package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.ReportStatusCountInfo;
import com.ailk.sets.platform.intf.model.position.PositionStatistics;

/**
 * 宣讲会相关联的测评信息
 * 
 * @author : lipan
 * @create_time : 2014年6月26日 下午4:34:23
 * @desc :
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class Position extends PositionStatistics implements Serializable
{

    private static final long serialVersionUID = -1787229315059150363L;
    public static final Integer WEIXIN_COMPANY_SELF = 1; // 使用自己微信号
    public static final Integer WEIXIN_COMPANY_101 = 0; // 使用百一测评的微信号
    public static final Integer TEST_TYPE_SOCIETY = 1; // 评测类型:1-社会招聘测评
    public static final Integer TEST_TYPE_CAMPUS = 2; // 评测类型:2-校园招聘测评
    public static final Integer NOTIFY_SCORE_YES = 1; // 是否通知考生分数：1-通知(默认)
    public static final Integer NOTIFY_SCORE_NO = 0; // 是否通知考生分数：0-不通知

    private Integer positionId; // 职位ID
    private String positionName; // 职位名称
    private String positionDesc; // 职位描述
    private Integer positionState; // 职位状态
    private Integer seriesId; // 职位序列
    private Integer level; // 职位级别
    private Integer employerId; // 发布人
    private Timestamp publishDate; // 发布时间
    private Timestamp modifyDate; //修改时间
	private Timestamp expDate; // 失效时间
//    private String positionAlias; // 别名，发送邀请时给候选人看见的名称
//    private String emailTemplate; // 邮件模板
    private Integer testType; // 评测类型  1-社会招聘测评; 2-校园招聘测评;
    private Integer paperId; // 试卷ID
    private Integer preBuilt; // 是否预置: 1 - 系统; 0 - 定制
    private String passport; // 测评口令
    private Integer weixinOnsite; // 是否启用微信答题
    private Integer weixinCompany; // 1.用公司微信号; 0-用百一微信号
    // private Integer weixinMode; // 企业是否有微信公众号: 无、编辑者模式、开发者模式
    private Integer notifyScore; // 答题结束后是否让学生知晓分数
    private String activityUrl; // 宣讲会具体信息网址
    private String entry; // 答题入口url链接后的随机字符串
    private Integer createFrom; //从哪个测评copy而来
    private Integer sample;// 是否样例职位: 0-否,1-是
    private Timestamp onlineReqEndDate; // 考试申请关闭时间
    private Timestamp examEndDate; // 考试关闭时间
    
    private List<PositionInfoConfig> configInfo;// 保存职位时需要添加的常规信息设置
    private List<CompanyRecruitActivity> activityList; // 宣讲会信息
    private String seriesName;// 职位序列名称 新建职位序列时需要传递

    private Collection<ConfigInfoExtEx> infoExt; // 查询宣讲会时的信息常规信息设置

    private String requiresDesc;// 需要删掉，放到paper中去

    private Integer editable;// 是否可以编辑 1可以编辑 0不可以编辑
//    private List<ReportStatusCountInfo> reportStatusCountInfo;  //报表统计信息   此字段已经没有用了
    
    
    private List<EmployerAuthorizationIntf> employerAuths;//授权信息
    
    private Integer groupFlag; //测评类型 0-普通测评   1-组测评, 2-子测评
    private List<Position> mustPositions; //必考测评
    private List<Position> choosePositions;//选考测评
    private Integer examState;// 测评组中子测评的状态  0为考  1正在考   2已经考试结束
    private List<Position> nextExamPositions;//下一步要考试的试卷
    
    
    public List<Position> getNextExamPositions() {
		return nextExamPositions;
	}
	public void setNextExamPositions(List<Position> nextExamPositions) {
		this.nextExamPositions = nextExamPositions;
	}
	public Integer getExamState() {
		return examState;
	}
	public void setExamState(Integer examState) {
		this.examState = examState;
	}
	public List<Position> getMustPositions() {
		return mustPositions;
	}
	public void setMustPositions(List<Position> mustPositions) {
		this.mustPositions = mustPositions;
	}
	public List<Position> getChoosePositions() {
		return choosePositions;
	}
	public void setChoosePositions(List<Position> choosePositions) {
		this.choosePositions = choosePositions;
	}
	public Integer getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}
	public List<EmployerAuthorizationIntf> getEmployerAuths() {
		return employerAuths;
	}
	public void setEmployerAuths(List<EmployerAuthorizationIntf> employerAuths) {
		this.employerAuths = employerAuths;
	}
	/*public List<ReportStatusCountInfo> getReportStatusCountInfo() {
		return reportStatusCountInfo;
	}
	public void setReportStatusCountInfo(List<ReportStatusCountInfo> reportStatusCountInfo) {
		this.reportStatusCountInfo = reportStatusCountInfo;
	}*/
	public Position(){
    	this.sample = 0;
    	this.groupFlag = Constants.GROUP_FLAG_NORMAL;
    }
    public Integer getSample() {
		return sample;
	}

	public void setSample(Integer sample) {
		this.sample = sample;
	}


	public Integer getCreateFrom() {
		return createFrom;
	}

	public void setCreateFrom(Integer createFrom) {
		this.createFrom = createFrom;
	}

	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	
    public String getRequiresDesc()
    {
        return requiresDesc;
    }

    public void setRequiresDesc(String requiresDesc)
    {
        this.requiresDesc = requiresDesc;
    }

    public Integer getPositionId()
    {
        return positionId;
    }

    public void setPositionId(Integer positionId)
    {
        this.positionId = positionId;
    }

    public String getPositionName()
    {
        return positionName;
    }

    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public String getPositionDesc()
    {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc)
    {
        this.positionDesc = positionDesc;
    }

    public Integer getPositionState()
    {
        return positionState;
    }

    public void setPositionState(Integer positionState)
    {
        this.positionState = positionState;
    }

    public Integer getSeriesId()
    {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId)
    {
        this.seriesId = seriesId;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Integer getEmployerId()
    {
        return employerId;
    }

    public void setEmployerId(Integer employerId)
    {
        this.employerId = employerId;
    }

    public Timestamp getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate)
    {
        this.publishDate = publishDate;
    }

    public Timestamp getExpDate()
    {
        return expDate;
    }

    public void setExpDate(Timestamp expDate)
    {
        this.expDate = expDate;
    }

    public Integer getTestType()
    {
        return testType;
    }

    public void setTestType(Integer testType)
    {
        this.testType = testType;
    }

    public Integer getPaperId()
    {
        return paperId;
    }

    public void setPaperId(Integer paperId)
    {
        this.paperId = paperId;
    }

    public Integer getPreBuilt()
    {
        return preBuilt;
    }

    public void setPreBuilt(Integer preBuilt)
    {
        this.preBuilt = preBuilt;
    }

    public String getPassport()
    {
        return passport;
    }

    public void setPassport(String passport)
    {
        this.passport = passport;
    }

    public Integer getNotifyScore()
    {
        return notifyScore;
    }

    public void setNotifyScore(Integer notifyScore)
    {
        this.notifyScore = notifyScore;
    }

    public Integer getWeixinOnsite()
    {
        return weixinOnsite;
    }

    public void setWeixinOnsite(Integer weixinOnsite)
    {
        this.weixinOnsite = weixinOnsite;
    }

    public Integer getWeixinCompany()
    {
        return weixinCompany;
    }

    public void setWeixinCompany(Integer weixinCompany)
    {
        this.weixinCompany = weixinCompany;
    }

    public String getActivityUrl()
    {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl)
    {
        this.activityUrl = activityUrl;
    }

    public String getSeriesName()
    {
        return seriesName;
    }

    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public List<PositionInfoConfig> getConfigInfo()
    {
        return configInfo;
    }

    public void setConfigInfo(List<PositionInfoConfig> configInfo)
    {
        this.configInfo = configInfo;
    }

    public List<CompanyRecruitActivity> getActivityList()
    {
        return activityList;
    }

    public void setActivityList(List<CompanyRecruitActivity> activityList)
    {
        this.activityList = activityList;
    }

    public String getEntry()
    {
        return entry;
    }

    public void setEntry(String entry)
    {
        this.entry = entry;
    }

    public Collection<ConfigInfoExtEx> getInfoExt()
    {
        return infoExt;
    }

    public void setInfoExt(Collection<ConfigInfoExtEx> infoExt)
    {
        this.infoExt = infoExt;
    }
    public Timestamp getOnlineReqEndDate()
    {
        return onlineReqEndDate;
    }
    public void setOnlineReqEndDate(Timestamp onlineReqEndDate)
    {
        this.onlineReqEndDate = onlineReqEndDate;
    }
    public Timestamp getExamEndDate()
    {
        return examEndDate;
    }
    public void setExamEndDate(Timestamp examEndDate)
    {
        this.examEndDate = examEndDate;
    }
	@Override
	public String toString() {
		return "Position [positionId=" + positionId + ", positionName=" + positionName + ", seriesId=" + seriesId
				+ ", level=" + level + ", employerId=" + employerId + ", testType=" + testType + ", paperId=" + paperId
				+ ", activityUrl=" + activityUrl + ",activityList=" +activityList+"]";
	}

}
