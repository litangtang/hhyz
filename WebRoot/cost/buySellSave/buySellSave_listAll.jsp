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
					$("[name=bssId]:checkbox").attr("checked",this.checked);
				});
			
				$("[name=bssId]:checkbox").click(
					function() {
						var flag = true;
						$("[name=bssId]:checkbox").each(function() {
							if(!this.checked) {
								flag = false;
							}
						});
						
						$("#checkedAll").attr("checked",flag);
					}
				);


				$('#searchDate').datepicker({
					changeMonth: true,
					changeYear: true
				});
				
				$("#addBtn").click(function(){
					document.forms[0].action = "buySellSave/forAdd.action";
					document.forms[0].submit();
				});

				$("#updateBtn").click(function(){
					if(!getSingleSelectCheckbox("bssId")) {
						return false;
					}
					document.forms[0].action = "buySellSave/forUpdate.action";
					document.forms[0].submit();
				});

				$("#deleteBtn").click(function(){
					if(!getSelectChectboxAtLeast("bssId")) {
						return false;
					}
					document.forms[0].action = "buySellSave/delete.action";
					document.forms[0].submit();
				});
				
				

				//查询
				$("#searchBtn").click(function() {
					
					document.forms[0].action = "buySellSave/listAll.action";
					document.forms[0].submit();
				});
			});
		</script>
	</head>
	<%
		
		
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
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<!-- 第一行：top -->
				<tr>
					<td width="25" height="50" background="img/main01.png">&nbsp;</td>
					<td background="img/main02.png">
						<span id="maintop"></span>
						
						<span class="mainAction">当前路径：蓖麻子进销存统计表</span>
						
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
											value="<%=searchDate%>" class="ui-widget-content ui-corner-all" />
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
									<th scope="col" rowspan="2"><span class="tableTop"></span><input type="checkbox" id="checkedAll"/></th>
									<th scope="col" colspan="2" align="center"><span class="tableTop"></span>日期</th>
									<th scope="col" colspan="3" align="center"><span class="tableTop"></span>当月购进蓖麻子</th>
									<th scope="col" colspan="3" align="center"><span class="tableTop"></span>当月开票蓖麻子</th>
									<th scope="col" colspan="3" align="center"><span class="tableTop"></span>当月结余蓖麻子</th>
								</tr>
								<tr>
									<th scope="col"><span class="tableTop"></span>月</th>
									<th scope="col"><span class="tableTop"></span>日</th>
									<th scope="col"><span class="tableTop"></span>当月购进数量</th>
									<th scope="col"><span class="tableTop"></span>购进单价</th>
									<th scope="col"><span class="tableTop"></span>购进金额</th>
									<th scope="col"><span class="tableTop"></span>开票数量</th>
									<th scope="col"><span class="tableTop"></span>开票单价</th>
									<th scope="col"><span class="tableTop"></span>开票金额</th>
									<th scope="col"><span class="tableTop"></span>结余数量</th>
									<th scope="col"><span class="tableTop"></span>结余金额</th>
									<th scope="col"><span class="tableTop"></span>平均单价</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="pageBean.list">
									<tr class="oTr">
										<td align="center">
											<input type="checkbox" name="bssId" value="<s:property value='id'/>">
										</td>
										<td colspan="2" align="center">
											<s:property value="date" />
										</td>
										<td>
											<s:property value="buyAmount" />
										</td>
										<td>
											<s:property value="buyPrice" />
										</td>
										<td>
											<s:property value="buyMoney" />
										</td>
										<td>
											<s:property value="invoiceAmount" />
										</td>
										<td>
											<s:property value="invoicePrice" />
										</td>
										<td>
											<s:property value="invoiceMoney" />
										</td>
										<td>
											<s:property value="balanceAmount" />
										</td>
										<td>
											<s:property value="balanceMoney" />
										</td>
										<td>
											<s:property value="averagePrice" />
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
								<a href="rate/listAll.action?page=<s:property value='%{pageBean.currentPage+1}'/>" class="tableD01">下页</a>
							</s:if>
							<s:if test="#request.pageBean.currentPage != 1">
								<a href="rate/listAll.action?page=<s:property value='%{pageBean.currentPage-1}'/>" class="tableD01">上页</a>
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
