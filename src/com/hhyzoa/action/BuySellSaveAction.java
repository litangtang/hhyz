package com.hhyzoa.action;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hhyzoa.model.BuySellSave;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.service.BuySellSaveService;
import com.hhyzoa.util.Constants;
import com.hhyzoa.util.DateUtil;

public class BuySellSaveAction extends BaseAction {
	
	private HttpServletRequest request;
	
	private BuySellSave buySellSave ;
	private BuySellSaveService buySellSaveService ;
	
	private int page;    //第几页   
    private PageBean pageBean;    //包含分布信息的bean  
    
    public String forAdd() {
    	return SUCCESS;
    }
    
    public String add() {
    	request = ServletActionContext.getRequest();
    	
    	return SUCCESS;
    }
    
    /**
     * 列出所有进销存统计记录
     * @return
     */
    public String listAll() {
    	request = ServletActionContext.getRequest();
    	BuySellSave queryBss = new BuySellSave();//封装查询条件 
    	
    	String searchDate = request.getParameter("searchDate");
    	if(null != searchDate && !"".equals(searchDate)) {
    		try {
				java.sql.Date sdate = DateUtil.strToDate(searchDate);
				queryBss.setDate(sdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	
    	//分页的pageBean,参数pageSize表示每页显示记录数,page为当前页   
        this.pageBean = buySellSaveService.queryAll(Constants.PAGE_SIZE, page,queryBss);  
        
        request.setAttribute("searchDate", searchDate);
    	return SUCCESS;
    }
    
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public BuySellSave getBuySellSave() {
		return buySellSave;
	}
	public void setBuySellSave(BuySellSave buySellSave) {
		this.buySellSave = buySellSave;
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

	public BuySellSaveService getBuySellSaveService() {
		return buySellSaveService;
	}

	@Resource(name="buySellSaveService")
	public void setBuySellSaveService(BuySellSaveService buySellSaveService) {
		this.buySellSaveService = buySellSaveService;
	}
    
    

}
