package com.hhyzoa.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hhyzoa.dao.InvoiceDao;
import com.hhyzoa.model.Invoice;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.service.InvoiceService;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.FormatUtil;

@Component("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {
	
	private static final double TAX_RATE = 0.13;//税率 20190429 从4月份起税率由16%调整为13%
	private InvoiceDao invoiceDao;


	/**
	 * 不含税金额 = 原始金额 /1.17
	 * 销项税 = 不含税金额 * 0.17
	 *
	 * 20190429
	 * 增值税是一种价外税，即对税前价征税，增值税=销项税 - 进项税
	 * 也就是说: origAmt = 不含税价格 + 销项税(不含税价格 * 税率)
	 * 不含税价格 = origAmt / （1 + 税率）
	 * 
	 * 新增的某一天的记录为该天的最后一条记录
	 */
	public void add(Invoice invoice) throws Exception {
		Date recordDate = invoice.getRecordDate();
		Integer year = DateUtil.getYearFromDate(recordDate);
		Integer month = DateUtil.getMonFromDate(recordDate);
		Integer day = DateUtil.getDayFromDate(recordDate);
		invoice.setYear(year);
		invoice.setMonth(month);
		invoice.setDay(day);
		
		//设置缺省值
		if(null == invoice.getCount())
			invoice.setCount(Double.valueOf(0));
		if(null == invoice.getPrice())
			invoice.setPrice(Double.valueOf(0));
		//以下三行计算顺序不能变
		invoice.setOrigAmt(FormatUtil.doubleFormat(invoice.getCount() * invoice.getPrice(), 2));
		invoice.setNoTaxAmt(FormatUtil.doubleFormat(invoice.getOrigAmt() / (1 + TAX_RATE), 2));
		invoice.setSellTax(FormatUtil.doubleFormat(invoice.getNoTaxAmt() * TAX_RATE, 2));
		
		//计算累计
		List<Invoice> invList = invoiceDao.findLeDay(year, month, day);
		if(invList.size() > 0) {
			//psList的最后一项的累计 + 当前增加的
			invoice.setNoTaxAccu(FormatUtil.doubleFormat(invList.get(invList.size()-1).getNoTaxAccu() + invoice.getNoTaxAmt(), 2));
			invoice.setSellTaxAccu(FormatUtil.doubleFormat(invList.get(invList.size()-1).getSellTaxAccu() + invoice.getSellTax(), 2));
			
		}else {//每个月初始情况,保证数据库值不为空 
			invoice.setNoTaxAccu(FormatUtil.doubleFormat(invoice.getNoTaxAmt()==null?0:invoice.getNoTaxAmt(), 2));
			invoice.setSellTaxAccu(FormatUtil.doubleFormat(invoice.getSellTax()==null?0:invoice.getSellTax(), 2));
		}
		invoiceDao.save(invoice);
		
		//更新日期比待增加日期大的累计
		List<Invoice> gtList = invoiceDao.findGtDay(year, month, day);
		if(gtList.size() > 0) {
			//由于是新增,所有对于之后的记录来说实际增加的值即为增量 
			invoice.setNoTaxAmtChange(FormatUtil.doubleFormat(invoice.getNoTaxAmt(), 2));
			invoice.setSellTaxChange(FormatUtil.doubleFormat(invoice.getSellTax(), 2));
			invoice.setId(null);
			invoiceDao.batchGtUpdateAccu(year, month, day, invoice);
		}
		
	}

	public PageBean listAll(int pageSize, int page, Invoice invoice) {
		if(null == invoice) {
			invoice = new Invoice();//初始情况 查询所有
    	}
		
		if(null == invoice.getYear()) {
			invoice.setYear(Integer.parseInt(DateUtil.getCurrntYear()));
		}
		
		if(null == invoice.getMonth()) {
			invoice.setMonth(Integer.parseInt(DateUtil.getCurrntMon()));
		}
		
        int allRow = invoiceDao.getAllRowCount(invoice);    //总记录数   
        int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
        final int offset = PageBean.countOffset(pageSize, page);    //当前页开始记录   
        final int currentPage = PageBean.countCurrentPage(page);   
        List<Invoice> list = invoiceDao.findAll(pageSize, offset, invoice);
        //格式化数据
        for(Invoice inv : list) {
        	inv.setNoTaxAccu(FormatUtil.doubleFormat(inv.getNoTaxAccu(), 2));
        	inv.setSellTaxAccu(FormatUtil.doubleFormat(inv.getSellTaxAccu(), 2));
        }
           
        //把分页信息保存到Bean中   
        PageBean pageBean = new PageBean();   
        pageBean.setPageSize(pageSize);       
        pageBean.setCurrentPage(currentPage);   
        pageBean.setAllRow(allRow);   
        pageBean.setTotalPage(totalPage);   
        pageBean.setList(list);   
        pageBean.init();   
        return pageBean;
	}

	public void removeById(String[] attrs) {
		int[] ids = FormatUtil.StringArrayToIntArray(attrs);
		invoiceDao.deleteById(ids);
		
	}

	public Invoice queryById(int id) {
		return invoiceDao.findById(id);
	}

	public void modify(Invoice invoice) throws Exception {
		//防止页面上没有点击金额文本框,金额实际值没有计算
		//以下三行计算顺序不能变
		invoice.setOrigAmt(FormatUtil.doubleFormat(invoice.getCount() * invoice.getPrice(), 2));
		invoice.setNoTaxAmt(FormatUtil.doubleFormat(invoice.getOrigAmt() / (1 + TAX_RATE), 2));
		invoice.setSellTax(FormatUtil.doubleFormat(invoice.getNoTaxAmt() * TAX_RATE, 2));
		
		//修改之前的 
		Invoice oldInv = invoiceDao.findById(invoice.getId());
		double noTaxAmtChange = FormatUtil.doubleFormat(invoice.getNoTaxAmt() - oldInv.getNoTaxAmt(), 2);
		double sellTaxChange = FormatUtil.doubleFormat(invoice.getSellTax() - oldInv.getSellTax(), 2);
		
		//修改修改的那条记录
		invoice.setNoTaxAccu(FormatUtil.doubleFormat(oldInv.getNoTaxAccu() + noTaxAmtChange, 2));
		invoice.setSellTaxAccu(FormatUtil.doubleFormat(oldInv.getSellTaxAccu() + sellTaxChange, 2));
		
		//oldPs组织修改时需要的参数 
		oldInv.setNoTaxAmtChange(FormatUtil.doubleFormat(noTaxAmtChange, 2));
		oldInv.setSellTaxChange(FormatUtil.doubleFormat(sellTaxChange, 2));
		
		invoiceDao.update(invoice);
		//修改后面的记录
		invoiceDao.batchGtUpdateAccu(invoice.getYear(), invoice.getMonth(), invoice.getDay(), oldInv);
		
	}

	public InvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	@Resource(name="invoiceDao")
	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	

}
