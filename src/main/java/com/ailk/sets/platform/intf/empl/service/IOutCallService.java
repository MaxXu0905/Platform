package com.ailk.sets.platform.intf.empl.service;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.OutResponse;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.OutReportInfo;
import com.ailk.sets.platform.intf.empl.domain.TokenInfo;
import com.ailk.sets.platform.intf.model.employer.RegisterInfo;

/**
 * 调用外部接口
 * @author panyl
 *
 */
public interface IOutCallService {
	 /**
	  * 更新职酷测评状态
	  * @param testId
	  * @param status
	  */
     public OutResponse updateMrReportStatus(CandidateTest test, int status);
     /**
      * 更新职酷测评状态
      * @param testId
      * @param status
      */
     public OutResponse updateMrReportStatus(long testId, int status);
     /**
      * 向职酷推送报告数据
      * @param testId
      * @param report
      */
     public OutResponse giveMrReportData(long testId);
     
 	 public OutResponse giveMrReportData(long testId, OutReportInfo report);
 	 
     
}
