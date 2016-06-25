/**
 * author :  lipan
 * filename :  Text.java
 * create_time : 2014年7月24日 下午6:23:52
 */
package com.ailk.sets.platform.intf.model.wx.msg;

/**
 * @author : lipan
 * @create_time : 2014年7月24日 下午6:23:52
 * @desc : 文本对象
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class Text
{
    private String content; // 文本消息内容

    public Text(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
