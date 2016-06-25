/**
 * author :  lipan
 * filename :  ISendNewReportService.java
 * create_time : 2014年8月14日 下午8:28:20
 */
package com.ailk.sets.platform.service.local;

import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.exception.PFServiceException;

/**
 * @author : lipan
 * @create_time : 2014年8月14日 下午8:28:20
 * @desc : 发送新报告
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public interface ISendNewReportService
{
    /**
     * 发送新报告通知
     * @param candidateReport
     * @throws PFServiceException
     */
    public void sendNewReport(CandidateReport candidateReport) throws Exception;
}
