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

				$('#searchDate').datepicker({
					changeMonth: true,
					changeYear: true
				});

				var from = $("#from").val();//from=client_1 or from=client_2
				//alert(tradeFlag);
				$("#addBtn").click(function(){
					document.forms[0].action = "trade/forAdd.action?from="+from;
					document.forms[0].submit();
				});
				
				$("#updateBtn").click(function(){
					if(!getSingleSelectCheckbox("tradeId")) {
						return false;
					}
					document.forms[0].action = "trade/forUpdateOfSB.action";
					document.forms[0].submit();
				});

				/*$("#backBtn").click(function(){
					history.go(-2);
				});*/

				
				var tradeFlag = $("#tradeFlag").val();//往来类别
				
				$("#deleteBtn").click(function(){
					if(!getSelectChectboxAtLeast("tradeId")) {
						return false;
					}
					
					document.forms[0].action = "trade/deleteFromSB.action?from=trade_"+tradeFlag;
					document.forms[0].submit();
				});
			});
		</script>
	</head>
	<%
		String clientName = (String)request.getAttribute("clientName");
		String clientId = (String)request.getAttribute("clientId");
		
		String tradeFlag = (String)request.getAttribute("tradeFlag");
		if(null == tradeFlag) {
			tradeFlag = "1";//0为所有往来1为原料往来 2为销售往来
		}
		
		String from = "client_"+tradeFlag;//from=client_1 or from=client_2
	%>
	<body>
		<form action="" method="post">
			<input type="hidden" name="clientId" value="<s:property value='clientId'/>">
			<input type="hidden" id="from" name="from" value="<%=from %>"/>
			<input type="hidden" id="name" name="name" value="<%=clientName %>"/>
			<input type="hidden" id="tradeFlag" name="tradeFlag" value="<%=tradeFlag %>"/>
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<!-- 第一行：top -->
				<tr>
					<td width="25" height="50" background="img/main01.png">&nbsp;</td>
					<td background="img/main02.png">
						<span id="maintop"></span>
						<span class="mainAction">当前路径：往来 - <%=clientName%> </span>
						
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
									<span class="mainTopR">日期</span>
									<input type="text" id="searchDate" name="searchDate"
											class="ui-widget-content ui-corner-all" />
								</td>
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
									<th scope="col"><span class="tableTop"></span>摘要</th>
									<th scope="col"><span class="tableTop"></span>件数</th>
									<th scope="col"><span class="tableTop"></span>数量</th>
									<th scope="col"><span class="tableTop"></span>单价</th>
									<th scope="col"><span class="tableTop"></span><%=(tradeFlag.equals("1")?"送货金额":"贷款金额") %></th>
									<th scope="col"><span class="tableTop"></span><%=(tradeFlag.equals("1")?"付款金额":"收回金额") %></th>
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
											<s:property value="abst" />
										</td>
										<td>
											<s:property value="packages" />
										</td>
										<td>
											<s:property value="amount" />
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
								<a href="trade/listAllOfSB.action?page=<s:property value='%{pageBean.currentPage+1}'/>&clientId=<%=clientId%>" class="tableD01">下页</a>
							</s:if>
							<s:if test="#request.pageBean.currentPage != 1">
								<a href="trade/listAllOfSB.action?page=<s:property value='%{pageBean.currentPage-1}'/>&clientId=<%=clientId%>" class="tableD01">上页</a>
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
