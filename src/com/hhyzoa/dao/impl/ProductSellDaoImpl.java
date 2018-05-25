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

import com.hhyzoa.dao.ProductSellDao;
import com.hhyzoa.model.ProductSell;


@SuppressWarnings("unchecked")
@Component("productSellDao")
public class ProductSellDaoImpl extends BaseDaoImpl implements ProductSellDao {
	
	private HibernateTemplate hibernateTemplate;
	private Session session;

	/**
	 * 分页查询
	 */
	@Transactional
	public List<ProductSell> findAll(int pageSize,int offset,ProductSell productSell) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria psCriteria = session.createCriteria(ProductSell.class);
		Example psExample = Example.create(productSell);
		psExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		psCriteria.add(psExample);
		psCriteria.addOrder(Order.desc("day"));
		psCriteria.addOrder(Order.desc("id"));
		List<ProductSell> list = super.listAll(psCriteria,offset, pageSize);        //"一页"的记录
		return list;
	}
	
	/**
	 * 根据条件查询,按day和id升序
	 * @param productSell
	 * @return
	 */
	@Transactional
	public List<ProductSell> findByCon(ProductSell productSell) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria psCriteria = session.createCriteria(ProductSell.class);
		Example psExample = Example.create(productSell);
		psExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		psCriteria.add(psExample);
		
		psCriteria.addOrder(Order.asc("day"));
		psCriteria.addOrder(Order.asc("id"));
		List<ProductSell> list = psCriteria.list();    //"一页"的记录
		return list;
	}

	/**
	 * 根据日期查询小于传入天的记录,并按正序排列
	 * @param year
	 * @param month
	 * @param day
	 * @param busiType
	 * @return
	 */
	@Transactional
	public List<ProductSell> findLeDay(Integer year, Integer month, Integer day, Integer busiType) {
		ProductSell productSell = new ProductSell();//拼装查询条件
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria psCriteria = session.createCriteria(ProductSell.class);
		Example psExample = Example.create(productSell);
		psCriteria.add(psExample);
		psCriteria.add(Restrictions.eq("busiType", busiType));
		psCriteria.add(Restrictions.eq("year", year));
		psCriteria.add(Restrictions.eq("month", month));
		//小于等于当前日期
		psCriteria.add(Restrictions.le("day", day));
		psCriteria.addOrder(Order.asc("day"));
		psCriteria.addOrder(Order.asc("id"));
		List<ProductSell> list = psCriteria.list();        //"一页"的记录
		return list;
	}

	/**
	 * 根据日期查询大于传入天的记录,并按正序排列
	 * @param year
	 * @param month
	 * @param day
	 * @param busiType
	 * @return
	 */
	@Transactional
	public List<ProductSell> findGtDay(Integer year, Integer month, Integer day, Integer busiType) {
		ProductSell productSell = new ProductSell();//拼装查询条件
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria psCriteria = session.createCriteria(ProductSell.class);
		Example psExample = Example.create(productSell);
		psCriteria.add(psExample);
		psCriteria.add(Restrictions.eq("busiType", busiType));
		psCriteria.add(Restrictions.eq("year", year));
		psCriteria.add(Restrictions.eq("month", month));
		psCriteria.add(Restrictions.gt("day", day));
		psCriteria.addOrder(Order.asc("day"));
		psCriteria.addOrder(Order.asc("id"));
		List<ProductSell> list = psCriteria.list();        //"一页"的记录
		return list;
	}
	
	
	@Transactional
    public int getAllRowCount(ProductSell productSell){   
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria psCriteria = session.createCriteria(ProductSell.class);
		Example psExample = Example.create(productSell);
		psExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		psCriteria.add(psExample);
		psCriteria.addOrder(Order.desc("id"));
		
		//criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return super.getAllRowCount(psCriteria); 
    }
	
	@Transactional
	public void deleteById(int[] ids) {
		StringBuilder hql = new StringBuilder("delete t_product_sell ");
		
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
	
	
	public List<?> listAll(Criteria criteria, int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> listAll(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAllRowCount(Criteria criteria) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAllRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	public ProductSell findById(int id) {
		return (ProductSell)hibernateTemplate.get(ProductSell.class, id);
	}

	@Transactional
	public void save(ProductSell productSell) {
		hibernateTemplate.save(productSell);
	}
	
	@Transactional
	public void update(ProductSell productSell) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		productSell = (ProductSell)session.merge(productSell);
		session.saveOrUpdate(productSell);
//		hibernateTemplate.update(productSell);
	}
	
	/**
	 * 批量更新日期大于day的3个累计,件数累计、数量累计、金额累计
	 * 日期大于等于改动的记录的日期,且id大于要改动的日期的id
	 * 保证如果一天中有多条记录,改动的是中间的记录,也能正确
	 * 更新就是将需要更新的记录+增量
	 * @param year
	 * @param month
	 * @param day
	 * @param ps 发生改动的记录
	 */
	@Transactional
	public void batchGtUpdateAccu(Integer year, Integer month, Integer day,ProductSell ps)throws Exception {
		StringBuilder hql = new StringBuilder("update t_product_sell t set packagesAccu = packagesAccu + :newPackagesAccu, countAccu = countAccu + :newCountAccu, amountAccu = amountAccu + :newAmountAccu where year = " + year + " and month = " + month);
		//修改当天的id比较大的记录,如果使用day和id两个条件同时限制则会出现日期靠前的记录是后加的,更新时靠前的id比靠后的id大,则不会更新靠后的
		//所以分两次更新,更新大于day的记录和更新等于day且id比较大的记录
		StringBuilder hql2 = new StringBuilder("update t_product_sell t set packagesAccu = packagesAccu + :newPackagesAccu, countAccu = countAccu + :newCountAccu, amountAccu = amountAccu + :newAmountAccu where year = " + year + " and month = " + month);
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		
		hql.append(" and day > " + day);
		//增加新记录时,id为null,只update日期大的就行
		//更新时既要update日期大的,也要update日期相同且id较大的记录
		if(null != ps.getId()) {
			hql2.append(" and day = " + day);
			hql2.append(" and id > " + ps.getId());
			
			//更新日期相同的记录
			Query query2 = session.createQuery(hql2.toString());
			query2.setInteger("newPackagesAccu", ps.getPackagesChange());
			query2.setDouble("newCountAccu", ps.getCountChange());
			query2.setDouble("newAmountAccu", ps.getAmountChange());
			query2.executeUpdate();
		}
		
		//更新日期大的记录
		Query query = session.createQuery(hql.toString());
		query.setInteger("newPackagesAccu", ps.getPackagesChange());
		query.setDouble("newCountAccu", ps.getCountChange());
		query.setDouble("newAmountAccu", ps.getAmountChange());
		query.executeUpdate();
		
	}


	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
