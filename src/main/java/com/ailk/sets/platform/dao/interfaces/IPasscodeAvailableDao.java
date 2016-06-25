/**
 * author :  lipan
 * filename :  IPasscodeAvailableDao.java
 * create_time : 2014年7月12日 上午10:48:30
 */
package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.domain.PasscodeAvailable;


/**
 * @author : lipan
 * @create_time : 2014年7月12日 上午10:48:30
 * @desc : 校招微信测评passcode码表Dao
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public interface IPasscodeAvailableDao extends IBaseDao<PasscodeAvailable>
{
    
    /**
     * 获取宣讲会可用的passcode
     * @return
     */
    public String getAvailablePassCode();
    
    /**
     * 回收宣讲会passcode
     * @param passcode
     */
    public void recyclePassCode(String passcode);
    
    
    /**
     * 获取测速可用的passcode
     * @return
     */
    public String getAvailableCSPassCode();
    
    /**
     * 回收测速passcode
     * @param passcode
     */
    public void recycleCSPassCode(String passcode);
    
    /**
     * 
     * @param passcode
     * @return
     */
    public PasscodeAvailable getPasscodeAvailable(String passcode);
}
