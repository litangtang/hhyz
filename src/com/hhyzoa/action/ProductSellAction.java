package com.hhyzoa.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.ProductSell;
import com.hhyzoa.service.ProductSellService;
import com.hhyzoa.util.Constants;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.StringUtil;

public class ProductSellAction extends BaseAction {
	
	private ProductSellService productSellService;
	private HttpServletRequest request;
	
	private int page;    //第几页   
    private PageBean pageBean;    //包含分布信息的bean 
    
    public ProductSell productSell;
    
    private String paramBusiType;
	
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
		
		ProductSell ps = new ProductSell();//封装查询条件 
		
		//1-精炼一级油，2-国标二级油，3-蓖麻饼
		String busiTypeStr = request.getParameter("busiType");
		int busiType = 0;
		if(null != busiTypeStr && !"".equals(busiTypeStr)) {
			busiType = Integer.parseInt(busiTypeStr);
		}
		ps.setBusiType(busiType);
		ps.setYear(Integer.parseInt(searchYear));
		ps.setMonth(Integer.parseInt(searchMonth));
		
    	
    	//分页的pageBean,参数pageSize表示每页显示记录数,page为当前页   
        this.pageBean = productSellService.listAll(Constants.PAGE_SIZE, page,ps); 
        
        request.setAttribute("searchYear", searchYear);
        request.setAttribute("searchMonth", searchMonth);
        request.setAttribute("yearList", DateUtil.getAllYearList());//年List
        request.setAttribute("monthList", DateUtil.getAllMonList());//1-12月List
        request.setAttribute("busiType", busiType);
    	return SUCCESS;
    }
	
	
	public String forAdd() {
		request = ServletActionContext.getRequest();
		String busiTypeStr = request.getParameter("busiType");
		request.setAttribute("busiType", busiTypeStr);
		return SUCCESS;
	}
	
	public String add() throws Exception{
		request = ServletActionContext.getRequest();
		String busiTypeStr = StringUtil.trim(request.getParameter("busiTypeHid"));
		if(null != busiTypeStr && !"".equals(busiTypeStr)) {
			productSell.setBusiType(Integer.parseInt(busiTypeStr));
		}
		
		productSellService.add(productSell);
		
		this.paramBusiType = busiTypeStr;
		request.setAttribute("busiType", busiTypeStr);
		return SUCCESS;
	}
	
	public String forUpdate() {
		request = ServletActionContext.getRequest();
		String busiTypeStr = StringUtil.trim(request.getParameter("busiType"));
		this.paramBusiType = busiTypeStr;
		
		String productSellId = request.getParameter("productSellId");
		if(null != productSellId && !"".equals(productSellId.trim())) {
			this.productSell = productSellService.queryById(Integer.parseInt(productSellId.trim()));
		}
		
		
		request.setAttribute("busiType", busiTypeStr);
		return SUCCESS;
	}
	
	public String update() throws Exception{
		request = ServletActionContext.getRequest();
		String busiTypeStr = StringUtil.trim(request.getParameter("busiTypeHid"));
		this.paramBusiType = busiTypeStr;
		
		productSell.setBusiType(Integer.parseInt(busiTypeStr));
		productSellService.modify(productSell);
		
		request.setAttribute("busiType", busiTypeStr);
		return SUCCESS;
	}
	
	public String delete() {
    	request = ServletActionContext.getRequest();
    	String[] deleteIds = request.getParameterValues("productSellId");
    	String busiTypeStr = StringUtil.trim(request.getParameter("busiTypeHid"));
    	this.paramBusiType = busiTypeStr;
    	
    	productSellService.removeById(deleteIds);
    	request.setAttribute("busiType", busiTypeStr);
    	return SUCCESS;
    }
	
	/**
	 * 初始时使用,用于计算已有数据的累计
	 * @return
	 */
	public String calAccu() {
		request = ServletActionContext.getRequest();
		String searchYear = StringUtil.trim((String)request.getParameter("searchYear"));
		if("".equals(searchYear)) {
			searchYear = DateUtil.getCurrntYear();
		}
		
		String searchMonth = StringUtil.trim((String)request.getParameter("searchMonth"));
		if("".equals(searchMonth)) {
			searchMonth = DateUtil.getCurrntMon();
		}
		
		String busiTypeStr = StringUtil.trim(request.getParameter("busiTypeHid"));
    	this.paramBusiType = busiTypeStr;
		
		productSellService.calAccu(Integer.parseInt(searchYear), Integer.parseInt(searchMonth), Integer.parseInt(busiTypeStr));
		
		request.setAttribute("busiType", busiTypeStr);
		request.setAttribute("searchYear", searchYear);
	    request.setAttribute("searchMonth", searchMonth);
	    request.setAttribute("yearList", DateUtil.getAllYearList());//年List
        request.setAttribute("monthList", DateUtil.getAllMonList());//1-12月List
		
		return SUCCESS;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public ProductSellService getProductSellService() {
		return productSellService;
	}

	public void setProductSellService(ProductSellService productSellService) {
		this.productSellService = productSellService;
	}

	public ProductSell getProductSell() {
		return productSell;
	}

	public void setProductSell(ProductSell productSell) {
		this.productSell = productSell;
	}


	public String getParamBusiType() {
		return paramBusiType;
	}


	public void setParamBusiType(String paramBusiType) {
		this.paramBusiType = paramBusiType;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}

}
