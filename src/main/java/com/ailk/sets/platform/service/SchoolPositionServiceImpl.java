package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IPositionInfoExtDao;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.platform.common.MailSenderInfo;
import com.ailk.sets.platform.dao.impl.QbNormalDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IActivityAddressSignalDao;
import com.ailk.sets.platform.dao.interfaces.IActivityRecruitAddressDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyEmailserverDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyRecruitActivityDao;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IConfigMobileRatioDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IInviteDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IPasscodeAvailableDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionLogDao;
import com.ailk.sets.platform.dao.interfaces.IPositionRelationDao;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.domain.PositionInfoExtId;
import com.ailk.sets.platform.domain.PositionLog;
import com.ailk.sets.platform.domain.PositionRelation;
import com.ailk.sets.platform.domain.PositionRelationId;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.ActivityAddressSignal;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivityCount;
import com.ailk.sets.platform.intf.domain.ConfigMobileRatio;
import com.ailk.sets.platform.intf.domain.PasscodeAvailable;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeNameWithCount;
import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExtEx;
import com.ailk.sets.platform.intf.empl.domain.PaperRelationInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionGroupInfo;
import com.ailk.sets.platform.intf.empl.service.IInfoCollect;
import com.ailk.sets.platform.intf.empl.service.IInvite;
import com.ailk.sets.platform.intf.empl.service.ISchoolPositionService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.campus.CampusRsp;
import com.ailk.sets.platform.intf.model.campus.SignUpOnlineSetsRsp;
import com.ailk.sets.platform.intf.model.condition.Interval;
import com.ailk.sets.platform.intf.model.invatition.InvitationState;
import com.ailk.sets.platform.util.DateUtils;
import com.ailk.sets.platform.util.PropsUtils;
import com.ailk.sets.platform.util.SimpleMailSender;
import com.ailk.sets.platform.util.TemplateHost;

@Transactional(rollbackFor = Exception.class)
public class SchoolPositionServiceImpl implements ISchoolPositionService {

    private Logger logger = LoggerFactory.getLogger(SchoolPositionServiceImpl.class);
    
	@Autowired
	private QbNormalDaoImpl qbNoramlDaoImpl;
	@Autowired
	private ICandidateTestDao candidateTestDaoImpl;
	@Autowired
	private IConfigDao configDao;
	@Autowired
	private ICandidateInfoDao candidateInfoDao;
	@Autowired
	private ICompanyRecruitActivityDao companyRecruitActivityDao;
	@Autowired
	private IActivityRecruitAddressDao activityRecruitAddressDao ;
    @Autowired
    private IPositionDao positionDao;
    @Autowired
    private IInfoCollect infoCollect;
    @Autowired
    private IPositionLogDao positionLogDao;
    @Autowired
    private ICandidateTestDao candidateTestDao;
    @Autowired
    private IPasscodeAvailableDao passcodeAvailableDao;
    @Autowired
    private IInvite invite;
    @Autowired
    private IEmployerDao employerDao;
    @Autowired
    private IActivityAddressSignalDao activityAddressSignalDao;
    @Autowired
    private IInviteDao inviteDao;
    @Autowired
    private ICandidateDao candidateDao;
    @Autowired
    private IConfigMobileRatioDao configMobileRatioDao;
    @Autowired
    private IPositionInfoExtDao positionInfoExtDao;
	@Autowired
    private IPositionRelationDao positionRelationDao;
	@Autowired
	private IPaperDao paperDao;
	
	@Autowired
	private ICompanyEmailserverDao companyEmailserverDaoImpl;

	@Override
	public List<CompanyRecruitActivity> getActivitiesByEmployerId(int employerId, Integer positionId) {
		logger.debug("get CompanyRecruitActivity for employerId {}, positionId {} ", employerId, positionId);
		return companyRecruitActivityDao.getCompanyRecruitActivity(employerId, positionId);
	}

	@Override
	public PFResponse updateActivity(int activityId, int status) {
		PFResponse responce = new PFResponse();
		responce.setCode(FuncBaseResponse.SUCCESS);
		logger.debug(" updateActivity for activityId {}, status {} ", activityId, status);
		CompanyRecruitActivity acti = companyRecruitActivityDao.getEntity(activityId);
		if (acti == null) {
			logger.warn("not found any activity for {} ", activityId);
			return responce;
		}
		if(status == 1){//如果是开启需要判断是否已经有
			Integer positionId = acti.getPositionId();
			Position position = positionDao.getEntity(positionId);
	       List<CompanyRecruitActivity> acties = companyRecruitActivityDao.getCompanyRecruitActivity(position.getEmployerId(), positionId);
			for(CompanyRecruitActivity act : acties){
				if(act.getTestState() == 1){
					logger.warn("have start activity for id {} , positionId {} ", act.getActivityId(), positionId);
					responce.setCode("HAVESTARTED");
					return responce;
				}
					
			}
		}
		
		acti.setTestState(status);
		companyRecruitActivityDao.saveOrUpdate(acti);
		return responce;
	}

	public CompanyRecruitActivityCount getCompanyRecruitActivityCount(int activityId) {
		logger.debug(" getCompanyRecruitActivityCount for activityId {}", activityId);
		CompanyRecruitActivityCount count = new CompanyRecruitActivityCount();
		CompanyRecruitActivity acti = companyRecruitActivityDao.getEntity(activityId);;
		String sql = "select count(*) from CandidateTest where begin_time is not null " +
				"and (positionId = " + acti.getPositionId() +  " or positionId in (select id.positionId from PositionRelation where id.positionGroupId = " + acti.getPositionId() +"))" + 
				" and passport = '" + acti.getPasscode() + "' ";
//		String joinSql = sql;
//		Long totalJoinNum = qbNoramlDaoImpl.getCountNumberBySql(joinSql);
		String answeringSql = sql + " and end_time is null"; // 正在答题
		Long totalAnsweringNum = qbNoramlDaoImpl.getCountNumberBySql(answeringSql);
//		String finishedSql = sql + " and end_time is not null";// 答题结束
//		Long totalFinishedNum = qbNoramlDaoImpl.getCountNumberBySql(finishedSql);
//		count.setTotalJoinNum(totalJoinNum);
		count.setTotalAnsweringNum(totalAnsweringNum);
//		count.setTotalFinishedNum(totalFinishedNum);
		return count;
	}
	/**
	 * 获取职位意向报考人数
	 * @param activityId
	 * type 1测评 2活动
	 * @return
	 * @throws PFServiceException 
	 */
	@Override
	public List<ConfigCodeNameWithCount> getPosIntention(int employerId,int posOrActId, int type) throws PFServiceException {
		try {
			List<Integer> ids = new ArrayList<Integer>();
			if(type == 2){
				CompanyRecruitActivity companyRecruitActivity = companyRecruitActivityDao.getEntity(posOrActId);
				if (companyRecruitActivity == null)
					return null;
				
				Position position = positionDao.getPosition(companyRecruitActivity.getPositionId());
				PositionInfoExt intExt = positionInfoExtDao.getEntity(new PositionInfoExtId(position.getEmployerId(), position.getPositionId(), ConfigCodeType.INTENTION_POSITION));
				if(intExt == null){//测评没有配置意向职位， 则不能按此过滤
					logger.info("the activity is not config intention for positionId {} , employerId {} ", position.getPositionId(), position.getEmployerId());
					return null;
				}
				List<CandidateTest> list = candidateTestDaoImpl.getByActivityId(companyRecruitActivity);
				for (CandidateTest candidateTest : list) {
					int testState = candidateTest.getTestState();
					if(testState == InvitationState.INVITATION_STATE_REPORT1 || testState == InvitationState.INVITATION_STATE_REPORT2){
						ids.add(candidateTest.getCandidateId());
					}
					
				}
			}else if(type == 1){
				Position position = positionDao.getPosition(posOrActId);
				PositionInfoExt intExt = positionInfoExtDao.getEntity(new PositionInfoExtId(position.getEmployerId(), position.getPositionId(), ConfigCodeType.INTENTION_POSITION));
				if(intExt == null){//测评没有配置意向职位， 则不能按此过滤
					logger.info("the position is not config intention for positionId {} , employerId {} ", position.getPositionId(), position.getEmployerId());
					return null;
				}
				Page page = new Page();
				page.setRequestPage(1);
				page.setPageSize(Integer.MAX_VALUE);
				List<Invitation> list = inviteDao.getInvitationInfo(employerId, posOrActId, page);
				for(Invitation invi : list){
					CandidateTest test = candidateTestDao.getCandidateTest(invi.getTestId());
					int testState = test.getTestState();
					if(testState == InvitationState.INVITATION_STATE_REPORT1 || testState == InvitationState.INVITATION_STATE_REPORT2){
						ids.add(test.getCandidateId());
					}
				}
			}
			
			List<CandidateInfoExt> candidateInfoList = candidateInfoDao.getCandidateInfoExts(ids, ConfigCodeType.INTENTION_POSITION);
			List<ConfigCodeName> config = configDao.getConfigCode(ConfigCodeType.INTENTION_POSITION);
			final Map<String, ConfigCodeName> configMap = new HashMap<String, ConfigCodeName>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (ConfigCodeName ccn : config) {
				map.put(ccn.getId().getCodeId(), 0);
				configMap.put(ccn.getId().getCodeId(), ccn);
			}
			for (CandidateInfoExt candidateInfo : candidateInfoList) {
				String value = candidateInfo.getValue();
				if (StringUtils.isNotEmpty(value))
					map.put(value, map.get(value) + 1);
			}
			List<Entry<String, Integer>> arrayList = new ArrayList<Entry<String, Integer>>(map.entrySet());
			Collections.sort(arrayList, new Comparator<Entry<String, Integer>>() {
				@Override
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					if (o2.getValue().equals(o1.getValue()))
						return configMap.get(o1.getKey()).getSeq() - configMap.get(o2.getKey()).getSeq();
					return o2.getValue() - o1.getValue();
				}
			});
			List<ConfigCodeNameWithCount> result = new ArrayList<ConfigCodeNameWithCount>();
			for (Entry<String, Integer> entry : arrayList) {
				ConfigCodeNameWithCount configCodeNameWithCount = new ConfigCodeNameWithCount();
				PropertyUtils.copyProperties(configCodeNameWithCount, configMap.get(entry.getKey()));
				configCodeNameWithCount.setCount(entry.getValue());
				result.add(configCodeNameWithCount);
			}
			return result;
		} catch (Exception e) {
			throw new PFServiceException(e);
		}
	}

	/**
	 * 新增校招宣讲会
	 */
    @Override
    public CampusRsp createCampusPosition(Position position) throws PFServiceException
    {
        CampusRsp res = new CampusRsp();
        StringBuffer activityIds = new StringBuffer();
        
        boolean isSave = false;
        
        // 职位ID
        Integer positionId = null;
        
        // 这里置空一些值，防止信息注入...
        position.setPassport(null); // 口令置空，因为口令为后台生成
        position.setEntry(null); // entry置空
        
        // 更新操作前
        if (null != position.getPositionId())
        {
            //  前台传递过来的属性 --> 拷贝db中的属性 
            Position positionDb = positionDao.getPosition(position.getPositionId());
            PropsUtils.copyProperties(positionDb, position);
            //生成passport / url后缀 
            addKey(positionDb);
            // 更新时间
            positionDb.setModifyDate(DateUtils.getCurrentTimestamp());
            // 保存测评
            positionDao.saveOrUpdate(positionDb);
            positionId = positionDb.getPositionId();
            res.setWeixinCompany(positionDb.getWeixinCompany());//
            res.setPassport(positionDb.getPassport());
            res.setEntry(positionDb.getEntry());
        }else
        {
            // 默认值
//            position.setPositionName("校招测评");
            position.setPositionDesc("无");
            position.setRequiresDesc("无");
            position.setPositionState(0); // 0-正常; 1-已关闭
            position.setPublishDate(DateUtils.getCurrentTimestamp());  // 当前时间
            position.setSeriesId(Constants.POSITION_SEREIS_ID_SCHOOL); // 职位序列
            position.setLevel(1);     // 级别;实习、助理
//            position.setPaperId(-1);  // 试卷id
            position.setPreBuilt(0);  // 是否预置: 1 - 系统; 0 - 定制
            position.setTestType(2); // 1-社会招聘测评; 2-校园招聘测评;
            //生成passport / url后缀 
            addKey(position); 
            // 保存时间
            position.setModifyDate(DateUtils.getCurrentTimestamp());
            // 保存测评
            positionDao.saveOrUpdate(position);
            positionId = position.getPositionId();
            isSave = true;
            res.setWeixinCompany(position.getWeixinCompany());//
            res.setPassport(position.getPassport());
            res.setEntry(position.getEntry());
        }
        
        // 查询数据库中 当前测评所对应的所有活动信息  -----需要删除的活动id列表
        List<Integer> deleteIds = companyRecruitActivityDao.getActivityIds(positionId);
        
        // 保存活动和地址信息   子测评不需要生成宣讲会
        if(position.getGroupFlag() != Constants.GROUP_FLAG_CHILD && !CollectionUtils.isEmpty(position.getActivityList()))
        {
            
            // 防止一个测评中的宣讲会passport重复,用一个map保存数据库中存在的passcode/ 当前测评中新增活动使用的passcode
            Map<String, String> passcodeMap = new HashMap<String, String>();
            long minTime = Long.MAX_VALUE;
            CompanyRecruitActivity  minActivity = null;
            for (CompanyRecruitActivity activity : position.getActivityList())
            {
            	long actTime = DateUtils.getDateMillis(activity.getActivityDate() +" "+activity.getBeginTime(), DateUtils.DATE_FORMAT_3);
            	if(minTime > actTime){
            		logger.debug("minTime is larger than actTime , activity is {} ", activity);
            		minTime = actTime;
            		minActivity = activity;
            	}
                CompanyRecruitActivity activityDb = null;
//                String addressIds = null; //  地址信息ID
                List<Integer> arrressIds = new ArrayList<Integer>();
                // 页面回传id了，从需要删除的id集合里移除
                if(!CollectionUtils.isEmpty(deleteIds) && deleteIds.contains(activity.getActivityId()))
                {
                    deleteIds.remove(activity.getActivityId());
                    
                    // 只更新/删除未开始的活动, 不处理已开始/已结束的活动 
                    activityDb = companyRecruitActivityDao.getActivityById(activity.getActivityId());
                    if(!activityDb.getTestState().equals(CompanyRecruitActivity.TEST_STATE_NORMAL))
                    {
                        continue;
                    }
                }
                
                // 前台传过来的json使用CompanyRecruitActivity接收，
                // 再把CompanyRecruitActivity中的地址信息拷贝到ActivityRecruitAddress中，进行保存
                try
                {   Integer addressId = null;
                	List<String> addresses = activity.getAddresses();
                	if(CollectionUtils.isNotEmpty(addresses)){//多个地址
                		for(String address : addresses){
                			activity.setAddress(address);
                			addressId=saveAddress(activity);
                			if(!arrressIds.contains(addressId)){
                        		arrressIds.add(addressId);
                        	}
                		}
                	}else{// 适配1个地址
                		addressId=saveAddress(activity);
                		if(!arrressIds.contains(addressId)){
                    		arrressIds.add(addressId);
                    	}
                	}
                	
                } catch (Exception e)
                {
                    throw new PFServiceException(e);
                }
                
                // 活动id分隔符
                if(!StringUtils.isBlank(activityIds.toString()))
                {
                    activityIds.append("|");
                }
                
                StringBuffer sb = new StringBuffer();
                for(Integer addId : arrressIds){
                	
                	sb.append(addId).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                // 更新
                if (null != activityDb)
                {
                    PropsUtils.copyProperties(activityDb, activity);
                    activityDb.setPositionId(positionId); // 测评ID
                    activityDb.setAddressId(sb.toString());
                    activityDb.setTestState(0);   // 考试状态, 0-未开始, 1-已开始, 2-已结束
                    companyRecruitActivityDao.saveOrUpdate(activityDb);
                    activityIds.append(activityDb.getActivityId());
                    // db中已存在的passcode
                    passcodeMap.put(activityDb.getPasscode(),null);
                }else
                {
                    /** 生成 passport(10位数字加字母) 用于兼容历史数据、统计参考人数**/
                    String passcode = "";
                    do
                    {
                        passcode = RandomStringUtils.randomAlphanumeric(10);
                    } while (passcodeMap.containsKey(passcode)); // 如果key重复那么继续随机..
                    // 设置passcode
                    activity.setPasscode(passcode);
                    // 保存当前测评新增活动使用到的passcode
                    passcodeMap.put(passcode,null);
                    
                    // 保存活动 , 地址Id
                    activity.setPositionId(positionId); // 测评ID
                    activity.setAddressId(sb.toString());
                    activity.setTestState(0);   // 考试状态, 0-未开始, 1-已开始, 2-已结束
                    companyRecruitActivityDao.saveOrUpdate(activity);
                    activityIds.append(activity.getActivityId());
                    
                }
            }
            if(minActivity != null){
            	minActivity.setTestState(1);
            	companyRecruitActivityDao.saveOrUpdate(minActivity);
            }
            processUnTestActivityAddresses(position.getEmployerId());
        }
        
        // 删除前台未回传id对应的活动  (只删除考试未开始的)
        if (!CollectionUtils.isEmpty(deleteIds))
        {
            for (Integer activityId : deleteIds)
            {
                companyRecruitActivityDao.deleteCompanyRecruitActivity(activityId);
            }
        }
        
        // 保存常规信息
        infoCollect.saveInfo(position.getEmployerId(), positionId, position.getConfigInfo());
        
        //　新增时插入log表
        if(isSave && position.getGroupFlag() == Constants.GROUP_FLAG_NORMAL )
        {
        	// 保存日志表信息
        	PositionLog log = new PositionLog();
        	log.setEmployerId(position.getEmployerId());
        	log.setLogTime(new Timestamp(System.currentTimeMillis()));
        	log.setPositionState(0);
        	log.setStateId(positionId.longValue());
        	log.setPositionId(positionId);
        	positionLogDao.saveOrUpdate(log);
        }
        
        res.setActivityIds(activityIds.toString());
        res.setPositionId(positionId);
        return res;
    }
    
    private Integer saveAddress(CompanyRecruitActivity activity) throws Exception{
    	   ActivityRecruitAddress address = new ActivityRecruitAddress();
//           PropertyUtils.copyProperties(address, activity);
           address.copyPropertiesFromActivity(activity);
           // 使用城市、学校、地址查询是否存在该地址，如果存在更新，不存在新增。
           ActivityRecruitAddress  addressDb = activityRecruitAddressDao.getAddress(address);
           
           // 更新
           if(null != addressDb)
           {
               PropsUtils.copyProperties(addressDb, address);
               addressDb.setCreateDate(DateUtils.getCurrentTimestamp());// 更新时间
               activityRecruitAddressDao.saveActivityAddress(addressDb);
               return addressDb.getAddressId();
           }else
           {
               // 保存
               address.setCreateDate(DateUtils.getCurrentTimestamp());// 保存时间
               activityRecruitAddressDao.saveActivityAddress(address);
               return address.getAddressId();
           }
    }
    
    /**
     * 生成passport / url后缀 
     * @param position
     */
    private void addKey(Position position)
    {
        /** entry为空 生成 entry**/
    	if(StringUtils.isBlank(position.getEntry()))
    	{
    		Map<String, String> entryMapDb = positionDao.getEntryMap();
    		String entry = RandomStringUtils.randomAlphanumeric(15);
    		do
    		{
    			entry = RandomStringUtils.randomAlphanumeric(15);
    		} while (entryMapDb.containsKey(entry)); // 如果key重复那么继续随机..
    		position.setEntry(entry);
    	}
    	
    	// 如果为百一测评微信号，为其生成随机单词
        if(Position.WEIXIN_COMPANY_101 == position.getWeixinCompany())
        {
            // passport为空，生成passport
            if(StringUtils.isBlank(position.getPassport()))
            {
                position.setPassport(passcodeAvailableDao.getAvailablePassCode());
            }
        }else
        { 
            // 如果为公司自己微信号，并且passport不为空，回收passport
            // modify by lipan 2014年10月15日 为了让所有测评都具备
//            if(!StringUtils.isBlank(position.getPassport()))
//            {
//                passcodeAvailableDao.recyclePassCode(position.getPassport());
//                position.setPassport("");
//            }
        }
    }
    
    /**
     * 查询活动地址列表
     */
    @Override
    public List<ActivityRecruitAddress> getActivityAddressList(ActivityRecruitAddress address)
            throws PFServiceException
    {
        return activityRecruitAddressDao.getActivityAddressList(address);
    }
    
    
    /**
     *查询校招测评
     */
    @Override
    public Position queryCampus(Integer employerId , Integer positionId) throws PFServiceException
    {
        // 查询测评信息
        Position positionDb = positionDao.getEntity(positionId);
        
        Position position = new Position();
        PropsUtils.copyProperties(position, positionDb);
        
        // 清空不必须字段
        position.setEmployerId(null); // 
        position.setPositionDesc(null);
        position.setPositionState(null);
        position.setSeriesId(null);
        position.setLevel(null);
        position.setPublishDate(null);
        position.setPreBuilt(null);
        position.setPassport(null);
        position.setEntry(null);
        
        // 如果已发邀请，那么测评不能编辑...
        long inviteNum = invite.getInvitationNum(employerId, positionId);
        if (inviteNum > 0) {
            position.setEditable(0);
        } else {
            position.setEditable(1);//可以编辑
        }
        
        if (null != position)
        {
            // 查询常规信息
            Collection<ConfigInfoExtEx> infoExt = infoCollect.getInfoExt(employerId, positionId);
            if (!CollectionUtils.isEmpty(infoExt))
            {
                position.setInfoExt(infoExt);
            }
            
            // 查询宣讲会信息
            List<CompanyRecruitActivity> activityList = companyRecruitActivityDao.getActivityList(positionId);
            if(!CollectionUtils.isEmpty(activityList))
            {
                // 系统当前时间
                long currentMillis = DateUtils.getCurrentMillis();
                // 活动时间与当前时间的offtime
                long offMillis = 0; 
                // 开始时间距离当前时间最近的活动的索引  
                int index = 0;
                for(int i=0; i<activityList.size() ; i++)
                {
                    CompanyRecruitActivity activity = activityList.get(i);
                    
                    String addressIds = activity.getAddressId();
  				    String[] addressIdsArray = addressIds.split(",");
  				    List<String> addresses = new ArrayList<String>();
  				    for(String addressid : addressIdsArray){
  					     	ActivityRecruitAddress address = activityRecruitAddressDao
  								.getEntity(Integer.valueOf(addressid));
  					       addresses.add(address.getAddress());
						try {
							activity.copyPropertiesFromAddress(address);
						} catch (Exception e) {
							logger.error("copy properties error ", e);
						}
  				  }
  				  activity.setAddresses(addresses);
                /*    // 查询宣讲会地址信息
                    ActivityRecruitAddress address = activityRecruitAddressDao.getEntity(activity.getAddressId());
                    
                    if(null != address)
                    {
                        // 将地址信息封装到活动bean中
//                        PropsUtils.copyProperties(activity, address);
                        activity.copyPropertiesFromAddress(address);
                    }*/
                    
                    // 统计已开始考试的活动信息
                    if (CompanyRecruitActivity.TEST_STATE_USING == activity.getTestState() 
                    		|| CompanyRecruitActivity.TEST_STATE_DONE == activity.getTestState()) 
                    {
                    	 Map<Integer, Long> countMap = candidateTestDao.getTestCount(positionId, activity.getPasscode());
                    	 // 总人数
                    	 long total = 0;
                    	 
                    	 //　默认值
                    	 activity.setTodo(0L); // todo 
	           			 activity.setRecommend(0L); // 已推荐
	           			 activity.setWeedOut(0L); // 已淘汰
	           			 activity.setTotal(0L); // 总数
                    	 
                    	 for(Integer testResult : countMap.keySet())
                    	 {
                    		 // 
                    		 long count = countMap.get(testResult);
                    		 total += count;
                    		 if(CandidateTest.TEST_RESULT_TODO == testResult) // 待定
                    		 {
                    			 activity.setTodo(count);
                    		 }else if(CandidateTest.TEST_RESULT_RECOMMEND == testResult) // 已推荐
                    		 {
                    			 activity.setRecommend(count);
                    		 }else if(CandidateTest.TEST_RESULT_WEEDOUT == testResult) // 已淘汰
                    		 {
                    			 activity.setWeedOut(count);
                    		 }
                    	 }
                    	 activity.setTotal(total); // 总数
					}
                    // 默认值
                    activity.setIsCurrent(Constants.NEGATIVE);
                    try
                    {
                        // 距离当前时间的毫秒数..
                        long tempMillis = Math.abs(currentMillis - DateUtils.getDateMillis(activity.getActivityDate() +" "+activity.getBeginTime(), DateUtils.DATE_FORMAT_3));
                        if (i == 0)
                        {
                            offMillis = tempMillis;
                        }else
                        {
                            if(tempMillis < offMillis)
                            {
                                offMillis = tempMillis;
                                index = i;
                            }
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                // 距离当前时间最近的宣讲会
                activityList.get(index).setIsCurrent(Constants.POSITIVE);
                position.setActivityList(activityList);
            }
        }
        return position;
    }

    /**
     * 更新宣讲会地址信息
     */
    @Override
    public void updateActivityRecruitAddress(CompanyRecruitActivity activity)
            throws PFServiceException
    {
        // 没有活动id的记录不处理
        if(null == activity.getActivityId())
        {
            return;
        }
        
        CompanyRecruitActivity activityDb = companyRecruitActivityDao.getEntity(activity.getActivityId());
        
        // 更新地址信息
        if(null != activityDb && null != activityDb.getAddressId())
        {
            // 获得数据库中的地址信息
            ActivityRecruitAddress addressDb = activityRecruitAddressDao.getEntity(activityDb.getAddressId());
            try
            {
                // 将活动信息中的信息复制到数据库中的地址信息中
//                PropsUtils.copyProperties(addressDb, activity);
                addressDb.copyPropertiesFromActivity(activity);
                // 设置地址id
                addressDb.setAddressId(Integer.valueOf(activityDb.getAddressId()));
                // 更新时间
                addressDb.setCreateDate(DateUtils.getCurrentTimestamp());
                // 更新地址信息
                activityRecruitAddressDao.saveOrUpdate(addressDb);
            } catch (Exception e)
            {
                throw new PFServiceException(e);
            }
        }
    }

    /**
     * 重新获取passport
     * @param positionId
     * @return
     * @throws PFServiceException
     */
    @Override
    public CampusRsp refreshCampusPassport(Integer positionId) throws PFServiceException
    {
        CampusRsp rsp = new CampusRsp();
        // 查询测评信息
        Position positionDb = positionDao.getEntity(positionId);
        
        // 百一微信号
        if(positionDb.getWeixinCompany() == Constants.NEGATIVE)
        {
            if(!StringUtils.isBlank(positionDb.getPassport()))
            {
                // 生成随机passport
                String passport = passcodeAvailableDao.getAvailablePassCode();
                // 回收旧的passport
                passcodeAvailableDao.recyclePassCode(positionDb.getPassport());
                // 保存新的passport
                positionDb.setPassport(passport);
                // 返回之...
                rsp.setPassport(passport);
            }
        }
        return rsp;
    }

    @Override
    public PFResponse updatePositionPassport(Integer positionId , String passport){
    	PFResponse pfResponse = new PFResponse();
    	pfResponse.setCode(FuncBaseResponse.SUCCESS);
    	/*Position idToPosition = positionDao.getEntity(positionId);
    	String oldPasscode = idToPosition.getPassport();*/
    	
    	PasscodeAvailable passAvailable = passcodeAvailableDao.getPasscodeAvailable(passport);
    	if(passAvailable != null){
    	    if(passAvailable.getStatus() == 1){
    	    	Position position = positionDao.getPositionObjByPassport(passport);
    	    	if(position != null && position.getPositionId().equals(positionId)){//被自己用
        	    	logger.debug("the passport  used be it self  , positionId {} , passport {} ", positionId,passport);
            		return pfResponse;
        		}
    	    	logger.debug("the passport has in avaiable and used , positionId {} , passport {} ", positionId,passport);
    	    	pfResponse.setCode("USED");
        		return pfResponse;
    	    }else{
    	    	passAvailable.setStatus(1);
    	    	passcodeAvailableDao.saveOrUpdate(passAvailable);
    	    	Position position = positionDao.getEntity(positionId);
    	    	position.setPassport(passport);
    	    	positionDao.saveOrUpdate(position);
    	    	
    	    	/*if(!passport.equals(oldPasscode)){
    	    		passcodeAvailableDao.recyclePassCode(oldPasscode);
    	    	}*/
    	    	
    	    	return pfResponse;
    	    }
    	}else{
    		Position position = positionDao.getPositionObjByPassport(passport);
    		if(position != null && !position.getPositionId().equals(positionId)){//不是修改自己
    	    	logger.debug("the passport has in position and used , positionId {} , passport {} ", positionId,passport);
    			pfResponse.setCode("USED");
        		return pfResponse;
    		}else{
    		    position = positionDao.getEntity(positionId);
    	    	position.setPassport(passport);
    	    	positionDao.saveOrUpdate(position);
    	    	
    	    	/*if(!passport.equals(oldPasscode)){
    	    		passcodeAvailableDao.recyclePassCode(oldPasscode);
    	    	}*/
    	    	
    	    	return pfResponse;
    		}
    	}
    	
    }
    /**
     * 获取测评passport
     * @param positionId
     * @return
     * @throws PFServiceException
     */
    @Override
    public CampusRsp getCampusPassport(Integer positionId) throws PFServiceException
    {
        CampusRsp rsp = new CampusRsp();
        
        // 查询测评信息
        Position positionDb = positionDao.getEntity(positionId);
        if(null != positionDb)
        {
            rsp.setEntry(positionDb.getEntry());
            rsp.setWeixinCompany(positionDb.getWeixinCompany());
            rsp.setPassport(positionDb.getPassport());
        }
        return rsp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CampusRsp getAddListByEpPassport(String authCode) throws PFServiceException
    {
        CampusRsp rsp = new CampusRsp();
        // 校验authCode
        Employer employer = employerDao.getEntity(StringUtils.trim(authCode), "authCode");
        if (null == employer)
        {
            // 口令不存在
            rsp.setErrorCode(BaseResponse.EBADDESC);
            rsp.setErrorDesc("口令不存在");
            return rsp;
        }
        rsp.setSignalTester(employer.getEmployerId());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("employerId", employer.getEmployerId()); // 应聘者Id
        params.put("testType", Constants.TEST_TYPE_SCHOOL); // 校招类型
        // 获得招聘者的所有测评
        List<Position> positionList = positionDao.getList(params);
        if (!CollectionUtils.isEmpty(positionList))
        {
            // 所有测评的地址信息...
            List<ActivityRecruitAddress> addressListRsp = new ArrayList<ActivityRecruitAddress>();
            for (Position position : positionList)
            {
                // 某个测评的地址信息
                List<ActivityRecruitAddress> addressList = activityRecruitAddressDao.getAddListByPositionId(position.getPositionId());
                if (!CollectionUtils.isEmpty(addressList))
                {
                    addressListRsp = (List<ActivityRecruitAddress>) CollectionUtils.union(addressListRsp, addressList);
                }
            }
            if (!CollectionUtils.isEmpty(addressListRsp))
            {
                rsp.setAddressList(addressListRsp);
            }
            
        }
        return rsp;
    }

    @Override
    public List<String> getDetailAddList(ActivityRecruitAddress address) throws PFServiceException
    {
        return activityRecruitAddressDao.getDetailAddList(address);
    }
    
    @Override
    public CampusRsp saveAddress(ActivityRecruitAddress address) throws PFServiceException
    {
        CampusRsp campusRsp = new CampusRsp();
        // 清空不必要的值
        address.setAddressId(null);
//        address.setSeatNumber(null);
        address.setCmcNum(null);
        address.setCucNum(null);
        address.setCtcNum(null);
        // 更新时间
        address.setCreateDate(DateUtils.getCurrentTimestamp());
        // 查询db中的值
        Map<String,Object> params= new HashMap<String, Object>();
        params.put("city", address.getCity());
        params.put("college", address.getCollege());
        params.put("address", address.getAddress());
        ActivityRecruitAddress addressDb = activityRecruitAddressDao.getEntity(params);
        if (null != addressDb)
        {
            PropsUtils.copyProperties(addressDb, address);
            campusRsp.setAddressId(addressDb.getAddressId());
        }else
        {
            activityRecruitAddressDao.saveOrUpdate(address);
            campusRsp.setAddressId(address.getAddressId());
        }
        return campusRsp;
    }
    
    @Override
    public CampusRsp updateAddress(ActivityRecruitAddress address) throws PFServiceException
    {
        CampusRsp campusRsp = new CampusRsp();
        if (null == address.getAddressId())
        {
            campusRsp.setErrorCode(BaseResponse.EINVAL);
            campusRsp.setErrorDesc("缺少地址Id");
            return campusRsp;
        }
        // 清空不必要的值
        address.setAddress(null);
        address.setCity(null);
        address.setCollege(null);
        address.setSeatNumber(null);
        // 更新时间
        address.setCreateDate(DateUtils.getCurrentTimestamp());
        // 查询db中的值
        Map<String,Object> params= new HashMap<String, Object>();
        params.put("addressId", address.getAddressId());
        ActivityRecruitAddress addressDb = activityRecruitAddressDao.getEntity(params);
        if (null == addressDb)
        {
            campusRsp.setErrorCode(BaseResponse.EINVAL);
            campusRsp.setErrorDesc("没有找到学校地址");
            return campusRsp;
        }
        PropsUtils.copyProperties(addressDb, address);
        // 如果联通、移动已完成测速，计算最终结果
        if (null!=addressDb.getCucNum() && null!=addressDb.getCmcNum())
        {
            ConfigMobileRatio ratio = configMobileRatioDao.getCityRatio(addressDb.getCity());
            if (null == ratio) // 为空时取默认值
            {
                ratio = configMobileRatioDao.getDefaultRatio();
            }
            List<Double> numList = new ArrayList<Double>();
            numList.add(addressDb.getCucNum()*100/ratio.getUnicomRatio());
            numList.add(addressDb.getCmcNum()*100/ratio.getMobileRatio());
            if (null!=addressDb.getCtcNum()) // 如果有电信的数据
            {
                numList.add(addressDb.getCtcNum()*100/ratio.getTelecomRatio());
            }
            Integer min = Collections.min(numList).intValue();
            addressDb.setTotalNum(min);
            
            //调用老潘发邮件接口
            finishedTestAddress(addressDb.getAddressId());
        }
        
        activityRecruitAddressDao.saveOrUpdate(addressDb);
        campusRsp.setAddressId(addressDb.getAddressId());
        campusRsp.setNum(addressDb.getTotalNum());
        return campusRsp;
    }

    @Override
    public CampusRsp saveSpeedTestLog(List<ActivityAddressSignal> signalList) throws PFServiceException
    {
        CampusRsp rsp = new CampusRsp();
        int num = 0;
        for (ActivityAddressSignal signal : signalList)
        {
            if (null == signal.getAddressId())
            {
                signal.setAddressId(-1);
            }
//            // 校验测试人Id
//            Employer employer = employerDao.getEntity(signal.getSignalTester() , "employerId");
//            if (null == employer)
//            {
//                // 口令不存在
//                rsp.setErrorCode(BaseResponse.EBADDESC);
//                rsp.setErrorDesc("测试人Id不合法");
//                return rsp;
//            }
            // 测试开始时间
            signal.setBegin_timeT(DateUtils.millis2Timestamp(signal.getBegin_time()));
            // 测试结束时间
            signal.setEnd_timeT(DateUtils.millis2Timestamp(signal.getEnd_time()));
            // 测试时长
            signal.setDuration((int)(signal.getEnd_time() - signal.getBegin_time()));
            // 创建时间
            signal.setCreate_date(DateUtils.getCurrentTimestamp());
            // 保存日志信息
            activityAddressSignalDao.saveOrUpdate(signal);
            num++;
        }
        rsp.setNum(num);
        return rsp;
    }

    /**
     * 查询校招转在线考试设置信息（考试终止时间）
     * @param positionId
     * @return
     * @throws PFServiceException
     */
    @Override
    public SignUpOnlineSetsRsp querySets(Integer positionId) throws PFServiceException
    {
        Position position = positionDao.getEntity(positionId);
        if (null != position)
        {
            // 设置测评口令
            if (StringUtils.isBlank(position.getPassport()))
            {
                position.setPassport(passcodeAvailableDao.getAvailablePassCode());
            }
            
            // 当前时间、半小时/整点                
            Calendar cal = Calendar.getInstance(Locale.CHINA);
            cal.setTimeInMillis(System.currentTimeMillis());
            if(cal.get(Calendar.MINUTE)<30)
            {
                cal.set(Calendar.MINUTE, 30);
            }else
            {
                cal.set(Calendar.MINUTE, 0);
                cal.add(Calendar.HOUR_OF_DAY, 1);
            }
            Timestamp endDate = DateUtils.millis2Timestamp(cal.getTimeInMillis());
            
            // 设置申请时间
            if (null == position.getOnlineReqEndDate())
            {
                
                position.setOnlineReqEndDate(endDate);
            }
            // 考试结束时间默认值
            if (null == position.getExamEndDate())
            {
                position.setExamEndDate(endDate);
            }
            positionDao.saveOrUpdate(position);
            SignUpOnlineSetsRsp rsp = new SignUpOnlineSetsRsp();
            rsp.setPassport(position.getPassport());
            rsp.setOnlineReqEndDate(DateUtils.getDateStr(DateUtils.timestamp2Date(position.getOnlineReqEndDate()), "yyyy-MM-dd|HH:mm"));
            rsp.setExamEndDate(DateUtils.getDateStr(DateUtils.timestamp2Date(position.getExamEndDate()), "yyyy-MM-dd|HH:mm"));
            return rsp;
        }
        return null;
    }

    /**
     * 更新校招转在线考试设置信息（考试终止时间）
     * @param 
     * @return
     * @throws PFServiceException
     */
    @Override
    public void updateSets(Integer positionId,String reqEndDate, String examEndDate) throws PFServiceException
    {
        Position position = positionDao.getEntity(positionId);
        if (null != position)
        {
            position.setOnlineReqEndDate(DateUtils.getTimestamp(reqEndDate, "yyyy-MM-dd|HH:mm"));
            position.setExamEndDate(DateUtils.getTimestamp(examEndDate, "yyyy-MM-dd|HH:mm"));
            positionDao.saveOrUpdate(position);
        }
    }

	@Override
	public CampusRsp createGroupPosition(PositionGroupInfo position) throws PFServiceException {
		logger.debug("createGroupPosition position {}  ", position);
		 CampusRsp res = new CampusRsp();
		try {
			position.setWeixinCompany(Position.WEIXIN_COMPANY_101);//统一为百一的 生成position的passport
			PaperRelationInfo paperRelationInfo = position.getPaperRelationInfo();
			List<Integer> choosedPaperIds = paperRelationInfo.getChoosePaperIds();
			List<Integer> mustPaperIds = paperRelationInfo.getMustPaperIds();
			List<CampusRsp> mustPositionRs = new ArrayList<CampusRsp>();
			List<CampusRsp> choosedPositionRs = new ArrayList<CampusRsp>();
			if(CollectionUtils.isEmpty(choosedPaperIds) && CollectionUtils.isEmpty(mustPaperIds)){
				logger.error("choosedPaperIds and mustPaperIds are both null ");
				throw new PFServiceException("choosedPaperIds and mustPaperIds are both null ");
			}
			
			if(choosedPaperIds != null && choosedPaperIds.size() == 1){
				logger.error("choosedPaperIds must not be one ");
				throw new PFServiceException("choosedPaperIds must not be one ");
			}
		   /*if(CollectionUtils.isEmpty(choosedPaperIds) && mustPaperIds.size() == 1){
				logger.debug("only one mustPaperIds to process for position {} ", position);
				//按正常一个测评处理
				CampusRsp rsp = createCampusPosition(position);
                return rsp;
			}*/
			if(choosedPaperIds != null && mustPaperIds != null &&  choosedPaperIds.removeAll(mustPaperIds)){
				logger.error("have the same paperIds {}  ",choosedPaperIds);
				throw new PFServiceException("have the same paperIds  " + choosedPaperIds);
			}
			if (CollectionUtils.isNotEmpty(choosedPaperIds)) {
				for (Integer paperId : choosedPaperIds) {
					Position newPosition = new Position();
					PropertyUtils.copyProperties(newPosition, position);
					newPosition.setGroupFlag(Constants.GROUP_FLAG_CHILD);
					Paper paper = paperDao.getEntity(paperId);
					newPosition.setPositionName(paper.getPaperName());
					newPosition.setPaperId(paperId);
					CampusRsp rsp = createCampusPosition(newPosition);
					choosedPositionRs.add(rsp);
				}
			}
			if (CollectionUtils.isNotEmpty(mustPaperIds)) {
				for (Integer paperId : mustPaperIds) {
					Position newPosition = new Position();
					PropertyUtils.copyProperties(newPosition, position);
					newPosition.setGroupFlag(Constants.GROUP_FLAG_CHILD);
					Paper paper = paperDao.getEntity(paperId);
					newPosition.setPositionName(paper.getPaperName());
					newPosition.setPaperId(paperId);
					CampusRsp rsp = createCampusPosition(newPosition);
					mustPositionRs.add(rsp);
				}
			}

			Position groupPosition = new Position();
			PropertyUtils.copyProperties(groupPosition, position);
			groupPosition.setGroupFlag(Constants.GROUP_FLAG_PARENT);
			groupPosition.setPaperId(null);
			res = createCampusPosition(groupPosition);
			
			for (CampusRsp rsp : mustPositionRs) {
				  PositionRelation positionRelation = new PositionRelation();
                  positionRelation.setId(new PositionRelationId(res.getPositionId(), rsp.getPositionId()));
                  positionRelation.setRelation(Constants.POSITION_RELATION_MUST);
                  positionRelationDao.save(positionRelation);
				
			}
			for(CampusRsp rsp : choosedPositionRs){
                  PositionRelation positionRelation = new PositionRelation();
                  positionRelation.setId(new PositionRelationId(res.getPositionId(), rsp.getPositionId()));
                  positionRelation.setRelation(Constants.POSITION_RELATION_CHOOSE);
                  positionRelationDao.save(positionRelation);
			}

		}catch(Exception e){
			 logger.error("error create group position ", e);
			 throw new PFServiceException(e);
		 }
		return res;
	}
	/**
	 * 完成地址测试时调用， 此方法在有些宣讲会所有地址都测试完成后会发邮件
	 * @param addressId
	 */
	@Override
	public  void finishedTestAddress(int addressIdInt){
		List<CompanyRecruitActivity> activities = companyRecruitActivityDao.getCompanyRecruitActivitiesByAddressId(addressIdInt);
		logger.debug("finishedTestAddress  ,activities size is {} addressIdInt {} ", activities.size(),addressIdInt);
		for(CompanyRecruitActivity activity : activities){
			if(activity.getNotifyTestResult() != 0){//已经通知
				continue;
			}
			String addressIds = activity.getAddressId();
			if(StringUtils.isEmpty(addressIds)){
				continue;
			}
			String[] addressIdsArray = addressIds.split(",");
			 boolean needSendMail = true;
			for(String addressId : addressIdsArray){
				ActivityRecruitAddress address = activityRecruitAddressDao.getEntity(Integer.valueOf(addressId));
				if(address.getTotalNum() == null){//此宣讲会有没有完成测试的地址
					needSendMail = false;
					break;
				}
			}
			if(needSendMail){//需要发送邮件
				logger.debug("sendTestFinishedMail start ... for acitivityId {}  ", activity.getActivityId());
				PFResponse pf = sendTestFinishedMail(activity);
				if(pf.getCode().equals(FuncBaseResponse.SUCCESS)){
					activity.setNotifyTestResult(1);
				}else{
					activity.setNotifyTestResult(-1);
				}
				companyRecruitActivityDao.saveOrUpdate(activity);
			}
		}
	}
	
	private PFResponse sendTestFinishedMail(CompanyRecruitActivity activity){
		PFResponse pfResponse = new PFResponse();
		try {
			Position position = positionDao.getEntity(activity.getPositionId());
			Employer employer = employerDao.getEntity(position.getEmployerId());
//			contactInfoDaoImpl.saveOrUpdate(contactInfo);
			MailSenderInfo mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(employer.getCompanyId(), "companyId"));
			mailSenderInfo.setSubject("宣讲会考试地址勘测信息");
			TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();
			
			Map<String,String> params = new HashMap<String, String>();
			String addressIds = activity.getAddressId();
			String[] addressIdsArray = addressIds.split(",");
			String college = null;
			StringBuffer sbAddresses = new StringBuffer();
			int maxNumber = 0;
			for(String addressId : addressIdsArray){
				ActivityRecruitAddress address = activityRecruitAddressDao.getEntity(Integer.valueOf(addressId));
				if(college == null){
					college = address.getCollege();
				}
				if(maxNumber < address.getTotalNum()){
					maxNumber = address.getTotalNum();
				}
				sbAddresses.append(address.getAddress()).append(",");
			}
			sbAddresses.deleteCharAt(sbAddresses.length() -1);
			params.put("employerName", employer.getEmployerName());
			params.put("college", college);
			params.put("addresses",sbAddresses.toString()) ;
	        params.put("seatNumber",  maxNumber + "");
			params.put("path", "finishedTest");
			context.put("entity", params);
			mailSenderInfo.setToAddress(employer.getEmployerAcct());
			mailSenderInfo.setContent(templateHost.makeFileString(TemplateHost.VM_PUBLICTEMPLATE));
			SimpleMailSender.sendHtmlMail(mailSenderInfo);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			logger.error("error to  sendTestFinishedMail ", e);
			pfResponse.setCode(FuncBaseResponse.SENDMAILERROR);
		}
		return pfResponse;
	
	}

	public void processUnTestActivityAddresses(int employerId){
	   List<CompanyRecruitActivity> activities = companyRecruitActivityDao.getCompanyRecruitActivity(employerId,null);
	   logger.debug("have acitiviyies size is {} for employerId {} ", activities.size(),employerId);
	   List<CompanyRecruitActivity> needSendMailActivities = new ArrayList<CompanyRecruitActivity>();
	   Map<Integer, CompanyRecruitActivity> addreessIdToMinTimeAct = new HashMap<Integer, CompanyRecruitActivity>();//地址最小时间
	   long minStamp = Long.MAX_VALUE;
	   for(CompanyRecruitActivity activity : activities){
		   long beginTime = DateUtils.getDateMillis(activity.getActivityDate() +" "+activity.getBeginTime(), DateUtils.DATE_FORMAT_3);
		   if (System.currentTimeMillis() > beginTime) {
				logger.debug(
						"the activity  beginTime is before now , so do not need send , beginTime {},activityId {} ",
						activity.getActivityDate() + " " + activity.getBeginTime(), activity.getActivityId());
				continue;
			}
		   if(beginTime - System.currentTimeMillis() <= 7 * 24 * 3600 * 1000){//7天内
			   activity.setIsCurrent(1);//邮件显示红色表示
		   }
		    String addressIds = activity.getAddressId();
			if(StringUtils.isEmpty(addressIds)){
				continue;
			}
			
			String[] addressIdsArray = addressIds.split(",");
			boolean needToSend = false;
			StringBuffer sb = new StringBuffer();
			for(String addressId : addressIdsArray){
				ActivityRecruitAddress address = activityRecruitAddressDao.getEntity(Integer.valueOf(addressId));
				if(StringUtils.isEmpty(activity.getCollege())){
					activity.setCollege(address.getCollege());
				}
				if(address.getTotalNum() == null){//此宣讲会有没有完成测试的地址
					needToSend = true;
					
					sb.append(address.getAddress() + ",");
					CompanyRecruitActivity oldActivity = addreessIdToMinTimeAct.get(Integer.valueOf(addressId)) ;
					if(oldActivity != null){//已经存在此地址的宣讲会
						needToSend = false;
						if(minStamp > beginTime){
							minStamp = beginTime;
							oldActivity.setMinDate(activity.getActivityDate());
							oldActivity.setMinTime(activity.getBeginTime());
						}
					}else{
						minStamp = beginTime;
						addreessIdToMinTimeAct.put(Integer.valueOf(addressId), activity);
					}
					
				}
				
			}
			if(needToSend){
				activity.setAddress(sb.deleteCharAt(sb.length() - 1).toString());
				needSendMailActivities.add(activity);
			}
	   }
		
	   if(needSendMailActivities.size() > 0){
		   logger.debug("needSendMailActivities size is {} for employerId {} ", needSendMailActivities.size(),employerId);
		   sendToTestActivityAddresses(needSendMailActivities, employerId);
	   }
		
	}
	
	private PFResponse sendToTestActivityAddresses(List<CompanyRecruitActivity> needSendMailActivities, int employerId){

		PFResponse pfResponse = new PFResponse();
		try {
			Employer employer = employerDao.getEntity(employerId);
//			contactInfoDaoImpl.saveOrUpdate(contactInfo);
			MailSenderInfo mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(employer.getCompanyId(), "companyId"));
			mailSenderInfo.setSubject("宣讲会未勘测考点信息");
			TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("employerName", employer.getEmployerName());
			params.put("path", "notifyToTest");
			params.put("activities", needSendMailActivities);
			context.put("entity", params);
			mailSenderInfo.setToAddress(employer.getEmployerAcct());
			mailSenderInfo.setContent(templateHost.makeFileString(TemplateHost.VM_PUBLICTEMPLATE));
			SimpleMailSender.sendHtmlMail(mailSenderInfo);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			logger.error("error to  sendToTestActivityAddresses ", e);
			pfResponse.setCode(FuncBaseResponse.SENDMAILERROR);
		}
		return pfResponse;
	
	
	}
	@Override
	public List<ActivityRecruitAddress> getUnTestActivityRecruitAddresses() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<ActivityRecruitAddress> getAddresseSignals(String keyword, Interval num)
    {
        return activityRecruitAddressDao.getAddresseSignals(keyword, num);
    }
}
