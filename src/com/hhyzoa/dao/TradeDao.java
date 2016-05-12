package com.hhyzoa.dao;

import java.util.List;

import com.hhyzoa.model.Trade;

public interface TradeDao extends BaseDao{
	
	public void save(Trade trade);
	
	public List<Trade> findAllOfSB(int clientId,int offset,int length);
	
	public List<Trade> findAll(int pageSize,int offset,Trade trade);
	
	public Trade findById(int id);
	
    public int getAllRowCount(Trade trade);
    
    /**
     * 取得与某个客户交易的最大level，即最近的记录
     * @param clientId
     * @return
     */
    public Integer findMaxLevelOfTrade(Integer clientId);
    
    /**
     * 取得最近的一笔交易记录
     * @param clientId
     * @param level
     * @return
     */
    public Trade findLatestTrade(final Integer clientId,final Integer level);
    
    /**
	 * 根据id删除往来
	 * @param id
	 */
	public void deleteById(int[] ids);
	
	
	/**
	 * 更新Trade
	 */
	public void update(Trade trade);
	
	/**
	 * 调用存储过程更新level>currLevle的所有balance
	 * @param clientId
	 * @param balanceChange
	 * @param currLevel
	 */
	public void proUpdateBalance(int clientId, float balanceChange, int currLevel);
	
	/**
	 * 调用存储过程增加往来
	 * @param trade
	 */
	public void proAddTrade(Trade trade);
	
	/**
	 * 获取客户的所有往来数
	 * @param clientId
	 * @return
	 */
	public int getClientTradeCount(final Integer clientId);
	
}
