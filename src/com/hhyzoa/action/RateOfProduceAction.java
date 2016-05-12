package com.hhyzoa.action;

import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.RateOfProduce;
import com.hhyzoa.service.RateOfProduceService;
import com.hhyzoa.util.Constants;
import com.hhyzoa.util.DateUtil;

//@SuppressWarnings("unchecked")
@Component
public class RateOfProduceAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private RateOfProduceService rateOfProduceService;
	private RateOfProduce rateOfProduce;
	private HttpServletRequest request;
	
	private int page;    //第几页   
    private PageBean pageBean;    //包含分布信息的bean   
    
    public String forAdd() {
    	request = ServletActionContext.getRequest();
    	Map<Integer, String> numOfMachinesMap = rateOfProduceService.getNumOfMachinesMap();
    	request.setAttribute("NUM_OF_MACHINES_MAP", numOfMachinesMap);
    	return SUCCESS;
    }
    
    public String add() {
    	rateOfProduceService.add(rateOfProduce);
    	return SUCCESS;
    }
    
    /**
     * 修改 初始化
     * @return
     */
    public String forUpdate() {
		request = ServletActionContext.getRequest();
		
		String rateId = request.getParameter("rateId");
		if(null != rateId && !"".equals(rateId.trim())) {
			this.rateOfProduce = rateOfProduceService.queryById(Integer.parseInt(rateId.trim()));
			this.rateOfProduce.setNumOfMachinesStr(rateOfProduceService.getNumOfMachinesMap()
					.get(rateOfProduce.getNumOfMachines()));
			this.rateOfProduce.setDate(DateUtil.dateFormat(this.rateOfProduce.getDate()));
		}
		return SUCCESS;
	}
    
    /**
     * 修改 出品率
     * @return
     */
    public String update() {
    	request = ServletActionContext.getRequest();
		String rateId = request.getParameter("rateId");
		if(null != rateId && !"".equals(rateId.trim())) {
			RateOfProduce oldRateProduce = rateOfProduceService.queryById(Integer.parseInt(rateId.trim()));
			rateOfProduceService.modify(oldRateProduce,this.rateOfProduce);
		}
    	return SUCCESS;
    }
    
    /**
     * 列出所有出品率
     * @return
     */
    public String listAll() {
    	request = ServletActionContext.getRequest();
    	RateOfProduce r = new RateOfProduce();//封装查询条件 
    	
    	String searchDate = request.getParameter("searchDate");
    	if(null != searchDate && !"".equals(searchDate)) {
    		try {
				java.sql.Date sdate = DateUtil.strToDate(searchDate);
				r.setDate(sdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	
    	//分页的pageBean,参数pageSize表示每页显示记录数,page为当前页   
        this.pageBean = rateOfProduceService.queryAll(Constants.PAGE_SIZE, page,r);  
        
        request.setAttribute("searchDate", searchDate);
    	return SUCCESS;
    }
    
    /**
     * 删除Trade
     * @return
     */
    public String delete() {
    	request = ServletActionContext.getRequest();
    	String[] deleteIds = request.getParameterValues("rateId");
    	rateOfProduceService.removeById(deleteIds);
    	return SUCCESS;
    }
    
    
	public RateOfProduceService getRateOfProduceService() {
		return rateOfProduceService;
	}
	
	@Resource(name="rateOfProduceService")
	public void setRateOfProduceServic(RateOfProduceService rateOfProduceService) {
		this.rateOfProduceService = rateOfProduceService;
	}
	
	public RateOfProduce getRateOfProduce() {
		return rateOfProduce;
	}
	
	public void setRateOfProduce(RateOfProduce rateOfProduce) {
		this.rateOfProduce = rateOfProduce;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
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

}
