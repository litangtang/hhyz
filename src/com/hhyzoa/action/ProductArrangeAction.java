package com.hhyzoa.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.ProductArrange;
import com.hhyzoa.service.ProductArrangeService;
import com.hhyzoa.util.Constants;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.StringUtil;

/**
 * 订单发货安排
 * @author CQ
 *
 */
public class ProductArrangeAction extends BaseAction {
	
	private ProductArrangeService productArrangeService;
	private HttpServletRequest request;
	
	private int page;    //第几页   
    private PageBean pageBean;    //包含分布信息的bean 
    
    public ProductArrange productArrange;
    
    
    /**
    * 列表页面
    * @return
    */
	public String listAll() {
		request = ServletActionContext.getRequest();
		//页面选择的查询条件,年、月
		String searchYear = StringUtil.trim((String)request.getParameter("searchYear"));
		if("".equals(searchYear)) {
			searchYear = DateUtil.getCurrntYear();
		}
		
		String searchMonth = StringUtil.trim((String)request.getParameter("searchMonth"));
		if("".equals(searchMonth)) {
			searchMonth = DateUtil.getCurrntMon();
		}
		
		ProductArrange pa = new ProductArrange();//封装查询条件 
		
		pa.setYear(Integer.parseInt(searchYear));
		pa.setMonth(Integer.parseInt(searchMonth));
		
   	
		//分页的pageBean,参数pageSize表示每页显示记录数,page为当前页   
       this.pageBean = productArrangeService.listAll(Constants.PAGE_SIZE, page,pa); 
       
       request.setAttribute("searchYear", searchYear);
       request.setAttribute("searchMonth", searchMonth);
       request.setAttribute("yearList", DateUtil.getAllYearList());//年List
       request.setAttribute("monthList", DateUtil.getAllMonList());//1-12月List
   	   return SUCCESS;
   }
	
	
	public String forAdd() {
		 return SUCCESS;
	}
	
	public String add() throws Exception{
		request = ServletActionContext.getRequest();
		productArrangeService.add(productArrange);
		return SUCCESS;
	}
    
    
	public String forUpdate() {
		request = ServletActionContext.getRequest();
		
		String productArrangeId = request.getParameter("productArrangeId");
		if(null != productArrangeId && !"".equals(productArrangeId.trim())) {
			this.productArrange = productArrangeService.queryById(Integer.parseInt(productArrangeId.trim()));
		}
		
		return SUCCESS;
	}
	
	public String update() throws Exception{
		request = ServletActionContext.getRequest();
		productArrangeService.modify(productArrange);
		return SUCCESS;
	}
	
	public String delete() {
    	request = ServletActionContext.getRequest();
    	String[] deleteIds = request.getParameterValues("productArrangeId");
    	
    	productArrangeService.removeById(deleteIds);
    	return SUCCESS;
    }
    
    

	public ProductArrangeService getProductArrangeService() {
		return productArrangeService;
	}

	public void setProductArrangeService(ProductArrangeService productArrangeService) {
		this.productArrangeService = productArrangeService;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public ProductArrange getProductArrange() {
		return productArrange;
	}

	public void setProductArrange(ProductArrange productArrange) {
		this.productArrange = productArrange;
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
