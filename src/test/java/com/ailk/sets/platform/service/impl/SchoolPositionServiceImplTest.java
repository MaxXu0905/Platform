package com.ailk.sets.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.domain.ActivityAddressSignal;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivityCount;
import com.ailk.sets.platform.intf.empl.domain.PaperRelationInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionGroupInfo;
import com.ailk.sets.platform.intf.empl.domain.PositionInfoConfig;
import com.ailk.sets.platform.intf.empl.service.ISchoolPositionService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.campus.CampusRsp;
import com.ailk.sets.platform.intf.model.condition.Interval;
import com.ailk.sets.platform.service.SchoolPositionServiceImpl;

public class SchoolPositionServiceImplTest {
	static ApplicationContext context;
	static ISchoolPositionService schoolPositionService ;
	static{
		PropertyConfigurator.configure(SchoolPositionServiceImplTest.class.getResource("/log4j.properties"));
		 context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
		  schoolPositionService = (ISchoolPositionService)context.getBean("schoolPositionService");
		
	}
	
	@Test
	public  void getCompanyRecruitActivityCount(){
		CompanyRecruitActivityCount count = schoolPositionService.getCompanyRecruitActivityCount(3);
		System.out.println(count);
	}
	
	@Test
	public void getActivitiesByEmployerId(){
		List<CompanyRecruitActivity> list = schoolPositionService.getActivitiesByEmployerId(100000048, null);
		for(CompanyRecruitActivity a : list)
		System.out.println(a.getActivityId());
	}
	
    @Test
    public void addCompanyRecruitActivity()
    {
        
        Position position = new Position();
        
        position.setPositionId(1188); //  TODO 更新操作ID
//        position.setPositionName("校招111111"); // 测评名称
//        position.setEmployerId(100000048); // 发布人
//        position.setPositionAlias("无"); // 别名，发送邀请时给候选人看见的名称
//        position.setPositionState(0);
//        position.setEmailTemplate("无"); // 邮件模板
//        position.setTestType(1024); // 评测类型
//        position.setPaperId(-1); // 试卷ID
//        position.setPreBuilt(1); // 是否预置: 1 - 系统; 0 - 定制
//        position.setPassport("测试"); // 测评口令
//        position.setNotifyScore(1); // 答题结束后是否让学生知晓分数
//        position.setWeixinOnsite(1); // 是否启用微信答题
//        position.setWeixinCompany(0); // 企业是否有微信公众号: 无、有
//        position.setWeixinMode(1); // 企业是否有微信公众号: 无、编辑者模式、开发者模式
        position.setActivityUrl("www.google.com"); // 宣讲会具体信息网址
        
        CompanyRecruitActivity activity1 = new CompanyRecruitActivity();
        // TODO 更新操作ID
        activity1.setActivityId(189);
        activity1.setCity("湖南");// 城市
//        activity1.setCollege("湘潭大学2");// 大学
//        activity1.setAddress("第二教室");// 详细地址
//        activity1.setSeatNumber(100);// 座位数量
//        activity1.setSignalStrength(-90);// 号强度
//        activity1.setActivityDate("20140404");// 活动日期
//        activity1.setBeginTime("上9点");// 开始时间
//        activity1.setEndTime("上午10点");// 结束时间

        CompanyRecruitActivity activity2 = new CompanyRecruitActivity();
        // TODO 更新操作ID
        activity2.setActivityId(190);
        activity2.setCity("北京");// 城市
//        activity2.setCollege("湘潭大学");// 大学
//        activity2.setAddress("第四教室");// 详细地址
//        activity2.setSeatNumber(150);// 座位数量
//        activity2.setSignalStrength(-70);// 号强度
//        activity2.setActivityDate("20140404");// 活动日期
//        activity2.setBeginTime("下午3点");// 开始时间
//        activity2.setEndTime("下午3点半");// 结束时间
        
        List<CompanyRecruitActivity> activityArray = new ArrayList<CompanyRecruitActivity>();
        activityArray.add(activity1);
        activityArray.add(activity2);
        
        
        PositionInfoConfig config1 = new PositionInfoConfig();
        config1.setInfoId("PHONE");
        config1.setSeq(1);
        
        PositionInfoConfig config2 = new PositionInfoConfig();
        config2.setInfoId("RESIDENT_ADDRESS");
        config2.setSeq(2);
        
        List<PositionInfoConfig> configInfo = new ArrayList<PositionInfoConfig>();
        configInfo.add(config1);
        configInfo.add(config2);
        
        // 宣讲会
        position.setActivityList(activityArray);
        
        // 常规信息
        position.setConfigInfo(configInfo);
        
        try
        {
            System.out.println(schoolPositionService.createCampusPosition(position).toString());
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getActivityAddressList()
    {
        
        ActivityRecruitAddress address = new ActivityRecruitAddress();
        address.setCity("北京");// 城市
        address.setCollege("北京大学");// 大学
        
        try
        {
            List<ActivityRecruitAddress> addresslList = schoolPositionService.getActivityAddressList(address);
            for (ActivityRecruitAddress addressD : addresslList)
            {
                System.out.println(addressD.toString());
            }
            
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
        
    }
    
    
    /**
     * 查询校招测评
     */
    @Test
    public void queryCampus()
    {
        try
        {
            Position position = schoolPositionService.queryCampus(100000069, 1175);
            List<CompanyRecruitActivity> activityList = position.getActivityList();
            for (int i = 0; i < activityList.size(); i++)
            {
                System.out.println("----------"+activityList.get(i).getIsCurrent());
            }
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 更新宣讲会地址信息
     * @param passport
     * @return
     * @throws PFServiceException
     */
    @Test
    public void updateActivityRecruitAddress()
    {
        try
        {
            CompanyRecruitActivity activity1 = new CompanyRecruitActivity();
            activity1.setActivityId(61);
            activity1.setCity("湖南");// 城市
            activity1.setCollege("湘潭大学2");// 大学
            activity1.setAddress("第二教室");// 详细地址
            activity1.setSeatNumber(100);// 座位数量
            activity1.setSignalStrength(-1000);// 号强度
            activity1.setActivityDate("20140404");// 活动日期
            activity1.setBeginTime("上9点");// 开始时间
            activity1.setEndTime("上午10点");// 结束时间
            schoolPositionService.updateActivityRecruitAddress(activity1);
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getCampusPassport()
    {
        try
        {
            CampusRsp campusPassport = schoolPositionService.getCampusPassport(1163);
            System.out.println(campusPassport.getPassport());
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void refreshCampusPassport()
    {
        try
        {
            CampusRsp campusPassport = schoolPositionService.refreshCampusPassport(1184);
            System.out.println(campusPassport.getPassport());
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getAddListByEpPassport()
    {
        try
        {
            CampusRsp rsp = schoolPositionService.getAddListByEpPassport("hello");
            System.out.println(rsp.getStatus());
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void saveAddress()
    {
        try
        {
            ActivityRecruitAddress add = new ActivityRecruitAddress();
            add.setCity("北京");
            add.setCollege("野鸡大学");
            add.setAddress("小窝");
            add.setSeatNumber(222222);
            add.setCmcNum(100);
            add.setCucNum(200);
            add.setCtcNum(300);
//            add.setDownload(200);
//            add.setSignalStrength(-40);
            add.setSignalTester(100000069);
            CampusRsp rsp = schoolPositionService.saveAddress(add);
            System.out.println(rsp.getStatus());
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void updateAddress()
    {
        try
        {
            ActivityRecruitAddress add = new ActivityRecruitAddress();
            add.setAddressId(157);
            add.setCity("xxxx");
            add.setCollege("xxxx");
            add.setAddress("xxxx");
            add.setSeatNumber(11111);
//            add.setCmcNum(100);
//            add.setCucNum(200);
//            add.setCtcNum(300);
            CampusRsp rsp = schoolPositionService.updateAddress(add);
            System.out.println(rsp.getStatus());
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void saveSpeedTestLog()
    {
        try
        {
            List<ActivityAddressSignal> list = new ArrayList<ActivityAddressSignal>();
            ActivityAddressSignal signal1 = new ActivityAddressSignal();
            signal1.setBegin_time(System.currentTimeMillis());
            signal1.setAddressId(61);
            signal1.setPhone_nbr("18801137101");
            signal1.setPhone_type("IPhone 6");
            signal1.setCarrier("CMC");
            signal1.setDownload(200);
            signal1.setSignalTester(100000069);
            signal1.setUpload(50);
            signal1.setSignal_strength(-50);
            signal1.setEnd_time(System.currentTimeMillis());
            list.add(signal1);
            
            ActivityAddressSignal signal2 = new ActivityAddressSignal();
            signal2.setBegin_time(System.currentTimeMillis());
            signal2.setAddressId(61);
            signal2.setPhone_nbr("18801137101");
            signal2.setPhone_type("IPhone 7");
            signal2.setCarrier("CMC");
            signal2.setDownload(200);
            signal2.setSignalTester(100000069);
            signal2.setUpload(50);
            signal2.setSignal_strength(-50);
            signal2.setEnd_time(System.currentTimeMillis());
            list.add(signal2);
            schoolPositionService.saveSpeedTestLog(list);
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void  getPosIntention() throws Exception{
    	List list = schoolPositionService.getPosIntention(100001212, 1150, 1);
    	
    	List list2 = schoolPositionService.getPosIntention(100001212, 157, 2);
    	System.out.println(list.size());
    }
    
    @Test
    public void querySets() throws Exception
    {
        System.out.println(schoolPositionService.querySets(1131).toString());
    }
    
    @Test
    public void updateSets() throws Exception
    {
        schoolPositionService.updateSets(1367, "2014-10-15|19-00", "2014-10-15|19-00");
    }
    
    @Test
    public void getDetailAddList()
    {
        
        ActivityRecruitAddress address = new ActivityRecruitAddress();
        address.setCity("北京");// 城市
        address.setCollege("北京大学");// 大学
        try
        {
            List<String> detailAddList = schoolPositionService.getDetailAddList(address);
            StringBuffer adds = new StringBuffer();
            for (int i = 0; i < detailAddList.size(); i++)
            {
                if (!adds.toString().equals(""))
                {
                    adds.append(",");
                }
                adds.append(detailAddList.get(i));
            }
            System.out.println("详细地址："+adds.toString());
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
        
    }
    @Test
    public void createGroupPosition() throws Exception{

        
        PositionGroupInfo position = new PositionGroupInfo();
        
//        position.setPositionId(1188); //  TODO 更新操作ID
        position.setPositionName("校招111111"); // 测评名称
        position.setEmployerId(100000114); // 发布人
        position.setPositionState(0);
        position.setTestType(1); // 评测类型
        position.setPreBuilt(1); // 是否预置: 1 - 系统; 0 - 定制
        position.setPassport("测试"); // 测评口令
        position.setNotifyScore(1); // 答题结束后是否让学生知晓分数
        position.setWeixinOnsite(1); // 是否启用微信答题
        position.setWeixinCompany(0); // 企业是否有微信公众号: 无、有
//        position.setWeixinMode(1); // 企业是否有微信公众号: 无、编辑者模式、开发者模式
        position.setActivityUrl("www.google.com"); // 宣讲会具体信息网址
        
        CompanyRecruitActivity activity1 = new CompanyRecruitActivity();
        // TODO 更新操作ID
//        activity1.setActivityId(189);
        activity1.setCity("湖南");// 城市
        activity1.setCollege("湘潭大学2");// 大学
        activity1.setAddress("第二教室333");// 详细地址
        activity1.setSeatNumber(100);// 座位数量
        activity1.setSignalStrength(-90);// 号强度
        activity1.setActivityDate("2014-11-14");// 活动日期
        activity1.setBeginTime("12:00");// 开始时间
        activity1.setEndTime("13:00");// 结束时间

        CompanyRecruitActivity activity2 = new CompanyRecruitActivity();
        // TODO 更新操作ID
//        activity2.setActivityId(190);
        activity2.setCity("北京");// 城市
        activity2.setCollege("湘潭大学");// 大学
        activity2.setAddress("第四教室");// 详细地址
        List<String> addesses = new ArrayList<String>();
        addesses.add("第一教室11111a");
        addesses.add("第2教室22222b");
        activity2.setAddresses(addesses);
        activity2.setSeatNumber(150);// 座位数量
        activity2.setSignalStrength(-70);// 号强度
        activity2.setActivityDate("2014-11-15");// 活动日期
        activity2.setBeginTime("12:00");// 开始时间
        activity2.setEndTime("13:00");// 结束时间
        
        List<CompanyRecruitActivity> activityArray = new ArrayList<CompanyRecruitActivity>();
        activityArray.add(activity1);
        activityArray.add(activity2);
        
        
        PositionInfoConfig config1 = new PositionInfoConfig();
        config1.setInfoId("PHONE");
        config1.setSeq(1);
        
        PositionInfoConfig config2 = new PositionInfoConfig();
        config2.setInfoId("RESIDENT_ADDRESS");
        config2.setSeq(2);
        
        List<PositionInfoConfig> configInfo = new ArrayList<PositionInfoConfig>();
        configInfo.add(config1);
        configInfo.add(config2);
        
        // 宣讲会
        position.setActivityList(activityArray);
        
        // 常规信息
        position.setConfigInfo(configInfo);
        
        PaperRelationInfo paperRelationInfo = new PaperRelationInfo();
        List<Integer> choosePaperIds = new ArrayList<Integer>();
        choosePaperIds.add(1315);
        choosePaperIds.add(1316);
        paperRelationInfo.setChoosePaperIds(choosePaperIds);
        
        List<Integer> mustPaperIds = new ArrayList<Integer>();
        mustPaperIds.add(894);
        paperRelationInfo.setMustPaperIds(mustPaperIds);
        
        position.setPaperRelationInfo(paperRelationInfo);
    	CampusRsp rsp = schoolPositionService.createGroupPosition(position);
    	System.out.println(rsp);
    }
    
    @Test
	public  void finishedTestAddress(){
    	schoolPositionService.finishedTestAddress(84);
	}
    
    @Test
    public void  processUnTestActivityAddresses(){
    	((SchoolPositionServiceImpl)schoolPositionService).processUnTestActivityAddresses(100001260);
    }

    @Test
    public void  getActivityAddresses(){
        List<ActivityRecruitAddress> addresseSignals = schoolPositionService.getAddresseSignals("北京", new Interval("1", "1000"));
        System.out.println(addresseSignals.size());
    }
}
