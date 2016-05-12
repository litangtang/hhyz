package com.hhyzoa.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.ProductArrangeDao;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.ProductArrange;
import com.hhyzoa.model.Trade;
import com.hhyzoa.service.ProductArrangeService;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.FormatUtil;


@Component("productArrangeService")
public class ProductArrangeServiceImpl implements ProductArrangeService {
	
	private ProductArrangeDao productArrangeDao;

	/**
	 * 增加的时候计算3个累计
	 * 件数累计、数量累计、金额累计
	 * 如果没有日期比待增加的小的话,则是最早的日期
	 * 同时,日期比待增加日期大的累计需要加上待增加对象的件数、数量、金额
	 */
	public void add(ProductArrange pa) throws Exception{
		Date sellDate = pa.getRecordDate();
		Integer year = DateUtil.getYearFromDate(sellDate);
		Integer month = DateUtil.getMonFromDate(sellDate);
		Integer day = DateUtil.getDayFromDate(sellDate);
		pa.setYear(year);
		pa.setMonth(month);
		pa.setDay(day);
		
		//设置默认值
		if(null == pa.getPackages())
			pa.setPackages(0);
		if(null == pa.getCount())
			pa.setCount(Float.valueOf(0));
		if(null == pa.getPrice())
			pa.setPrice(Float.valueOf(0));
		if(null == pa.getAmount())
			pa.setAmount(Float.valueOf(0));
		if(null == pa.getIsSend())
			pa.setIsSend(0);
		
		productArrangeDao.save(pa);
	}
	
	@Transactional
    public void modify(ProductArrange pa) throws Exception {
		//防止页面上没有点击金额文本框,金额实际值没有计算
		pa.setAmount(pa.getPrice() * pa.getCount());
		
		//修改之前的 
		ProductArrange oldPa = productArrangeDao.findById(pa.getId());
		pa.setId(oldPa.getId());
		
		//设置默认值
		if(null == pa.getPackages())
			pa.setPackages(0);
		if(null == pa.getCount())
			pa.setCount(Float.valueOf(0));
		if(null == pa.getPrice())
			pa.setPrice(Float.valueOf(0));
		if(null == pa.getAmount())
			pa.setAmount(Float.valueOf(0));
		if(null == pa.getIsSend())
			pa.setIsSend(0);
		
		productArrangeDao.update(pa);
    }

	/**
	 * 查询某个月的
	 * 如果ps.month为空则查询当前月
	 * 如果不为空，则查询查询月
	 */
	public PageBean listAll(int pageSize, int page, ProductArrange pa) {
		if(null == pa) {
    		pa = new ProductArrange();//初始情况 查询所有
    	}
		
		if(null == pa.getYear()) {
			pa.setYear(Integer.parseInt(DateUtil.getCurrntYear()));
		}
		
		if(null == pa.getMonth()) {
			pa.setMonth(Integer.parseInt(DateUtil.getCurrntMon()));
		}
		
        int allRow = productArrangeDao.getAllRowCount(pa);    //总记录数   
        int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
        final int offset = PageBean.countOffset(pageSize, page);    //当前页开始记录   
        //final int length = pageSize;    //每页记录数   
        final int currentPage = PageBean.countCurrentPage(page);   
        List<ProductArrange> list = productArrangeDao.findAll(pageSize, offset, pa);      //"一页"的记录 
        
        if(null != list && list.size() > 0) {
        	for(int i=list.size()-1; i>=0; i--) {
        		ProductArrange t = list.get(i);
        		
        		/******************************************************************
        		 * 			计算累计方法
        		 * 1. 获得两个对象,当前遍历的对象和previous对象
        		 * 2. 当前对象需要累加的字段+previous对象该字段的累加=当前对象该字段的累加
        		 * 3. 保证列表是有序的 ,由于列表本身是按时间倒序排序,所以从最后一项开始累加
        		 *  packageAccu //件数累计
        		 *  countAccu;	//数量累计
        		 *  amountAccu //金额累计
        		 */
        		
        		if(i == list.size()-1) {
        			t.setPackageAccu(t.getPackages());
        			t.setPackageAccuStr(String.valueOf(t.getPackageAccu()));
        			t.setCountAccu(t.getCount());
        			t.setCountAccuStr(String.valueOf(t.getCountAccu()));
        			t.setAmountAccu(t.getAmount());
        			t.setAmountAccuStr(FormatUtil.convertE2Num(String.valueOf(t.getAmountAccu())));
        		}else {
        			ProductArrange prevProductArrange = list.get(i+1);//获得列表中前一个对象,倒序排序相加,所以这里为+1而不是-1
        			t.setPackageAccu(prevProductArrange.getPackageAccu() + t.getPackages());
        			t.setPackageAccuStr(String.valueOf(t.getPackageAccu()));
        			t.setCountAccu(prevProductArrange.getCountAccu() + t.getCount());
        			t.setCountAccuStr(String.valueOf(t.getCountAccu()));
        			t.setAmountAccu(prevProductArrange.getAmountAccu() + t.getAmount());
        			t.setAmountAccuStr(FormatUtil.convertE2Num(String.valueOf(t.getAmountAccu())));
        		}
        	}
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
	
	public ProductArrange queryById(int id) {
		return productArrangeDao.findById(id);
	}
    
    @Transactional
    public void removeById(String[] attrs) {
    	int[] ids = FormatUtil.StringArrayToIntArray(attrs);
    	productArrangeDao.deleteById(ids);
    }
    

	public ProductArrangeDao getProductArrangeDao() {
		return productArrangeDao;
	}

	@Resource(name="productArrangeDao")
	public void setProductArrangeDao(ProductArrangeDao productArrangeDao) {
		this.productArrangeDao = productArrangeDao;
	}

}
