package com.hhyzoa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hhyzoa.dao.BuySellSaveServiceDao;
import com.hhyzoa.model.BuySellSave;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.service.BuySellSaveService;
import com.hhyzoa.util.DateUtil;

@Component("buySellSaveService")
public class BuySellSaveServiceImpl implements BuySellSaveService {
	
	private BuySellSaveServiceDao buySellSaveDao;
	
	/**
     * 增加一条进销存记录
     * @param buySellSave
     */
    public void add(BuySellSave buySellSave) {
    	Double buyMoney = buySellSave.getBuyAmount() * buySellSave.getBuyPrice();//当月购进金额
    }

	
	/**
	 * 分页查询所有进销存记录
	 * @param pageSize 每页大小 
	 * @param page 当前第几页
	 * @param buySellSave 封装了查询条件
	 * @return PageBean 封装了分页信息(包括记录集list)的Bean
	 */
	public PageBean queryAll(int pageSize, int page,
			BuySellSave buySellSave) {
		 int allRow = buySellSaveDao.getAllRowCount(buySellSave);    //总记录数   
	     int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
	     final int offset = PageBean.countOffset(pageSize, page);    //当前页开始记录   
	     final int currentPage = PageBean.countCurrentPage(page);	//计算当前页，页码从1开始而不是0   
	     List<BuySellSave> list = buySellSaveDao.findAll(pageSize, offset, buySellSave);      //"一页"的记录 
	     
	     //格式化日期
	     for(BuySellSave bss : list) {
	    	 bss.setDate(DateUtil.dateFormat(bss.getDate()));
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


	public BuySellSaveServiceDao getBuySellSaveDao() {
		return buySellSaveDao;
	}

	@Resource(name="buySellSaveDao")
	public void setBuySellSaveDao(BuySellSaveServiceDao buySellSaveDao) {
		this.buySellSaveDao = buySellSaveDao;
	}

}
