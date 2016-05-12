package com.hhyzoa.service;

import java.util.Map;

import com.hhyzoa.model.Client;
import com.hhyzoa.model.PageBean;

public interface ClientService {
	
	public void add(Client client);
	
	public void modify(Client client);
	
	/**  
     * 分页查询所有 
     * @param currentPage 当前第几页  
     * @param pageSize 每页大小  
     * @param client 封装了查询条件
     * @return 封闭了分页信息(包括记录集list)的Bean  
     */  
    public PageBean listAll(int pageSize,int currentPage,Client client);  
    
    /**
     * 通过id查询
     * @param id
     * @return
     */
    public Client queryById(int id);
    
    
    /**
     * 根据id删除用户
     * @param id数组
     */
    public void removeById(String[] ids);
    
    
    /**
     * 删除用户
     * @param client
     */
    public void remove(Client client);
    
    /**
	 * 按类别查询所有的客户，非分页，用于增加往来时，下拉列表显示客户
	 * @param type 客户类别
	 * @return
	 */
    public Map<Integer,String> queryByType(Integer type);


}
