package com.hhyzoa.service;

import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.Trade;

public interface TradeService {
	
	public void add(Trade trade);
	
	/**  
     * 分页查询所有 
     * @param currentPage 当前第几页  
     * @param pageSize 每页大小  
     * @return 封装了分页信息(包括记录集list)的Bean  
     */  
    public PageBean listAll(int pageSize,int page,Trade trade); 
    
    /**
	 * 查询某个人的所有往来历史记录
	 * @param trade 
	 * @param pageSize 每页大小 
	 * @param page 当前第几页 
     * @return 封装了分页信息(包括记录集list)的Bean   
	 */
    public PageBean listAllOfSB(Trade trade,int pageSize,int page);
    
    /**
     * 通过id查询
     * @param id
     * @return
     */
    public Trade queryById(int id);
    
    /**
     * 根据id删除Trade
     * @param attrs 所选择的trade的id构成的数组
     */
    public void removeById(String[] attrs);
    
    
    /**
     * 更新trade
     * @param trade
     */
    public void modify(Trade oldTrade,Trade newTrade);
    
    

}
