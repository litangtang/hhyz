package com.hhyzoa.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hhyzoa.model.Invoice;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.service.InvoiceService;
import com.hhyzoa.util.Constants;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.StringUtil;

public class InvoiceAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private InvoiceService invoiceService;
	private HttpServletRequest request;
	
	private int page;    //第几页   
    private PageBean pageBean;    //包含分布信息的bean 
    
    public Invoice invoice;
    
	
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
		
		Invoice invoice = new Invoice();//封装查询条件 
		
		invoice.setYear(Integer.parseInt(searchYear));
		invoice.setMonth(Integer.parseInt(searchMonth));
		
    	
    	//分页的pageBean,参数pageSize表示每页显示记录数,page为当前页   
        this.pageBean = invoiceService.listAll(Constants.PAGE_SIZE, page,invoice); 
        
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
		invoiceService.add(invoice);
		return SUCCESS;
	}
	
	public String forUpdate() {
		request = ServletActionContext.getRequest();
		
		String invoiceId = request.getParameter("invoiceId");
		if(!"".equals(StringUtil.trim(invoiceId))) {
			this.invoice = invoiceService.queryById(Integer.parseInt(invoiceId.trim()));
		}
		return SUCCESS;
	}
	
	public String update() throws Exception{
		request = ServletActionContext.getRequest();
		
		invoiceService.modify(invoice);
		
		return SUCCESS;
	}
	
	public String delete() {
    	request = ServletActionContext.getRequest();
    	String[] deleteIds = request.getParameterValues("invoiceId");
    	
    	invoiceService.removeById(deleteIds);
    	return SUCCESS;
    }
	

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public InvoiceService getInvoiceService() {
		return invoiceService;
	}


	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}


	public Invoice getInvoice() {
		return invoice;
	}


	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
