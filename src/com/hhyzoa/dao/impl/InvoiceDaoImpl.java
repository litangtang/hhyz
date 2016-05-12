package com.hhyzoa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.InvoiceDao;
import com.hhyzoa.model.Invoice;

@SuppressWarnings("unchecked")
@Component("invoiceDao")
public class InvoiceDaoImpl extends BaseDaoImpl implements InvoiceDao {
	
	private HibernateTemplate hibernateTemplate;
	private Session session;

	@Transactional
	public Invoice findById(int id) {
		return (Invoice)hibernateTemplate.get(Invoice.class, id);
	}

	@Transactional
	public void save(Invoice invoice) {
		hibernateTemplate.save(invoice);
	}

	@Transactional
	public List<Invoice> findAll(int pageSize, int offset, Invoice invoice) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria invCriteria = session.createCriteria(Invoice.class);
		Example invExample = Example.create(invoice);
		invExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		invCriteria.add(invExample);
		invCriteria.addOrder(Order.desc("day"));
		invCriteria.addOrder(Order.desc("id"));
		List<Invoice> list = super.listAll(invCriteria,offset, pageSize);        //"一页"的记录
		return list;
	}

	@Transactional
	public int getAllRowCount(Invoice invoice) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria invCriteria = session.createCriteria(Invoice.class);
		Example invExample = Example.create(invoice);
		invExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		invCriteria.add(invExample);
		invCriteria.addOrder(Order.desc("day"));
		invCriteria.addOrder(Order.desc("id"));
		return super.getAllRowCount(invCriteria); 
	}

	@Transactional
	public void deleteById(int[] ids) {
		StringBuilder hql = new StringBuilder("delete t_invoice ");
		
		if(ids.length == 1) {
			hql.append("where id = "+ ids[0]);
		}else if(ids.length > 1) {
			String tempStr = "";
			for(int i=0;i<ids.length;i++) {
				tempStr += ids[i]+",";
			}
			hql.append("where id in (").append(tempStr.substring(0, tempStr.length()-1)).append(")");
		}
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.executeUpdate();
	}

	@Transactional
	public void update(Invoice invoice) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		invoice = (Invoice)session.merge(invoice);
		session.saveOrUpdate(invoice);
	}
	
	/**
	 * 批量更新日期大于day的2个累计,不含税金额累计、销项税累计
	 * 日期大于改动的记录的日期,且id大于要改动的日期的id
	 * 保证如果一天中有多条记录,改动的是中间的记录,也能正确
	 * 更新就是将需要更新的记录+增量
	 * @param year
	 * @param month
	 * @param day
	 * @param invoice 封装两个增量
	 */
	@Transactional
	public void batchGtUpdateAccu(Integer year, Integer month, Integer day,Invoice invoice)throws Exception {
		StringBuilder hql = new StringBuilder("update t_invoice t set noTaxAccu = noTaxAccu + :noTaxAccuChange, sellTaxAccu = sellTaxAccu + :sellTaxAccuChange where year = " + year + " and month = " + month);
		//修改当天的id比较大的记录,如果使用day和id两个条件同时限制则会出现日期靠前的记录是后加的,更新时靠前的id比靠后的id大,则不会更新靠后的
		//所以分两次更新,更新大于day的记录和更新等于day且id比较大的记录
		StringBuilder hql2 = new StringBuilder("update t_invoice t set noTaxAccu = noTaxAccu + :noTaxAccuChange, sellTaxAccu = sellTaxAccu + :sellTaxAccuChange where year = " + year + " and month = " + month);
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		
		//增加新记录时,id为null,只update日期大的就行
		//更新时既要update日期大的,也要update日期相同且id较大的记录-只修改时执行
		if(null != invoice.getId()) {
			hql2.append(" and day = " + day);
			hql2.append(" and id > " + invoice.getId());
			
			//更新日期相同的记录
			Query query2 = session.createQuery(hql2.toString());
			query2.setDouble("noTaxAccuChange", invoice.getNoTaxAmtChange());
			query2.setDouble("sellTaxAccuChange", invoice.getSellTaxChange());
			query2.executeUpdate();
		}
		
		//更新日期大的记录-增加修改都执行
		hql.append(" and day > " + day);
		Query query = session.createQuery(hql.toString());
		query.setDouble("noTaxAccuChange", invoice.getNoTaxAmtChange());
		query.setDouble("sellTaxAccuChange", invoice.getSellTaxChange());
		query.executeUpdate();
		
	}

	
	/**
	 * 根据日期查询小于传入天的记录,并按正序排列
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	@Transactional
	public List<Invoice> findLeDay(Integer year, Integer month, Integer day) {
		Invoice invoice = new Invoice();//封装查询条件
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria invCriteria = session.createCriteria(Invoice.class);
		Example invExample = Example.create(invoice);
		invCriteria.add(invExample);
		invCriteria.add(Restrictions.eq("year", year));
		invCriteria.add(Restrictions.eq("month", month));
		//小于等于当前日期
		invCriteria.add(Restrictions.le("day", day));
		invCriteria.addOrder(Order.asc("day"));
		invCriteria.addOrder(Order.asc("id"));
		List<Invoice> list = invCriteria.list();
		return list;
	}
	
	/**
	 * 根据日期查询大于传入天的记录,并按正序排列
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	@Transactional
	public List<Invoice> findGtDay(Integer year, Integer month, Integer day) {
		Invoice invoice = new Invoice();//封装查询条件
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria invCriteria = session.createCriteria(Invoice.class);
		Example invExample = Example.create(invoice);
		invCriteria.add(invExample);
		invCriteria.add(Restrictions.eq("year", year));
		invCriteria.add(Restrictions.eq("month", month));
		invCriteria.add(Restrictions.gt("day", day));
		invCriteria.addOrder(Order.asc("day"));
		invCriteria.addOrder(Order.asc("id"));
		List<Invoice> list = invCriteria.list();
		return list;
	}
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
