/**
 * author :  lipan
 * filename :  QRCodeInfo.java
 * create_time : 2014年8月6日 上午9:26:44
 */
package com.ailk.sets.platform.intf.model.qrcode;

import java.io.Serializable;

import net.glxn.qrgen.vcard.VCard;

/**
 * @author : lipan
 * @create_time : 2014年8月6日 上午9:26:44
 * @desc : 二维码信息
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class QRCodeInfo implements Serializable
{
    private static final long serialVersionUID = -5467347025634635951L;
    
    public static final String OUT_TYPE_STREAM = "STREAM"; // 流输出
    public static final String OUT_TYPE_FILE = "FILE"; // 文件输出
    
    public static final String IMG_TYPE_PNG = "PNG"; // 图片类型
    public static final String IMG_TYPE_JPG = "JPG"; // 图片类型
    public static final String IMG_TYPE_GIF = "GIF"; // 图片类型
    
    private String textContent = "www.101test.com"; // 二维码文本内容
    private VCard vcardContent; // 名片内容
    private int width = 250; // 宽度；默认250
    private int height = 250; // 高度；默认250
    private String imageType = IMG_TYPE_PNG; // 图片类型 ;默认PNG
    private String outType = OUT_TYPE_STREAM; // 输出类型；默认通过流输出
    
    public String getTextContent()
    {
        return textContent;
    }
    public void setTextContent(String textContent)
    {
        this.textContent = textContent;
    }
    public VCard getVcardContent()
    {
        return vcardContent;
    }
    public void setVcardContent(VCard vcardContent)
    {
        this.vcardContent = vcardContent;
    }
    public int getWidth()
    {
        return width;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public int getHeight()
    {
        return height;
    }
    public void setHeight(int height)
    {
        this.height = height;
    }
    public String getImageType()
    {
        return imageType;
    }
    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }
    public String getOutType()
    {
        return outType;
    }
    public void setOutType(String outType)
    {
        this.outType = outType;
    }
    
    
 }
