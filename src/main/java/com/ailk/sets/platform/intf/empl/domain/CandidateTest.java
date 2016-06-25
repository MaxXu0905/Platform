package com.ailk.sets.platform.intf.empl.domain;

import java.sql.Timestamp;

/**
 * CandidateTest entity. @author MyEclipse Persistence Tools
 */

public class CandidateTest implements java.io.Serializable {
	private static final long serialVersionUID = -3096840053729832663L;
	
	//0-待定; 1-通过; 2-淘汰
	public static final Integer TEST_RESULT_TODO = 0;
	public static final Integer TEST_RESULT_RECOMMEND = 1;
	public static final Integer TEST_RESULT_WEEDOUT = 2;
	
	private Long testId;
	private String passport;
	private Integer candidateId;
	private String candidatePic;
	private Integer positionId;
	private Integer testState;
	private Integer testResult;
	private Integer paperState;
	private Timestamp beginTime;
	private Timestamp endTime;
	private Integer elapsedTime;
	private Integer breakTimes;
	private Integer switchTimes;
	private Integer freshTimes;
	private String clientIp;
	private String sessionTicket;
	private Integer sessionState;
	private String partSeq;
	private String questionMark;
	private Long questionId;
	private Integer partIndex;
	private Integer questionIndex;
	private Integer channelType; //账户类型 1职酷 11英才

	private String os;
	private String browser;
	private String browserVersion;
	private Timestamp loginTime;
	
	// add by lipan 2014年7月5日14:01:09
	private Integer canWithOutCamera; // 是否允许考生在没有摄像头时进行考试 0:允许，1不允许
	private String testPositionName; // 测评职位名称
	
	// add by lipan 2014年7月29日15:48:44   微信位置信息
	private String longitude; //经度
	private String latitude; //纬度
	private String accuracy; //经纬度的精度（米）
	
	private Integer paperId;
	


	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	
	public Integer getPartIndex() {
		return partIndex;
	}

	public void setPartIndex(Integer partIndex) {
		this.partIndex = partIndex;
	}

	public Integer getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(Integer questionIndex) {
		this.questionIndex = questionIndex;
	}
	public Integer getPaperState() {
		return paperState;
	}

	public void setPaperState(Integer paperState) {
		this.paperState = paperState;
	}

	// Constructors

	/** default constructor */
	public CandidateTest() {
	}

	/** minimal constructor */
	public CandidateTest(Integer candidateId, Integer positionId, Integer testState, Integer testResult, Integer elapsedTime, Integer breakTimes, Integer switchTimes, Integer freshTimes) {
		this.candidateId = candidateId;
		this.positionId = positionId;
		this.testState = testState;
		this.testResult = testResult;
		this.elapsedTime = elapsedTime;
		this.breakTimes = breakTimes;
		this.switchTimes = switchTimes;
		this.freshTimes = freshTimes;
	}

	/** full constructor */
	public CandidateTest(String passport, Integer candidateId, String candidatePic, Integer positionId, Integer testState, Integer testResult, Timestamp beginTime, Timestamp endTime,
			Integer elapsedTime, Integer breakTimes, Integer switchTimes, Integer freshTimes, String clientIp, String sessionTicket, Integer sessionState, String partSeq, String questionMark,
			Long questionId) {
		this.passport = passport;
		this.candidateId = candidateId;
		this.candidatePic = candidatePic;
		this.positionId = positionId;
		this.testState = testState;
		this.testResult = testResult;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.elapsedTime = elapsedTime;
		this.breakTimes = breakTimes;
		this.switchTimes = switchTimes;
		this.freshTimes = freshTimes;
		this.clientIp = clientIp;
		this.sessionTicket = sessionTicket;
		this.sessionState = sessionState;
		this.partSeq = partSeq;
		this.questionMark = questionMark;
		this.questionId = questionId;
	}

	// Property accessors

	public Long getTestId() {
		return this.testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getPassport() {
		return this.passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Integer getCandidateId() {
		return this.candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidatePic() {
		return this.candidatePic;
	}

	public void setCandidatePic(String candidatePic) {
		this.candidatePic = candidatePic;
	}

	public Integer getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getTestState() {
		return this.testState;
	}

	public void setTestState(Integer testState) {
		this.testState = testState;
	}

	public Integer getTestResult() {
		return this.testResult;
	}

	public void setTestResult(Integer testResult) {
		this.testResult = testResult;
	}

	public Timestamp getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getElapsedTime() {
		return this.elapsedTime;
	}

	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Integer getBreakTimes() {
		return this.breakTimes;
	}

	public void setBreakTimes(Integer breakTimes) {
		this.breakTimes = breakTimes;
	}

	public Integer getSwitchTimes() {
		return this.switchTimes;
	}

	public void setSwitchTimes(Integer switchTimes) {
		this.switchTimes = switchTimes;
	}

	public Integer getFreshTimes() {
		return this.freshTimes;
	}

	public void setFreshTimes(Integer freshTimes) {
		this.freshTimes = freshTimes;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getSessionTicket() {
		return this.sessionTicket;
	}

	public void setSessionTicket(String sessionTicket) {
		this.sessionTicket = sessionTicket;
	}

	public Integer getSessionState() {
		return this.sessionState;
	}

	public void setSessionState(Integer sessionState) {
		this.sessionState = sessionState;
	}

	public String getPartSeq() {
		return this.partSeq;
	}

	public void setPartSeq(String partSeq) {
		this.partSeq = partSeq;
	}

	public String getQuestionMark() {
		return this.questionMark;
	}

	public void setQuestionMark(String questionMark) {
		this.questionMark = questionMark;
	}

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

    public Integer getCanWithOutCamera()
    {
        return canWithOutCamera;
    }

    public void setCanWithOutCamera(Integer canWithOutCamera)
    {
        this.canWithOutCamera = canWithOutCamera;
    }

    public String getTestPositionName()
    {
        return testPositionName;
    }

    public void setTestPositionName(String testPositionName)
    {
        this.testPositionName = testPositionName;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getAccuracy()
    {
        return accuracy;
    }

    public void setAccuracy(String accuracy)
    {
        this.accuracy = accuracy;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

}