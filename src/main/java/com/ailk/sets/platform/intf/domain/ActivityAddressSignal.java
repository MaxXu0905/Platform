/**
 * author :  lipan
 * filename :  ActivityAddressSignal.java
 * create_time : 2014年8月19日 上午10:13:05
 */
package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author : lipan
 * @create_time : 2014年8月19日 上午10:13:05
 * @desc : 学校地址信号信息
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class ActivityAddressSignal implements Serializable
{

    private static final long serialVersionUID = -1889074274731071279L;
    
    private Integer id; // id
    private Integer addressId;  //地址Id
    private Integer signalTester;  //测试人
    private String phone_nbr;  //手机号
    private String phone_type; //手机型号
    private String carrier; //运营商: CUC, CMC, CTC, MVNO
    private String net_type; //网络类型：2G, 3G, 4G
    private Integer signal_strength; //移动信号强度：-110 - -50dB，值越大表示信号越好
    private Integer download; //下载带宽, KB为单位
    private Integer upload;  // 上传带宽, KB为单位
    private Long begin_time; //开始时间
    private Long end_time; //结束时间
    private Timestamp begin_timeT; //开始时间
    private Timestamp end_timeT; //结束时间
    private Timestamp create_date; //创建日期
    private Integer duration;  //测试时长
    
    
    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public String getPhone_nbr()
    {
        return phone_nbr;
    }
    public void setPhone_nbr(String phone_nbr)
    {
        this.phone_nbr = phone_nbr;
    }
    public String getPhone_type()
    {
        return phone_type;
    }
    public void setPhone_type(String phone_type)
    {
        this.phone_type = phone_type;
    }
    public String getCarrier()
    {
        return carrier;
    }
    public void setCarrier(String carrier)
    {
        this.carrier = carrier;
    }
    public String getNet_type()
    {
        return net_type;
    }
    public void setNet_type(String net_type)
    {
        this.net_type = net_type;
    }
    public Integer getSignal_strength()
    {
        return signal_strength;
    }
    public void setSignal_strength(Integer signal_strength)
    {
        this.signal_strength = signal_strength;
    }
    public Integer getDownload()
    {
        return download;
    }
    public void setDownload(Integer download)
    {
        this.download = download;
    }
    public Integer getUpload()
    {
        return upload;
    }
    public void setUpload(Integer upload)
    {
        this.upload = upload;
    }
    public Timestamp getBegin_timeT()
    {
        return begin_timeT;
    }
    public void setBegin_timeT(Timestamp begin_timeT)
    {
        this.begin_timeT = begin_timeT;
    }
    public Timestamp getEnd_timeT()
    {
        return end_timeT;
    }
    public void setEnd_timeT(Timestamp end_timeT)
    {
        this.end_timeT = end_timeT;
    }
    public Long getBegin_time()
    {
        return begin_time;
    }
    public void setBegin_time(Long begin_time)
    {
        this.begin_time = begin_time;
    }
    public Long getEnd_time()
    {
        return end_time;
    }
    public void setEnd_time(Long end_time)
    {
        this.end_time = end_time;
    }
    public Integer getAddressId()
    {
        return addressId;
    }
    public void setAddressId(Integer addressId)
    {
        this.addressId = addressId;
    }
    public Timestamp getCreate_date()
    {
        return create_date;
    }
    public void setCreate_date(Timestamp create_date)
    {
        this.create_date = create_date;
    }
    public Integer getDuration()
    {
        return duration;
    }
    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }
    public Integer getSignalTester()
    {
        return signalTester;
    }
    public void setSignalTester(Integer signalTester)
    {
        this.signalTester = signalTester;
    }
    
    
}
