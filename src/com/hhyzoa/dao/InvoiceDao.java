package com.hhyzoa.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.model.Invoice;

public interface InvoiceDao extends BaseDao {
	
	/**
	 * 按主键查询
	 * @param id
	 * @return
	 */
	public Invoice findById(int id);
	
	/**
	 * 保存
	 * @param productSell
	 */
	public void save(Invoice invoice);
	
	/**
	 * 分页查询
	 */
	public List<Invoice> findAll(int pageSize,int offset,Invoice invoice); 
	
	
	public int getAllRowCount(Invoice invoice);
	
	public void deleteById(int[] ids);
	
	public void update(Invoice invoice);
	
	/**
	 * 根据日期查询小于传入天的记录,并按正序排列
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	@Transactional
	public List<Invoice> findLeDay(Integer year, Integer month, Integer day);
	
	/**
	 * 根据日期查询大于传入天的记录,并按正序排列
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	@Transactional
	public List<Invoice> findGtDay(Integer year, Integer month, Integer day);
	
	/**
	 * 批量更新日期大于day的2个累计,不含税金额累计、销项税累计
	 * 日期大于改动的记录的日期,且id大于要改动的日期的id
	 * 保证如果一天中有多条记录,改动的是中间的记录,也能正确
	 * 更新就是将需要更新的记录+增量
	 * @param year
	 * @param month
	 * @param day
	 * @param invoice 封装两个增量
	 */
	@Transactional
	public void batchGtUpdateAccu(Integer year, Integer month, Integer day,Invoice invoice)throws Exception;
	
}
