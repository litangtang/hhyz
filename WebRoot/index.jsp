<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	
	
	<link type="text/css" href="../styles/ui-lightness/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="../jquery-1.4.2.js"></script>
	<script type="text/javascript" src="../js/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/ui/jquery.ui.datepicker.js"></script>
	
	 <script type="text/javascript">
		$(function(){
			$("#datepicker").datepicker({
				showOtherMonths:true,
				selectOtherMonths:true,
				numberOfMonths:1,
				showWeek:true,
				changYear:true,
				changMonth:true,
				yearRange:"c-20:c+20",
				showAnim:"slideDown"
			});
		})
	</script>
	
	<link rel="stylesheet" type="text/css" href="../styles/themes/ui-lightness/jquery.ui.datepicker.css">
	<link rel="stylesheet" type="text/css" href="../styles/themes/ui-lightness/jquery.ui.core.css">
	<style type="text/css">
		body,.ui-datepicker,input {font-size:12px;}
		.ui-datepicker-month{width:4em !import;}
		.ui-datepicker-year{width:5em !import;}
	</style>
  </head>
 
  <body>
   <form action="actions/Trade_add" method="post">
   			<input type="text" id="datepicker" class="ui-widget-content ui-corner-all"/>
   			<br />
   			<input type="submit" value="增加" />
   		</form>
  </body>
</html>
