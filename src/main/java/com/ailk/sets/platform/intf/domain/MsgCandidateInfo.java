package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;
import java.util.List;
/**
 * app短信内容接口
 * @author panyl
 *
 */
public class MsgCandidateInfo implements Serializable{
     /**
	 * 
	 */
	private static final long serialVersionUID = 3701247617403802918L;
	private String fileName;
	private List<MsgInfo> msgInfos ;
    
	public List<MsgInfo> getMsgInfos() {
		return msgInfos;
	}
	public void setMsgInfos(List<MsgInfo> msgInfos) {
		this.msgInfos = msgInfos;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "MsgCandidateInfo [fileName=" + fileName + ", msgInfos=" + msgInfos + "]";
	}
     
}
