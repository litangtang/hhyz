package com.hhyzoa.dao;

import java.util.List;

import com.hhyzoa.model.Client;

public interface ClientDao extends BaseDao{
	
	/**
	 * 
	 * @param client
	 */
	public void save(Client client);
	
	public void update(Client client);
	
	/**
	 * 分页查询所有客户
	 * @param pageSize 每页显示记录数 
	 * @param offset 开始记录
	 * @param client 封装查询条件
	 * @return
	 */
	public List<Client> findALL(int pageSize,int offset,Client client); 
	
	 /** 
     * 查询所有记录数  
     * @return 总记录数  
     */  
    public int getAllRowCount(Client client);
	
	public Client findById(int id);
	
	/**
	 * 
	 * @param id
	 */
	public void deleteById(int[] id);
	
	public void delete(Client client);
	
	/**
	 * 按类别查询所有的客户，非分页，用于增加往来时，下拉列表显示客户
	 * @param type
	 * @return
	 */
	public List<Client> findALL(final Integer type);
	
}
