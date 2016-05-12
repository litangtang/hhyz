package com.hhyzoa.service;

import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.ProductArrange;

public interface ProductArrangeService {
	
	public void add(ProductArrange pa) throws Exception;
	
	/**  
     * 分页查询所有 
     * @param currentPage 当前第几页  
     * @param pageSize 每页大小  
     * @return 封装了分页信息(包括记录集list)的Bean  
     */  
    public PageBean listAll(int pageSize,int page,ProductArrange pa); 
    
    public void removeById(String[] attrs);
    
    public ProductArrange queryById(int id);
    
    public void modify(ProductArrange productArrange) throws Exception;
    
}
