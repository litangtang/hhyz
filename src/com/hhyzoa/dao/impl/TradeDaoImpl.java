package com.hhyzoa.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.TradeDao;
import com.hhyzoa.model.Client;
import com.hhyzoa.model.Trade;

@SuppressWarnings("unchecked")
@Component("tradeDao")
public class TradeDaoImpl extends BaseDaoImpl implements TradeDao {
	
	private HibernateTemplate hibernateTemplate;
	private Session session;
	
	public void save(Trade trade) {
		hibernateTemplate.save(trade);
	}
	
	
	public Trade findById(int id){
		return (Trade)hibernateTemplate.get(Trade.class, id);
	}
	/**
	 * 查询某个人的所有往来历史记录
	 * @param clientId 客户id
	 * @param offset 开始记录  
     * @param length 一次查询几条记录  
	 * @return
	 */
	@Transactional
	public List<Trade> findAllOfSB(int clientId,int offset,int length) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria tradeCriteria = session.createCriteria(Trade.class);
		tradeCriteria.add(Restrictions.eq("client.id", clientId));
		tradeCriteria.addOrder(Order.desc("date"));
		tradeCriteria.addOrder(Order.desc("level"));
		List<Trade> trades = this.listAll(tradeCriteria, offset, length);
		return trades;
		
	}
	
	/**
	 * 查看所有的交易
	 * @param offset 开始记录  
     * @param pageSize 每页显示记录数 
     * @param trade 封装查询条件 
	 * @return
	 */
	@Transactional
	public List<Trade> findAll(int pageSize,int offset,Trade trade) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria tradeCriteria = session.createCriteria(Trade.class);
		Example tradeExample = Example.create(trade);
		tradeExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		tradeCriteria.add(tradeExample);
		tradeCriteria.addOrder(Order.desc("date"));
		tradeCriteria.addOrder(Order.desc("id"));
		Criteria clientCriteria = tradeCriteria.createCriteria("client");
		Client client = trade.getClient();
		if(null != client) {
			if(null != client.getId()) {
				clientCriteria.add(Restrictions.eq("id", client.getId()));
			}
			Example clientExample = Example.create(client);
			clientExample.enableLike(MatchMode.ANYWHERE);
			clientCriteria.add(clientExample);
		}
		
		List<Trade> list = super.listAll(clientCriteria,offset, pageSize);        //"一页"的记录
		return list;
	}
	
	/** 
     * 查询所有记录数  条件查询时使用
     * @return 总记录数  
     */  
	@Transactional
    public int getAllRowCount(Trade trade){   
    	session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria tradeCriteria = session.createCriteria(Trade.class);
		Example tradeExample = Example.create(trade);
		tradeExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		tradeCriteria.add(tradeExample);
		tradeCriteria.addOrder(Order.desc("date"));
		tradeCriteria.addOrder(Order.desc("level"));
		
		Criteria clientCriteria = tradeCriteria.createCriteria("client");
		Client client = trade.getClient();
		if(null != client) {
			if(null != client.getId()) {
				clientCriteria.add(Restrictions.eq("id", client.getId()));
			}
			Example clientExample = Example.create(client);
			clientExample.enableLike(MatchMode.ANYWHERE);
			clientCriteria.add(clientExample);
		}
		//criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return super.getAllRowCount(clientCriteria); 
    }
	
	/**
	 * 取得最近的一笔交易记录
	 */
	@Transactional
	public Trade findLatestTrade(final Integer clientId,final Integer level) {
		return (Trade)getHibernateTemplate().execute(new HibernateCallback() {
			public Trade doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery("FROM Trade as t where t.level = :level and t.client.id = :clientId");
				query.setInteger("level", level);
				query.setInteger("clientId", clientId);
				return (Trade)query.uniqueResult();
			}
		});
		
	}
	
	/**
	 * 取得与某个客户交易的最大level，即最近的记录
	 */
	public Integer findMaxLevelOfTrade(final Integer clientId) {
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery("SELECT MAX(level) FROM Trade where client.id = :clientId");
				query.setInteger("clientId", clientId);
				return (Integer)query.uniqueResult();
			}
		});
	}
	
	
	/**
	 * 根据id删除往来
	 * @param ids
	 */
	@Transactional
	public void deleteById(int[] ids) {
		StringBuilder hql = new StringBuilder("delete Trade ");
		
		if(ids.length == 1) {
			hql.append("where id = "+ ids[0]);
		}else if(ids.length > 1) {
			String tempStr = "";
			for(int i=0;i<ids.length;i++) {
				tempStr += ids[i]+",";
			}
			hql.append("where id in (").append(tempStr.substring(0, tempStr.length()-1)).append(")");
		}
		//delete trade associated with client
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.executeUpdate();
	}
	
	/**
	 * 更新Trade
	 */
	@Transactional
	public void update(Trade trade) {
		getHibernateTemplate().update(trade);
	}
	
	/**
	 * 调用存储过程更新level>currLevle的所有balance
	 * @param clientId
	 * @param balanceChange
	 * @param currLevel
	 */
	@Transactional
	public void proUpdateBalance(int clientId, double balanceChange, int currLevel) {
		Query query =  hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery("{Call p_update_balance(?,?,?)}");
		query.setInteger(0, clientId);
		query.setDouble(1, balanceChange);
		query.setInteger(2, currLevel);
		query.executeUpdate();
	}
	
	/**
	 * 调用存储过程增加交易往来
	 * @param trade
	 */
	@Transactional
	public void proAddTrade(Trade trade) {
		/* 
		 * 存储过程入参：11个入参
		 * 			IN in_client_id int, IN in_tradeDate date, IN in_abst varchar(50),IN in_packages int,IN in_amount float,IN in_price float,
		 * 			IN in_carriage float,IN in_payment float,IN in_verify varchar(50),IN in_remark varchar(100),IN in_flag int
		 */
		Query query =  hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery("{Call p_add_trade(?,?,?,?,?,?,?,?,?,?,?)}");
		query.setInteger(0, trade.getClient().getId());
		query.setDate(1, trade.getDate());
		query.setString(2, trade.getAbst());
		query.setInteger(3, trade.getPackages());
		query.setDouble(4, trade.getAmount());
		query.setDouble(5,trade.getPrice());
		query.setDouble(6,trade.getCarriage());
		query.setDouble(7,trade.getPayment());
		query.setString(8, trade.getVerify());
		query.setString(9, trade.getRemark());
		query.setInteger(10, trade.getFlag());
		query.executeUpdate();
	}
	
	/**
	 * 获取客户的所有往来数
	 * @param clientId
	 * @return
	 */
	@Transactional
	public int getClientTradeCount(final Integer clientId) {
		Trade trade = new Trade();
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria tradeCriteria = session.createCriteria(Trade.class);
		Example tradeExample = Example.create(trade);
		tradeCriteria.add(tradeExample);
		
		Criteria clientCriteria = tradeCriteria.createCriteria("client");
		Client client = new Client();
		client.setId(clientId);
		clientCriteria.add(Restrictions.eq("id", client.getId()));
		Example clientExample = Example.create(client);
		clientCriteria.add(clientExample);
		
		return super.getAllRowCount(clientCriteria); 
	}
	
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
