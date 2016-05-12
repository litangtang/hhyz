package com.hhyzoa.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.ClientDao;
import com.hhyzoa.dao.ProductSellDao;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.ProductSell;
import com.hhyzoa.service.ProductSellService;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.FormatUtil;


@Component("productSellService")
public class ProductSellServiceImpl implements ProductSellService {
	
	private ClientDao clientDao;
	private ProductSellDao productSellDao;

	/**
	 * 增加的时候计算3个累计
	 * 件数累计、数量累计、金额累计
	 * 如果没有日期比待增加的小的话,则是最早的日期
	 * 同时,日期比待增加日期大的累计需要加上待增加对象的件数、数量、金额
	 * 新增的某一天的记录为该天的最后一条记录
	 */
	public void add(ProductSell ps) throws Exception{
		Date sellDate = ps.getSellDate();
		Integer year = DateUtil.getYearFromDate(sellDate);
		Integer month = DateUtil.getMonFromDate(sellDate);
		Integer day = DateUtil.getDayFromDate(sellDate);
		ps.setYear(year);
		ps.setMonth(month);
		ps.setDay(day);
		
		//计算累计
		List<ProductSell> psList = productSellDao.findLeDay(year, month, day, ps.getBusiType());
		if(psList.size() > 0) {
			//psList的最后一项的累计 + 当前增加的
			ps.setPackagesAccu(psList.get(psList.size()-1).getPackagesAccu() + ps.getPackages());
			ps.setCountAccu(FormatUtil.floatFormat(psList.get(psList.size()-1).getCountAccu() + ps.getCount(), 2));
			ps.setAmountAccu(FormatUtil.floatFormat(psList.get(psList.size()-1).getAmountAccu() + ps.getAmount(), 2));
			
		}else {//每个月初始情况,保证数据库值不为空 
			ps.setPackagesAccu(ps.getPackages()==null?0:ps.getPackages());
			ps.setCountAccu(FormatUtil.floatFormat(ps.getCount()==null?0:ps.getCount(), 2));
			ps.setAmountAccu(FormatUtil.floatFormat(ps.getAmount()==null?0:ps.getAmount(), 2));
		}
		productSellDao.save(ps);
		
		//更新日期比待增加日期大的累计
		List<ProductSell> gtList = productSellDao.findGtDay(year, month, day, ps.getBusiType());
		if(gtList.size() > 0) {
			//由于是新增,所有对于之后的记录来说实际增加的值即为增量 
			ps.setPackagesChange(ps.getPackages());
			ps.setCountChange(FormatUtil.floatFormat(ps.getCount(), 2));
			ps.setAmountChange(FormatUtil.floatFormat(ps.getAmount(), 2));
			ps.setId(null);
			productSellDao.batchGtUpdateAccu(year, month, day, ps);
		}
		
	}
	
	/**
	 * 修改包括修改本条记录的累计及日期大于本条记录的所有累计
	 * 修改时只修改之后的记录 
	 */
	@Transactional
    public void modify(ProductSell ps) throws Exception {
		//防止页面上没有点击金额文本框,金额实际值没有计算
		ps.setAmount(ps.getPrice() * ps.getCount());
		
		//修改之前的 
		ProductSell oldPs = productSellDao.findById(ps.getId());
		int packagesChange = ps.getPackages() - oldPs.getPackages();
		float countChange = FormatUtil.floatFormat(ps.getCount() - oldPs.getCount(), 2);
		float amountChange = FormatUtil.floatFormat(ps.getAmount() - oldPs.getAmount(), 2);
		
		//修改修改的那条记录
		ps.setPackagesAccu(oldPs.getPackagesAccu() + packagesChange);
		ps.setCountAccu(FormatUtil.floatFormat(oldPs.getCountAccu() + countChange, 2));
		ps.setAmountAccu(FormatUtil.floatFormat(oldPs.getAmountAccu() + amountChange, 2));
		
		//oldPs组织修改时需要的参数 
		oldPs.setPackagesChange(packagesChange);
		oldPs.setCountChange(FormatUtil.floatFormat(countChange, 2));
		oldPs.setAmountChange(FormatUtil.floatFormat(amountChange, 2));
		
		productSellDao.update(ps);
		//修改后面的记录
		productSellDao.batchGtUpdateAccu(ps.getYear(), ps.getMonth(), ps.getDay(), oldPs);
    }

	/**
	 * 查询某个月的
	 * 如果ps.month为空则查询当前月
	 * 如果不为空，则查询查询月
	 */
	public PageBean listAll(int pageSize, int page, ProductSell ps) {
		if(null == ps) {
    		ps = new ProductSell();//初始情况 查询所有
    	}
		
		if(null == ps.getYear()) {
			ps.setYear(Integer.parseInt(DateUtil.getCurrntYear()));
		}
		
		if(null == ps.getMonth()) {
			ps.setMonth(Integer.parseInt(DateUtil.getCurrntMon()));
		}
		
        int allRow = productSellDao.getAllRowCount(ps);    //总记录数   
        int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
        final int offset = PageBean.countOffset(pageSize, page);    //当前页开始记录   
        //final int length = pageSize;    //每页记录数   
        final int currentPage = PageBean.countCurrentPage(page);   
        List<ProductSell> list = productSellDao.findAll(pageSize, offset, ps);
        //格式化数据
        for(ProductSell pSell : list) {
        	pSell.setCountAccu(FormatUtil.floatFormat(pSell.getCountAccu(), 2));
        	pSell.setAmountAccu(FormatUtil.floatFormat(pSell.getAmountAccu(), 2));
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
	
	public ProductSell queryById(int id) {
		return productSellDao.findById(id);
	}
    
    @Transactional
    public void removeById(String[] attrs) {
    	int[] ids = FormatUtil.StringArrayToIntArray(attrs);
    	productSellDao.deleteById(ids);
    }
    
    /**
	 * 初始时使用,用于计算已有数据的累计
	 * @return
	 */
    public void calAccu(Integer year, Integer month, Integer busiType) {
    	ProductSell proSell = new ProductSell();
    	proSell.setYear(year);
    	proSell.setMonth(month);
    	proSell.setBusiType(busiType);
    	List<ProductSell> list = productSellDao.findByCon(proSell);
    	for(int i=0; i<list.size(); i++) {
    		ProductSell ps = list.get(i);
    		if(i == 0) {
    			ps.setPackagesAccu(ps.getPackages());
    			ps.setCountAccu(FormatUtil.floatFormat(ps.getCount(), 2));
	    		ps.setAmountAccu(FormatUtil.floatFormat(ps.getAmount(), 2));
    		}else {
	    		int pAccu = 0;
	    		float cAccu = 0;
	    		float aAccu = 0;
	    		for(int j=0; j<i; j++) {
	    			ProductSell psl = list.get(j);
	    			pAccu += psl.getPackages();
	    			cAccu += FormatUtil.floatFormat(psl.getCount(), 2);
	    			aAccu += FormatUtil.floatFormat(psl.getAmount(), 2);
	    		}
	    		
	    		ps.setPackagesAccu(ps.getPackages() + pAccu);
	    		ps.setCountAccu(FormatUtil.floatFormat(ps.getCount() + cAccu, 2));
	    		ps.setAmountAccu(FormatUtil.floatFormat(ps.getAmount() + aAccu, 2));
    		}
    		productSellDao.update(ps);
    	}
    }

	public ClientDao getClientDao() {
		return clientDao;
	}

	@Resource(name="clientDao")
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public ProductSellDao getProductSellDao() {
		return productSellDao;
	}

	@Resource(name="productSellDao")
	public void setProductSellDao(ProductSellDao productSellDao) {
		this.productSellDao = productSellDao;
	}

}