package com.ailk.sets.platform.service.local;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ailk.sets.grade.service.intf.IReportService;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.service.IOutCallService;
import com.ailk.sets.platform.util.DateUtils;

/**
 * 扫描新报告并发送邮件/微信 定时任务
 * 
 * @author : lipan
 * @create_time : 2014年8月14日 下午8:34:47
 * @desc :
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
@Service
public class NotifyScheduler
{
    private Logger logger = LoggerFactory.getLogger(NotifyScheduler.class);

    @Autowired
    private IReportService reportService;
    @Autowired
    private ISendNewReportService sendNewReportService;
    
    @Autowired
	private IOutCallService outCallService;

    private boolean scheduleOn = false; // 运行标识

    /**
     * 任务调度
     */
    @Scheduled(fixedDelay = 1000 * 60, initialDelay = 1000 * 10)
    public void schedule()
    {
        try
        {
            long scheduleTime = DateUtils.getCurrentMillis();

            logger.info("扫描新报告...[" + scheduleTime + "]");

            if (scheduleOn)
            {
                logger.info("上一次的报告还没发送完，等待...[" + scheduleTime + "]");
                return;
            } else
            {
                scheduleOn = true;
            }

            List<CandidateReport> list = reportService.getUnNotifiedReport();
            if (CollectionUtils.isNotEmpty(list))
            {
                logger.info("准备推送" + list.size() + "个新报告...[" + scheduleTime + "]");
                int successSize = 0;
                for (CandidateReport candidateReport : list)
                {
                    try
                    {
                        // 先设置已推送标识，再进行推送~
                        candidateReport.setNotified(1);
                        reportService.updateReport(candidateReport);
                        // 发送新报告通知
                        sendNewReportService.sendNewReport(candidateReport);
                        successSize += 1;
                        //通知状态变更，3:已出报告 modify by zengjie 14/8/18
                        outCallService.updateMrReportStatus(candidateReport.getTestId(),3);
                        //通知第三方报告数据
                        outCallService.giveMrReportData(candidateReport.getTestId());
                    } catch (Exception temp)
                    {
                        logger.error("新报告推送异常，报告id：" + candidateReport.getTestId() + "...["
                                + scheduleTime + "]", temp);
                        // 　推送失败标识
                        candidateReport.setNotified(-1);
                        reportService.updateReport(candidateReport);
                    }
                }
                logger.info("新报告推送完成，成功处理" + successSize + "个报告，失败" + (list.size() - successSize)
                        + "个报告...[" + scheduleTime + "]");
            } else
            {
                logger.info("没有新报告需要推送...[" + scheduleTime + "]");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            scheduleOn = false;
        }
    }
}
