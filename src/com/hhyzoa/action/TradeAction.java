package com.hhyzoa.action;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hhyzoa.model.Client;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.Trade;
import com.hhyzoa.service.ClientService;
import com.hhyzoa.service.TradeService;
import com.hhyzoa.util.Constants;
import com.hhyzoa.util.DateUtil;

public class TradeAction extends BaseAction {
	
	private static final long serialVersionUID = 4980007116518278167L;
	
	private TradeService tradeService;
	private ClientService clientService;
	private HttpServletRequest request;
	
	public Trade trade;
	
	private int clientId ; //客户id
	private String clientName;//客户名称
	
	private int page;    //第几页   
    private PageBean pageBean;    //包含分布信息的bean  
    private String fromWhere ;	//对应与参数中的from，用于配置文件传递参数
	
	 //增加初始化
    public String forAdd() throws Exception {
    	request = ServletActionContext.getRequest();
    	String from = request.getParameter("from");
    	String tradeFlag = "1";//初始值为1即原料往来
    	if(null != from && !"".equals(from)) {
    		tradeFlag = from.substring(from.length()-1);
	    	if(from.startsWith("client")) {//客户直接跳转到增加往来
				this.clientId = Integer.parseInt(request.getParameter("clientId"));
				this.clientName = clientService.queryById(clientId).getName();
				
	    	}
	    	else if(from.startsWith("trade")) {//往来页面对应的增加事件
	    		/*
	    		 * type=0查询所有客户 
	    		 * type=1查询所有原料客户
	    		 * type=2查询所有销售客户
	    		 */
	    		Integer type = null ;
	    		String tmpType = request.getParameter("type");
	    		if(null != tmpType && !"".equals(tmpType)) {
	    			type = Integer.parseInt(tmpType);
	    		}
	    		//取得所有原料或者销售客户列表
	    		Map<Integer,String> clientMap = clientService.queryByType(type);
	    		request.setAttribute("clientMap", clientMap);
	    	}
	    	request.setAttribute("from", from);
	    	request.setAttribute("tradeFlag", tradeFlag);
	    	return SUCCESS;
    	}
    	return null;
    }
	
    /**
     * 添加成功以后跳转到个人往来页面
     * @return
     */
	public String tradeAdd() {
		request = ServletActionContext.getRequest();
		String from = request.getParameter("from");
		String tclientId = "";//客户跳转过来时去clientId,往来跳转过来时取selectClientId
		if(null != from && !"".equals(from.trim())) {
			if(from.startsWith("client")) {//从客户跳转过来，增加往来跳转到该客户的所有往来页面
				tclientId = request.getParameter("clientId");
			}else if(from.startsWith("trade")) {
				tclientId = request.getParameter("selectClientId");
			}
		}
		
		if(null != tclientId && !"".equals(tclientId)) {
			this.clientId = Integer.parseInt(tclientId);
			this.setFromWhere(from.trim());
			Client client = clientService.queryById(clientId);
			trade.setClient(client);
			tradeService.add(trade);
		}
		return SUCCESS;
		
	}
	
	public String forUpdate() {
		request = ServletActionContext.getRequest();
		
		String from = "trade_"+request.getParameter("type");
		
		String tradeId = request.getParameter("tradeId");
		if(null != tradeId && !"".equals(tradeId.trim())) {
			this.trade = tradeService.queryById(Integer.parseInt(tradeId.trim()));
			
			if(null != this.trade.getDate()) {
				this.trade.setDate(DateUtil.dateFormat(this.trade.getDate()));
			}
		}
		
		request.setAttribute("from",from);
		return SUCCESS;
	}
	
	/**
	 * 个人往来修改
	 * @return
	 */
	public String forUpdateOfSB() {
		request = ServletActionContext.getRequest();
		String tradeId = request.getParameter("tradeId");
		if(null != tradeId && !"".equals(tradeId.trim())) {
			this.trade = tradeService.queryById(Integer.parseInt(tradeId.trim()));
		}
		return SUCCESS;
	}
	
	/**
	 * update时不能修改客户名称(客户id)
	 * @return
	 */
	public String update() {
		request = ServletActionContext.getRequest();
		String from = request.getParameter("from");
		String tmpClientId = request.getParameter("clientId");
		String tradeId = request.getParameter("tradeId");
		
		Trade oldTrade = new Trade();//修改之前的trade

		if(null != tmpClientId && !"".equals(tmpClientId)) 
		{
			this.clientId = Integer.parseInt(tmpClientId);
//			服务层做下面的逻辑
//			Client client = clientService.queryById(clientId);
//			trade.setClient(client);
		}
		
		if(null != tradeId && !"".equals(tradeId.trim())) 
		{
			this.trade.setId(Integer.parseInt(tradeId.trim()));
			oldTrade = tradeService.queryById(Integer.parseInt(tradeId.trim()));
		}
		
		tradeService.modify(oldTrade,this.trade);
		
	    request.setAttribute("from", from);
		
    	return SUCCESS;
	}
	
	public String listAll() {
		request = ServletActionContext.getRequest();
    	Trade t = new Trade();//封装查询条件 
    	Map<Integer,String> clientMap = new HashMap<Integer,String>();//页面查询-客户下拉列表
    	String tradeFlag = request.getParameter("tradeFlag");//0为所有往来1为原料往来2为销售往来
    	String from = request.getParameter("from");
    	if(null == tradeFlag || "".equals(tradeFlag)) {
//    		String from = request.getParameter("from");
    		if(null != from && !"".equals(from)) {//删除往来时，跳转过来传递的是from
    			tradeFlag = from.substring(from.length()-1);
    			if(0 != Integer.parseInt(tradeFlag)) {
    				t.setFlag(Integer.parseInt(tradeFlag));
    			}
    		}else {
    			tradeFlag = "0";//默认值
    		}
    	}else if(0 != Integer.parseInt(tradeFlag)) {
    		t.setFlag(Integer.parseInt(tradeFlag));
    	}
    	
    	clientMap = clientService.queryByType(Integer.parseInt(tradeFlag)); 
    	
    	String searchClient = request.getParameter("searchClient");//clientId
    	if(null != searchClient && !"".equals(searchClient.trim()) && !"-1".equals(searchClient.trim())) {
    		Client tmp = new Client();
    		tmp.setId(Integer.parseInt(searchClient.trim()));
    		t.setClient(tmp);
    	}
    	
    	String searchDate = request.getParameter("searchDate");
    	if(null != searchDate && !"".equals(searchDate)) {
    		try {
				java.sql.Date sdate = DateUtil.strToDate(searchDate);
				t.setDate(sdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	 
    	
    	//分页的pageBean,参数pageSize表示每页显示记录数,page为当前页   
        this.pageBean = tradeService.listAll(Constants.PAGE_SIZE, page,t); 
        
        request.setAttribute("tradeFlag", tradeFlag);
        request.setAttribute("searchDate", searchDate);
        request.setAttribute("clientMap", clientMap);
        request.setAttribute("searchClient", searchClient);
    	return SUCCESS;
    }
	
	 /**
	 * 查询某个人的所有往来历史记录
	 * @return
	 */
	public String listAllOfSB() {
		request = ServletActionContext.getRequest();
		//来源，来自客户列表页面
		String from = request.getParameter("from");
		String cid = request.getParameter("clientId");
		
		String tradeFlag = "1";//初始值为1即原料往来
		if(null != from && !"".equals(from)) {
			tradeFlag = from.substring(from.length()-1);
		}
		
		
		Trade t = new Trade();//封装查询条件
		Client c = null ;
		if(null != cid && !"".equals(cid)) {
			this.clientId = Integer.parseInt(cid.trim());
			c = clientService.queryById(this.clientId);
			if(null != c) {
				t.setClient(c);
			}
		}
		
		String searchDate = request.getParameter("searchDate");
    	if(null != searchDate && !"".equals(searchDate)) {
    		try {
				java.sql.Date sdate = DateUtil.strToDate(searchDate);
				t.setDate(sdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
		
		this.pageBean = tradeService.listAllOfSB(t, Constants.PAGE_SIZE, page);
		this.setFromWhere(from);
		
		request.setAttribute("clientName", c.getName());
		request.setAttribute("searchDate", searchDate);
		request.setAttribute("clientId", cid);
		request.setAttribute("tradeFlag", tradeFlag);
		request.setAttribute("from", from);
		
		return SUCCESS;
	}
	
	
	/**
     * 删除Trade
     * @return
     */
    public String deleteFromSB() {
    	request = ServletActionContext.getRequest();
    	String from = request.getParameter("from");
    	String clientIdStr = request.getParameter("clientId")==null?"":request.getParameter("clientId").trim();
    	if(!"".equals(clientIdStr)) {
    		this.clientId = Integer.parseInt(clientIdStr);
    	}
    	String[] deleteIds = request.getParameterValues("tradeId");
    	if(null != from && !"".equals(from.trim())) {
    		this.setFromWhere(from.trim());
    	}
    	tradeService.removeById(deleteIds);
    	return SUCCESS;
    }
    
    public String delete() {
    	request = ServletActionContext.getRequest();
    	String from = request.getParameter("from");
    	String[] deleteIds = request.getParameterValues("tradeId");
    	if(null != from && !"".equals(from.trim())) {
    		this.setFromWhere(from.trim());
    	}
    	tradeService.removeById(deleteIds);
    	return SUCCESS;
    }

	public TradeService getTradeService() {
		return tradeService;
	}

	@Resource(name="tradeService")
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
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
