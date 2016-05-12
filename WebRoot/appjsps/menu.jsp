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
					<span id="menutop"></span><span class="menuAction">功能菜单</span>
				</td>
			</tr>
			<tr>
				<td height="40" align="center" valign="middle">
					<a href="client/listAll.action" class="menulink" target="mainFrame">客户</a>
				</td>
			</tr>
			<tr>
				<td height="40">
					<a href="#" class="menulink">模块一功能二</a>
				</td>
			</tr>
			<tr>
				<td height="40">
					<a href="#" class="menulink">模块一功能三</a>
				</td>
			</tr>
			<tr>
				<td height="40">
					<a href="#" class="menulink">模块一功能四</a>
				</td>
			</tr>
		</table>
	</body>
</html>