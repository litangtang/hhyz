package com.hhyzoa.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.hhyzoa.dao.BaseDao;

@Component("baseDao")
public class BaseDaoImpl implements BaseDao {
	
	private HibernateTemplate hibernateTemplate;

	/**   
     * 分页查询  
     * @param hql 查询的条件  
     * @param offset 开始记录  
     * @param length 一次查询几条记录  
     * @return  
     */  
	@SuppressWarnings("unchecked")
    public List listAll(final String hql,final int offset,final int length){   
        List list = getHibernateTemplate().executeFind(new HibernateCallback(){   
            public Object doInHibernate(Session session) throws HibernateException,SQLException{   
                Query query = session.createQuery(hql);   
                query.setFirstResult(offset);   
                query.setMaxResults(length);   
                List list = query.list();   
                return list;   
            }   
        });   
        return list;   
    } 
    
    /**   
     * 分页查询 
     * @param criteria  查询条件
     * @param offset 开始记录  
     * @param length 一次查询几条记录  
     * @return  
     */  
    @SuppressWarnings("unchecked")
    public List listAll(final Criteria criteria,final int offset,final int length){   
        List list = getHibernateTemplate().executeFind(new HibernateCallback(){   
            public Object doInHibernate(Session session) throws HibernateException,SQLException{   
                criteria.setFirstResult(offset);   
                criteria.setMaxResults(length);   
                List list = criteria.list();   
                return list;   
            }   
        });   
        return list;   
    }   
       
       
      
    /** 
     * 查询所有记录数  criteria查询
     * @return 总记录数  
     */ 
    @SuppressWarnings("unchecked")
    public int getAllRowCount(final Criteria criteria){   
    	List list = getHibernateTemplate().executeFind(new HibernateCallback(){   
            public Object doInHibernate(Session session) throws HibernateException,SQLException{   
                List list = criteria.list();   
                return list;   
            }   
        });
        return list.size();  
    } 
    
    /** 
     * 查询所有记录数  hql查询
     * @return 总记录数  
     */  
    public int getAllRowCount(String hql){   
        return getHibernateTemplate().find(hql).size();   
    } 

    
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
