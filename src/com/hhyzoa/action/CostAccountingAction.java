package com.hhyzoa.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hhyzoa.model.Client;
import com.hhyzoa.model.CostAccounting;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.service.ClientService;
import com.hhyzoa.service.CostAccountingService;
import com.hhyzoa.util.Constants;

public class CostAccountingAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private CostAccountingService costAccountingService;
	private ClientService clientService;
	private HttpServletRequest request;
	
	public CostAccounting costAccounting;
	
	
	private int page;    //第几页   
    private PageBean pageBean;    //包含分布信息的bean  
	
	/**
	 * 增加初始化
	 * @return
	 */
    public String forAdd() {
    	request = ServletActionContext.getRequest();
    	//销售客户Map
        Map<Integer,String> clientMap = clientService.queryByType(Constants.SELL_CUSTOMER);
        request.setAttribute("clientMap", clientMap);
    	return SUCCESS;
    }
    
    /**
	 * 增加初始化 for  合同价格
	 * @return
	 */
    public String forAdd2() {
    	request = ServletActionContext.getRequest();
    	//销售客户Map
        Map<Integer,String> clientMap = clientService.queryByType(Constants.SELL_CUSTOMER);
        request.setAttribute("clientMap", clientMap);
    	return SUCCESS;
    }
	
    /**
     * 添加成功以后跳转到核算成本页面
     * @return
     */
	public String add() {
		request = ServletActionContext.getRequest();
		String tid = request.getParameter("selectClientId");
		if(null != tid && !tid.trim().equals("-1")) {
			Client client = clientService.queryById(Integer.parseInt(tid));
			if(null != client) {
				this.costAccounting.setClient(client);
			}
		}
		costAccountingService.add(costAccounting);
		return SUCCESS;
		
	}
	
	
	 /**
     * 添加成功以后跳转到核算成本页面  for  合同价格
     * @return
     */
	public String add2() {
		request = ServletActionContext.getRequest();
		String tid = request.getParameter("selectClientId");
		if(null != tid && !tid.trim().equals("-1")) {
			Client client = clientService.queryById(Integer.parseInt(tid));
			if(null != client) {
				this.costAccounting.setClient(client);
			}
		}
		costAccountingService.add2(costAccounting);
		return SUCCESS;
		
	}
	
	public String forUpdate() {
		request = ServletActionContext.getRequest();
    	//销售客户Map
        Map<Integer,String> clientMap = clientService.queryByType(Constants.SELL_CUSTOMER);
        
        String costId = request.getParameter("costId");
        if(null != costId && !"".equals(costId.trim())) {
        	this.costAccounting = costAccountingService.queryById(Integer.parseInt(costId));
//        	this.costAccounting.setClientName(clientMap.get(this.costAccount))
        }
        request.setAttribute("clientMap", clientMap);
		return SUCCESS;
	}
	
	/**
	 * update时不能修改客户名称(客户id)
	 * @return
	 */
	public String update() {
		request = ServletActionContext.getRequest();
    	return SUCCESS;
	}
	
	/**
	 * 生产成本核算列表页面
	 * @return
	 */
	public String listAll() {
		request = ServletActionContext.getRequest();
		CostAccounting t = new CostAccounting();//封装查询条件 
    	
    	String selectClientId = request.getParameter("selectClientId");//clientId
    	if(null != selectClientId && !"".equals(selectClientId.trim()) && !"-1".equals(selectClientId.trim())) {
    		Client tmp = new Client();
    		tmp.setId(Integer.parseInt(selectClientId.trim()));
    		t.setClient(tmp);
    	}
//    	
//    	String searchDate = request.getParameter("searchDate");
//    	if(null != searchDate && !"".equals(searchDate)) {
//    		try {
//				java.sql.Date sdate = DateUtil.strToDate(searchDate);
//				t.setDate(sdate);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//    	}
    	 
    	
    	//分页的pageBean,参数pageSize表示每页显示记录数,page为当前页   
        this.pageBean = costAccountingService.listAll(Constants.PAGE_SIZE, page,t); 
        //销售客户Map
        Map<Integer,String> clientMap = clientService.queryByType(Constants.SELL_CUSTOMER);
        request.setAttribute("clientMap", clientMap);
        request.setAttribute("selectClientId", selectClientId);
    	return SUCCESS;
    }
	
	
	/**
     * 删除
     * @return
     */
    public String delete() {
    	request = ServletActionContext.getRequest();
    	String[] deleteIds = request.getParameterValues("costId");
    	costAccountingService.removeById(deleteIds);
    	return SUCCESS;
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

	public CostAccountingService getCostAccountingService() {
		return costAccountingService;
	}

	@Resource(name="tradeService")
	public void setCostAccountingService(CostAccountingService costAccountingService) {
		this.costAccountingService = costAccountingService;
	}

	public CostAccounting getCostAccounting() {
		return costAccounting;
	}

	public void setCostAccounting(CostAccounting costAccounting) {
		this.costAccounting = costAccounting;
	}

	public ClientService getClientService() {
		return clientService;
	}

	@Resource(name="clientService")
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}


}
