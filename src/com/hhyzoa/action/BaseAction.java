package com.hhyzoa.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hhyzoa.model.PageBean;
import com.hhyzoa.util.Constants;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	
	private HttpSession session;
	
	
	/**
	 * 分页bean
	 * 
	 * @param tList
	 *            所要显示的List
	 * @param allRow
	 *            总记录数
	 * @param currPage
	 *            当前页
	 * @param totalPage
	 *            总页数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean getPageBean(List tList, int allRow, int currPage, int totalPage)
	{

		PageBean pb = new PageBean();
		pb.setList(tList); // 分页
		pb.setAllRow(allRow);// 总记录数
		pb.setPageSize(Constants.PAGE_SIZE);// 每页显示条数
		pb.setCurrentPage(currPage);// 当前页
		pb.setTotalPage(totalPage);// 总页数
		pb.init();
		return pb;

	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public HttpSession getSession() {
		return session;
	}


	public void setSession(HttpSession session) {
		this.session = session;
	}
	


}
