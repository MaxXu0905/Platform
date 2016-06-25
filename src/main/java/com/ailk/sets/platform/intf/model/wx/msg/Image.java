/**
 * author :  lipan
 * filename :  Image.java
 * create_time : 2014年7月24日 下午6:24:18
 */
package com.ailk.sets.platform.intf.model.wx.msg;

/**
 * @author : lipan
 * @create_time : 2014年7月24日 下午6:24:18
 * @desc : 图片对象
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class Image
{
    private String media_id; // 发送的图片的媒体ID

    public Image(String media_id)
    {
        this.media_id = media_id;
    }

    public String getMedia_id()
    {
        return media_id;
    }

    public void setMedia_id(String media_id)
    {
        this.media_id = media_id;
    }
}
