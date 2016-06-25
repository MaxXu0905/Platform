/**
 * author :  lipan
 * filename :  IWeixinServiceImpl.java
 * create_time : 2014年7月28日 下午9:51:30
 */
package com.ailk.sets.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.model.WXAuthInfo;
import com.ailk.sets.platform.intf.model.wx.model.WXBaseInfo;
import com.ailk.sets.platform.intf.wx.service.IWeixinService;

/**
 * @author : lipan
 * @create_time : 2014年7月28日 下午9:51:30
 * @desc : 微信接口实现类
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
@Transactional(rollbackFor = Exception.class)
public class WeixinServiceImpl implements IWeixinService
{

    private static Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);

    @Autowired
    private IConfigSysParamDao configSysParamDao;

    @Override
    public WXAuthInfo getAuthInfoByCode(String code) throws Exception
    {
        try
        {
            WXBaseInfo wxBaseInfo = new WXBaseInfo(
                    configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID),
                    configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
            WXCommunicator communicator = new WXCommunicator(HttpClientUtil.getHttpClient());
            return communicator.getAuthInfoByCode(wxBaseInfo, code);
        } catch (Exception e)
        {
            logger.error(" get auth info by code error, ", e);
            throw new PFServiceException(e.getMessage());
        }
    }

    @Override
    public WXAuthInfo getAuthInfoByCodeER(String code) throws Exception
    {
        try
        {
            WXBaseInfo wxBaseInfo = new WXBaseInfo(
                    configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID),
                    configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
            WXCommunicator communicator = new WXCommunicator(HttpClientUtil.getHttpClient());
            return communicator.getAuthInfoByCode(wxBaseInfo, code);
        } catch (Exception e)
        {
            logger.error(" get auth info by code error, ", e);
            throw new PFServiceException(e.getMessage());
        }
    }

}
