<%@ page language="java" pageEncoding="utf-8"%>
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

		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="stylesheets/style.css" rel="stylesheet" type="text/css" />
		
		<!--[if IE]><link href="stylesheets/style-ie.css" type="text/css" media="screen" rel="stylesheet" /><![endif]-->
	</head>

	<frameset rows="102,*,43" cols="*" frameborder="no" border="0" framespacing="0">
		<frame src="appjsps/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
		<frame src="" name="mainFrame" scrolling="No" noresize="noresize" id="mainFrame" title="mainFrame" />
		<!--  
		<frameset cols="186,*" frameborder="no" border="0" framespacing="0">
			<frame src="appjsps/menu.jsp" name="leftFrame" noresize="noresize" id="leftFrame" title="leftFrame" />
			<frame src="appjsps/main.jsp" name="mainFrame" id="mainFrame" title="mainFrame" />
		</frameset>
		-->
		 <frame src="appjsps/foot.jsp" name="bottomFrame" noresize="noresize" id="footFrame" title="footFrame" />
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>