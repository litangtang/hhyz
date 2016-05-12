package com.hhyzoa.service;

import com.hhyzoa.model.PageBean;

public interface PageService {
	
	/**   
     * @param currentPage   
     * @param pageSize  
     * @return 
     */  
    public PageBean queryForPage(int pageSize,int currentPage);  
}
