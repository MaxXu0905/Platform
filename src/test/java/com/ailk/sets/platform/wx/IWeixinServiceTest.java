/**
 * author :  lipan
 * filename :  IWeixinServiceTest.java
 * create_time : 2014年7月28日 下午10:02:48
 */
package com.ailk.sets.platform.wx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.intf.wx.service.IWeixinService;

/**
 * @author : lipan
 * @create_time : 2014年7月28日 下午10:02:48
 * @desc : 微信接口类
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/provider.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional(rollbackFor = Exception.class)
public class IWeixinServiceTest
{
    @Autowired
    private IWeixinService iWeixinService;
    
    @Test
    public void getAuthInfoByCode()
    {
        try
        {
            iWeixinService.getAuthInfoByCode("1");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
