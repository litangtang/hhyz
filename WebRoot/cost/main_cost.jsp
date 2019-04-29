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

	<frameset cols="186,*" frameborder="no" border="0" framespacing="0" name="pageframe">
		<frame src="appjsps/cost/menu_cost.jsp" name="leftFrame" noresize="noresize"
			id="leftFrame" title="leftFrame" />
		<!--  <frame src="appjsps/cost/line_frame.jsp" scolling="no" noresize="noresize" name="lineFrame" /> -->
		<frame src="appjsps/cost/cost_index.jsp" name="rightFrame" id="rightFrame"
			title="rightFrame" />
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>