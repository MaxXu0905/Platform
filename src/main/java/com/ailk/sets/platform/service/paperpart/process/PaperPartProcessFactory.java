package com.ailk.sets.platform.service.paperpart.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
@Transactional(rollbackFor = Exception.class)
@Service
public class PaperPartProcessFactory {
	@Autowired
	private PaperPartProcessObject object;
	@Autowired
	private PaperPartProcessSubject subject;
	@Autowired
	private PaperPartProcessEssay essay;
	@Autowired
	private PaperPartProcessExtra extra;
	@Autowired
	private PaperPartProcessInterview interview;
	public IPaperPartProcess getPaperPartProcess(PaperPartSeqEnum seq) {
           if(seq == PaperPartSeqEnum.OBJECT)
        	   return object;
           else if(seq == PaperPartSeqEnum.SUBJECT)
        	   return subject;
           else if(seq == PaperPartSeqEnum.EXTRA)
        	   return extra;
           else if(seq == PaperPartSeqEnum.ESSAY)
        	   return essay;
           else if(seq == PaperPartSeqEnum.INTEVEIW)
        	   return interview;
           return null;
	}
}
