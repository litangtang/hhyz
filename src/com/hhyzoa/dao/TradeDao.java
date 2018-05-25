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
     * ???????????????????level???????????
     * @param clientId
     * @return
     */
    public Integer findMaxLevelOfTrade(Integer clientId);
    
    /**
     * ????????????????
     * @param clientId
     * @param level
     * @return
     */
    public Trade findLatestTrade(final Integer clientId,final Integer level);
    
    /**
	 * ????id???????
	 * @param id
	 */
	public void deleteById(int[] ids);
	
	
	/**
	 * ????Trade
	 */
	public void update(Trade trade);
	
	/**
	 * ????????????level>currLevle??????balance
	 * @param clientId
	 * @param balanceChange
	 * @param currLevel
	 */
	public void proUpdateBalance(int clientId, double balanceChange, int currLevel);
	
	/**
	 * ?????????????????
	 * @param trade
	 */
	public void proAddTrade(Trade trade);
	
	/**
	 * ??????????????????
	 * @param clientId
	 * @return
	 */
	public int getClientTradeCount(final Integer clientId);
	
}
