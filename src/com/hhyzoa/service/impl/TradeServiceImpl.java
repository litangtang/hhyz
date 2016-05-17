package com.hhyzoa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.ClientDao;
import com.hhyzoa.dao.TradeDao;
import com.hhyzoa.model.Client;
import com.hhyzoa.model.PageBean;
import com.hhyzoa.model.Trade;
import com.hhyzoa.service.TradeService;
import com.hhyzoa.util.DateUtil;
import com.hhyzoa.util.FormatUtil;

@Component("tradeService")
public class TradeServiceImpl implements TradeService {
	
	private Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);
	private TradeDao tradeDao;
	private ClientDao clientDao;

	/**
	 * 增加往来
	 * 1. 查询客户是否有过往来
	 * 2. 如果没有往来则是首笔往来
	 * 3. 如果有往来则调用存储过程增加往来
	 */
	public void add(Trade trade) {
		Client c = trade.getClient();
		if(null != c) {
			//初始化trade数据
			this.initTradeData(trade, c);
			Float balanceChange = trade.getCarriage() - trade.getPayment();//往来余额=送货金额-付款金额
			//查询客户往来数
			int count = tradeDao.getClientTradeCount(c.getId());
			if(count == 0) {//首笔交易,直接入库
				trade.setBalance(balanceChange);
				trade.setLevel(1);//level 初始值为1
				trade.setIsLoan(this.getIsLoanFlag(trade, balanceChange));
				tradeDao.save(trade);
			}else if(count > 0) {//老客户,客户已经有往来
				//调用存储过程增加往来
				if(null != trade.getDate()) {//tradeDate页面上控制不能为空
					tradeDao.proAddTrade(trade);
				}
			}
		}
	}
	
	/**
	 * 初始化trade数据
	 * @param trade
	 * @param c
	 * @return
	 */
	public Trade initTradeData(Trade trade, Client c) {
		Float carriage = trade.getCarriage(); //送货金额
		Float payment = trade.getPayment(); //付款金额
		
		if(null == trade.getAbst()) {
			trade.setAbst("");
		}
		if(null == trade.getPackages()) {
			trade.setPackages(Integer.valueOf(0));
		}
		if(null == trade.getAmount()) {
			trade.setAmount(Float.valueOf(0));
		}
		if(null == trade.getPrice()) {
			trade.setPrice(Float.valueOf(0));
		}
		if(null == carriage) {
			carriage = Float.valueOf(0);
		}
		if(null == payment) {
			payment = Float.valueOf(0);
		}
		if(null == trade.getVerify()) {
			trade.setVerify("");
		}
		if(null == trade.getRemark()) {
			trade.setRemark("");
		}
		if(null == trade.getLevel()) {
			trade.setLevel(0);
		}
		
		trade.setCarriage(trade.getAmount() * trade.getPrice());//送货金额 - 防止增加时没有点击送货金额即没有自动计算
		trade.setPayment(payment);//付款金额
		trade.setFlag(c.getType());//设置往来的类别 1为原料往来2为销售往来
		
		return trade;
	}
	
	/**
	 * 设置借贷标志
	 * @param trade
	 * @param balance
	 * @return isLoan 0-平,1-借,2-贷
	 */
	public Integer getIsLoanFlag(Trade trade, Float balance) {
		Integer isLoan = 0;
		if(1 == trade.getFlag()) { //原料往来，送货金额-付款金额，余额为正则为2即贷，为负则为1即借
			if(balance > 0) {
				isLoan = 2; //贷
			}else if(balance < 0) {
				isLoan = 1;//借
			}
		}else { //销售往来，贷款金额-收款金额，余额为正则为1即借，为负则为2即贷
			if(balance > 0) {
				isLoan = 1; //借
			}else if(balance < 0) {
				isLoan = 2;//贷
			}
		}
		return isLoan;
	}
	
	 /**
     * 更新trade
     * @param oldTrade 修改之前的trade
     * @param newTrade 修改之后的trade
     */
    public void modify(Trade oldTrade,Trade newTrade) {
    	if(null != oldTrade.getFlag()) 
    	{
    		newTrade.setFlag(oldTrade.getFlag());
    	}
    	
    	if(null != oldTrade.getLevel())
    	{
    		newTrade.setLevel(oldTrade.getLevel());
    	}
    	
    	//与增加重复，目的是为了不修改数据库，将未填写的值设初始值
    	if(null == newTrade.getAbst()) {
    		newTrade.setAbst("");
    	}
    	if(null == newTrade.getCarriage()) {
    		newTrade.setCarriage(Float.valueOf(0));
    	}
    	if(null == newTrade.getPayment()) {
    		newTrade.setPayment(Float.valueOf(0));
    	}
    	if(null == newTrade.getPackages()) {
    		newTrade.setPackages(Integer.valueOf(0));
    	}
    	if(null == newTrade.getAmount()) {
    		newTrade.setAmount(Float.valueOf(0));
    	}
    	if(null == newTrade.getPrice()) {
    		newTrade.setPrice(Float.valueOf(0));
    	}
    	
    	//此行的目的是防止页面上修改了数量或者单价，但是没有点击送货金额文本框导致从页面取得的值是旧的
    	newTrade.setCarriage(newTrade.getAmount() * newTrade.getPrice());
    	
    	
    	Client c = oldTrade.getClient();
		if(null != c) {
			newTrade.setClient(c);
			Float balance = Float.valueOf(0) ; //余额
			
			Integer maxLevel = tradeDao.findMaxLevelOfTrade(c.getId());
			if(null == maxLevel || 0 == maxLevel || 1 == maxLevel){  //第一笔交易来往，更新时应该不会出现此种情况
				newTrade.setLevel(1);
				balance = newTrade.getCarriage()-newTrade.getPayment();
			}else{ //不是第一笔交易，则需要根据上一笔的交易记录来计算余额
				
				//取得最近的记录(上一笔记录，根据level来计算，上一笔即level-1)
				Trade latestTrade = tradeDao.findLatestTrade(c.getId(), newTrade.getLevel()-1);
				if(null != latestTrade){
					balance = newTrade.getCarriage()-newTrade.getPayment()+latestTrade.getBalance();
				}else {
					//latestTrade = null : 说明修改的是该客户的第一笔交易，则余额=送货金额 - 支付金额
					balance = newTrade.getCarriage() - newTrade.getPayment();
				}
			}
			newTrade.setBalance(balance);
			if(1 == newTrade.getFlag()) { //原料往来，送货金额-付款金额，余额为正则为2即贷，为负则为1即借
				if(balance == 0) {
					newTrade.setIsLoan(0);//平
				}else if(balance > 0) {
					newTrade.setIsLoan(2); //贷
				}else {
					newTrade.setIsLoan(1);//借
				}
			}else { //销售往来，贷款金额-收款金额，余额为正则为1即借，为负则为2即贷
				if(balance == 0) {
					newTrade.setIsLoan(0);//平
				}else if(balance > 0) {
					newTrade.setIsLoan(1); //借
				}else {
					newTrade.setIsLoan(2);//贷
				}
			}
		}
		
    	tradeDao.update(newTrade);
    	/**
		 * 调用存储过程p_update_balance,更新level > newTrade.getLevel()的所有balance
		 */
		tradeDao.proUpdateBalance(newTrade.getClient().getId(), newTrade.getBalance()-oldTrade.getBalance(), newTrade.getLevel());
    }
	
	/**  
     * 分页查询所有 
     * @param page 当前第几页  
     * @param pageSize 每页大小  
     * @param trade 封装查询条件
     * @return 封装了分页信息(包括记录集list)的Bean  
     */  
    public PageBean listAll(int pageSize,int page,Trade trade){  
    	if(null == trade) {
    		trade = new Trade();//初始情况 查询所有
    	}
        int allRow = tradeDao.getAllRowCount(trade);    //总记录数   
        int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
        final int offset = PageBean.countOffset(pageSize, page);    //当前页开始记录   
        //final int length = pageSize;    //每页记录数   
        final int currentPage = PageBean.countCurrentPage(page);   
        List<Trade> list = tradeDao.findAll(pageSize, offset, trade);      //"一页"的记录 
        if(null != list && list.size() > 0) {
//        	for(Trade t : list) {
        	for(int i=list.size()-1; i>=0; i--) {
        		Trade t = list.get(i);
        		t.setIsLoanStr(this.getIsLoanStr(t.getIsLoan()));//借贷标志字符串
        		t.setDate(DateUtil.dateFormat(t.getDate()));//日期格式化
        		t.setBalance(FormatUtil.floatFormat(t.getBalance(), 1));//格式化余额
        		
        		/******************************************************************
        		 * 			计算累计方法
        		 * 1. 获得两个对象,当前遍历的对象和previous对象
        		 * 2. 当前对象需要累加的字段+previous对象该字段的累加=当前对象该字段的累加
        		 * 3. 保证列表是有序的 ,由于列表本身是按时间倒序排序,所以从最后一项开始累加
        		 *  packageAccu //件数累计
        		 *  amountAccu;	//数量累计
        		 *  balanceAccu //余额累计 - 没用，余额本身就是累计
        		 */
        		if(i == list.size()-1) {
        			t.setPackageAccu(t.getPackages());
        			t.setPackageAccuStr(FormatUtil.convertE2Num(String.valueOf(t.getPackageAccu())));
        			t.setAmountAccu(t.getAmount());
        			t.setAmountAccuStr(FormatUtil.convertE2Num(String.valueOf(t.getAmountAccu())));
        			//t.setBalanceAccu(t.getBalance());
        			//t.setBalanceAccuStr(FormatUtil.convertE2Num(String.valueOf(t.getBalanceAccu())));
        		}else {
        			Trade prevTrade = list.get(i+1);//获得列表中前一个对象,倒序排序相加,所以这里为+1而不是-1
        			t.setPackageAccu(prevTrade.getPackageAccu() + t.getPackages());
        			t.setPackageAccuStr(FormatUtil.convertE2Num(String.valueOf(t.getPackageAccu())));
        			t.setAmountAccu(prevTrade.getAmountAccu() + t.getAmount());
        			t.setAmountAccuStr(FormatUtil.convertE2Num(String.valueOf(t.getAmountAccu())));
        			//t.setBalanceAccu(prevTrade.getBalanceAccu() + t.getBalance());
        			//t.setBalanceAccuStr(FormatUtil.convertE2Num(String.valueOf(t.getBalanceAccu())));
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
	 * 查询某个人的所有往来历史记录
	 * @param trade 
	 * @param pageSize 每页大小 
	 * @param page 当前第几页 
     * @return 封闭了分页信息(包括记录集list)的Bean   
	 */
    public PageBean listAllOfSB(Trade trade,int pageSize,int page) {
    	Trade t = new Trade();
    	Client c = clientDao.findById(trade.getClient().getId());
    	if(null != c) {
    		t.setClient(c);
    	}
        int allRow = tradeDao.getAllRowCount(t);    //总记录数   
        int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数   
        final int offset = PageBean.countOffset(pageSize, page);    //当前页开始记录   
        //final int length = pageSize;    //每页记录数   
        final int currentPage = PageBean.countCurrentPage(page);   
        List<Trade> list = tradeDao.findAllOfSB(trade.getClient().getId(), offset, pageSize);      //"一页"的记录   
        
        if(null != list && list.size() > 0) {
        	for(Trade tr : list) {
        		tr.setIsLoanStr(this.getIsLoanStr(tr.getIsLoan()));
        		tr.setDate(DateUtil.dateFormat(tr.getDate()));
        		tr.setBalance(FormatUtil.floatFormat(tr.getBalance(), 1));
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
     * @return
     */
    public Trade queryById(int id) {
    	return tradeDao.findById(id);
    }
    
    /**
     * 根据id删除Trade
     * @param attrs 所选择的trade的id构成的数组
     */
    @Transactional
    public void removeById(String[] attrs) {
    	int[] ids = FormatUtil.StringArrayToIntArray(attrs);
    	tradeDao.deleteById(ids);
    }
    
	
	//取得借贷字符串，只用于显示
	public String getIsLoanStr(Integer isLoan) {
		if(0 == isLoan) {
			return "平";
		}else if(1 == isLoan) {
			return "借";
		}else {
			return "贷";
		}
	}

	
	public TradeDao getTradeDao() {
		return tradeDao;
	}
	@Resource(name="tradeDao")
	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}
	
	public ClientDao getClientDao() {
		return clientDao;
	}

	@Resource(name="clientDao")
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

}
