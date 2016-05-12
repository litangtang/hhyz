package com.hhyzoa.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.model.ProductSell;

public interface ProductSellDao extends BaseDao {
	
	/**
	 * 按主键查询
	 * @param id
	 * @return
	 */
	public ProductSell findById(int id);
	
	/**
	 * 保存
	 * @param productSell
	 */
	public void save(ProductSell productSell);
	
	/**
	 * 分页查询
	 */
	public List<ProductSell> findAll(int pageSize,int offset,ProductSell ps); 
	
	/**
	 * 根据条件查询,按day和id升序
	 * @param productSell
	 * @return
	 */
	@Transactional
	public List<ProductSell> findByCon(ProductSell productSell);
	
	public int getAllRowCount(ProductSell productSell);
	
	public void deleteById(int[] ids);
	
	public void update(ProductSell productSell);
	
	/**
	 * 根据日期查询小于传入天的记录,并按正序排列
	 * @param pageSize
	 * @param offset
	 * @param productSell
	 * @return
	 */
	public List<ProductSell> findLeDay(Integer year, Integer month, Integer day, Integer busiType);
	
	/**
	 * 根据日期查询大于传入天的记录,并按正序排列
	 * @param pageSize
	 * @param offset
	 * @param productSell
	 * @return
	 */
	public List<ProductSell> findGtDay(Integer year, Integer month, Integer day, Integer busiType) ;
	
	/**
	 * 批量更新日期大于day的3个累计,件数累计、数量累计、金额累计
	 * 日期大于等于改动的记录的日期,且id大于要改动的日期的id
	 * 保证如果一天中有多条记录,改动的是中间的记录,也能正确
	 * @param year
	 * @param month
	 * @param day
	 * @param ps 发生改动的记录
	 */
	@Transactional
	public void batchGtUpdateAccu(Integer year, Integer month, Integer day,ProductSell ps)throws Exception;
	
	
}
