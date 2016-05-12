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
					$("[name=costId]:checkbox").attr("checked",this.checked);
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


				$('#searchDate').datepicker({
					changeMonth: true,
					changeYear: true
				});
				
				$("#addBtn").click(function(){
					document.forms[0].action = "cost/forAdd.action";
					document.forms[0].submit();
				});

				$("#addBtn2").click(function(){
					document.forms[0].action = "cost/forAdd2.action";
					document.forms[0].submit();
				});
				
				$("#updateBtn").click(function(){
					if(!getSingleSelectCheckbox("costId")) {
						return false;
					}
					document.forms[0].action = "cost/forUpdate.action";
					document.forms[0].submit();
				});

				$("#deleteBtn").click(function(){
					if(!getSelectChectboxAtLeast("costId")) {
						return false;
					}
					document.forms[0].action = "cost/delete.action";
					document.forms[0].submit();
				});
				
				

				//查询
				$("#searchBtn").click(function() {
					document.forms[0].action = "cost/listAll.action";
					document.forms[0].submit();
				});
			});
		</script>
	</head>
	<%
		
		
		/*String searchClient = (String)request.getAttribute("searchClient");
		if(null == searchClient) {
			searchClient = "";
		}*/
		
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
						
						<span class="mainAction">当前路径： 蓖麻油产品成本核算表</span>
						
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
									<span class="mainTopR">供应单位</span>
									<s:select list="#request.clientMap" id="selectClientId"
														name="selectClientId" headerKey="-1" headerValue="--请选择供应单位--" value="#request.selectClientId"/>
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
									<th scope="col"><span class="tableTop"></span>产地</th>
									<th scope="col"><span class="tableTop"></span>原料价</th>
									<th scope="col"><span class="tableTop"></span>出油率</th>
									<th scope="col"><span class="tableTop"></span>原料成本</th>
									<th scope="col"><span class="tableTop"></span>副产品</th>
									<th scope="col"><span class="tableTop"></span>二级油</th>
									<th scope="col"><span class="tableTop"></span>二级工费</th>
									<th scope="col"><span class="tableTop"></span>二级成本</th>
									<th scope="col"><span class="tableTop"></span>一级工费</th>
									<th scope="col"><span class="tableTop"></span>损耗</th>
									<th scope="col"><span class="tableTop"></span>一级成本</th>
									<th scope="col"><span class="tableTop"></span>国地税</th>
									<th scope="col"><span class="tableTop"></span>包装</th>
									
									<th scope="col"><span class="tableTop"></span>销售成本</th>
									<th scope="col"><span class="tableTop"></span>销售利润</th>
									<th scope="col"><span class="tableTop"></span>出厂价</th>
									
									<th scope="col"><span class="tableTop"></span>运费</th>
									<th scope="col"><span class="tableTop"></span>运到价</th>
									
									<th scope="col"><span class="tableTop"></span>单位</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="pageBean.list">
									<tr class="oTr">
										<td align="center">
											<input type="checkbox" name="costId" value="<s:property value='id'/>">
										</td>
										<td>
											<s:property value="date" />
										</td>
										<td>
											<s:property value="materialOrigin" />
										</td>
										<td>
											<s:property value="materialPrice" />
										</td>
										
										<td>
											<s:property value="rateOfOil" />
										</td>
										<td>
											<s:property value="materialCost" />
										</td>
										<td>
											<s:property value="fcpz" />
										</td>
										<td>
											<s:property value="ejyz" />
										</td>
										<td>
											<s:property value="ejyJgf" />
										</td>
										<td>
											<s:property value="ejjsCost" />
										</td>
										<td>
											<s:property value="yjyJgf" />
										</td>
										<td>
											<s:property value="jlsh" />
										</td>
										
										<td>
											<s:property value="yjjsCost" />
										</td>
										<td>
											<s:property value="tax" />
										</td>
										<td>
											<s:property value="ttbz" />
										</td>
										<td>
											<s:property value="sellCost" />
										</td>
										<td>
											<s:property value="sellProfit" />
										</td>
										<td>
											<s:property value="sellPrice" /> <!-- 出厂价 -->
										</td>
										<td>
											<s:property value="tqyf" />
										</td>
										<td>
											<s:property value="finalPrice" /> <!-- 送到价 -->
										</td>
										<td>
											<s:property value="client.name" />
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<!-- 分页 -->
						<div id="tableDown">
							<input type="button" id="addBtn" class="btnOpe" value="增加" />
							<input type="button" id="addBtn2" class="btnOpe" value="按合同价格增加" />
							<!--  <input type="button" id="updateBtn" class="btnOpe" value="修改" /> -->
							<input type="button" id="deleteBtn" class="btnOpe" value="删除" />
							
							<span class="tableD01">共<span id="tableD04"><s:property value="pageBean.allRow" /></span>条记录</span>
							<span class="tableD01">共<span id="tableD04"><s:property value="pageBean.totalPage" /></span>页</span>
							<s:if test="#request.pageBean.currentPage != #request.pageBean.totalPage">
								<a href="cost/listAll.action?page=<s:property value='%{pageBean.currentPage+1}'/>" class="tableD01">下页</a>
							</s:if>
							<s:if test="#request.pageBean.currentPage != 1">
								<a href="cost/listAll.action?page=<s:property value='%{pageBean.currentPage-1}'/>" class="tableD01">上页</a>
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
