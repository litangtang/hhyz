package com.hhyzoa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.BuySellSaveServiceDao;
import com.hhyzoa.model.BuySellSave;

@SuppressWarnings("unchecked")
@Component("buySellSaveDao")
public class BuySellSaveServiceDaoImpl extends BaseDaoImpl implements
		BuySellSaveServiceDao {
	
	private HibernateTemplate hibernateTemplate;
	
	private Session session;
	
	/**
	 * 
	 */
	@Transactional
	public void save(BuySellSave buySellSave) {
		
	}

	
	/**
	 * 
	 */
	@Transactional
	public List<BuySellSave> findAll(int pageSize, int offset,
			BuySellSave buySellSave) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(BuySellSave.class);
		Example example = Example.create(buySellSave);
		criteria.add(example)
		.addOrder(Order.desc("date"));
		List<BuySellSave> list = super.listAll(criteria,offset, pageSize);        //"一页"的记录
		return list;
	}
	
	 /** 
     * 查询所有记录数  
     * @return 总记录数  
     */  
	@Transactional
    public int getAllRowCount(BuySellSave buySellSave) {
    	session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(BuySellSave.class);
		Example example = Example.create(buySellSave);
		criteria.add(example)
				.addOrder(Order.desc("date"));
        return super.getAllRowCount(criteria); 
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
