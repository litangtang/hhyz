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
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="60" align="left" valign="top" background="img/top.jpg">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="380" height="60" background="img/logo1.jpg">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
							<td width="126">
								<a href="appjsps/index/main_index.jsp" target="mainFrame" class="toplink">首页</a>
							</td>
							<td width="126">
								<a href="appjsps/client/main_client.jsp" target="mainFrame" class="toplink">客户</a>
							</td>
							<td width="126">
								<a href="appjsps/trade/main_trade.jsp" target="mainFrame" class="toplink">往来</a>
							</td>
							<td width="126">
								<a href="appjsps/cost/main_cost.jsp" class="toplink" target="mainFrame" class="toplink">核算</a>
							</td>
							<td width="126">
								<a href="appjsps/sys/main_sys.jsp" class="toplink" target="mainFrame">系统管理</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="36" background="img/topm.jpg">
					<span class="min">欢迎你，系统管理员</span><a href="#" class="minR">修改密码</a><a
						href="#" class="minR">注销系统</a>
				</td>
			</tr>
		</table>
	</body>
</html>