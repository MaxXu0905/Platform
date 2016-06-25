package com.ailk.sets.platform.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.EmployerPosHistory;
import com.ailk.sets.platform.domain.PositionLog;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.domain.PositionOutInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionTestTypeInfo;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.position.PositionInfo;

public interface IPositionDao extends IBaseDao<Position>{
	public List<EmployerPosHistory> getHistory(int employerId, String category, String historyId, Page page);

	public List<PositionLog> getPositionLog(int employerId,List<Integer> positionGrantedIds, Page page);

	public List<Position> getPosition(int employerId,List<Integer> positionGrantedIds, Page page,Integer testType);
	public List<PositionLog> getPositionLog(int positionId, int employerId);

	public void delPositionLog(int positionId, long stateId);
	
	public void savePositionLog(PositionLog positionLog);

	/**
	 * 删除职位相关消息
	 * 
	 * @param positionId
	 * @throws PFDaoException
	 */
	public void delPositionLog(int positionId);

	public void delQuesHistory(int employerId, Long qId, String category);

	public Position getPositionByInvitation(long testId);

	public boolean isPositionNew(int employerId, int positionId);

	/**
	 * 更新职位的状态
	 * 
	 * @throws PFDaoException
	 */
	public void updatePositionState(int positionId, int state);

	public void delPositionLogByState(int positionId, int state);
	
	public Position getPosition(int positionId);
	
	
	/**
	 *  获得entry的Map集合
	 * @return
	 */
	public Map<String,String> getEntryMap();
	
	/**
	 *  获得passport的Map集合
	 * @return
	 */
	public Map<String,String> getPassportMap();
	
    /**
     * 使用entry查询职位信息
     * @param passport
     * @return
     */
    public Position getPositionByEntry(String entry);
    
    /**
     * 使用passport查询职位信息
     * @param passport
     * @return
     */
    public String getPositionByPassport(String passport);
    
    
    /**
     * 使用passport查询职位信息
     * @param passport
     * @return
     */
    public Position getPositionObjByPassport(String passport);
    
    /**
     * 获取由series，level的试卷定制的测评
     * @param employer
     * @param series
     * @param level
     * @return
     */
    public List<Position> getCustomedPaperPositions(int employer);
    
    public List<PositionOutInfo> getPositionOutInfos(int employerId) ;
    
    
   /**
    * 根据createFrom寻找测评
    * @param createFromPositionId
    * @return
    */
    public Position getPositionByCreateFrom(int createFromPositionId, int employerId);
    
    /**
     * 获取sample测评列表
     * @return
     */
    public List<Position> getPositionsOfSample();
    
    public List<PositionTestTypeInfo> getPositionTestTypeInfo(int employerId,List<Integer> positionGrantedIds);
    
    
    public List<PositionLog> getPositionLogByTestType(int employerId,List<Integer> positionGrantedIds, Page page, Integer testType);
}
