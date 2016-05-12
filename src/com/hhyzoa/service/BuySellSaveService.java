package com.hhyzoa.service;

import com.hhyzoa.model.BuySellSave;
import com.hhyzoa.model.PageBean;

public interface BuySellSaveService {
	
	/**  
     * 分页查询所有 
     * @param currentPage 当前第几页  
     * @param pageSize 每页大小  
     * @param buySellSave 封装了查询条件
     * @return 封闭了分页信息(包括记录集list)的Bean  
     */  
    public PageBean queryAll(int pageSize,int currentPage,BuySellSave buySellSave);
    
    /**
     * 增加一条进销存记录
     * @param buySellSave
     */
    public void add(BuySellSave buySellSave);

}
