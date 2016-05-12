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

import com.hhyzoa.dao.CostAccountingDao;
import com.hhyzoa.model.Client;
import com.hhyzoa.model.CostAccounting;

/**
 * 生产成本核算DAO实现类
 * @author lizhibin
 *
 */
@SuppressWarnings("unchecked")
@Component("costAcountingDao")
public class CostAccountingDaoImpl extends BaseDaoImpl implements CostAccountingDao {
	
	private HibernateTemplate hibernateTemplate;
	private Session session;

	/**
	 * @param pageSize 每页显示记录数 
	 * @param offset 开始记录
	 * @param costAccounting 封装查询条件
	 * @return List<CostAccounting>
	 */
	@Transactional
	public List<CostAccounting> findAll(int pageSize, int offset,
			CostAccounting costAccounting) {
		List<CostAccounting> list = null ;
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria costAccountCriteria = session.createCriteria(CostAccounting.class);
		Example costAccountCriteriaExample = Example.create(costAccounting);
		costAccountCriteriaExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		costAccountCriteria.add(costAccountCriteriaExample);
		costAccountCriteria.addOrder(Order.desc("id"));
		
		Criteria clientCriteria = costAccountCriteria.createCriteria("client");
		Client client = costAccounting.getClient();
		if(null != client) {
			if(null != client.getId()) {
				clientCriteria.add(Restrictions.eq("id", client.getId()));
			}
			Example clientExample = Example.create(client);
			clientExample.enableLike(MatchMode.ANYWHERE);
			clientCriteria.add(clientExample);
			list = super.listAll(clientCriteria,offset, pageSize);
		}else {
			list = super.listAll(costAccountCriteria,offset, pageSize);
		}
		
//		List<CostAccounting> list = super.listAll(clientCriteria,offset, pageSize); //"一页"的记录
		return list;
	}
	
	/**
	 * 根据id查询
	 * @param id 主键
	 * @return
	 */
	public CostAccounting findById(int id) {
		return (CostAccounting)hibernateTemplate.get(CostAccounting.class, id);
	}
	
	/**
	 * 
	 * @param costAccounting 封装了查询条件
	 * @return 总记录数
	 */
	@Transactional
    public int getAllRowCount(CostAccounting costAccounting){ 
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria costAccountCriteria = session.createCriteria(CostAccounting.class);
		Example costAccountCriteriaExample = Example.create(costAccounting);
		costAccountCriteriaExample.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		costAccountCriteria.add(costAccountCriteriaExample);
		return super.getAllRowCount(costAccountCriteria); 
	}
	
	/**
     * 增加一条成本核算记录
     * @param costAccounting
     */
	@Transactional
    public void save(CostAccounting costAccounting) {
    	hibernateTemplate.save(costAccounting);
    }
	
	/**
	 * 根据id删除成本核算记录
	 * @param id
	 */
	@Transactional
	public void deleteById(int[] ids) {
		StringBuilder hql = new StringBuilder("delete com.hhyzoa.model.CostAccounting ");
		
		if(ids.length == 1) {
			hql.append("where id = "+ ids[0]);
		}else if(ids.length > 1) {
			String tempStr = "";
			for(int i=0;i<ids.length;i++) {
				tempStr += ids[i]+",";
			}
			hql.append("where id in (").append(tempStr.substring(0, tempStr.length()-1)).append(")");
		}
		//delete costAccounting 
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.executeUpdate();
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
