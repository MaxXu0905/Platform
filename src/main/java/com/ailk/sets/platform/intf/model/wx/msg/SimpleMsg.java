/**
 * author :  lipan
 * filename :  SimpleMsg.java
 * create_time : 2014年7月24日 下午6:18:06
 */
package com.ailk.sets.platform.intf.model.wx.msg;

/**
 * @author : lipan
 * @create_time : 2014年7月24日 下午6:18:06
 * @desc : 微信简单消息封装
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class SimpleMsg
{
    public static final String MSG_TYPE_TEXT = "text"; // 文本消息类型
    public static final String MSG_TYPE_IMAGE = "image"; // 文本消息类型
    
    private String touser;
    private String msgtype;
    private Text text; // 文本消息
    private Image image; // 图片消息

    public SimpleMsg()
    {
    }

    // 实例文本消息
    public SimpleMsg(String touser, String msgtype, Text text)
    {
        this.touser = touser;
        this.msgtype = msgtype;
        this.text = text;
    }

    public String getTouser()
    {
        return touser;
    }

    public void setTouser(String touser)
    {
        this.touser = touser;
    }

    public String getMsgtype()
    {
        return msgtype;
    }

    public void setMsgtype(String msgtype)
    {
        this.msgtype = msgtype;
    }

    public Text getText()
    {
        return text;
    }

    public void setText(Text text)
    {
        this.text = text;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

}
