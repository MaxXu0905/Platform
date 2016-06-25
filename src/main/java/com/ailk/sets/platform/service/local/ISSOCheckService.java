package com.ailk.sets.platform.service.local;

import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;

public interface ISSOCheckService {
public EmployerRegistInfo login(String tocken);

public EmployerRegistInfo getEmployerInfoByToken(String token);
}
