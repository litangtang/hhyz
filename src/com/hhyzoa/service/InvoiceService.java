package com.hhyzoa.service;

import com.hhyzoa.model.Invoice;
import com.hhyzoa.model.PageBean;

public interface InvoiceService {
	
	public void add(Invoice invoice) throws Exception;
	
	/**  
     * 分页查询所有 
     * @param currentPage 当前第几页  
     * @param pageSize 每页大小  
     * @return 封装了分页信息(包括记录集list)的Bean  
     */  
    public PageBean listAll(int pageSize,int page,Invoice invoice); 
    
    public void removeById(String[] attrs);
    
    public Invoice queryById(int id);
    
    public void modify(Invoice invoice) throws Exception;
    
}
