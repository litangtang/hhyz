package com.hhyzoa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.hhyzoa.util.PropertiesUtil;
import com.hhyzoa.util.PropertyPlaceholder;
import com.hhyzoa.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.ClientDao;
import com.hhyzoa.dao.TradeDao;
import com.hhyzoa.model.Client;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.service.ClientService;
import com.hhyzoa.util.FormatUtil;


@Component("clientService")
public class ClientServiceImpl implements ClientService {

	private Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
	private ClientDao clientDao;
	private TradeDao tradeDao;

	public void add(Client client) {
		client.setCount(0);
		clientDao.save(client);

	}
	
	public void modify(Client client) {
		clientDao.update(client);
	}
	
	public void modifyCount(Integer id, Integer count) {
		clientDao.updateCount(id, count);
	}
	
	/**
     * 通过id查询
     * @param id
     * @return
     */
    public Client queryById(int id) {
    	return clientDao.findById(id);
    }
	
   
	
	/**  
     * 分页查询所有 
     * @param currentPage 当前第几页  
     * @param pageSize 每页大小  
     * @param client 封装了查询条件
     * @return 封装了分页信息(包括记录集list)的Bean  
     */  
    public PageBean listAll(int pageSize,int currentPage,Client client){ 
    	
        int allRow = clientDao.getAllRowCount(client);    //总记录数   
        int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
        final int offset = PageBean.countOffset(pageSize, currentPage);    //当前页开始记录   
        //final int length = pageSize;    //每页记录数   
        final int currPage = PageBean.countCurrentPage(currentPage);   
        List<Client> list = clientDao.findALL(pageSize, offset, client);    //"一页"的记录   
        
        List<Client> clientList = new ArrayList<Client>();
        Map typeStrMap = Client.getTypeString();
        for(Client c : list) {
        	c.setTypeStr((String)typeStrMap.get(c.getType()));
        	clientList.add(c);
        }

//		clientList = reorderClientList(clientList, client.getType());
        //把分页信息保存到Bean中   
        PageBean pageBean = new PageBean();   
        pageBean.setPageSize(pageSize);       
        pageBean.setCurrentPage(currPage);   
        pageBean.setAllRow(allRow);   
        pageBean.setTotalPage(totalPage);   
        pageBean.setList(clientList);   
        pageBean.init();   
        return pageBean;   
    }  

    /**
     * 根据id删除用户
     * @param attrs id数组
     */
    @Transactional
    public void removeById(String[] attrs) {
    	int[] ids = FormatUtil.StringArrayToIntArray(attrs);
    	clientDao.deleteById(ids);
    	tradeDao.deleteById(ids);
    }
    
    
    /**
     * 删除用户
     * @param client
     */
    public void remove(Client client) {
    	clientDao.delete(client);
    }
    
    /**
	 * 按类别查询所有的客户，非分页，用于增加往来时，下拉列表显示客户
	 * @param type 客户类别
	 * @return
	 */
    public Map<Integer,String> queryByType(Integer type) {
    	List<Client> cList = clientDao.findALL(type);
    	Map<Integer,String> cMap = new HashMap<Integer,String>();
    	if(null != cList && cList.size() > 0) {
    		for(Client c : cList) {
    			cMap.put(c.getId(), c.getName());
    		}
    	}
    	
    	return cMap;
    }

	/**
	 *	TODO 分页查询，查出来的可能没有
	 * @param clientList
	 * @param type 1为原料客户 2为销售客户
	 * @return
	 */
    private List<Client> reorderClientList(List<Client> clientList, Integer type) {
    	if(null != type) {
			List<Client> reorderedClientList = new ArrayList<Client>();
			String prefix = "CLIENTID_ORDER_";
			String clientIdOrder = StringUtil.trim((String) PropertyPlaceholder.getProperty(prefix + type));
			if(!StringUtil.isEmpty(clientIdOrder)) {
				String[] clientIdOrderArr = clientIdOrder.split(",");
				log.info(String.format("客户类型[%s]置顶客户顺序[%s]", type, clientIdOrder));

				//组织数据
				Map<String, Client> clientMap = new HashMap<String, Client>();
				for (Client c : clientList) {
					clientMap.put(String.valueOf(c.getId()), c);
				}
				//组装置顶数据,从原list中移除置顶数据
				for (String id : clientIdOrderArr) {
					reorderedClientList.add(clientMap.get(id));
					clientList.remove(clientMap.get(id));
				}
				//增加原list中除置顶数据以为的数据
				reorderedClientList.addAll(clientList);
				clientList = reorderedClientList;
			}
		}
		return clientList;
	}



	public ClientDao getClientDao() {
		return clientDao;
	}

	@Resource(name="clientDao")
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public TradeDao getTradeDao() {
		return tradeDao;
	}

	@Resource(name="tradeDao")
	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

}
