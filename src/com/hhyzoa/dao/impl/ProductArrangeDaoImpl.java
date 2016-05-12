package com.hhyzoa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhyzoa.dao.ProductArrangeDao;
import com.hhyzoa.model.ProductArrange;


@SuppressWarnings("unchecked")
@Component("productArrangeDao")
public class ProductArrangeDaoImpl extends BaseDaoImpl implements ProductArrangeDao {
	
	private HibernateTemplate hibernateTemplate;
	private Session session;

	/**
	 * 分页查询
	 */
	@Transactional
	public List<ProductArrange> findAll(int pageSize,int offset,ProductArrange productArrange) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria paCriteria = session.createCriteria(ProductArrange.class);
		Example paExample = Example.create(productArrange);
		paExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		paCriteria.add(paExample);
		paCriteria.addOrder(Order.asc("isSend"));
		paCriteria.addOrder(Order.asc("arriveDate"));
		List<ProductArrange> list = super.listAll(paCriteria,offset, pageSize);        //"一页"的记录
		return list;
	}
	
	
	@Transactional
    public int getAllRowCount(ProductArrange productArrange){   
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria paCriteria = session.createCriteria(ProductArrange.class);
		Example paExample = Example.create(productArrange);
		paExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		paCriteria.add(paExample);
		paCriteria.addOrder(Order.asc("arriveDate"));
		
        return super.getAllRowCount(paCriteria); 
    }
	
	@Transactional
	public void deleteById(int[] ids) {
		StringBuilder hql = new StringBuilder("delete t_product_arrange ");
		
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
		return null;
	}

	public List<?> listAll(String hql, int offset, int length) {
		return null;
	}

	public int getAllRowCount(Criteria criteria) {
		return 0;
	}

	public int getAllRowCount(String hql) {
		return 0;
	}

	@Transactional
	public ProductArrange findById(int id) {
		return (ProductArrange)hibernateTemplate.get(ProductArrange.class, id);
	}

	@Transactional
	public void save(ProductArrange productArrange) {
		hibernateTemplate.save(productArrange);
	}
	
	@Transactional
	public void update(ProductArrange productArrange) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		productArrange = (ProductArrange)session.merge(productArrange);
		session.saveOrUpdate(productArrange);
//		hibernateTemplate.update(productSell);
	}
	

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
