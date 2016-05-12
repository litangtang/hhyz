package com.hhyzoa.dao;

import java.util.List;

import com.hhyzoa.model.CostAccounting;

/**
 * 生产成本核算DAO
 * @author lizhibin
 *
 */
public interface CostAccountingDao extends BaseDao {
	
	/**
	 * 查询所有成本
	 * @param pageSize
	 * @param offset
	 * @param costAccounting
	 * @return
	 */
	public List<CostAccounting> findAll(int pageSize,int offset,CostAccounting costAccounting);
	
	/**
	 * 根据id查询
	 * @param id 主键
	 * @return
	 */
	public CostAccounting findById(int id);
	
	/**
	 * 
	 * @param costAccounting 封装了查询条件
	 * @return 总记录数
	 */
    public int getAllRowCount(CostAccounting costAccounting);
    
    /**
     * 增加一条成本核算记录
     * @param costAccounting
     */
    public void save(CostAccounting costAccounting);
    
    /**
	 * 根据id删除成本核算记录
	 * @param id
	 */
	public void deleteById(int[] ids);

}
