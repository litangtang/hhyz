<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					$("[name=invoiceId]:checkbox").attr("checked",this.checked);
				});
			
				$("[name=invoiceId]:checkbox").click(
					function() {
						var flag = true;
						$("[name=invoiceId]:checkbox").each(function() {
							if(!this.checked) {
								flag = false;
							}
						});
						
						$("#checkedAll").attr("checked",flag);
					}
				);

				
				$("#addBtn").click(function(){
					document.forms[0].action = "invoice/forAdd.action";
					document.forms[0].submit();
				});

				$("#updateBtn").click(function(){
					if(!getSingleSelectCheckbox("invoiceId")) {
						return false;
					}
					document.forms[0].action = "invoice/forUpdate.action";
					document.forms[0].submit();
				});

				$("#deleteBtn").click(function(){
					if(!getSelectChectboxAtLeast("invoiceId")) {
						return false;
					}
					document.forms[0].action = "invoice/delete.action";
					document.forms[0].submit();
				});
				
				//查询
				$("#searchBtn").click(function() {
					document.forms[0].action = "invoice/listAll.action";
					document.forms[0].submit();
				});

			});
		</script>
	</head>
	<body>
		
		<form action="" method="post">
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<!-- 第一行：top -->
				<tr>
					<td width="25" height="50" background="img/main01.png">&nbsp;</td>
					<td background="img/main02.png">
						<span id="maintop"></span>
						<span class="mainAction">当前路径：开票记录</span>
					</td>
					<td width="29" background="img/main03.png">&nbsp;</td>
				</tr>
				<!-- 第二行：content -->
				<tr>
					<td background="img/main04.png">&nbsp;</td>
					
					<td align="center" valign="top" bgcolor="#FEFCFC" height="600">
						<!-- 查询 -->
						<table width="100%" height="42" border="0" align="center" cellpadding="0" cellspacing="0" id="mainTopMenu">
							<tr>
								<td width="7"></td>
								<td width="32"><img src="img/titleR1.png" width="28" height="28" class="mainmin">
								</td>
								<td width="150"><span class="maintoptext"></span></td>
								<td>&nbsp;</td>
								<td width="206" align="left" valign="middle">
									<select name="searchYear" id="searchYear">
										<c:forEach items="${yearList}" var="yearItem">
											<option value="${yearItem}" ${yearItem == searchYear?"selected":"" }>${yearItem}</option>
										</c:forEach>
									</select>
									<span class="mainTopR">年</span>
									<select name=searchMonth id="searchMonth">
										<c:forEach items="${monthList}" var="monthItem">
											<option value="${monthItem}" ${monthItem == searchMonth?"selected":""}>${monthItem}</option>
										</c:forEach>
									</select>
									<span class="mainTopR">月</span>
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
									<th scope="col"><span class="tableTop"></span>月</th>
									<th scope="col"><span class="tableTop"></span>日</th>
									<th scope="col"><span class="tableTop"></span>开票单位</th>
									<th scope="col"><span class="tableTop"></span>数量</th>
									<th scope="col"><span class="tableTop"></span>单价(元)</th>
									<th scope="col"><span class="tableTop"></span>金额(元)</th>
									<th scope="col"><span class="tableTop"></span>不含税金额</th>
									<th scope="col"><span class="tableTop"></span>不含税累计</th>
									<th scope="col"><span class="tableTop"></span>销项税</th>
									<th scope="col"><span class="tableTop"></span>销项税累计</th>
									<th scope="col"><span class="tableTop"></span>备注</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="pageBean.list">
									<tr class="oTr">
										<td align="center">
											<input type="checkbox" name="invoiceId" value="<s:property value='id'/>">
										</td>
										<td align="center">
											<s:property value="month" />
										</td>
										<td align="center">
											<s:property value="day" />
										</td>
										<td align="center">
											<s:property value="invoiceComp" />
										</td>
										<td align="center">
											<s:property value="count" />
										</td>
										<td align="center">
											<s:property value="price" />
										</td>
										<td align="center">
											<s:property value="origAmt" />
										</td>
										<td align="center">
											<s:property value="noTaxAmt" />
										</td>
										<td align="center">
											<s:property value="noTaxAccu" />
										</td>
										<td align="center">
											<s:property value="sellTax" />
										</td>
										<td align="center">
											<s:property value="sellTaxAccu" />
										</td>
										<td align="center">
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
								<a href="invoice/listAll.action?page=<s:property value='%{pageBean.currentPage+1}'/>&searchYear=${searchYear}&searchMonth=${searchMonth}" class="tableD01">下页</a>
							</s:if>
							<s:if test="#request.pageBean.currentPage != 1">
								<a href="invoice/listAll.action?page=<s:property value='%{pageBean.currentPage-1}'/>&searchYear=${searchYear}&searchMonth=${searchMonth}" class="tableD01">上页</a>
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
