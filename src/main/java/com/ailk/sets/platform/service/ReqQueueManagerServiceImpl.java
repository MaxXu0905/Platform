package com.ailk.sets.platform.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.cand.domain.ReqInfo;
import com.ailk.sets.platform.intf.cand.domain.ReqInfoKey;
import com.ailk.sets.platform.intf.cand.service.IReqQueueManagerService;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;

public class ReqQueueManagerServiceImpl implements IReqQueueManagerService,
		Runnable {

	@PostConstruct
	public void init() {
		new Thread(this).start();
	}

	private Logger logger = LoggerFactory
			.getLogger(ReqQueueManagerServiceImpl.class);
	private Map<ReqInfoKey, ReqInfo> queues = new ConcurrentHashMap<ReqInfoKey, ReqInfo>();
	private int maxWaitNumber = 30;
	private int maxWaitTime = 100;

	@Override
	public PFResponse addToReqQueue(ReqInfoKey key) {
		PFResponse res = new PFResponse();
		ReqInfo que = new ReqInfo();
		try {
			PropertyUtils.copyProperties(que, key);
		} catch (Exception e) {
			logger.error("error add to req ", e);
		}
		que.setBeginTime(System.currentTimeMillis());
		queues.put(key, que);
		res.setCode(FuncBaseResponse.SUCCESS);
		return res;
	}

	@Override
	public PFResponse delFromReqQueue(ReqInfoKey key) {
		PFResponse res = new PFResponse();
		queues.remove(key);
		res.setCode(FuncBaseResponse.SUCCESS);
		return res;
	}

	@Override
	public PFResponse delAllReqQueue() {
		PFResponse res = new PFResponse();
		queues.clear();
		res.setCode(FuncBaseResponse.SUCCESS);
		return res;
	}

	@Override
	public Collection<ReqInfo> getAllReqQueue() {
		Collection<ReqInfo> col = queues.values();
		for (ReqInfo info : col) {
			info.setUsedTime(System.currentTimeMillis() - info.getBeginTime());
		}
		return col;
	}

	@Override
	public boolean canAccessPage(String sessionId) {
		Set<ReqInfoKey> set = queues.keySet();
		for (ReqInfoKey key : set) {
			if (key.getSessionId().equals(sessionId)) {
				logger.debug("the sessionId has alread contains {}", sessionId);
				return true;
			}
		}
		if (queues.size() >= maxWaitNumber) {
			return false;
		}
		return true;
	}

	@Override
	public PFResponse updateMaxWaitTime(int maxWaitTime) {
		PFResponse res = new PFResponse();
		res.setCode(FuncBaseResponse.SUCCESS);
		this.maxWaitTime = maxWaitTime;
		return res;
	}

	@Override
	public PFResponse updateMaxWaitNumber(int maxWaitNumber) {
		PFResponse res = new PFResponse();
		res.setCode(FuncBaseResponse.SUCCESS);
		this.maxWaitNumber = maxWaitNumber;
		return res;
	}

	public Integer getMaxWaitNumber() {
		return maxWaitNumber;
	}

	public Integer getMaxWaitTime() {
		return maxWaitTime;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(maxWaitTime * 1000l);
				Iterator<Map.Entry<ReqInfoKey, ReqInfo>> it = queues.entrySet()
						.iterator();
				while (it.hasNext()) {
					Map.Entry<ReqInfoKey, ReqInfo> entry = it.next();
					if ((System.currentTimeMillis() - entry.getValue()
							.getBeginTime()) > maxWaitTime * 1000l) {// 超过最大自动清除
						logger.debug(
								"delete the key {} because it is timeout ",
								entry.getKey());
						it.remove();
					}

				}
			}
		} catch (Exception e) {
			logger.error("error in scheduler ", e);
		}
	}

}
