package com.hhyzoa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.RateOfProduceDao;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.RateOfProduce;
import com.hhyzoa.service.RateOfProduceService;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.FormatUtil;

/**
 * 生产出品率包含出油率、出饼率业务实现类
 * @author lizhibin
 *
 */
@Component("rateOfProduceService")
public class RateOfProduceServiceImpl implements RateOfProduceService {
	
	private RateOfProduceDao rateOfProduceDao;
	
	private final int SCALE = 3 ; //保留小数位数
	private final Double RATE_MUTIPLY = 0.945;	//出品率基数
	//private final Double OIL_RATE = 0.93;	//油的比重

	@Transactional
	public void add(RateOfProduce rateOfProduce) {
		Double packagesMultiply = rateOfProduce.getPackagesMultiply();//饼规格
		Double numOfPackages = rateOfProduce.getNumOfPackages();//饼件数
		Double capacity = rateOfProduce.getCapacity();//当日油的容积
		Double oilRate = rateOfProduce.getOilRate();//比重
		Double rateOfOil = null; //出油率
		Double rateOfCake = null;//出饼率
		//Float zero = new Float(0); //可不可以为0？
		if(null != capacity && null != packagesMultiply && null != numOfPackages) {
			rateOfOil = countRateOfOil(oilRate, capacity, packagesMultiply, numOfPackages);//(capacity * OIL_RATE) / ((OIL_RATE + numOfPackages * packagesMultiply)/RATE_MUTIPLY) ;
			rateOfCake = countRateOfCake(oilRate, capacity, packagesMultiply, numOfPackages);//(numOfPackages * packagesMultiply) / ((OIL_RATE + numOfPackages * packagesMultiply)/RATE_MUTIPLY) ;
			rateOfProduce.setRateOfOil(FormatUtil.doubleFormat(rateOfOil,SCALE));
			rateOfProduce.setRateOfCake(FormatUtil.doubleFormat(rateOfCake,SCALE));
		}
		rateOfProduce.setFlag(0);
		rateOfProduceDao.save(rateOfProduce);
	}

	/**
	 * 查询所有出品率
	 * @param pageSize 每页大小
	 * @param page 当前第几页
	 * @param rateOfProduce 封装了查询条件
	 * @return PageBean 封装了分页信息(包括记录集list)的Bean
	 */
	public PageBean queryAll(int pageSize, int page,
			RateOfProduce rateOfProduce) {
		 int allRow = rateOfProduceDao.getAllRowCount(rateOfProduce);    //总记录数   
	     int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
	     final int offset = PageBean.countOffset(pageSize, page);    //当前页开始记录   
	     final int currentPage = PageBean.countCurrentPage(page);   
	     List<RateOfProduce> list = rateOfProduceDao.findAll(pageSize, offset, rateOfProduce);      //"一页"的记录 
	        if(null != list && list.size() > 0) {
	        	for(RateOfProduce r : list) {
	        		r.setDate(DateUtil.dateFormat(r.getDate()));
	        		if(null != r.getNumOfMachines()) {
	        			r.setNumOfMachinesStr(getNumOfMachinesMap().get(r.getNumOfMachines()));
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
	
	/**
     * 通过id查询
     * @param id
     * @return RateOfProduce
     */
    public RateOfProduce queryById(int id) {
    	return rateOfProduceDao.findById(id);
    }
	
	/**
     * 根据id删除rateOfProduce
     * @param attrs 所选择的rateOfProduce的id构成的数组
     */
    @Transactional
    public void removeById(String[] attrs) {
    	int[] ids = FormatUtil.StringArrayToIntArray(attrs);
    	rateOfProduceDao.deleteById(ids);
    }
	
    
    /**
     * 修改出品率
     * @param oldRateOfProduce 旧的出品率
     * @param newRateOfProduce 包含了修改后的比重，容积，饼规格和饼件数，但还没计算出品率
     */
    @Transactional
    public void modify(RateOfProduce oldRateOfProduce, RateOfProduce newRateOfProduce) {
    	if(oldRateOfProduce.getNumOfCans() != newRateOfProduce.getNumOfCans() || oldRateOfProduce.getNumOfPackages() != newRateOfProduce.getNumOfPackages()) {
    		newRateOfProduce.setId(oldRateOfProduce.getId());
    		newRateOfProduce.setNumOfMachines(oldRateOfProduce.getNumOfMachines());
    		newRateOfProduce.setFlag(oldRateOfProduce.getFlag());
    		//重新计算出油率和出饼率(计算方法应该单独提取出来，作为两个方法)
    		newRateOfProduce.setRateOfOil(countRateOfOil(newRateOfProduce.getOilRate(), newRateOfProduce.getCapacity(), newRateOfProduce.getPackagesMultiply(), newRateOfProduce.getNumOfPackages()));
    		newRateOfProduce.setRateOfCake(countRateOfCake(newRateOfProduce.getOilRate(), newRateOfProduce.getCapacity(), newRateOfProduce.getPackagesMultiply(), newRateOfProduce.getNumOfPackages()));
    		rateOfProduceDao.update(newRateOfProduce);
    	}
    }
    
	/**
	 * 单机生产/双机生产Map
	 * @return
	 */
	public Map<Integer, String> getNumOfMachinesMap() {
		Map<Integer, String> numOfMachinesMap = new HashMap<Integer, String>();
		numOfMachinesMap.put(1, "单机生产");
		numOfMachinesMap.put(2, "双机生产");
		return numOfMachinesMap;
	}
	
	/**
	 * 计算出油率
	 * 容积*OIL_RATE结果单位为kg
	 * @param oilRate 比重
	 * @param capacity 容积
	 * @param packagesMultiply 饼规格(190kg)
	 * @param numOfPackages 饼件数
	 * @return 出油率
	 */
	public Double countRateOfOil(double oilRate, double capacity, double numOfPackages, double packagesMultiply) {
		Double rateOfOil = (capacity * oilRate) / ((capacity * oilRate + numOfPackages * packagesMultiply)/RATE_MUTIPLY) ;
		return FormatUtil.doubleFormat(rateOfOil,SCALE);
	}
	
	/**
	 * 计算出饼率
	 * @param oilRate 比重
	 * @param capacity 容积
	 * @param packagesMultiply 饼规格
	 * @param numOfPackages 饼件数
	 * @return 出饼率
	 */
	public Double countRateOfCake(double oilRate, double capacity, double numOfPackages, double packagesMultiply) {
		Double rateOfCake = (numOfPackages * packagesMultiply) / ((capacity * oilRate + numOfPackages * packagesMultiply)/RATE_MUTIPLY) ;
		return FormatUtil.doubleFormat(rateOfCake,SCALE);
	}
	
	
	public RateOfProduceDao getRateOfProduceDao() {
		return rateOfProduceDao;
	}

	@Resource(name="rateOfProduceDao")
	public void setRateOfProduceDao(RateOfProduceDao rateOfProduceDao) {
		this.rateOfProduceDao = rateOfProduceDao;
	}



}
