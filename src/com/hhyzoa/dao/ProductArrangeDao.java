package com.hhyzoa.dao;

import java.util.List;

import com.hhyzoa.model.ProductArrange;

public interface ProductArrangeDao extends BaseDao {
	
	/**
	 * 按主键查询
	 * @param id
	 * @return
	 */
	public ProductArrange findById(int id);
	
	/**
	 * 保存
	 * @param ProductArrange
	 */
	public void save(ProductArrange productSell);
	
	/**
	 * 分页查询
	 */
	public List<ProductArrange> findAll(int pageSize,int offset,ProductArrange pa); 
	
	public int getAllRowCount(ProductArrange productArrange);
	
	public void deleteById(int[] ids);
	
	public void update(ProductArrange productArrange);
	
}
