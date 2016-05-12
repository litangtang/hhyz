package com.hhyzoa.service;

import com.hhyzoa.model.CostAccounting;
import com.hhyzoa.model.PageBean;

/**
 * 生产成本核算服务层接口
 * @author lizhibin
 *
 */
public interface CostAccountingService {
	
	/**  
     * 分页查询所有 
     * @param currentPage 当前第几页  
     * @param pageSize 每页大小  
     * @return 封装了分页信息(包括记录集list)的Bean  
     */  
    public PageBean listAll(int pageSize,int page,CostAccounting costAccounting); 
    
    /**
     * 通过id查询
     * @param id
     * @return
     */
    public CostAccounting queryById(int id);
    
    /**
     * 增加一条成本核算记录
     * @param costAccounting
     */
    public void add(CostAccounting costAccounting);
    
    /**
     * 增加一条成本核算记录 for 合同价格(相当于送到价格)
     * @param costAccounting
     */
    public void add2(CostAccounting costAccounting);
    
    /**
     * 根据id删除costAccounting
     * @param attrs 所选择的costAccounting的id构成的数组
     */
    public void removeById(String[] attrs);
    
}
