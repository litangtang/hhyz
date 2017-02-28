package com.hhyzoa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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

import com.hhyzoa.dao.ClientDao;
import com.hhyzoa.model.Client;

@SuppressWarnings("unchecked")
@Component("clientDao")
public class ClientDaoImpl extends BaseDaoImpl implements ClientDao {
	private Logger log = Logger.getLogger(ClientDaoImpl.class);
	private HibernateTemplate hibernateTemplate;
	
	private Session session;

	public void save(Client client) {		
		hibernateTemplate.save(client);
	}
	
	public void update(Client client) {
		hibernateTemplate.update(client);
	}

	/**
	 * 分页查询所有客户
	 * @param pageSize 每页显示记录数 
	 * @param offset 开始记录
	 * @param client 封装查询条件
	 * @return
	 */
	@Transactional
	public List<Client> findALL(int pageSize,int offset,Client client) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Client.class);
		Example example = Example.create(client);
		example.enableLike(MatchMode.ANYWHERE);//使QBE支持模糊查询
		//2017-02-27 修改为按访问频率count排序
//		criteria.add(example).addOrder(GBKOrder.asc("name"));
		criteria.add(example);
		criteria.addOrder(Order.desc("count"));
		criteria.addOrder(Order.desc("modtime"));
		
		List<Client> list = super.listAll(criteria,offset, pageSize);        //"一页"的记录
		return list;
	}
	
	 /** 
     * 查询所有记录数  
     * @return 总记录数  
     */  
	@Transactional
    public int getAllRowCount(Client client){   
    	session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Client.class);
		Example example = Example.create(client);
		example.enableLike(MatchMode.ANYWHERE);//默认是EXACT
		criteria.add(example);
        return super.getAllRowCount(criteria); 
    }   
	
	/**
	 * 根据id查询
	 */
	public Client findById(int id) {
		return (Client)hibernateTemplate.get(Client.class, id);
	}
	
	/**
	 * 根据id删除客户
	 * @param id
	 */
	@Transactional
	public void deleteById(int[] ids) {
		StringBuilder hql = new StringBuilder("delete Client ");
		
		if(ids.length == 1) {
			hql.append("where id = "+ ids[0]);
		}else if(ids.length > 1) {
			String tempStr = "";
			for(int i=0;i<ids.length;i++) {
				tempStr += ids[i]+",";
			}
			hql.append("where id in (").append(tempStr.substring(0, tempStr.length()-1)).append(")");
		}
		//delete client
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.executeUpdate();
	}
	
	/**
	 * 删除客户
	 */
	@Transactional
	public void delete(Client client) {
		hibernateTemplate.delete(client);
	}
    
	/**
	 * 按类别查询所有的客户，非分页，用于增加往来时，下拉列表显示客户
	 * @param type
	 * @return
	 */
	@Transactional
	public List<Client> findALL(final Integer type) {
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Client.class);
		if(null != type && 0 != type) {
			criteria.add(Restrictions.eq("type", type));
		}
		return criteria.list();
		
//		return (List<Client>)getHibernateTemplate().executeFind(new HibernateCallback() {
//			
//			public List<Client> doInHibernate(Session arg0) throws HibernateException,
//					SQLException {
//				Query query = null ;
//				
//				StringBuilder hql = new StringBuilder("FROM Client WHERE 1=1");
//				if(null != type) { //查询原料客户或者销售客户
//					hql.append("and type = :type");
//					query = session.createQuery(hql.toString());
//					query.setInteger("type", type);
//				}else {
//					query = session.createQuery(hql.toString());
//				}
//				return (List<Client>)query.list();
//			}
//		});
	}
	
	//2017-02-27 add
	@Transactional
	public void updateCount(Integer id, Integer count) {
		StringBuilder hql = new StringBuilder("update Client c set c.count = :count where c.id = :id");
		log.info(String.format("更新用户访问频率sql语句：%s, count=%s, id=%s", hql.toString(), count, id));
        Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql.toString());
        query.setInteger("count", count);
        query.setInteger("id", id);
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
