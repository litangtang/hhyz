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
		<title>开票记录</title>

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
			

			//送货金额根据数量和单价自动算出，即送货金额=单价*数量
			$("#origAmt").focus(function(){
				var count = $("#count").val();
				var price = $("#price").val();
				if(null != count && "" != count && null != price && "" != price) {
					var temp = price*count;//计算送货金额=单价*数量
					//var tempNum = new Number(temp);
					$("#origAmt").attr("value",numFormat(temp,2));//格式化数据，保留两位小数
				}
			});
			
			//不含税金额 = 原始金额 / 1.17
			$("#noTaxAmt").focus(function(){
				var origAmt = $("#origAmt").val();
				if(null != origAmt && "" != origAmt) {
					var temp = origAmt / 1.17;
					$("#noTaxAmt").attr("value",numFormat(temp,2));//格式化数据，保留两位小数
				}
			});
			
			//销项税 = 不含税金额 * 0.17
			$("#sellTax").focus(function(){
				var noTaxAmt = $("#noTaxAmt").val();
				if(null != noTaxAmt && "" != noTaxAmt) {
					var temp = noTaxAmt * 0.17;
					$("#sellTax").attr("value",numFormat(temp,2));//格式化数据，保留两位小数
				}
			});

			$("#submitBtn").click(function() {
				
				var recordDate = document.getElementById("recordDate");
				if(!validateNull(recordDate,'日期不能为空')) {
					return false;
				}

				document.forms[0].action = "invoice/add.action";
				document.forms[0].submit();
			});

			$("#backBtn").click(function() {
				document.forms[0].action = "invoice/listAll.action";
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
								<span class="mainAction">当前路径：添加开票记录</span>
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
												<input type="text" id="recordDate" name="invoice.recordDate"
													class="ui-widget-content ui-corner-all" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												开票单位：
											</td>
											<td>
												<input type="text" id="invoiceComp" name="invoice.invoiceComp" />&nbsp;
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												数量：
											</td>
											<td>
												<input type="text" id="count" name="invoice.count" />&nbsp;(吨)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												单价：
											</td>
											<td>
												<input type="text" id="price" name="invoice.price" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												金额：
											</td>
											<td>
												<input type="text" id="origAmt" name="invoice.origAmt" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												不含税金额：
											</td>
											<td>
												<input type="text" id="noTaxAmt" name="invoice.noTaxAmt" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												销项税：
											</td>
											<td>
												<input type="text" id="sellTax" name="invoice.sellTax" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												备注：
											</td>
											<td>
												<input type="text" id="remark" name="invoice.remark" />&nbsp;
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
