<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
		<base href="<%=basePath%>">
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="stylesheets/style.css" rel="stylesheet" type="text/css" />
		<link href="css/elson.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonLink.css" rel="stylesheet" type="text/css" />
		<!--[if IE]><link href="stylesheets/style-ie.css" type="text/css" media="screen" rel="stylesheet" /><![endif]-->
	</head>
	<body>
		<table width="186" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="30" align="left" valign="middle">
					<span id="menutop"></span><span class="menuAction">销售记录</span>
				</td>
			</tr>
			<tr>
				<td height="40" valign="middle">
					<a href="productSell/listAll.action?busiType=1" class="menulink" target="rightFrame"><font color="red">精炼一级油</font></a>
				</td>
			</tr>
			<tr>
				<td height="40" valign="middle">
					<a href="productSell/listAll.action?busiType=2" class="menulink" target="rightFrame"><font color="red">国标二级油</font></a>
				</td>
			</tr>
			<tr>
				<td height="40" valign="middle">
					<a href="productSell/listAll.action?busiType=3" class="menulink" target="rightFrame"><font color="red">蓖麻饼</font></a>
				</td>
			</tr>
			
			<!-- 发货安排   -->
			<tr>
				<td height="30" align="left" valign="middle">
					<span id="menutop"></span><span class="menuAction">发货安排</span>
				</td>
			</tr>
			<tr>
				<td height="40" valign="middle">
					<a href="productArrange/listAll.action" class="menulink" target="rightFrame"><font color="red">订单发货安排</font></a>
				</td>
			</tr>
			
			<!-- 开票记录   -->
			<tr>
				<td height="30" align="left" valign="middle">
					<span id="menutop"></span><span class="menuAction">开票记录</span>
				</td>
			</tr>
			<tr>
				<td height="40" valign="middle">
					<a href="invoice/listAll.action" class="menulink" target="rightFrame"><font color="red">开票记录</font></a>
				</td>
			</tr>
		</table>
	</body>
</html>