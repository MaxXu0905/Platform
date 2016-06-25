/**
 * author :  lipan
 * filename :  PositionImplTest.java
 * create_time : 2014年7月3日 下午9:18:26
 */
package com.ailk.sets.platform.empl.service.impl;

import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.IPosition;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.position.PosMessage;
import com.ailk.sets.platform.intf.model.position.PositionInfo;
import com.ailk.sets.platform.intf.model.position.PositionPaperInfo;

/**
 * @author : lipan
 * @create_time : 2014年7月3日 下午9:18:26
 * @desc : 查询测评Test
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class PositionImplTest
{
    static ApplicationContext context;
    static
    {
        PropertyConfigurator
                .configure(LabelAnalysisImplTest.class.getResource("/log4j.properties"));
        context = new ClassPathXmlApplicationContext(new String[] {"/spring/localbean.xml","/spring/beans.xml"  });
    }

    @Test
    public void getPositionInfo()
    {
        IPosition iPosition = (IPosition)context.getBean("position");
        try
        {
           List<PositionInfo> list =  iPosition.getPositionInfo(100000073, new Page(10, 1));
           for (PositionInfo positionInfo : list)
            {
               List<PosMessage> posMessage = positionInfo.getPosMessage();
               System.out.println(posMessage.size());
            }
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getPositionInfo2()
    {
        IPosition iPosition = (IPosition)context.getBean("position");
        try
        {
            iPosition.getPositionInfo(1162,100000069,0,"afqr3134af");
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
    }
     
    
    @Test
    public void getPositionInfoWithPaperCount() throws Exception
    {
        IPosition iPosition = (IPosition)context.getBean("position");
         List<PositionPaperInfo> list =  iPosition.getPositionInfoWithPaperCount(100000114, new Page(10, 1));
         System.out.println(list);
    }
    
    
    @Test
    public void getPositionByPositionId() throws Exception
    {
        IPosition iPosition = (IPosition)context.getBean("position");
         Position p =  iPosition.getPositionByPositionId(100000120, 1619);
         System.out.println(p);
    }
    
    @Test
    public void getPositionActivityAddressInfo() throws Exception
    {
        IPosition iPosition = (IPosition)context.getBean("position");
         Object p =  iPosition.getPositionActivityAddressInfo(1619);
         System.out.println(p);
    }
    
}
