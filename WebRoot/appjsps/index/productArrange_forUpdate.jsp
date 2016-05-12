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
		<title>修改发货安排</title>

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

				document.forms[0].action = "productArrange/update.action";
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
					<!-- 第一行：top -->
					<table width="100%" border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="25" height="50" background="img/main01.png">
								&nbsp;
							</td>
							<td background="img/main02.png">
								<span class="mainAction">当前路径：修改发货安排</span>
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
											<span class="maintoptext">修改</span><span
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
										<tr style="display: none">
											<td>
												id：
											</td>
											<td>
												<input type="text" id="pid" name="productArrange.id" value="<s:property value='productArrange.id'/>" />&nbsp;
												
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr style="display: none">
											<td>
												年：
											</td>
											<td>
												<input type="text" id="year" name="productArrange.year" value="<s:property value='productArrange.year'/>" />&nbsp;
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												月：
											</td>
											<td>
												<input type="text" id="month" name="productArrange.month" value="<s:property value='productArrange.month'/>" />&nbsp;
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												日：
											</td>
											<td>
												<input type="text" id="day" name="productArrange.day" value="<s:property value='productArrange.day'/>" />&nbsp;
												
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												订货单位：
											</td>
											<td>
												<input type="text" id="clientName" name="productArrange.clientName" value="<s:property value='productArrange.clientName'/>" />&nbsp;
												
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												件数：
											</td>
											<td>
												<input type="text" id="packages" name="productArrange.packages" value="<s:property value='productArrange.packages'/>"/>&nbsp;(件)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												吨数：
											</td>
											<td>
												<input type="text" id="count" name="productArrange.count" value="<s:property value='productArrange.count'/>"/>&nbsp;(吨)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												单价：
											</td>
											<td>
												<input type="text" id="price" name="productArrange.price" value="<s:property value='productArrange.price'/>"/>&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												金额：
											</td>
											<td>
												<input type="text" id="amount" name="productArrange.amount" value="<s:property value='productArrange.amount'/>"/>&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												到货时间：
											</td>
											<td>
												<input type="text" id="arriveDate" name="productArrange.arriveDate" value="<s:property value='productArrange.arriveDate'/>" class="ui-widget-content ui-corner-all"/>&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												是否发货：
											</td>
											<td>
												<select name="productArrange.isSend" id="isSend">
													<option value="0" ${productArrange.isSend == 0?"selected":""}>未发货</option>
													<option value="1" ${productArrange.isSend == 1?"selected":""}>已发货</option>
												</select>
											</td>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
											<td>
												备注：
											</td>
											<td>
												<input type="text" id="remark" name="productArrange.remark" value="<s:property value='productArrange.remark'/>"/>&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
											<td colspan="2" align="center">
												<input type="button" value="修改" id="submitBtn" />
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
