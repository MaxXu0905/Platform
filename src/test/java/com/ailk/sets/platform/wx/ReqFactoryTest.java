/**
 * author :  lipan
 * filename :  ReqFactoryTest.java
 * create_time : 2014年7月26日 上午11:24:40
 */
package com.ailk.sets.platform.wx;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.intf.model.wx.IWXService;
import com.ailk.sets.platform.intf.model.wx.req.ReqFactory;

/**
 * @author : lipan
 * @create_time : 2014年7月26日 上午11:24:40
 * @desc : 测试微信请求处理类
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/beans.xml", "/spring/provider.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional(rollbackFor = Exception.class)
public class ReqFactoryTest
{   
    @Autowired
    @Qualifier("wxCandServiceImpl")
    private IWXService wxService;
    
    @Test
    public void getReq()
    {
        String subscribe = "<xml>"+
                "<ToUserName><![CDATA[toUser]]></ToUserName>"+
                "<FromUserName><![CDATA[obivdt_1_XclG2XIydo7DQlDpna0]]></FromUserName>"+
                "<CreateTime>123456789</CreateTime>"+
                "<MsgType><![CDATA[event]]></MsgType>"+
                "<Event><![CDATA[subscribe]]></Event>"+
                "<EventKey></EventKey>"+
                "</xml>";
        ReqFactory reqFactory = new ReqFactory(String2InputStream(subscribe), wxService);
        try
        {
            reqFactory.getReq();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static InputStream String2InputStream(String str){
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
     }

}
