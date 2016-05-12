package com.hhyzoa.dao;

import java.util.List;

import org.hibernate.Criteria;

public interface BaseDao {
	
	public List<?> listAll(final Criteria criteria,final int offset,final int length);
    
	public List<?> listAll(final String hql,final int offset,final int length); 
    
       
    public int getAllRowCount(Criteria criteria);
    
    public int getAllRowCount(String hql);

}
