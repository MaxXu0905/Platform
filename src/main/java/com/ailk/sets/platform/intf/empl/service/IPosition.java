package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.PositionActivityAddressInfo;
import com.ailk.sets.platform.intf.domain.PositionOutInfo;
import com.ailk.sets.platform.intf.domain.PositionQuickInfo;
import com.ailk.sets.platform.intf.domain.PositionSeriesCustom;
import com.ailk.sets.platform.intf.empl.domain.EmployerAuthorizationIntf;
import com.ailk.sets.platform.intf.empl.domain.PaperOutInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionInitInfo;
import com.ailk.sets.platform.intf.empl.domain.PositionSet;
import com.ailk.sets.platform.intf.empl.domain.PositionTestTypeInfo;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.Question;
import com.ailk.sets.platform.intf.model.param.RandomQuestionParam;
import com.ailk.sets.platform.intf.model.position.PosResponse;
import com.ailk.sets.platform.intf.model.position.PositionInfo;
import com.ailk.sets.platform.intf.model.position.PositionPaperInfo;
import com.ailk.sets.platform.intf.model.position.PositionStatistics;

/**
 * 职位服务接口
 * 
 * @author 毕希研
 * 
 */
public interface IPosition {



	/**
	 * 获取一道随机问题
	 * 
	 * @param param
	 * @return
	 * @throws PFServiceException
	 */
	public Question getRandomQuestion(RandomQuestionParam param) throws PFServiceException;

	/**
	 * 保存职位信息
	 * 
	 * @param positionSet
	 * @return
	 */
//	public PosResponse commitPosition(PositionSet positionSet) throws PFServiceException;

	/**
	 * 获取试卷模板信息
	 * 
	 * @param positin
	 * @return
	 */
//	public PaperModel analysiPosition(Position positin) throws PFServiceException;

	/**
	 * 获取职位信息
	 * 
	 * @param employerId
	 * @param page
	 * @return
	 * @throws PFServiceException
	 */
	public List<PositionInfo> getPositionInfo(int employerId, Page page) throws PFServiceException;

	/**
	 * 获取职位信息
	 * 
	 * @param positionIds
	 * @param employerId
	 * @return
	 * @throws PFServiceException
	 */
//	public List<PositionInfo> getPositionInfo(List<Integer> positionIds, int employerId) throws PFServiceException;


	/**
	 * 获取职位信息
	 * 
	 * @param positionId
	 * @param employerId
	 * @return
	 * @throws PFServiceException
	 */
	public PositionInfo getPositionInfo(int positionId, int employerId, Integer posNumber) throws PFServiceException;

	/**
	 * 根据邀请id获取某个职位信息
	 * 
	 * @param testId
	 * @return
	 */
	public Position getPosition(long testId);


	/**
	 * 删除邀请失败的消息记录
	 * 
	 * @param employerId
	 * @param positionId
	 * @return
	 */
	public PFResponse delInvitationFailedLog(int employerId, int positionId) throws PFServiceException;

	/**
	 * 获取职位相关的统计信息
	 * 
	 * @param ps
	 * @param employerId
	 * @param positionId
	 * @return
	 */
	public PositionStatistics setStatistics(PositionStatistics ps, int employerId, int positionId);

	/**
	 * 获取职位的全部信息
	 * 
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public PositionSet getPositionInfo(int positionId) throws PFServiceException;

	/**
	 * 是否是该职位的拥有者 包含授权
	 * 
	 * @param employerId
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse ownPosition(int employerId, int positionId) throws PFServiceException;
	/**
	 * 是否是该职位的拥有者 不包含授权
	 * 
	 * @param employerId
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse ownPositionSelf(int employerId, int positionId) throws PFServiceException;
	
	/**
	 * 获取创建测评的初始化信息
	 * @param employerId
	 * @return
	 * @throws PFServiceException
	 */
	public PositionInitInfo getPositionInitInfo(int employerId ) throws PFServiceException;
	
	/**
	 * 创建职位
	 * @param position
	 * @return
	 * @throws PFServiceException
	 */
	public PosResponse createPosition(Position position) throws PFServiceException;

    /**
     * 获得测评信息——使用活动唯一标识passport
     * @param positionId
     * @param employerId
     * @param posNumber
     * @param activityPassport
     * @return
     * @throws PFServiceException
     */
	public PositionInfo getPositionInfo(int positionId, int employerId, Integer posNumber,
            String activityPassport) throws PFServiceException;
	
	/**
	 * 获取需要定制的测评试卷信息
	 * @param employerId
	 * @return
	 */
	public List<PositionSeriesCustom>  getPositionSeriesCustom(int employerId);
	
    /**
     * 创建快速定值测评
     * @param paperIds
     * @return
     * @throws PFServiceException
     */
	public PFResponse createPositionByCustom(int employerId, PositionQuickInfo quickInfo) throws PFServiceException;
	
	/**
	 * 第三方获取测评信息
	 * @return
	 */
	public List<PositionOutInfo> getPositionOutInfos(int employerId) ;
	
	/**
	 * 获取demo 测评列表（试卷列表）
	 * @return
	 * @throws PFServiceException
	 */
	public 	List<PositionInfo> getPositionInfoOfSample()  throws PFServiceException;
	
	/**
	 * 根据测评id获取测评
	 * @param positionId
	 * @return
	 */
	public Position getPositionByPositionId(int employerId, int positionId) throws Exception;
	
	/**
	 * 获取测评统计信息，手机端登陆首页显示数据
	 * @param employerId
	 * @return
	 */
	public List<PositionTestTypeInfo> getPositionTestTypeInfo(int employerId);
	
	/**
	 * 获取测评列表
	 * @param employerId
	 * @param testType  校招 社招
	 * @param page
	 * @return
	 */
	public List<Position> getPositionByTestType(int employerId, int testType, Page page);
	
	/**
	 * 增加授权
	 * @param employerAuth
	 * @return
	 */
	public PFResponse addEmployerAuthorization(EmployerAuthorizationIntf employerAuth) throws PFServiceException;
	/**
	 * 检验邮箱是否可以授权
	 * @param email
	 * @return
	 */
	public PFResponse checkAuthorEmail(int employerId, String email);
	
	
	/**
	 * 获取职位信息  校招hr
	 * 
	 * @param employerId
	 * @param page
	 * @return
	 * @throws PFServiceException
	 */
	public List<PositionPaperInfo> getPositionInfoWithPaperCount(int employerId, Page page) throws PFServiceException;
	
	/**
	 * 根据测评id获取未勘测的地址
	 * @param positionId
	 * @return
	 */
    public List<ActivityRecruitAddress>  getUnTestActivityRecruitAddressesByPositionId(int positionId);
    
    /**
     *  根据测评id获取的地址
     * @param positionId
     * @return
     */
    public List<ActivityRecruitAddress> getActivityRecruitAddressesByPositionId(int positionId);
    
    
    /**
     * 获取测评下的地址勘测信息
     * @param positionId
     * @return
     */
    public PositionActivityAddressInfo getPositionActivityAddressInfo(int positionId);
    
    /**
     * 根据用户Id
     * @param employerId
     * @return
     */
    public List<PaperOutInfo> getPaperOutInfo(int employerId);
	
	
}
