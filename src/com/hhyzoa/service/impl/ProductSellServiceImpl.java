package com.hhyzoa.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger log = LoggerFactory.getLogger(ProductSellServiceImpl.class);
	
	private ClientDao clientDao;
	private ProductSellDao productSellDao;

	/**
	 * 增加的时候计算3个累计,件数累计、数量累计、金额累计
	 * 1. 如果没有日期比待增加的小的话,则是最早的日期
	 * 2. 日期比待增加日期大的累计需要加上待增加对象的件数、数量、金额即增量
	 * 3. 新增的某一天的记录为该天的最后一条记录
	 * 4. 增加之前先按月重置所有记录的3个累计值，防止删除中间某一条记录后，导致新增加的累计计算错误 - 暂时不需要，保证删除完重置即可
	 */
	public void add(ProductSell ps) throws Exception{
		Date sellDate = ps.getSellDate();
		Integer year = DateUtil.getYearFromDate(sellDate);
		Integer month = DateUtil.getMonFromDate(sellDate);
		Integer day = DateUtil.getDayFromDate(sellDate);
		ps.setYear(year);
		ps.setMonth(month);
		ps.setDay(day);
		
		//2016-05-17 增加 暂时不需要，保证删除完重置即可
//		log.info(String.format("重置[%s]年[%s]月busiType为[%s]的所有记录累计开始......", ps.getYear(), ps.getMonth(), ps.getBusiType()));
//		calAccu(ps.getYear(), ps.getMonth(),ps.getBusiType());
//		log.info("重置所有记录累计结束......");
		
		//1. 计算累计
		List<ProductSell> psList = productSellDao.findLeDay(year, month, day, ps.getBusiType());
		if(psList.size() > 0) {
			//psList的最后一项的累计 + 当前增加的
			ps.setPackagesAccu(psList.get(psList.size()-1).getPackagesAccu() + ps.getPackages());
			ps.setCountAccu(FormatUtil.doubleFormat(psList.get(psList.size()-1).getCountAccu() + ps.getCount(), 2));
			ps.setAmountAccu(FormatUtil.doubleFormat(psList.get(psList.size()-1).getAmountAccu() + ps.getAmount(), 2));
			
		}else {//每个月初始情况,保证数据库值不为空 
			ps.setPackagesAccu(ps.getPackages()==null?0:ps.getPackages());
			ps.setCountAccu(FormatUtil.doubleFormat(ps.getCount()==null?0:ps.getCount(), 2));
			ps.setAmountAccu(FormatUtil.doubleFormat(ps.getAmount()==null?0:ps.getAmount(), 2));
		}
		productSellDao.save(ps);
		
		//2. 更新日期比待增加日期大的累计
		List<ProductSell> gtList = productSellDao.findGtDay(year, month, day, ps.getBusiType());
		if(gtList.size() > 0) {
			//由于是新增,所有对于之后的记录来说实际增加的值即为增量 
			ps.setPackagesChange(ps.getPackages());
			ps.setCountChange(FormatUtil.doubleFormat(ps.getCount(), 2));
			ps.setAmountChange(FormatUtil.doubleFormat(ps.getAmount(), 2));
			ps.setId(null);
			productSellDao.batchGtUpdateAccu(year, month, day, ps);
		}
	}
	
	/**
	 * 1. 修改包括修改本条记录的累计及日期大于本条记录的所有累计,修改时只修改之后的记录
	 * 2. 修改完以后按月更新修改记录所在月的所有记录的所有累计，防止删除 - 不存在，只要删除完重置累计即可
	 */
	@Transactional
    public void modify(ProductSell ps) throws Exception {
		//防止页面上没有点击金额文本框,金额实际值没有计算
		ps.setAmount(ps.getPrice() * ps.getCount());
		
		//修改之前的 
		ProductSell oldPs = productSellDao.findById(ps.getId());
		int packagesChange = ps.getPackages() - oldPs.getPackages();
		double countChange = FormatUtil.doubleFormat(ps.getCount() - oldPs.getCount(), 2);
		double amountChange = FormatUtil.doubleFormat(ps.getAmount() - oldPs.getAmount(), 2);
		
		//修改修改的那条记录
		ps.setPackagesAccu(oldPs.getPackagesAccu() + packagesChange);
		ps.setCountAccu(FormatUtil.doubleFormat(oldPs.getCountAccu() + countChange, 2));
		ps.setAmountAccu(FormatUtil.doubleFormat(oldPs.getAmountAccu() + amountChange, 2));
		
		//oldPs组织修改时需要的参数 
		oldPs.setPackagesChange(packagesChange);
		oldPs.setCountChange(FormatUtil.doubleFormat(countChange, 2));
		oldPs.setAmountChange(FormatUtil.doubleFormat(amountChange, 2));
		
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
        	pSell.setCountAccu(FormatUtil.doubleFormat(pSell.getCountAccu(), 2));
        	pSell.setAmountAccu(FormatUtil.doubleFormat(pSell.getAmountAccu(), 2));
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
    
	/**
	 * 删除时只能在同一个页面删除同一个月的多条记录
	 * 删除完成以后，需要重置该月份的所有记录的3个累计值
	 */
    @Transactional
    public void removeById(int[] ids, Integer year, Integer month, Integer busiType) {
    	log.info("删除记录开始");
    	productSellDao.deleteById(ids);
    	log.info(String.format("删除完成，重置[%s]年[%s]月类型为[%s]的所有累计开始......", year, month, busiType));
    	calAccu(year, month, busiType);
    	log.info(String.format("重置[%s]年[%s]月类型为[%s]的所有累计结束......", year, month, busiType));
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
    			ps.setCountAccu(FormatUtil.doubleFormat(ps.getCount(), 2));
	    		ps.setAmountAccu(FormatUtil.doubleFormat(ps.getAmount(), 2));
    		}else {
	    		int pAccu = 0;
	    		double cAccu = 0;
				double aAccu = 0;
	    		for(int j=0; j<i; j++) {
	    			ProductSell psl = list.get(j);
	    			pAccu += psl.getPackages();
	    			cAccu += FormatUtil.doubleFormat(psl.getCount(), 2);
	    			aAccu += FormatUtil.doubleFormat(psl.getAmount(), 2);
	    		}
	    		
	    		ps.setPackagesAccu(ps.getPackages() + pAccu);
	    		ps.setCountAccu(FormatUtil.doubleFormat(ps.getCount() + cAccu, 2));
	    		ps.setAmountAccu(FormatUtil.doubleFormat(ps.getAmount() + aAccu, 2));
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
