package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.empl.domain.EmployerCompanyInfo;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApply;

public interface IEmployerTrialDao extends IBaseDao<EmployerTrialApply> {
   public Employer getEmployerByEmail(String email);
   public EmployerTrialApply getEmployerTrialByEmail(String email);
   public EmployerTrialApply getEmployerTrialByKey(String key);
   
   public void initCompanyInfoExt(Employer employer );
   
   /**
    * 获取账号公司信息
    * @param employerId
    * @return
    */
   public EmployerCompanyInfo getEmployerCompanyInfo(Integer employerId);
   
   /**
    * 获取正常注册的账户，不是第三方或者体验的账户
    * @param email
    * @return
    */
   public Employer getNormalEmployerByEmail(String email);
   
}
