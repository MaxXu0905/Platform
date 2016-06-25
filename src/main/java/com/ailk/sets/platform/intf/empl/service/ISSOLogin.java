package com.ailk.sets.platform.intf.empl.service;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.empl.domain.ConfigChannel;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.TokenInfo;

public interface ISSOLogin {
/**
 * 验证单点登陆
 * @param tocken
 * @param type 单点登陆类型  1亚信  2QQ
 * @return
 */
public EmployerRegistInfo ssoLogin(String tocken,int type);

/**
  * 第三方获取token
  * @param registInfo
  * @return
  */
 public PFResponseData<TokenInfo> getToken(EmployerRegistInfo registInfo);
 
 /**
  * 获取用户 根据token
  * @param token
  * @return
  */
 public PFResponseData<Employer> getEmployerByToken(String token);
 
 /**
  * 获取渠道配置信息  如达内
  * @param id
  * @param secret
  * @return
  */
 public PFResponseData<ConfigChannel> getConfigChannelByClient(int id, String secret);
}
