package com.ailk.sets.platform.intf.cand.service;

import java.util.Collection;

import com.ailk.sets.platform.intf.cand.domain.ReqInfo;
import com.ailk.sets.platform.intf.cand.domain.ReqInfoKey;
import com.ailk.sets.platform.intf.common.PFResponse;

/**
 * 请求队列管理
 * @author panyl
 *
 */
public interface IReqQueueManagerService {
   public PFResponse addToReqQueue(ReqInfoKey key);
   public PFResponse delFromReqQueue(ReqInfoKey key);
   public PFResponse delAllReqQueue();
   public Collection<ReqInfo> getAllReqQueue();
   public boolean canAccessPage(String sessionId);
   public PFResponse updateMaxWaitNumber(int maxWaitNumber);
   public PFResponse updateMaxWaitTime(int maxWaitTime);
   public Integer getMaxWaitNumber();
   public Integer getMaxWaitTime();
}
