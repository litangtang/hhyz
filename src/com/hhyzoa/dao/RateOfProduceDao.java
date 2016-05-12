package com.hhyzoa.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.model.RateOfProduce;

public interface RateOfProduceDao extends BaseDao{
	
	public void save(RateOfProduce rateOfProduce);
	
	/**
	 * 查询所有的出品率
	 * @param pageSize	每页显示记录数
	 * @param offset	开始记录
	 * @param rateOfProduce 封装查询条件
	 */
	public List<RateOfProduce> findAll(int pageSize,int offset,RateOfProduce rateOfProduce);
	
	/**
	 * 根据id查询
	 * @param id 主键
	 * @return
	 */
	public RateOfProduce findById(int id);
	
	 /**
	 * 根据id删除rateOfProduce
	 * @param id
	 */
	public void deleteById(int[] ids);
	
	/**
	 * 更新出品率
	 */
	public void update(RateOfProduce rateOfProduce);
	
	/** 
     * 查询所有记录数  条件查询时使用
     * @return 总记录数  
     */  
	@Transactional
    public int getAllRowCount(RateOfProduce rateOfProduce);

}
