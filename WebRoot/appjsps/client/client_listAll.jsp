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
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
			<link rel="stylesheet" type="text/css" href="styles.css">
		-->
		<link href="css/elson.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonLink.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="js/tableMain.js"></script>
		<script type="text/javascript" src="js/validForm.js"></script>
		<script language="javascript">
			$(function() {
				$("table.datalist tr:nth-child(odd)").addClass("altrow");
				
				/* 复选框 */
				$("#checkedAll").click(function() {
					$("[name=clientId]:checkbox").attr("checked",this.checked);
				});
			
				$("[name=clientId]:checkbox").click(
					function() {
						var flag = true;
						$("[name=clientId]:checkbox").each(function() {
							if(!this.checked) {
								flag = false;
							}
						});
						
						$("#checkedAll").attr("checked",flag);
					}
				);
				
				$("#addBtn").click(function() {
					document.forms[0].action = "client/forAdd.action";
					document.forms[0].submit();
				});

				$("#updateBtn").click(function() {
					if(!getSingleSelectCheckbox("clientId")) {
						return false;
					}
					document.forms[0].action = "client/forUpdate.action";
					document.forms[0].submit();
				});
				
				$("#deleteBtn").click(function() {
					if(!getSelectChectboxAtLeast("clientId")) {
						return false;
					}
					document.forms[0].action = "client/delete.action";
					document.forms[0].submit();
				});
				
				//查询
				$("#searchBtn").click(function() {
					var searchType = $("#searchType").val();
					document.forms[0].action = "client/listAll.action?searchType="+searchType;
					document.forms[0].submit();
				});
				
				
		});
	</script>

	</head>
	<%
			String searchName = (String) request.getAttribute("searchName");
			String searchType = (String) request.getAttribute("searchType");
			
			//标志位，区分是原料客户还是销售客户，用来确定页面跳转
			//client_1为原料客户，client_2为销售客户
			String from = "client_";
			
			if (null == searchName) {
				searchName = "";
			}
			if (null == searchType) {
				searchType = "0";//0为所有客户1为原料客户2为销售客户
			}
			from = from + searchType;
			//else {
			//	from = from + searchType;
			//}
			

			//Map typeStrMap = (Map)request.getAttribute("typeStrMap");
		%>
	<body>
		<form action="" method="post">
			<input type="hidden" name="from" value="<%=from%>"/>
			<input type="hidden" id="searchType" name="searchType" value="<%=searchType%>"/>
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<!-- 第一行：top -->
				<tr>
					<td width="25" height="50" background="img/main01.png">&nbsp;</td>
					<td background="img/main02.png">
						<span id="maintop"></span>
						<%
							if("0".equals(searchType.trim())) {
						%>
							<span class="mainAction">当前路径：所有客户</span>
						<%
							}else {
						%>
						<span class="mainAction">当前路径：客户 - <%=(searchType.equals("1")?"原料客户":"销售客户") %></span>
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
								<td width="150"><span class="maintoptext">查询客户信息</span></td>
								<td>&nbsp;</td>
								<td width="206" align="left" valign="middle">
									<span class="mainTopR">姓名</span>
									<input name="searchName" type="text" id="searchName"
										value="<%=searchName%>" size="15" class="ui-widget-content ui-corner-all" />
								</td>
								<!-- 
								<td width="134" align="left" valign="middle">
									<span class="mainTopR">类别</span>
									<select name="searchType" id="searchType">
										<option value="0"
											<%=(searchType.equals("0") ? "selected" : "")%>>
											请选择类别
										</option>
										<option value="1"
											<%=(searchType.equals("1") ? "selected" : "")%>>
											原料客户
										</option>
										<option value="2"
											<%=(searchType.equals("2") ? "selected" : "")%>>
											销售客户
										</option>
									</select>
								</td>
								 -->
								<td width="100" align="center" valign="middle">
									<input type="button" id="searchBtn" class="btn2" value="开始查询" />
								</td>
							</tr>
						</table>
						<!-- 列表 -->
						<table class="datalist" id="oTable">
							<tr valign="middle">
								<th scope="col" width="5%">
									<span class="tableTop"></span><input type="checkbox" id="checkedAll"/>
								</th>
								<th scope="col" width="10%"><span class="tableTop"></span>姓名</th>
								<th scope="col" width="10%"><span class="tableTop"></span>类别</th>
								<th scope="col" width="18%"><span class="tableTop"></span>公司名称</th>
								<th scope="col" width="13%"><span class="tableTop"></span>公司地址</th>
								<th scope="col" width="10%"><span class="tableTop"></span>办公电话</th>
								<th scope="col" width="10%"><span class="tableTop"></span>手机</th>
								<th scope="col" width="10%"><span class="tableTop"></span>传真</th>
								<s:if test=" 1 == #request.searchType || 2 == #request.searchType ">
									<th scope="col" width="24%"><span class="tableTop"></span>操作</th>
								</s:if>
							</tr>

							<s:iterator value="pageBean.list">
								<tr class="oTr">
									<td align="center">
										<input type="checkbox" name="clientId" value="<s:property value='id'/>">
									</td>
									<td><s:property value="name" /></td>
									<td><s:property value="typeStr" /></td>
									<td><s:property value="companyName" /></td>
									<td><s:property value="companyAddr" /></td>
									<td><s:property value="officePhone" /></td>
									<td><s:property value="mobilePhone" /></td>
									<td><s:property value="fax" /></td>
									<s:if test=" 1 == #request.searchType || 2 == #request.searchType ">
										<td>
											<a href="trade/forAdd.action?from=<%=from%>&clientId=<s:property value='id'/>">增加往来</a>
											<a href="trade/listAllOfSB.action?clientId=<s:property value='id'/>&from=<%=from%>">查看往来</a>
										</td>
									</s:if>
								</tr>
							</s:iterator>

						</table>
						<!-- 分页 -->
						<div id="tableDown">
							<%
								//所有客户列表不显示增加、修改、删除按钮
								//if(!"0".equals(searchType)) {
							%>
							<input type="button" id="addBtn" class="btnOpe" value="增加" />
							<input type="button" id="updateBtn" class="btnOpe" value="修改" />
							<input type="button" id="deleteBtn" class="btnOpe" value="删除" />
							<%
								//}
							%>
							
							<span class="tableD01">共<span id="tableD04"><s:property value="pageBean.allRow" /></span>条记录</span>
							<span class="tableD01">共<span id="tableD04"><s:property value="pageBean.totalPage" /></span>页</span>
							<s:if test="#request.pageBean.currentPage != #request.pageBean.totalPage">
								<a href="client/listAll.action?page=<s:property value='%{pageBean.currentPage+1}'/>&searchType=<%=searchType%>&searchName=<%=searchName%>" class="tableD01">下页</a>
							</s:if>
							<s:if test="#request.pageBean.currentPage != 1">
								<a href="client/listAll.action?page=<s:property value='%{pageBean.currentPage-1}'/>&searchType=<%=searchType%>&searchName=<%=searchName%>" class="tableD01">上页</a>
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
