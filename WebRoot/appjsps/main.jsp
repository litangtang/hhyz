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
		<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="25" height="50" background="img/main01.png">
					&nbsp;
				</td>
				<td background="img/main02.png">
					<span id="maintop"></span><span class="mainAction">当前路径：</span>
				</td>
				<td width="29" background="img/main03.png">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td background="img/main04.png">
					&nbsp;
				</td>
				<td bgcolor="#FEFCFC">
					&nbsp;
				</td>
				<td background="img/main05.png">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td height="28" background="img/main06.png">
					&nbsp;
				</td>
				<td background="img/main07.png">
					&nbsp;
				</td>
				<td background="img/main08.png">
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>