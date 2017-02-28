package com.hhyzoa.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.hhyzoa.model.Client;
import com.hhyzoa.service.ClientService;
import com.hhyzoa.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 2017-02-20 客户访问记录统计
 * @author Bill
 * 
 */
public class ClientAccessInterceptor extends AbstractInterceptor {
	
	Logger log = Logger.getLogger(ClientAccessInterceptor.class);
	private ClientService clientService;

	/**
	 * 根据增加往来来判断客户的访问频率
	 * 每增加一次，count+1
	 */
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		
		ActionContext actionContext = arg0.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		String url = request.getRequestURI();
		log.info(String.format("ClientAccessInterceptor：请求url=%s", url));
		
//		if (url.contains("trade/forAdd.action")) {
//			log.info(String.format("trade/forAdd.action:from=%s,clientId=%S", request.getParameter("from"), request.getParameter("clientId")));
//		}
		
		/**
		 * 因为增加的时候做了重定向，所以request.getRequestURI()是取不到trade/add.action，改为取trade/listAllOfSB.action
		 */
//		if (url.contains("trade/add.action")) {
//			log.info(String.format("trade/tradeAdd.action:from=%s,clientId=%S", request.getParameter("from"), request.getParameter("clientId")));
//		}
		
		if(url.contains("trade/listAllOfSB.action")) {
			log.info(String.format("trade/listAllOfSB.action:from=%s,clientId=%S", request.getParameter("from"), request.getParameter("clientId")));
			String id = StringUtil.trim(request.getParameter("clientId"));
			if(!StringUtil.isEmpty(id)) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");//如果要展示再格式化
//				Date curDate = new Date();
				Integer clientId = Integer.parseInt(id);
				Client oldClient = clientService.queryById(clientId);
				if(null == oldClient.getCount()) {
					oldClient.setCount(0);
				}
				
				Integer newCount = oldClient.getCount() + 1;
				clientService.modifyCount(clientId, newCount);
			}
		}
		
		return arg0.invoke();
	}

	public ClientService getClientService() {
		return clientService;
	}

	@Resource(name="clientService")
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

}
