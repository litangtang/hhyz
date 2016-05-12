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
     * ȡ����ĳ���ͻ����׵����level��������ļ�¼
     * @param clientId
     * @return
     */
    public Integer findMaxLevelOfTrade(Integer clientId);
    
    /**
     * ȡ�������һ�ʽ��׼�¼
     * @param clientId
     * @param level
     * @return
     */
    public Trade findLatestTrade(final Integer clientId,final Integer level);
    
    /**
	 * ����idɾ������
	 * @param id
	 */
	public void deleteById(int[] ids);
	
	
	/**
	 * ����Trade
	 */
	public void update(Trade trade);
	
	/**
	 * ���ô洢���̸���level>currLevle������balance
	 * @param clientId
	 * @param balanceChange
	 * @param currLevel
	 */
	public void proUpdateBalance(int clientId, float balanceChange, int currLevel);
	
	/**
	 * ���ô洢������������
	 * @param trade
	 */
	public void proAddTrade(Trade trade);
	
	/**
	 * ��ȡ�ͻ�������������
	 * @param clientId
	 * @return
	 */
	public int getClientTradeCount(final Integer clientId);
	
}
