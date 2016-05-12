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
					$("[name=rateId]:checkbox").attr("checked",this.checked);
				});
			
				$("[name=rateId]:checkbox").click(
					function() {
						var flag = true;
						$("[name=rateId]:checkbox").each(function() {
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
					document.forms[0].action = "rate/forAdd.action";
					document.forms[0].submit();
				});

				$("#updateBtn").click(function(){
					if(!getSingleSelectCheckbox("rateId")) {
						return false;
					}
					document.forms[0].action = "rate/forUpdate.action";
					document.forms[0].submit();
				});

				$("#deleteBtn").click(function(){
					if(!getSelectChectboxAtLeast("rateId")) {
						return false;
					}
					document.forms[0].action = "rate/delete.action";
					document.forms[0].submit();
				});
				
				

				//查询
				$("#searchBtn").click(function() {
					
					document.forms[0].action = "rate/listAll.action";
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
						
						<span class="mainAction">当前路径： 蓖麻油出品率核算表</span>
						
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
									<th scope="col"><span class="tableTop"></span><input type="checkbox" id="checkedAll"/></th>
									<th scope="col"><span class="tableTop"></span>日期</th>
									<th scope="col"><span class="tableTop"></span>机器数</th>
									<th scope="col"><span class="tableTop"></span>容积</th>
									<th scope="col"><span class="tableTop"></span>饼规格</th>
									<th scope="col"><span class="tableTop"></span>饼件数</th>
									<th scope="col"><span class="tableTop"></span>出油率</th>
									<th scope="col"><span class="tableTop"></span>出饼率</th>
									<th scope="col"><span class="tableTop"></span>比重</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="pageBean.list">
									<tr class="oTr">
										<td align="center">
											<input type="checkbox" name="rateId" value="<s:property value='id'/>">
										</td>
										<td>
											<s:property value="date" />
										</td>
										<td>
											<s:property value="numOfMachinesStr" />
										</td>
										<td>
											<s:property value="capacity" />
										</td>
										<td>
											<s:property value="packagesMultiply" />
										</td>
										<td>
											<s:property value="numOfPackages" />
										</td>
										<td>
											<s:property value="rateOfOil" />
										</td>
										<td>
											<s:property value="rateOfCake" />
										</td>
										<td>
											<s:property value="oilRate" />
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
