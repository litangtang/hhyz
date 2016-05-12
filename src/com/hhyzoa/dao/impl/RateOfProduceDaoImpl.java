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

import com.hhyzoa.dao.RateOfProduceDao;
import com.hhyzoa.model.RateOfProduce;

@SuppressWarnings("unchecked")
@Component("rateOfProduceDao")
public class RateOfProduceDaoImpl extends BaseDaoImpl implements RateOfProduceDao {

	private HibernateTemplate hibernateTemplate;
	
	private Session session;
	
	@Transactional
	public void save(RateOfProduce rateOfProduce) {
		hibernateTemplate.save(rateOfProduce);

	}
	
	/**
	 * 根据id删除rateOfProduce
	 * @param id
	 */
	@Transactional
	public void deleteById(int[] ids) {
		StringBuilder hql = new StringBuilder("delete com.hhyzoa.model.RateOfProduce ");
		
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

	/**
	 * 查询所有的出品率
	 * @param pageSize	每页显示记录数
	 * @param offset	开始记录
	 * @param rateOfProduce 封装查询条件
	 */
	@Transactional
	public List<RateOfProduce> findAll(int pageSize, int offset,
			RateOfProduce rateOfProduce) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria rateOfProduceCriteria = session.createCriteria(RateOfProduce.class);
		Example rateOfProduceExample = Example.create(rateOfProduce);
		rateOfProduceExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询,针对字符串属性
		rateOfProduceCriteria.add(rateOfProduceExample);
		rateOfProduceCriteria.addOrder(Order.desc("date"));
		List<RateOfProduce> list = super.listAll(rateOfProduceCriteria,offset, pageSize);
		return list;
	}
	
	/**
	 * 根据id查询
	 * @param id 主键
	 * @return
	 */
	public RateOfProduce findById(int id) {
		return (RateOfProduce)hibernateTemplate.get(RateOfProduce.class, id);
	}
	
	/**
	 * 更新出品率
	 */
	@Transactional
	public void update(RateOfProduce rateOfProduce) {
		getHibernateTemplate().update(rateOfProduce);
	}
	
	/** 
     * 查询所有记录数  条件查询时使用
     * @return 总记录数  
     */  
	@Transactional
    public int getAllRowCount(RateOfProduce rateOfProduce){   
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria rateOfProduceCriteria = session.createCriteria(RateOfProduce.class);
		Example rateOfProduceExample = Example.create(rateOfProduce);
		rateOfProduceExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询,针对字符串属性
		rateOfProduceCriteria.add(rateOfProduceExample);
		rateOfProduceCriteria.addOrder(Order.desc("date"));
        return super.getAllRowCount(rateOfProduceCriteria); 
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
