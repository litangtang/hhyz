package com.hhyzoa.interceptor;

import java.util.Map;

import com.hhyzoa.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登陆权限控制
 * @author Bill
 *
 */
public class AuthorityInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		User user = (User)session.get("user");
		if(null == user)
			return Action.LOGIN;
		
		return null;
	}

}
