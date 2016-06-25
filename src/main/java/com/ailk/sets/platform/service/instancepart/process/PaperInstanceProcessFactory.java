package com.ailk.sets.platform.service.instancepart.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;

/**
 * 生成试卷部分工厂
 * 
 * @author panyl
 * 
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class PaperInstanceProcessFactory {

	@Autowired
	@Qualifier("paperInstanceProcessAbstract")
	private PaperInstanceProcessAbstract abstractPro;

	@Autowired
	private PaperInstanceProcessObject object;

	@Autowired
	private PaperInstanceProcessSubject subject;

	@Autowired
	private PaperInstanceProcessInterview interview;

	public IPaperInstanceProcess getPaperInstanceProcess(PaperPartSeqEnum seq) {
		if (seq == PaperPartSeqEnum.OBJECT)
			return object;
		else if (seq == PaperPartSeqEnum.SUBJECT)
			return subject;
		else if (seq == PaperPartSeqEnum.ESSAY)
			return abstractPro;
		else if (seq == PaperPartSeqEnum.EXTRA)
			return abstractPro;
		else if (seq == PaperPartSeqEnum.INTEVEIW
				|| seq == PaperPartSeqEnum.TEST_INTERVIEW)
			return interview;
		else if (seq == PaperPartSeqEnum.TEST_OBJECT
				|| seq == PaperPartSeqEnum.TEST_SUBJECT)
			return abstractPro;
		return null;
	}
}
