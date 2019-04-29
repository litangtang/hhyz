<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<link type="text/css" href="styles/themes/base/jquery.ui.all.css"
			rel="stylesheet" />
		<link href="css/elson.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonLink.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="js/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="js/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript"
			src="js/ui/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript" src="js/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="js/tableMain.js"></script>
		<script type="text/javascript" src="js/validForm.js"></script>
		<script language="javascript">
			$(function() {
				$("table.datalist tr:nth-child(odd)").addClass("altrow");

				/* 复选框 */
				$("#checkedAll").click(function() {
					$("[name=tradeId]:checkbox").attr("checked",this.checked);
				});
			
				$("[name=tradeId]:checkbox").click(
					function() {
						var flag = true;
						$("[name=tradeId]:checkbox").each(function() {
							if(!this.checked) {
								flag = false;
							}
						});
						
						$("#checkedAll").attr("checked",flag);
					}
				);

				var tradeFlag = $("#tradeFlag").val();//往来类别
				//alert(tradeFlag);

				$('#searchDate').datepicker({
					changeMonth: true,
					changeYear: true
				});
				
				$("#addBtn").click(function(){
					document.forms[0].action = "trade/forAdd.action?from=trade_"+tradeFlag+"&type="+tradeFlag;
					document.forms[0].submit();
				});

				$("#updateBtn").click(function(){
					if(!getSingleSelectCheckbox("tradeId")) {
						return false;
					}
					document.forms[0].action = "trade/forUpdate.action?type="+tradeFlag;
					document.forms[0].submit();
				});

				$("#deleteBtn").click(function(){
					if(!getSelectChectboxAtLeast("tradeId")) {
						return false;
					}
					document.forms[0].action = "trade/delete.action?from=trade_"+tradeFlag+"&type="+tradeFlag;
					document.forms[0].submit();
				});
				
				

				//查询
				$("#searchBtn").click(function() {
					
					document.forms[0].action = "trade/listAll.action";
					document.forms[0].submit();
				});
			});
		</script>
	</head>
	<%
		//String from = "trade_";
		String tradeFlag = (String)request.getAttribute("tradeFlag");
		if(null == tradeFlag) {
			tradeFlag = "0";//0为所有往来1为原料往来 2为销售往来
		}
		//else {
		//	from = from + tradeFlag;
		//}
		
		String searchDate = (String)request.getAttribute("searchDate");
		if(null == searchDate) {
			searchDate = "";
		}
		
		/*String searchClient = (String)request.getAttribute("searchClient");
		if(null == searchClient) {
			searchClient = "";
		}*/
	%>
	<body>
		<form action="" method="post">
			<input type="hidden" id="tradeFlag" value="<%=tradeFlag%>"/>
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<!-- 第一行：top -->
				<tr>
					<td width="25" height="50" background="img/main01.png">&nbsp;</td>
					<td background="img/main02.png">
						<span id="maintop"></span>
						<%
							if("0".equals(tradeFlag.trim())) {
						%>
						
								<span class="mainAction">当前路径：所有往来</span>
						<%
							}else {
						
						%>
						<span class="mainAction">当前路径：往来 - <%=(tradeFlag.equals("1")?"原料往来":"销售往来") %></span>
						<% } %>
						
					</td>
					<td width="29" background="img/main03.png">&nbsp;</td>
				</tr>
				<!-- 第二行：content -->
				<tr>
					<td background="img/main04.png">&nbsp;</td>
					
					<td align="center" valign="top" bgcolor="#FEFCFC">
						<!-- 查询 -->
						<table width="100%" height="42" border="0" align="center" cellpadding="0" cellspacing="0" id="mainTopMenu">
							<tr>
								<td width="7"></td>
								<td width="32"><img src="img/titleR1.png" width="28" height="28" class="mainmin">
								</td>
								<td width="150"><span class="maintoptext">查询</span></td>
								<td>&nbsp;</td>
								<td width="206" align="left" valign="middle">
									<span class="mainTopR">客户</span>
									<s:select list="#request.clientMap" id="searchClient" 
										name="searchClient" headerKey="-1" headerValue="--请选择客户--" value="#request.searchClient"/>
								</td>
								<td width="206" align="left" valign="middle">
									<span class="mainTopR">日期</span>
									<input type="text" id="searchDate" name="searchDate"
											value="<%=searchDate%>" class="ui-widget-content ui-corner-all" />
								</td>
								<!--  
								<td width="134" align="left" valign="middle">
									<span class="mainTopR">类别</span>
								</td>
								-->
								<td width="100" align="center" valign="middle">
									<input type="button" id="searchBtn" class="btn2" value="开始查询" />
								</td>
							</tr>
						</table>
						<!-- 列表 -->
						<table class="datalist" id="oTable">
							<thead>
								<tr>
									<th scope="col"><span class="tableTop"></span><input type="checkbox" id="checkedAll"/></th>
									<th scope="col"><span class="tableTop"></span>日期</th>
									<th scope="col"><span class="tableTop"></span>客户</th>
									<th scope="col"><span class="tableTop"></span>摘要</th>
									<th scope="col"><span class="tableTop"></span>件数</th>
									<th scope="col"><span class="tableTop"></span>累计</th>
									<th scope="col"><span class="tableTop"></span>数量</th>
									<th scope="col"><span class="tableTop"></span>累计</th>
									<th scope="col"><span class="tableTop"></span>单价</th>
									<th scope="col"><span class="tableTop"></span>送货金额</th>
									<th scope="col"><span class="tableTop"></span>付款金额</th>
									<th scope="col"><span class="tableTop"></span>借贷</th>
									<th scope="col"><span class="tableTop"></span>余额</th>
									<th scope="col"><span class="tableTop"></span>核对情况</th>
									<th scope="col"><span class="tableTop"></span>备注</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="pageBean.list">
									<tr class="oTr">
										<td align="center">
											<input type="checkbox" name="tradeId" value="<s:property value='id'/>">
										</td>
										<td>
											<s:property value="date" />
										</td>
										<td>
											<s:property value="client.name" />
										</td>
										<td>
											<s:property value="abst" />
										</td>
										<td>
											<s:property value="packages" />
										</td>
										<td>
											<s:property value="packageAccuStr" />
										</td>
										<td>
											<s:property value="amount" />
										</td>
										<td>
											<s:property value="amountAccuStr" />
										</td>
										<td>
											<s:property value="price" />
										</td>
										<td>
											<s:property value="carriage" />
										</td>
										<td>
											<s:property value="payment" />
										</td>
										<td>
											<s:property value="isLoanStr"  />
										</td>
										<td>
											<s:property value="balance" />
										</td>
										<td>
											<s:property value="verify" />
										</td>
										<td>
											<s:property value="remark" />
										</td>
										
										<!--
										<td>
											<a href="trade/forAdd.action?clientId=<s:property value='id'/>&name=<s:property value='name'/>">增加往来</a>
											<a href="trade/forAdd.action?clientId=<s:property value='id'/>">查看往来</a>
										</td>
										-->
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<!-- 分页 -->
						<div id="tableDown">
							<input type="button" id="addBtn" class="btnOpe" value="增加" />
							<input type="button" id="updateBtn" class="btnOpe" value="修改" />
							<input type="button" id="deleteBtn" class="btnOpe" value="删除" />
							
							<span class="tableD01">共<span id="tableD04"><s:property value="pageBean.allRow" /></span>条记录</span>
							<span class="tableD01">共<span id="tableD04"><s:property value="pageBean.totalPage" /></span>页</span>
							<s:if test="#request.pageBean.currentPage != #request.pageBean.totalPage">
								<a href="trade/listAll.action?page=<s:property value='%{pageBean.currentPage+1}'/>&tradeFlag=<%=tradeFlag%>&searchClient=<s:property value='#request.searchClient'/>" class="tableD01">下页</a>
							</s:if>
							<s:if test="#request.pageBean.currentPage != 1">
								<a href="trade/listAll.action?page=<s:property value='%{pageBean.currentPage-1}'/>&tradeFlag=<%=tradeFlag%>&searchClient=<s:property value='#request.searchClient'/>" class="tableD01">上页</a>
							</s:if>
							<span class="tableD01">第<span id="tableD04"><s:property value="pageBean.currentPage" /></span>页</span>
						</div>
					</td>
					<td background="img/main05.png">&nbsp;</td>
				</tr>
				<!-- 第三行:下边儿 -->
				<tr>
					<td height="28" background="img/main06.png">&nbsp;</td>
					<td background="img/main07.png">&nbsp;</td>
					<td background="img/main08.png">&nbsp;</td>
				</tr>
			</table>
		</form>
	</body>
</html>
