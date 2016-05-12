package com.hhyzoa.service;

import java.util.Map;

import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.RateOfProduce;

public interface RateOfProduceService {
	
	public void add(RateOfProduce rateOfProduce);
	
	/**
	 * 查询所有出品率
	 * @param pageSize 每页大小
	 * @param currentPage 当前第几页
	 * @param rateOfProduce 封装了查询条件
	 * @return PageBean 封装了分页信息(包括记录集list)的Bean
	 */
	public PageBean queryAll(int pageSize,int currentPage,RateOfProduce rateOfProduce);
	
	/**
     * 通过id查询
     * @param id
     * @return
     */
    public RateOfProduce queryById(int id);
	

	/**
     * 根据id删除rateOfProduce
     * @param attrs 所选择的rateOfProduce的id构成的数组
     */
    public void removeById(String[] attrs);
    
    /**
     * 修改出品率
     * @param oldRateOfProduce 旧的出品率
     * @param newRateOfProduce 包含了修改后的油桶数和饼袋数，但还没计算出品率
     */
    public void modify(RateOfProduce oldRateOfProduce, RateOfProduce newRateOfProduce);
	
	/**
	 * 单机生产/双机生产Map
	 * @return
	 */
	public Map<Integer, String> getNumOfMachinesMap();

}
