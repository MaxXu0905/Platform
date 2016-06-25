package com.ailk.sets.platform.intf.empl.service;

import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;

public interface ITokenLogin {
/**
 * 验证单点登陆
 * @param tocken
 * @param type 单点登陆类型  1亚信  2QQ
 * @return
 */
public EmployerRegistInfo ssoLogin(String tocken,int type);
}
