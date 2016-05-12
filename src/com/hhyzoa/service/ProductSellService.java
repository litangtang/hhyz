package com.hhyzoa.service;

import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.ProductSell;

public interface ProductSellService {
	
	public void add(ProductSell ps) throws Exception;
	
	/**  
     * 分页查询所有 
     * @param currentPage 当前第几页  
     * @param pageSize 每页大小  
     * @return 封装了分页信息(包括记录集list)的Bean  
     */  
    public PageBean listAll(int pageSize,int page,ProductSell ps); 
    
    public void removeById(String[] attrs);
    
    public ProductSell queryById(int id);
    
    public void modify(ProductSell productSell) throws Exception;
    
    /**
	 * 初始时使用,用于计算已有数据的累计
	 * @return
	 */
    public void calAccu(Integer year, Integer month, Integer busiType);
}
