package com.ailk.sets.platform.intf.model.wx.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ailk.sets.platform.intf.model.wx.util.CDataAdapter;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespText extends BaseMessage {
	@XmlJavaTypeAdapter(CDataAdapter.class)
	private String Content;

	public void setContent(String content) {
		Content = content;
	}

}
