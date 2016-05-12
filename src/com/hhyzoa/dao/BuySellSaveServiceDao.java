package com.hhyzoa.dao;

import java.util.List;

import com.hhyzoa.model.BuySellSave;

public interface BuySellSaveServiceDao extends BaseDao {
	
	public void save(BuySellSave buySellSave);

	
	/**
	 * 分页查询所有客户
	 * @param pageSize 每页显示记录数 
	 * @param offset 开始记录
	 * @param buySellSave 封装查询条件
	 * @return
	 */
	public List<BuySellSave> findAll(int pageSize,int offset,BuySellSave buySellSave);
	
	 /** 
     * 查询所有记录数  
     * @return 总记录数  
     */  
    public int getAllRowCount(BuySellSave buySellSave);
}
