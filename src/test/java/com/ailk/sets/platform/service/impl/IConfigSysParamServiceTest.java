/**
 * author :  lipan
 * filename :  IConfigSysParamServiceTest.java
 * create_time : 2014年8月20日 下午5:11:06
 */
package com.ailk.sets.platform.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.empl.service.IConfigSysParamService;

/**
 * @author : lipan
 * @create_time : 2014年8月20日 下午5:11:06
 * @desc : 系统常量获取
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/localbean.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional(rollbackFor = Exception.class)
public class IConfigSysParamServiceTest
{

   
    @Autowired
    private IConfigSysParamService iConfigSysParamService;
    
    @Test
    public void getConfigSysParam()
    {
        System.out.println(iConfigSysParamService.getConfigSysParam(Constants.SPEED_TEST_DOWNLOAD_URL));
    }
}
