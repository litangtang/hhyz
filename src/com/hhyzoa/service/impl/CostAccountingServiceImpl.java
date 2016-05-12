package com.hhyzoa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.CostAccountingDao;
import com.hhyzoa.model.CostAccounting;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.service.CostAccountingService;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.FormatUtil;

/**
 * 生产成本核算服务层实现类
 * @author lizhibin
 *
 */
@Component("costAccountingService")
public class CostAccountingServiceImpl implements CostAccountingService {
	
	private CostAccountingDao costAcountingDao;

	/**  
     * 分页查询所有 
     * @param pageSize 每页大小  
     * @param page 当前第几页  
     * @param costAccounting 封装查询条件
     * @return 封装了分页信息(包括记录集list)的Bean  
     */  
	public PageBean listAll(int pageSize, int page,
			CostAccounting costAccounting) {
		if(null == costAccounting) {
			costAccounting = new CostAccounting();//初始情况 查询所有
    	}
        int allRow = costAcountingDao.getAllRowCount(costAccounting);    //总记录数   
        int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
        final int offset = PageBean.countOffset(pageSize, page);    //当前页开始记录   
        //final int length = pageSize;    //每页记录数   
        final int currentPage = PageBean.countCurrentPage(page);   
        List<CostAccounting> list = costAcountingDao.findAll(pageSize, offset, costAccounting);      //"一页"的记录 
        for(CostAccounting ca : list) {
        	ca.setDate(DateUtil.dateFormat(ca.getDate()));
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
	
	/**
     * 通过id查询
     * @param id
     * @return CostAccounting
     */
    public CostAccounting queryById(int id) {
    	return costAcountingDao.findById(id);
    }
	
	/**
     * 增加一条成本核算记录
     * @param costAccounting
     */
    public void add(CostAccounting costAccounting) {
    	//对于需要计算的变量，需要重新计算，页面计算结果只是为了即时显示
    	//原料单价  = 原料价格 / 2000
    	Double materialUnitPrice = costAccounting.getMaterialPrice() / 2000 ;
    	costAccounting.setMaterialUnitPrice(materialUnitPrice);
    	//原料成本 = 原料价格 / 出油率
    	Integer materialCost = FormatUtil.doubleFormat(costAccounting.getMaterialPrice() / costAccounting.getRateOfOil(),0).intValue() ;
    	costAccounting.setMaterialCost(materialCost);
    	//二级油值 = 原料成本 - 副产品值
    	Integer ejyz = materialCost - costAccounting.getFcpz() ;
    	costAccounting.setEjyz(ejyz) ;
    	//二级净水成本(不带包装) = 二级油值 + 二级油加工费
    	Integer ejjsCost = ejyz + costAccounting.getEjyJgf() ;
    	costAccounting.setEjjsCost(ejjsCost);
    	//精炼损耗 = 二级净水成本(不带包装) * 一级油损耗率
    	Double jlsh = ejjsCost * costAccounting.getYjyShl() ;
    	costAccounting.setJlsh(FormatUtil.doubleFormat(jlsh, 0).intValue());
    	//一级净水成本  = 二级净水成本 + 一级油加工费 + 精炼损耗
    	Integer yjjsCost = ejjsCost + costAccounting.getYjyJgf() + costAccounting.getJlsh() ;
    	costAccounting.setYjjsCost(yjjsCost);
    	//销售成本  = 一级净水成本 + 国地两税 + 铁桶包装
    	Integer sellCost = yjjsCost + costAccounting.getTax() + costAccounting.getTtbz() ;
    	costAccounting.setSellCost(sellCost);
    	//销售价格(出厂价格)  = 销售利润 + 销售成本
    	Integer sellPrice = costAccounting.getSellProfit() + sellCost;
    	costAccounting.setSellPrice(sellPrice);
    	//送到价格 = 出厂价格  + 铁汽运费 
    	Integer finalPrice = sellPrice + costAccounting.getTqyf() ;
    	costAccounting.setFinalPrice(finalPrice);
    	
    	costAcountingDao.save(costAccounting);
    	
    }
    
    /**
     * 增加一条成本核算记录 for 合同价格(相当于送到价格)
     * @param costAccounting
     */
    public void add2(CostAccounting costAccounting) {
    	//对于需要计算的变量，需要重新计算，页面计算结果只是为了即时显示
    	//原料单价  = 原料价格 / 2000
    	Double materialUnitPrice = costAccounting.getMaterialPrice() / 2000 ;
    	costAccounting.setMaterialUnitPrice(materialUnitPrice);
    	//原料成本 = 原料价格 / 出油率
    	Integer materialCost = FormatUtil.doubleFormat(costAccounting.getMaterialPrice() / costAccounting.getRateOfOil(),0).intValue() ;
    	costAccounting.setMaterialCost(materialCost);
    	//二级油值 = 原料成本 - 副产品值
    	Integer ejyz = materialCost - costAccounting.getFcpz() ;
    	costAccounting.setEjyz(ejyz) ;
    	//二级净水成本(不带包装) = 二级油值 + 二级油加工费
    	Integer ejjsCost = ejyz + costAccounting.getEjyJgf() ;
    	costAccounting.setEjjsCost(ejjsCost);
    	//精炼损耗 = 二级净水成本(不带包装) * 一级油损耗率
    	Double jlsh = ejjsCost * costAccounting.getYjyShl() ;
    	costAccounting.setJlsh(FormatUtil.doubleFormat(jlsh, 0).intValue());
    	//一级净水成本  = 二级净水成本 + 一级油加工费 + 精炼损耗
    	Integer yjjsCost = ejjsCost + costAccounting.getYjyJgf() + costAccounting.getJlsh() ;
    	costAccounting.setYjjsCost(yjjsCost);
    	//销售成本  = 一级净水成本 + 国地两税 + 铁桶包装
    	Integer sellCost = yjjsCost + costAccounting.getTax() + costAccounting.getTtbz() ;
    	costAccounting.setSellCost(sellCost);
    	
    	//销售利润  = 合同价格(相当于送到价格)  - 铁汽运费 - 销售成本  
    	Integer sellProfit = costAccounting.getFinalPrice() - sellCost - costAccounting.getTqyf();
    	costAccounting.setSellProfit(sellProfit);
    	
    	costAcountingDao.save(costAccounting);
    	
    }

    
    /**
     * 根据id删除Trade
     * @param attrs 所选择的trade的id构成的数组
     */
    @Transactional
    public void removeById(String[] attrs) {
    	int[] ids = FormatUtil.StringArrayToIntArray(attrs);
    	costAcountingDao.deleteById(ids);
    }
    
	public CostAccountingDao getCostAcountingDao() {
		return costAcountingDao;
	}

	@Resource(name="costAcountingDao")
	public void setCostAcountingDao(CostAccountingDao costAcountingDao) {
		this.costAcountingDao = costAcountingDao;
	}

}
