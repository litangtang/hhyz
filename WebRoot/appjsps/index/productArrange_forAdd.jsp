<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<!-- 页面所有路径以项目路径为基础即以WebRoot为基础 -->
		<title>订单发货安排</title>

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
			$("table.maintable tr:nth-child(odd)").addClass("ymaintext");
			$("table.maintable tr:nth-child(even)").addClass("zmaintext");
			$("table.maintable tr:not(:last) td:first-child").attr("align","right");
			
			$('#recordDate').datepicker({
				changeMonth: true,
				changeYear: true
			});
			
			$('#arriveDate').datepicker({
				changeMonth: true,
				changeYear: true
			});

			//送货金额根据数量和单价自动算出，即送货金额=单价*数量
			$("#amount").focus(function(){
				var count = $("#count").val();
				var price = $("#price").val();
				if(null != count && "" != count && null != price && "" != price) {
					var temp = price*count;//计算送货金额=单价*数量
					//var tempNum = new Number(temp);
					$("#amount").attr("value",numFormat(temp,2));//格式化数据，保留两位小数
				}
			});

			$("#submitBtn").click(function() {
				
				var recordDate = document.getElementById("recordDate");
				if(!validateNull(recordDate,'日期不能为空')) {
					return false;
				}

				document.forms[0].action = "productArrange/add.action";
				document.forms[0].submit();
			});

			$("#backBtn").click(function() {
				document.forms[0].action = "productArrange/listAll.action";
				document.forms[0].submit();
			});
			
		});
	</script>
	</head>
	<body>
		<center>

			<div>
				<form action="" method="post">
				<input type="hidden" name="busiTypeHid" id="busiTypeHid" value="${requestScope.busiType}"/>
					<!-- 第一行：top -->
					<table width="100%" border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="25" height="50" background="img/main01.png">
								&nbsp;
							</td>
							<td background="img/main02.png">
								<span class="mainAction">当前路径：添加发货安排</span>
							</td>
							<td width="29" background="img/main03.png">
								&nbsp;
							</td>
						</tr>
						<!-- 第二行：content -->
						<tr>
							<td background="img/main04.png">
								&nbsp;
							</td>
							<td align="center" valign="top" bgcolor="#FEFCFC" height="500">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="maintop">
									<tr>
										<td height="30">
											<img src="img/titleR1.png" width="28" height="28"
												class="mainmin">
											<span class="maintoptext">添加信息</span><span
												class="maintopbutt"></span>
										</td>
									</tr>
								</table>
								<table width="60%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="5"></td>
									</tr>
								</table>
								<table class="maintable" width="600" border="0" align="center" cellpadding="0" cellspacing="0">
										
										<tr>
											<td>
												日期：
											</td>
											<td>
												<input type="text" id="recordDate" name="productArrange.recordDate"
													class="ui-widget-content ui-corner-all" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												订货单位：
											</td>
											<td>
												<input type="text" id="clientName" name="productArrange.clientName" />&nbsp;
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												件数：
											</td>
											<td>
												<input type="text" id="packages" name="productArrange.packages" />&nbsp;(件)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												吨数：
											</td>
											<td>
												<input type="text" id="count" name="productArrange.count" />&nbsp;(吨)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												单价：
											</td>
											<td>
												<input type="text" id="price" name="productArrange.price" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												金额：
											</td>
											<td>
												<input type="text" id="amount" name="productArrange.amount" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												到货时间：
											</td>
											<td>
												<input type="text" id="arriveDate" name="productArrange.arriveDate"
													class="ui-widget-content ui-corner-all" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												是否发货：
											</td>
											<td>
												<select name="productArrange.isSend" id="isSend">
													<option value="0">未发货</option>
													<option value="1">已发货</option>
												</select>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												备注：
											</td>
											<td>
												<input type="text" id="remark" name="productArrange.remark" />&nbsp;
											</td>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
											<td colspan="2" align="center">
												<input type="button" value="增加" id="submitBtn" />
												<input type="button" value="返回" id="backBtn" />
											</td>
										</tr>

								</table>
							</td>
							<td background="img/main05.png">&nbsp;</td>
						</tr>
					</table>
					<s:token />
				</form>
			</div>
		</center>
	</body>
</html>
