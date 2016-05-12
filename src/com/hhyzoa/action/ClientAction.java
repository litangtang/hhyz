package com.hhyzoa.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.hhyzoa.model.Client;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.service.ClientService;
import com.hhyzoa.util.Constants;

@Component
public class ClientAction extends BaseAction {
	
	private static final long serialVersionUID = -2949005187224449514L;
	private ClientService clientService;
	private Client client;
	private HttpServletRequest request;
	
	
	
	private int page;    //第几页   
    private PageBean pageBean;    //包含分布信息的bean   
    private String fromWhere ;	//从哪里来，用于配置文件传递参数
    
    //增加初始化
    public String forAdd() {
    	request = ServletActionContext.getRequest();
    	String from = request.getParameter("from");
    	request.setAttribute("from", from);
    	return SUCCESS;
    }
    
    /**
     * 增加
     * @return
     */
    public String add() {
    	request = ServletActionContext.getRequest();
    	String from = request.getParameter("from");
    	if(null != from && !"".equals(from)) {
    		//配置文件传递参数使用，即searchType=${fromWhere},取from的最后以为，因为from=client_0/1/2
    		this.setFromWhere(from.substring(from.length()-1));
    		//从原料客户/销售客户跳转到增加页面时，页面上没有client的type属性输入值，所以需要认为设定
    		if(!"client_0".equals(from.trim())) {
    			this.client.setType(Integer.parseInt(from.substring(from.length()-1)));
    		}
    		clientService.add(client);
    	}
		
		return SUCCESS;
	}
    
    /**
     * 更新初始化
     * @return
     */
    public String forUpdate() {
    	request = ServletActionContext.getRequest();
    	String clientId = request.getParameter("clientId");
    	String from = request.getParameter("from");
    	
    	if(null != clientId && !"".equals(clientId)) {
    		this.client = clientService.queryById(Integer.parseInt(clientId.trim()));
    		request.setAttribute("from", from);
    		return SUCCESS;
    	}
    	
    	return SUCCESS;
    }
    
    /**
     * 更新客户信息
     * @return
     */
    public String update() {
    	request = ServletActionContext.getRequest();
    	String from = request.getParameter("from");
    	String clientId = request.getParameter("clientId");
    	if(null != clientId && !"".equals(clientId)) {
    		this.client.setId(Integer.parseInt(clientId.trim()));
    		//配置文件传递参数使用，即searchType=${fromWhere},取from的最后以为，因为from=client_0/1/2
    		this.setFromWhere(from.substring(from.length()-1));
    		request.setAttribute("from", from);
    		clientService.modify(this.client);
    		return SUCCESS;
    	}
    	return SUCCESS;
    }
    
    /**
     * 列出所有客户
     * @return
     */
    public String listAll() {
    	request = ServletActionContext.getRequest();
    	Client c = new Client();//封装查询条件 
    	
    	String searchName = request.getParameter("searchName");
    	String searchType = request.getParameter("searchType");
    		
    	if(null != searchName && !"".equals(searchName)) {
    		c.setName(searchName.trim());
    	}
    	if(null != searchType && !"".equals(searchType) && 0 != Integer.parseInt(searchType.trim())) {
    		c.setType(Integer.parseInt(searchType));
    	}
    	
    	//分页的pageBean,参数pageSize表示每页显示记录数,page为当前页   
        this.pageBean = clientService.listAll(Constants.PAGE_SIZE, page,c);  
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchType", searchType);
        //request.setAttribute("typeStrMap", Client.getTypeString());
    	return SUCCESS;
    }
    
   
    
    /**
     * 删除客户
     * @return
     */
    public String delete() {
    	String[] deleteIds = ServletActionContext.getRequest().getParameterValues("clientId");
    	clientService.removeById(deleteIds);
    	return SUCCESS;
    }

	
	public ClientService getClientService() {
		return clientService;
	}

	@Resource(name="clientService")
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getFromWhere() {
		return fromWhere;
	}

	public void setFromWhere(String fromWhere) {
		this.fromWhere = fromWhere;
	}




}
