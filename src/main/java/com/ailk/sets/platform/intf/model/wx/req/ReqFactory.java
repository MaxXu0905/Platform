package com.ailk.sets.platform.intf.model.wx.req;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.model.wx.Constant;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;
import com.ailk.sets.platform.intf.model.wx.IWXService;
import com.ailk.sets.platform.intf.model.wx.excuter.DefaultExcuter;
import com.ailk.sets.platform.intf.model.wx.excuter.DefaultSubscribeExcuter;
import com.ailk.sets.platform.intf.model.wx.excuter.GetExamResultClickExcuter;
import com.ailk.sets.platform.intf.model.wx.excuter.GoDemoClickExcuter;
import com.ailk.sets.platform.intf.model.wx.excuter.GoLoginClickExcuter;
import com.ailk.sets.platform.intf.model.wx.excuter.GoOnlineExamReqClickExcuter;
import com.ailk.sets.platform.intf.model.wx.excuter.SubscribeExcuter;
import com.ailk.sets.platform.intf.model.wx.excuter.TextExcuter;
import com.ailk.sets.platform.intf.model.wx.excuter.UnSubscribeExcuter;
import com.ailk.sets.platform.intf.model.wx.util.DocumentWraper;

/**
 * 注意：使用此类的接口包中要包含dom4j的相关jar包
 *
 */
public class ReqFactory {
	private Logger logger = LoggerFactory.getLogger(ReqFactory.class);
	
	private Document document; // 微信请求的xml数据
	private IWXService wxService; //处理请求的service
	private DocumentWraper docWraper; // 请求的xml文档对象

	/**
	 * 微信请求后，实例改对象
	 * @param is 微信请求的输入流
	 * @param wxService 
	 */
	public ReqFactory(InputStream is, IWXService wxService) {
		logger.debug("ReqFactory wxCandService is " + wxService);
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(is);
			docWraper = new DocumentWraper(document);
			print(document);
			this.wxService = wxService;
		} catch (Exception e) {
			logger.error("error new ReqFactory ", e);
		}
	}
	
    @SuppressWarnings("rawtypes")
    private void print(Document doc) {
        Element root = doc.getRootElement();
        logger.debug("root is " + root.getName() + "------------------" + root.getText());
        // 枚举根节点下所有子节点
        StringBuffer buffer = new StringBuffer();
        for (Iterator ie = root.elementIterator(); ie.hasNext();) {
            Element element = (Element) ie.next();
            buffer.append(element.getName() + ":" + element.getText()+", ");
        }
        logger.info("----------------xml content :{"+buffer.toString()+"}");
    }
	/**
	 * 处理请求
	 * @return
	 */
	public String getReq() throws Exception{
		String msgType = getElementText(Constant.MSGTYPE_TAG); // MsgType 消息类型
		IWXExcuter wxExcuter;
		logger.debug("MsgType is " + msgType);
		if (msgType.equalsIgnoreCase(Constant.MSGTYPE_EVENT)) // 接收事件推送
		{ 
			wxExcuter = getEventReq(getElementText(Constant.MSGTYPE_EVENT_TAG));
		}else if (msgType.equalsIgnoreCase(Constant.MSGTYPE_TEXT)) // 接收文本消息
		{
			wxExcuter = getContentReq(getElementText(Constant.MSGTYPE_CONTENT_TAG));
		}else // 其他消息
		{
			wxExcuter = new DefaultExcuter();
		}
		logger.debug("call wxExcuter is " + wxExcuter);
		return wxExcuter.execute(wxService, docWraper);
	}
	
	/**
	 * 处理文本消息
	 * @param elementText
	 * @return
	 */
	private IWXExcuter getContentReq(String elementText) {
		logger.debug("elementText is " + elementText);
		return new TextExcuter();
	}

	/**
	 * 处理事件推送消息
	 * @param subType 事件类型
	 * @return
	 */
	private IWXExcuter getEventReq(String subType) throws Exception{
		logger.debug("subType is " + subType);
		if (subType.equalsIgnoreCase(Constant.EVENT_SUBSCRIBE)) //1.  扫描带参数二维码事件: 用户未关注时，进行关注后的事件推送； 2.关注事件
		{
			String eventKey = getElementText(Constant.EVENT_EVENTKEY_TAG);// 事件KEY值
			if (StringUtils.isBlank(eventKey)) // 手动关注
			{
			    return new DefaultSubscribeExcuter();
			}
			else // 通过二维码关注
			{
			    return new SubscribeExcuter();
			}
		} else if (subType.equalsIgnoreCase(Constant.EVENT_UNSUBSCRIBE))  // 取消关注事件
		{
			return new UnSubscribeExcuter();
		} else if (subType.equalsIgnoreCase(Constant.EVENT_SCAN)) // 扫描带参数二维码事件: 用户已关注时的事件推送
		{  
			return new DefaultExcuter();
		} else if (subType.equalsIgnoreCase(Constant.EVENT_CLICK)) { // 自定义菜单事件: 点击菜单拉取消息时的事件推送
			String eventKey = getElementText(Constant.EVENT_EVENTKEY_TAG); // 事件KEY值，设置的跳转URL
			if (eventKey.equals("goLogin"))      // 百一内测
			{
			    return new GoLoginClickExcuter();
			}
			else if(eventKey.equals("goDemo")){ //校招demo
				return new GoDemoClickExcuter();
			}
			else if(eventKey.equals("getExamResult")){ // 查成绩
			    return new GetExamResultClickExcuter();
			}
			else if(eventKey.equals("goOnlineExamReq")){ // 在线考试申请
			    return new GoOnlineExamReqClickExcuter();
			}
//			else if(eventKey.equals("goDemo")){
//			    return new GoDemoClickExcuter();
//			}
			else
			{
			    return new DefaultExcuter();
			}
		}
		else if(subType.equalsIgnoreCase(Constant.EVENT_LOCATION)) // 获取用户地理位置
		{
		    // Latitude 地理位置纬度
		    // Longitude 地理位置经度
		    // Precision 地理位置精度
		    logger.debug(" UPLOAD USER LOCATION ,{ Latitude: " + getElementText(Constant.LATITUDE_TAG) +
		           " ,Longitude: "+getElementText(Constant.LONGITUDE_TAG) + 
		           " ,Precision: "+getElementText(Constant.PRECISION_TAG)+" }");
		}
		else if(subType.equalsIgnoreCase(Constant.EVENT_VIEW)) // 点击URL菜单
		{
		    logger.debug(" OPEN URL " + getElementText(Constant.EVENT_EVENTKEY_TAG));
		}
		return new DefaultExcuter();
	}

	private String getElementText(String eleName) {
		return document.getRootElement().elementText(eleName);
	}
}
